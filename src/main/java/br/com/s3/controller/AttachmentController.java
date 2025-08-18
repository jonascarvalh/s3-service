package br.com.s3.controller;

import br.com.s3.service.AttachmentService;
import br.com.s3.controller.dto.response.attachment.AttachmentUploadResponseDto;
import br.com.s3.controller.dto.request.attachment.AttachmentUploadRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("api/v1/attachment")
@RequiredArgsConstructor
public class AttachmentController {

    private final AttachmentService attachmentService;

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<AttachmentUploadResponseDto> upload(
            @RequestPart("request") AttachmentUploadRequestDto request,
            @RequestPart("file") List<MultipartFile> files
    ) {
        AttachmentUploadResponseDto response = attachmentService.uploadFiles(request, files);
        return ResponseEntity.ok(response);
    }
}
