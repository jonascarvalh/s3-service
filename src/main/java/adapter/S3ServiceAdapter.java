package adapter;

import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.ObjectCannedACL;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;

public class S3ServiceAdapter {

    private final S3Client s3Client;


    public S3ServiceAdapter(S3Client s3Client) {
        this.s3Client = S3Client.builder()
                .region(Region.US_EAST_1)
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
