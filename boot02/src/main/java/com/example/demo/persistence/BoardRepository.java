package com.example.demo.persistence;

import org.springframework.data.repository.CrudRepository;
import com.example.demo.domain.Board;

public interface BoardRepository extends CrudRepository<Board, Long> {

}
