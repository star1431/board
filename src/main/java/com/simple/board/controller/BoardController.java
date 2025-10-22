package com.simple.board.controller;

import com.simple.board.domain.Board;
import com.simple.board.dto.BoardRequestDTO;
import com.simple.board.dto.BoardResponseDTO;
import com.simple.board.exception.PasswordMismatchException;
import com.simple.board.service.BoardService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 게시판 제어계층 class
 * @author 정병천
 * @since 2025-10-21
 */
@Controller
@RequiredArgsConstructor
// @RequestMapping("/board")
public class BoardController {
    private final BoardService boardService;

    /** 게시글 목록 */
    @GetMapping({"/", "/list"}) // localhost:8080/
    public String BoardList(
            Model model,
            // size: 리밋, sort : 기준점, drirection : 정렬타입
            @PageableDefault(size = 5, sort = "id", direction = Sort.Direction.DESC)
            Pageable pageable
    ) {
        Page<BoardResponseDTO> boards = boardService.getBoardList(pageable);
        model.addAttribute("boards", boards);
        model.addAttribute("dataTotal", boards.getTotalElements()); // getSize() ? 현재 페이징 내 갯수임.
        return "board/list";
    }

    /** 게시글 상세 */
    @GetMapping("/view/{id}") // localhost:8080/view/1
    public String viewBoard(Model model, @PathVariable("id") Long id) {
        BoardResponseDTO board = boardService.getBoard(id);
        model.addAttribute("board", board);
        return "board/view";
    }

    /** 게시글 등록 */
    @GetMapping("/writeform")
    public String writeForm(Model model) {
        // 요청 DTO 삽입
        model.addAttribute("board", new BoardRequestDTO());
        return "board/writeForm";
    }
    @PostMapping("/write")
    public String writeBoard(
            // 유효성 검증 - 어트리뷰트와 결과 객체
            @Valid @ModelAttribute("board") BoardRequestDTO reqDTO,
            BindingResult res
    ) {
        if(res.hasErrors()) return "board/writeForm"; // error 그대로
        Long id = boardService.createBoard(reqDTO);
        return "redirect:/view/" + id; // 상세글로 이동
    }

    /** 게시글 수정 */
    @GetMapping("/update/{id}")
    public String updateForm(Model model, @PathVariable("id") Long id) {
        BoardResponseDTO board = boardService.getBoard(id);
        board.setPassword(""); // resDTO의 pw값 날리고 진입
        model.addAttribute("board", board);
        model.addAttribute("id", id);
        return "board/updateForm";
    }
    @PostMapping("/update")
    public String updateBoard(
            // 유효성 검증 - 어트리뷰트와 결과 객체
            @Valid @ModelAttribute("board") BoardRequestDTO reqDTO,
            BindingResult res,
            @RequestParam("id") Long id,
            Model model
    ) {
        if(res.hasErrors()) {
            model.addAttribute("id", id);
            return "board/updateForm"; // error 그대로
        }
        try {
            boardService.updateBoard(id, reqDTO);
        } catch (PasswordMismatchException e) {
            // 비밀번호 불일치 시 다시 폼으로
            model.addAttribute("id", id);
            model.addAttribute("pwMismatch", "비밀번호가 일치하지 않습니다.");
            return "board/updateForm";
        }
        return "redirect:/view/" + id; // 상세글로 이동
    }

    /** 게시글 삭제 */
    @GetMapping("/delete/{id}")
    public String deleteForm(Model model, @PathVariable("id") Long id) {
        BoardResponseDTO board = boardService.getBoard(id);
        board.setPassword(""); // resDTO의 pw값 날리고 진입
        model.addAttribute("board", board);
        model.addAttribute("id", id);
        return "board/deleteForm";
    }
    @PostMapping("/delete")
    public String deleteBoard(
            // 유효성 검증 - 어트리뷰트와 결과 객체
            @Valid @ModelAttribute("board") BoardRequestDTO reqDTO,
            BindingResult res,
            @RequestParam("id") Long id,
            Model model
    ) {
        if(res.hasErrors()) {
            model.addAttribute("id", id);
            return "board/deleteForm"; // error 그대로
        }
        try {
            boardService.deleteBoard(id, reqDTO.getPassword());
        } catch (PasswordMismatchException e) {
            // 비밀번호 불일치 시 다시 폼으로
            model.addAttribute("id", id);
            model.addAttribute("pwMismatch", "비밀번호가 일치하지 않습니다.");
            return "board/deleteForm";
        }
        return "redirect:/list";
    }



}
