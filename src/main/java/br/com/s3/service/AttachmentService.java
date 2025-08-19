package br.com.s3.service;

import br.com.s3.controller.dto.response.attachment.AttachmentUploadResponseDto;
import br.com.s3.controller.dto.request.attachment.AttachmentUploadRequestDto;

public interface AttachmentService {

	AttachmentUploadResponseDto uploadFile(AttachmentUploadRequestDto requestDto);
}
