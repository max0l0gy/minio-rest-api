package ru.maxmorev.minioservice;

import io.minio.errors.MinioException;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.maxmorev.minioservice.domain.FileUploadResponse;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@ControllerAdvice
public class GlobalDefaultExceptionHandler {

    /**
     * Other errors
     * @param req
     * @param ex
     * @return
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public FileUploadResponse handleBadRequest(HttpServletRequest req, Exception ex) {
        log.error(ex.getLocalizedMessage(), ex);
        FileUploadResponse responseMessage = new FileUploadResponse(FileUploadResponse.Status.ERROR.name(), req.getRequestURL().toString(), "Internal server error");
        return responseMessage;
    }

    /**
     * Processing HibernateException
     * @param req
     * @param ex
     * @return
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(value = MinioException.class)
    @ResponseBody
    public FileUploadResponse handleHibernateException(HttpServletRequest req, Exception ex) {
        FileUploadResponse responseMessage = new FileUploadResponse(FileUploadResponse.Status.ERROR.name(), req.getRequestURL().toString(), "Internal storage error");
        log.error("Minio exeption {}", ex);
        return responseMessage;
    }

}
