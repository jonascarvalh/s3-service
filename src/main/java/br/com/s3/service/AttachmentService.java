package br.com.s3.service;

import br.com.s3.controller.dto.response.attachment.AttachmentUploadResponseDto;
import br.com.s3.controller.dto.request.attachment.AttachmentUploadRequestDto;
import br.com.s3.controller.dto.response.attachment.AttachmentBatchPresignedUrlResponseDto;
import br.com.s3.controller.dto.request.attachment.AttachmentBatchUploadRequestDto;

public interface AttachmentService {

	AttachmentUploadResponseDto uploadFile(AttachmentUploadRequestDto requestDto);

	AttachmentBatchPresignedUrlResponseDto uploadFiles(AttachmentBatchUploadRequestDto requestDto);
}
