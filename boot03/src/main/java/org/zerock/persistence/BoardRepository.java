package org.zerock.persistence;

import java.util.Collection;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.zerock.domain.Board;

public interface BoardRepository extends CrudRepository<Board, Long>, QuerydslPredicateExecutor<Board> {

	public List<Board> findBoardByTitle(String title);
	
	public Collection<Board> findByWriter(String writer);
	
	public Collection<Board> findByWriterContaining(String writer);
	
	public Collection<Board> findByTitleContainingOrContentContaining(String title, String content);
	
	public Collection<Board> findByTitleContainingAndBnoGreaterThan(String keyword, Long num);
	
	public Collection<Board> findByBnoGreaterThanOrderByBnoDesc(Long bno);
	
	public List<Board> findByBnoGreaterThanOrderByBnoDesc(Long bno, Pageable paging);
	
	/* public List<Board> findByBnoGreaterThan(Long bno, Pageable paging); */
	public Page<Board> findByBnoGreaterThan(Long bno, Pageable paging);
	
	@Query("SELECT b FROM Board b WHERE b.title Like %?1% AND b.bno > 0 ORDER BY b.bno DESC")
	public List<Board> findByTitle(String title);
	
	@Query("SELECT b FROM Board b WHERE b.content LIKE %:content% AND b.bno > 0 ORDER BY b.bno DESC")
	public List<Board> findBycontent(@Param("content") String content);
	
	@Query("SELECT b FROM #{#entityName} b WHERE b.writer LIKE %?1% AND b.bno > 0 ORDER BY b.bno DESC")
	public List<Board> findByWriter2(String writer);
	
	@Query("SELECT b.bno, b.title, b.writer, b.Regdate FROM Board b WHERE b.title LIKE %?1% AND b.bno > 0 ORDER BY b.bno DESC")
	public List<Object[]> findByTitle2(String title);
	
	@Query(value="SELECT b.bno, b.title, b.writer, b.Regdate FROM tbl_boards b WHERE b.title LIKE CONCAT('%', ?1, '%') AND b.bno > 0 ORDER BY b.bno DESC", nativeQuery=true)
	public List<Object[]> findByTitle3(String title);
	
	@Query("SELECT b FROM Board b WHERE b.bno > 0 ORDER BY b.bno DESC")
	public List<Board> findBypage(Pageable pageable);
}
