package br.com.s3.controller.dto.response.attachment;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@Builder
public class AttachmentBatchPresignedUrlResponseDto {

	private List<AttachmentUploadedResponseDto> files;
	private Long expiresInSeconds;
	private String bucket;
}
