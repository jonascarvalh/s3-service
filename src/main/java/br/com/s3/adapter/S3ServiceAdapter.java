package br.com.s3.adapter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.ObjectCannedACL;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;
import software.amazon.awssdk.services.s3.presigner.model.PresignedGetObjectRequest;
import software.amazon.awssdk.services.s3.presigner.model.PutObjectPresignRequest;
import software.amazon.awssdk.services.s3.presigner.model.PresignedPutObjectRequest;

import java.io.IOException;
import java.time.Duration;

@Component
public class S3ServiceAdapter {

	private final S3Client s3Client;
	private final S3Presigner s3Presigner;

	public S3ServiceAdapter(@Value("${s3.region}") String region) {
		Region awsRegion = Region.of(region);

		this.s3Client = S3Client.builder()
				.region(awsRegion)
				.credentialsProvider(DefaultCredentialsProvider.create())
				.build();

		this.s3Presigner = S3Presigner.builder()
				.region(awsRegion)
				.credentialsProvider(DefaultCredentialsProvider.create())
				.build();
	}

    /**
     * Realiza o upload de um arquivo para o bucket S3 especificado.
     *
     * @param bucket Nome do bucket no S3
     * @param key Caminho/nome do arquivo no S3 (ex: uploads/123/documento.pdf)
     * @param file Arquivo a ser enviado
     * @throws RuntimeException em caso de erro no upload
     */
    public void uploadFile(String bucket, String key, MultipartFile file) {
        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucket)
                .key(key)
                .contentType(file.getContentType())
                .acl(ObjectCannedACL.BUCKET_OWNER_FULL_CONTROL)
                .build();

        try {
            s3Client.putObject(putObjectRequest, RequestBody.fromInputStream(file.getInputStream(), file.getSize()));
        } catch (IOException e) {
            throw new RuntimeException("Erro ao fazer upload do arquivo.");
        } catch (Exception e) {
            throw new RuntimeException("Falha na comunicação com o serviço S3.");
        }
    }

    /**
     * Gera uma URL pré-assinada (presigned URL) para permitir o acesso de leitura
     * temporário a um objeto no S3, sem a necessidade de credenciais da AWS.
     *
     * @param bucket Nome do bucket onde o objeto está localizado.
     * @param key Caminho/nome do objeto no S3.
     * @param expirationInSeconds Duração, em segundos, da validade da URL.
     * @return A URL pré-assinada como uma String.
     * @throws RuntimeException em caso de erro na geração da URL.
     */
    public String generateGetPresignedUrl(String bucket, String key, Integer expirationInSeconds) {
        GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                .bucket(bucket)
                .key(key)
                .build();

        GetObjectPresignRequest presignRequest = GetObjectPresignRequest.builder()
                .signatureDuration(Duration.ofSeconds(expirationInSeconds))
                .getObjectRequest(getObjectRequest)
                .build();

        try {
            PresignedGetObjectRequest presigned = s3Presigner.presignGetObject(presignRequest);
            return presigned.url().toString();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao gerar URL pré-assinada: " + e.getMessage());
        }
    }

    /**
     * Remove um arquivo do bucket S3 especificado.
     *
     * @param bucket Nome do bucket no S3
     * @param key Caminho/nome do arquivo no S3 (ex: uploads/123/documento.pdf)
     * @throws RuntimeException em caso de erro na exclusão do arquivo
     */
    public void deleteFile(String bucket, String key) {
        DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
                .bucket(bucket)
                .key(key)
                .build();

        try {
            s3Client.deleteObject(deleteObjectRequest);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao excluir arquivo do S3: " + e.getMessage());
        }
    }
}
