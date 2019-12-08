package ru.maxmorev.minioservice;

import io.minio.errors.MinioException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.maxmorev.minioservice.domain.Message;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;


@ControllerAdvice
public class GlobalDefaultExceptionHandler {

    private final static Logger logger = LoggerFactory.getLogger(GlobalDefaultExceptionHandler.class);

    /**
     * Other errors
     * @param req
     * @param ex
     * @return
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Message handleBadRequest(HttpServletRequest req, Exception ex) {
        logger.error(ex.getLocalizedMessage(), ex);
        Message responseMessage = new Message(Message.ERROR, req.getRequestURL().toString(), ex, Collections.EMPTY_LIST);
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
    public Message handleHibernateException(HttpServletRequest req, Exception ex) {
        Message responseMessage = new Message(Message.ERROR, req.getRequestURL().toString(), "Internal storage error", Collections.EMPTY_LIST);
        logger.error("Minio exeption {}", ex);
        return responseMessage;
    }

}
