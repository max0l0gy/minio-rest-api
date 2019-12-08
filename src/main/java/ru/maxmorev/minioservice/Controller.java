package ru.maxmorev.minioservice;

import io.minio.MinioClient;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import ru.maxmorev.minioservice.domain.FileUploadResponse;

import java.io.InputStream;

@Slf4j
@RestController
@RequestMapping("/io")
public class Controller {

    @Value("${img.endpoint}")
    private String serviceEndpoint;
    @Value("${img.accessKey}")
    private String accessKey;

    private final MinioClient minioClient;

    public Controller(@Autowired MinioClient mc) {
        this.minioClient = mc;
    }

    @SneakyThrows
    @RequestMapping(path = "/upload/jpg/{bucket}/", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public FileUploadResponse uploadFile(
            @PathVariable(name = "bucket") String bucket,
            @RequestParam(value = "key", required = true) String key,
            @RequestParam("file") MultipartFile file) {
        log.info("KEY: " + key);
        if (!accessKey.equals(key)) {
            throw new IllegalAccessException("Access key error");
        }
        if (minioClient.bucketExists(bucket)) {
            log.info("Bucket already exists.");
        } else {
            log.info("Create {} bucket", bucket);
            minioClient.makeBucket(bucket);
        }

        InputStream is = file.getInputStream();
        log.info("is.available() {}", is.available());
        minioClient
                .putObject(
                        bucket,
                        file.getOriginalFilename(),
                        is,
                        (long) is.available(),
                        null,
                        null,
                        "image/jpeg"
                );
        log.info("{} is successfully uploaded", file.getOriginalFilename());
        return new FileUploadResponse(FileUploadResponse.Status.OK.name(),
                serviceEndpoint + "/io/jpg/" + bucket + "/" + file.getOriginalFilename(),
                null);
    }

    @SneakyThrows
    @RequestMapping(path = "/jpg/{bucket}/{fileName}", method = RequestMethod.GET, produces = MediaType.IMAGE_JPEG_VALUE)
    @ResponseBody
    public byte[] getJpeg(@PathVariable(name = "bucket") String bucket,
                          @PathVariable(name = "fileName") String fileName) {
        InputStream is = minioClient.getObject(bucket, fileName);
        return is.readAllBytes();
    }


}
