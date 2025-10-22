package com.simple.board.exception;

/**
 * 게시글 못찾음 exception
 * @autor 정병천
 * @since 2025-10-21
 */
public class BoardNotFoundException extends RuntimeException {
    public BoardNotFoundException(String message) {
        super(message);
    }
}
