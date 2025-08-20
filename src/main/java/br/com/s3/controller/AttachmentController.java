package br.com.s3.controller;

import br.com.s3.controller.dto.request.attachment.AttachmentUploadRequestDto;
import br.com.s3.controller.dto.response.attachment.AttachmentUploadResponseDto;
import br.com.s3.controller.dto.request.attachment.AttachmentBatchUploadRequestDto;
import br.com.s3.controller.dto.response.attachment.AttachmentBatchPresignedUrlResponseDto;
import br.com.s3.service.AttachmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.validation.Valid;

@RestController
@RequestMapping("api/v1/attachment")
@RequiredArgsConstructor
@Validated
public class AttachmentController {

    private final AttachmentService attachmentService;

	@PostMapping(value = "/upload", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<AttachmentUploadResponseDto> uploadFile(@RequestBody @Valid AttachmentUploadRequestDto request) {
		AttachmentUploadResponseDto response = attachmentService.uploadFile(request);
		return ResponseEntity.ok(response);
	}

	@PostMapping(value = "/upload-files", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<AttachmentBatchPresignedUrlResponseDto> uploadFiles(@RequestBody @Valid AttachmentBatchUploadRequestDto request) {
		AttachmentBatchPresignedUrlResponseDto response = attachmentService.uploadFiles(request);
		return ResponseEntity.ok(response);
	}
}
