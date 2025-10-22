package com.simple.board;

import com.simple.board.dto.BoardRequestDTO;
import com.simple.board.service.BoardService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BoardApplication {

    public static void main(String[] args) {
        SpringApplication.run(BoardApplication.class, args);
    }


    // 테스트
    @Bean
    public CommandLineRunner commandLineRunner(BoardService bs) {
        return args -> {
            // Long id = testCreate(bs);
            testDetail(bs, 1L);

        };
    }
    
    // 서비스 - 생성 test
    private Long testCreate(BoardService bs) {
        BoardRequestDTO reqDTO = new BoardRequestDTO();
        reqDTO.setName("test01");
        reqDTO.setTitle("2233253246");
        reqDTO.setPassword("123456");
        reqDTO.setContent("내용요요요용요요요용요용");
        Long id = bs.createBoard(reqDTO);
        return id;
    }

    // 서비스 - 상세 test
    private void testDetail(BoardService bs, Long id) {
        System.out.println(
                "-".repeat(10) +
                " testDetail " +
                "-".repeat(10)
        );

        System.out.println("id: " + id);
        System.out.println(bs.getBoard(id));
    }
}
