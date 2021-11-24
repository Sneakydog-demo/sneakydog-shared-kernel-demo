package dog.sneaky.demo.sharedkernel.oss;

import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.InputStream;


@RequiredArgsConstructor
@Slf4j
@Configuration
@ConfigurationProperties("application.minio")
@ConditionalOnProperty(prefix = "application.minio", name = "enable", havingValue = "true")
@ConditionalOnClass(MinioClient.class)
class OssConfiguration {
    private final String endpoint;
    private final String accessKeyId;
    private final String accessKeySecret;

    @SneakyThrows
    @Bean
    MinioClient minioClient() {
        final MinioClient.Builder builder = MinioClient.builder();
        return builder.endpoint(endpoint).credentials(accessKeyId, accessKeySecret).build();
    }

    @Bean
    public OssClient ossClient(MinioClient minioClient, @Value("application.minio.bucketName") String bucketName) {
        return new OssClient() {
            @Override
            public void putObject(InputStream inputStream, String objectName) {
                final PutObjectArgs.Builder builder = PutObjectArgs.builder();
                builder.bucket(bucketName).object(objectName).stream(inputStream, 0, -1);
                final PutObjectArgs putObjectArgs = builder.build();
                try {
                    minioClient.putObject(putObjectArgs);
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new RuntimeException(e);
                }
            }
        };
    }
}
