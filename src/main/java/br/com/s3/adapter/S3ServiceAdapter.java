package br.com.s3.adapter;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.ObjectCannedACL;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;

@Component
public class S3ServiceAdapter {

    private final S3Client s3Client;


    // Inicializa o cliente S3 com as configurações padrão
    // As credenciais são obtidas automaticamente do ambiente (variáveis, IAM role, etc)
    // TODO: Definir o region utilizando variável de ambiente
    public S3ServiceAdapter() {
        this.s3Client = S3Client.builder()
                .region(Region.of("sa-east-1"))
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
        try {
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucket)
                    .key(key)
                    .contentType(file.getContentType())
                    .acl(ObjectCannedACL.BUCKET_OWNER_FULL_CONTROL)
                    .build();

            s3Client.putObject(putObjectRequest,
                    RequestBody.fromInputStream(file.getInputStream(), file.getSize()));
        } catch (IOException e) {
            throw new RuntimeException("Erro ao fazer upload do arquivo: " + e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException("Erro ao comunicar com o S3: " + e.getMessage());
        }
    }
}
