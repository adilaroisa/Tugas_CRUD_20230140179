package com.example.crudktp.controller;

import com.example.crudktp.model.WebResponse;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // 1. Menangani error validasi otomatis dari Controller (@Valid)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public WebResponse<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())
        );
        return new WebResponse<>(HttpStatus.BAD_REQUEST.value(), "Validasi Gagal", errors);
    }

    // 2. Menangani error validasi manual dari ValidationUtil di Service
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public WebResponse<String> handleConstraintViolation(ConstraintViolationException ex) {
        // ex.getMessage() akan menghasilkan pesan error dari DTO (misal: "Nomor KTP tidak boleh kosong")
        return new WebResponse<>(HttpStatus.BAD_REQUEST.value(), "Validasi Gagal: " + ex.getMessage(), null);
    }

    // 3. Menangani error (misal: NIK sudah terdaftar / Data tidak ditemukan)
    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public WebResponse<String> handleRuntimeException(RuntimeException ex) {
        return new WebResponse<>(HttpStatus.BAD_REQUEST.value(), ex.getMessage(), null);
    }
}