package br.com.s3.service;

import br.com.s3.controller.dto.response.attachment.AttachmentUploadResponseDto;
import org.springframework.web.multipart.MultipartFile;
import br.com.s3.controller.dto.request.attachment.AttachmentUploadRequestDto;

import java.util.List;

public interface AttachmentService {

    AttachmentUploadResponseDto uploadFiles(AttachmentUploadRequestDto requestDto, List<MultipartFile> files);
}
