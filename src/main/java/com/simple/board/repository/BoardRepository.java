package com.simple.board.repository;

import com.simple.board.domain.Board;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * @author 정병천
 * @since 2025-10-21
 */
@Repository
public interface BoardRepository extends CrudRepository<Board, Long>, PagingAndSortingRepository<Board, Long> {

    // 기본 메소드 제공:
    // - save(), findById(), findAll(), deleteById(), count()
}
