package com.simple.board.service;

import com.simple.board.domain.Board;
import com.simple.board.dto.BoardRequestDTO;
import com.simple.board.dto.BoardResponseDTO;
import com.simple.board.exception.BoardNotFoundException;
import com.simple.board.exception.PasswordMismatchException;
import com.simple.board.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.StreamSupport;

/**
 * 게시판 서비스계층 class
 * @author 정병천
 * @since 2025-10-21
 */
@Service
@RequiredArgsConstructor
@Transactional (readOnly = true)
public class BoardService {
    private final BoardRepository boardRepository;

    /** 목록 조회 */
    public Page<BoardResponseDTO> getBoardList(Pageable pageable) {
        // Page 매핑 - PagingAndSortingRepository 종속됨
        Page<BoardResponseDTO> boardsDTO = boardRepository.findAll(pageable)
                .map(BoardResponseDTO::from);

        return boardsDTO;
    }

    /** 상세 조회 */
    public BoardResponseDTO getBoard(Long id) {
        // 엔티티 변환
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new BoardNotFoundException("board못찾음:: " + id));

        return BoardResponseDTO.from(board);
    }

    /** 게시글 등록 */
    @Transactional // readonly false로
    public Long createBoard(BoardRequestDTO reqDTO) {
        // 엔티티 생성
        Board board = new Board(
                null,
                reqDTO.getName(),
                reqDTO.getTitle(),
                reqDTO.getPassword(),
                reqDTO.getContent(),
                LocalDateTime.now(), // 생성일
                LocalDateTime.now() // 수정일
        );
        Board saved = boardRepository.save(board);

        return saved.getId();
    }

    /** 게시글 수정 */
    @Transactional // readonly false로
    public void updateBoard(Long id, BoardRequestDTO reqDTO) {
        // 대상 엔티티 get
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new BoardNotFoundException("board못찾음:: " + id));

        // 비번 검증
        if (!board.isPasswordCorrect(reqDTO.getPassword())) {
            throw new PasswordMismatchException("비밀번호 불일치");
        } else {
            board.update(
                reqDTO.getName(),
                reqDTO.getTitle(),
                reqDTO.getContent()
            );
            boardRepository.save(board);
        }

    }

    /** 게시글 삭제 */
    @Transactional // readonly false로
    public void deleteBoard(Long id, String password) {
        // 대상 엔티티 get
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new BoardNotFoundException("board못찾음:: " + id));

        // 비번 검증
        if (!board.isPasswordCorrect(password)) {
            throw new PasswordMismatchException("비밀번호 불일치");
        } else {
            boardRepository.deleteById(id);
        }
    }
}
