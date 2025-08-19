package br.com.s3.controller.dto.request.attachment;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@Builder
public class AttachmentUploadRequestDto {

	@NotBlank(message = "O prefixo não pode ser nulo ou vazio.")
	private String prefix;

	@NotBlank(message = "A chave não pode ser nula ou vazia.")
	private String key;

	private String contentType;
}


