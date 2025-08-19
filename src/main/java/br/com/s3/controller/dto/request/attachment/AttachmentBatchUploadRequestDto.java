package br.com.s3.controller.dto.request.attachment;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
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
public class AttachmentBatchUploadRequestDto {

	@NotEmpty(message = "A lista de arquivos não pode estar vazia.")
	@Size(max = 10, message = "Máximo de 10 arquivos por lote.")
	@Valid
	private List<AttachmentUploadRequestDto> files;
}
