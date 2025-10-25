package br.com.devsibre.error;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.*;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<MessageResponse> handleNotFound(NotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse(ex.getMessage()));
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<MessageResponse> handleBusiness(BusinessException ex) {
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(new MessageResponse(ex.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<MessageResponse> handleValidation(MethodArgumentNotValidException ex) {
        var field = ex.getBindingResult().getFieldError();
        var msg = field != null ? field.getField() + ": " + field.getDefaultMessage() : "Dados inválidos";
        return ResponseEntity.badRequest().body(new MessageResponse(msg));
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<MessageResponse> handleResponseStatus(ResponseStatusException ex) {
        return ResponseEntity
                .status(ex.getStatusCode())
                .body(new MessageResponse(ex.getReason()));
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Object> handleConstraintViolation(DataIntegrityViolationException ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("message", "Telefone já está em uso. Informe outro número.");
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<MessageResponse> handleGeneric(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new MessageResponse("Erro interno"));
    }
}