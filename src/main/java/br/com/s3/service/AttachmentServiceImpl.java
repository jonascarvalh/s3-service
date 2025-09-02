package br.com.s3.service;

import br.com.s3.adapter.S3ServiceAdapter;
import br.com.s3.controller.dto.request.attachment.AttachmentUploadRequestDto;
import br.com.s3.controller.dto.response.attachment.AttachmentUploadResponseDto;
import br.com.s3.controller.dto.request.attachment.AttachmentBatchUploadRequestDto;
import br.com.s3.controller.dto.response.attachment.AttachmentBatchPresignedUrlResponseDto;
import br.com.s3.controller.dto.response.attachment.AttachmentUploadedResponseDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

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

		String url = s3ServiceAdapter.generatePutPresignedUrl(bucket, key, requestDto.getContentType(),
				Duration.ofSeconds(pressignExpiresSeconds));
            
		return generateAttachmentUploadResponseDto(url, key);
	}

	@Override
	public AttachmentBatchPresignedUrlResponseDto uploadFiles(AttachmentBatchUploadRequestDto requestDto) {
		validateBatchRequest(requestDto);

		List<AttachmentUploadedResponseDto> fileResponses = requestDto.getFiles().stream()
				.map(this::generateFileResponse)
				.collect(Collectors.toList());

		return AttachmentBatchPresignedUrlResponseDto.builder()
				.files(fileResponses)
				.expiresInSeconds(pressignExpiresSeconds)
				.bucket(bucket)
				.build();
	}

	private AttachmentUploadedResponseDto generateFileResponse(AttachmentUploadRequestDto fileRequest) {
		String prefix = formatPrefix(fileRequest.getPrefix());
		String key = prefix + fileRequest.getKey();
		String contentType = fileRequest.getContentType() != null ? fileRequest.getContentType() : "application/octet-stream";

		String url = s3ServiceAdapter.generatePutPresignedUrl(bucket, key, contentType,
				Duration.ofSeconds(pressignExpiresSeconds));

		return AttachmentUploadedResponseDto.builder()
				.url(url)
				.key(key)
				.prefix(prefix)
				.contentType(contentType)
				.build();
	}

	private void validateBatchRequest(AttachmentBatchUploadRequestDto requestDto) {
		if (requestDto.getFiles() == null || requestDto.getFiles().isEmpty()) {
			throw new IllegalArgumentException("A lista de arquivos não pode estar vazia.");
		}

		if (requestDto.getFiles().size() > 10) {
			throw new IllegalArgumentException("Máximo de 10 arquivos por lote.");
		}

		if (requestDto.getFiles().stream()
				.anyMatch(fileRequest -> 
					fileRequest.getPrefix() == null || fileRequest.getPrefix().trim().isEmpty() ||
					fileRequest.getKey() == null || fileRequest.getKey().trim().isEmpty())) {
			throw new IllegalArgumentException("O prefixo e a chave não podem ser nulos ou vazios.");
		}
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
