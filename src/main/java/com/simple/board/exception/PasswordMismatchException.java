package com.simple.board.exception;

/**
 * 암호 불일치 exception
 * @autor 정병천
 * @since 2025-10-21
 */
public class PasswordMismatchException extends RuntimeException {
    public PasswordMismatchException(String message) {
        super(message);
    }
}
