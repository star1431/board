package com.simple.board.dto;

import com.simple.board.domain.Board;
import jdk.jshell.Snippet;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.management.StringValueExp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 응답 DTO
 * @author 정병천
 * @since 2025-10-21
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoardResponseDTO {
    private String id;
    private String name;
    private String title;
    private String password;
    private String content;
    private String createdAt;
    private String updatedAt;
    
    /** 정보 빌드 */
    public static BoardResponseDTO from(Board board) {
        DateTimeFormatter formatterDate = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        DateTimeFormatter formatterDateTime = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");

        return BoardResponseDTO.builder()
                .id(String.valueOf(board.getId()))
                .name(board.getName())
                .title(board.getTitle())
                .password(board.getPassword())
                .content(board.getContent())
                .createdAt(board.getCreatedAt().format(formatterDate))
                .updatedAt(board.getUpdatedAt().format(formatterDateTime))
                .build();
    }
}
