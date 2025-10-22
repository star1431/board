package com.simple.board.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

/**
 * 게시글 엔티티 class
 * @author 정병천
 * @since 2025-10-21
 */
@Data // getter, setter, tostring, equals
@NoArgsConstructor
@AllArgsConstructor
@Table("board")
public class Board {
    @Id
    private Long id;            // pk
    private String name;        // 작성자
    private String title;       // 제목
    private String password;    // 게시글암호
    private String content;     // 글내용
    private LocalDateTime createdAt;    // 생성일
    private LocalDateTime updatedAt;    // 수정일

    /** 암호 확인 */
    public boolean isPasswordCorrect(String inputPassword) {
        if(this.password == null) return false;
        return this.password.equals(inputPassword);
    }
    
    /** 수정 - 수정일반영 */
    public void update(String name, String title, String content) {
        this.name = name;
        this.title = title;
        this.content = content;
        this.updatedAt = LocalDateTime.now();
    }
}
