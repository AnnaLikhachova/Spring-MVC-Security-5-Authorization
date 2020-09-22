package guide.tour.exception.handler;

import guide.tour.util.CustomResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailAuthenticationException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
 
    @Autowired
    private MessageSource messages;
 
    @Override
    protected ResponseEntity<Object> handleBindException(final BindException exeption, final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
        logger.error("400 Status Code", exeption);
        final BindingResult result = exeption.getBindingResult();
        final CustomResponse bodyOfResponse = new CustomResponse(result.getAllErrors(), "Invalid" + result.getObjectName());
        return handleExceptionInternal(exeption, bodyOfResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(final MethodArgumentNotValidException exeption, final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
        logger.error("400 Status Code", exeption);
        final BindingResult result = exeption.getBindingResult();
        final CustomResponse bodyOfResponse = new CustomResponse(result.getAllErrors(), "Invalid" + result.getObjectName());
        return handleExceptionInternal(exeption, bodyOfResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }
    
    @ExceptionHandler({ UserNotFoundException.class })
    public ResponseEntity<Object> handleUserNotFound(RuntimeException exeption, WebRequest request) {
        logger.error("404 Status Code", exeption);
        CustomResponse bodyOfResponse = new CustomResponse(
          messages.getMessage("message.userNotFound", null, request.getLocale()), "UserNotFound");
        
        return handleExceptionInternal(
                exeption, bodyOfResponse, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }
 
    @ExceptionHandler({ MailAuthenticationException.class })
    public ResponseEntity<Object> handleMail(RuntimeException exeption, WebRequest request) {
        logger.error("500 Status Code", exeption);
        CustomResponse bodyOfResponse = new CustomResponse(
          messages.getMessage(
            "message.email.config.error", null, request.getLocale()), "MailError");
        
        return handleExceptionInternal(
                exeption, bodyOfResponse, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }
    
    @ExceptionHandler({ InvalidOldPasswordException.class })
    public ResponseEntity<Object> handleInvalidOldPassword(RuntimeException exeption, WebRequest request) {
        logger.error("400 Status Code", exeption);
        final CustomResponse bodyOfResponse = new CustomResponse(messages.getMessage("message.invalidOldPassword", null, request.getLocale()), "InvalidOldPassword");
        return handleExceptionInternal(exeption, bodyOfResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }
    
    @ExceptionHandler({ UserAlreadyExistException.class })
    public ResponseEntity<Object> handleUserAlreadyExist(RuntimeException exeption, WebRequest request) {
        logger.error("409 Status Code", exeption);
        final CustomResponse bodyOfResponse = new CustomResponse(messages.getMessage("message.regError", null, request.getLocale()), "UserAlreadyExist");
        return handleExceptionInternal(exeption, bodyOfResponse, new HttpHeaders(), HttpStatus.CONFLICT, request);
    }
 
    @ExceptionHandler({ Exception.class })
    public ResponseEntity<Object> handleInternal(RuntimeException exeption, WebRequest request) {
        logger.error("500 Status Code", exeption);
        CustomResponse bodyOfResponse = new CustomResponse(
          messages.getMessage(
            "message.error", null, request.getLocale()), "InternalError");
        
        return handleExceptionInternal(
                exeption, bodyOfResponse, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }
}
