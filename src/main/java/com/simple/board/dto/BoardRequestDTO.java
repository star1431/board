package com.simple.board.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 요청 DTO
 * @author 정병천
 * @since 2025-10-21
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BoardRequestDTO {

    @NotBlank(message = "입력 필수")
    @Size(max = 100, message = "100자 이하 입력해주세요.")
    private String name;

    @NotBlank(message = "입력 필수")
    @Size(max = 255, message = "255자 이하 입력해주세요.")
    private String title;

    @NotBlank(message = "입력 필수")
    @Size(max = 255, message = "255자 이하 입력해주세요.")
    private String password;

    @NotBlank(message = "입력 필수")
    private String content;
}
