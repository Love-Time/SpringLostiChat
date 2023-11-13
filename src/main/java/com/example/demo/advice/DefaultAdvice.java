package com.example.demo.advice;



import com.example.demo.service.BindingErrorsService;
import org.hibernate.ObjectNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;



@ControllerAdvice
public class DefaultAdvice  {
    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public ResponseEntity<IncorrectDataResponse> handleIncorrectDataException(MethodArgumentNotValidException ex) {
        IncorrectDataResponse incorrectDataResponse = new IncorrectDataResponse(BindingErrorsService.getErrors(ex.getFieldErrors()));
        return new ResponseEntity<>(incorrectDataResponse, HttpStatus.BAD_REQUEST);
    }
//    @ExceptionHandler(value = {Exception.class})
//    public ResponseEntity<Response> handleAllExceptions(Exception ex) {
//        Response response = new Response(ex.getMessage());
//        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
//    }


    @MessageExceptionHandler(org.springframework.messaging.handler.annotation.support.MethodArgumentNotValidException.class)
    @SendToUser("/topic/errors")
    public IncorrectDataResponse methodArgumentNotValidWebSocketExceptionHandler(
            org.springframework.messaging.handler.annotation.support.MethodArgumentNotValidException ex) {


        assert ex.getBindingResult() != null;
        return new IncorrectDataResponse(BindingErrorsService.getErrors(ex.getBindingResult().getFieldErrors()));
    }

    @MessageExceptionHandler(ObjectNotFoundException.class)
    @SendToUser("/topic/private-messages")
    public Response ObjectNotFoundWebSocketExceptionHandler(ObjectNotFoundException ex) {
        return new Response(ex.getMessage());
    }

    @ExceptionHandler(ObjectNotFoundException.class)
    public ResponseEntity<Response> ObjectNotFoundExceptionHandler(ObjectNotFoundException ex) {
        Response response = new Response(ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

}
