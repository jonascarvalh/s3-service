package br.com.s3.service;

import br.com.s3.controller.dto.request.attachment.AttachmentUploadRequestDto;
import br.com.s3.controller.dto.response.attachment.AttachmentUploadResponseDto;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.beans.factory.annotation.Value;
import br.com.s3.adapter.S3ServiceAdapter;

import java.util.List;
import java.util.UUID;

@Service
public class AttachmentServiceImpl implements AttachmentService {

    private final S3ServiceAdapter s3ServiceAdapter;

    @Value("${s3.bucket}")
    private String bucket;

    public AttachmentServiceImpl(S3ServiceAdapter s3ServiceAdapter) {
        this.s3ServiceAdapter = s3ServiceAdapter;
    }

    @Override
    public AttachmentUploadResponseDto uploadFiles(AttachmentUploadRequestDto requestDto, List<MultipartFile> files) {
        if (files == null || files.isEmpty()) {
            return AttachmentUploadResponseDto.builder().message("Nenhum arquivo enviado").build();
        }

        String prefix = requestDto != null ? requestDto.getPrefix() : null;
        if (prefix == null) {
            prefix = "";
        }
        if (prefix.startsWith("/")) {
            prefix = prefix.substring(1);
        }
        if (!prefix.isEmpty() && !prefix.endsWith("/")) {
            prefix = prefix + "/";
        }

        for (MultipartFile file : files) {
            String original = file.getOriginalFilename() != null ? file.getOriginalFilename() : "file";
            String key = prefix + UUID.randomUUID() + "_" + original;
            s3ServiceAdapter.uploadFile(bucket, key, file);
        }

        return AttachmentUploadResponseDto.builder().message("Arquivos enviados com sucesso").build();
    }
}
