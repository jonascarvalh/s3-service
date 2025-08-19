package br.com.s3.service;

import br.com.s3.adapter.S3ServiceAdapter;
import br.com.s3.controller.dto.request.attachment.AttachmentUploadRequestDto;
import br.com.s3.controller.dto.response.attachment.AttachmentUploadResponseDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
public class AttachmentServiceImpl implements AttachmentService {

    private final S3ServiceAdapter s3ServiceAdapter;

    @Value("${s3.bucket}")
    private String bucket;

    @Value("${s3.presign.expires-seconds}")
    private Long pressignExpiresSeconds;

    public AttachmentServiceImpl(S3ServiceAdapter s3ServiceAdapter) {
        this.s3ServiceAdapter = s3ServiceAdapter;
    }

	@Override
	public AttachmentUploadResponseDto uploadFile(AttachmentUploadRequestDto requestDto) {
		validateToUpload(requestDto);

		String prefix = formatPrefix(requestDto.getPrefix());
		String key = prefix + requestDto.getKey();

		String url = s3ServiceAdapter.generatePresignedPutUrl(bucket, key, requestDto.getContentType(),
				Duration.ofSeconds(pressignExpiresSeconds));
            
		return generateAttachmentUploadResponseDto(url, key);
	}

	void validateToUpload(AttachmentUploadRequestDto requestDto) {
		if (requestDto.getPrefix() == null || requestDto.getPrefix().isEmpty()) {
			throw new IllegalArgumentException("O prefixo nao pode ser nulo ou vazio.");
		}

		if (requestDto.getKey() == null || requestDto.getKey().isEmpty()) {
			throw new IllegalArgumentException("O key nao pode ser nulo ou vazio.");
		}

		if (requestDto.getContentType() == null || requestDto.getContentType().isEmpty()) {
			throw new IllegalArgumentException("O content type nao pode ser nulo ou vazio.");
		}
	}

	private static String formatPrefix(String prefix) {
		if (prefix.startsWith("/")) {
			prefix = prefix.substring(1);
		}
		if (!prefix.endsWith("/")) {
			prefix = prefix + "/";
		}
		return prefix;
	}

	private AttachmentUploadResponseDto generateAttachmentUploadResponseDto(String url, String key) {
		return AttachmentUploadResponseDto.builder()
				.url(url)
				.bucket(bucket)
				.key(key)
				.expiresInSeconds(pressignExpiresSeconds)
				.build();
	}
}
