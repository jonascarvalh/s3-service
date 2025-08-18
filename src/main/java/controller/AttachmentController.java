package controller;

import adapter.S3ServiceAdapter;
import controller.dto.settings.MessageResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("api/v1/attachment")
@RequiredArgsConstructor
public class AttachmentController {

    private final S3ServiceAdapter s3ServiceAdapter;

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<MessageResponseDto> upload(
            @RequestParam("file") List<MultipartFile> files
    ) {

    }
}
