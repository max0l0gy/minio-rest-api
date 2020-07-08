package ru.maxmorev.minioservice;

import io.minio.MinioClient;
import io.minio.errors.ErrorResponseException;
import io.minio.errors.InsufficientDataException;
import io.minio.errors.InternalException;
import io.minio.errors.InvalidArgumentException;
import io.minio.errors.InvalidBucketNameException;
import io.minio.errors.InvalidResponseException;
import io.minio.errors.NoResponseException;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.tika.detect.Detector;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.xmlpull.v1.XmlPullParserException;
import ru.maxmorev.minioservice.domain.FileUploadResponse;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Slf4j
@RestController
public class Controller {

    @Value("${file.endpoint}")
    private String serviceEndpoint;
    @Value("${file.accessKey}")
    private String accessKey;

    private final MinioClient minioClient;

    public Controller(@Autowired MinioClient mc) {
        this.minioClient = mc;
    }

    @SneakyThrows
    @RequestMapping(path = "/{bucket}/", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
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
        // only by file name
        String mimeType = getContentType(is, file.getOriginalFilename());
        log.info("contentType is {}", mimeType);
        is = file.getInputStream();
        log.info("is.available() {}", is.available());
        minioClient
                .putObject(
                        bucket,
                        file.getOriginalFilename(),
                        is,
                        (long) is.available(),
                        null,
                        null,
                        mimeType);
        is.close();
        log.info("{} is successfully uploaded", file.getOriginalFilename());
        return new FileUploadResponse(FileUploadResponse.Status.OK.name(),
                serviceEndpoint + "/" + bucket + "/" + file.getOriginalFilename(),
                null);
    }

    @RequestMapping(path = "/{bucket}/{fileName}", method = RequestMethod.GET)
    public ResponseEntity<InputStreamResource> downloadFile(@PathVariable(name = "bucket") String bucket,
                                                            @PathVariable(name = "fileName") String fileName) throws IOException, InvalidKeyException, NoSuchAlgorithmException, InsufficientDataException, InvalidArgumentException, InvalidResponseException, InternalException, NoResponseException, InvalidBucketNameException, XmlPullParserException, ErrorResponseException {
        byte[] bytes = minioClient.getObject(bucket, fileName).readAllBytes();
        String mimeType = getContentType(new ByteArrayInputStream(bytes), fileName);
        log.info("contentType is {}", mimeType);
        ByteArrayInputStream in = new ByteArrayInputStream(bytes);
        log.info("length {}", in.available());
        return ResponseEntity
                .ok()
                .contentLength(in.available())
                .contentType(
                        MediaType.parseMediaType(mimeType))
                .body(new InputStreamResource(in));
    }

    private String getContentType(InputStream theInputStream, String theFileName) throws IOException {
        try (InputStream is = theInputStream;
             BufferedInputStream bis = new BufferedInputStream(is);) {
            AutoDetectParser parser = new AutoDetectParser();
            Detector detector = parser.getDetector();
            Metadata md = new Metadata();
            md.add(Metadata.RESOURCE_NAME_KEY, theFileName);
            org.apache.tika.mime.MediaType mediaType = detector.detect(bis, md);
            return mediaType.toString();
        }
    }


}
