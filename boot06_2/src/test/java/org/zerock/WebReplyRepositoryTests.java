package org.zerock;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.IntStream;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.junit4.SpringRunner;
import org.zerock.domain.WebBoard;
import org.zerock.domain.WebReply;
import org.zerock.persistence.WebBoardRepository;
import org.zerock.persistence.WebReplyRepository;

import lombok.extern.java.Log;

@RunWith(SpringRunner.class)
@SpringBootTest
@Log
@Commit
public class WebReplyRepositoryTests {
	
	@Autowired
	WebBoardRepository repoB;
	
	@Autowired
	WebReplyRepository repo;
	
	@Test
	public void testInsertBoards() {
		WebBoard board;
		
		for(long i = 1; i <= 400; i++) {
			board = new WebBoard();
			
			board.setBno(i);
			repoB.save(board);
		}
	}
	
	@Test
	public void testUpdateBoards() {
		Optional<WebBoard> board;
		
		for(long i = 1; i <= 400; i++) {
			board = repoB.findById(i);
			
			board.get().setTitle("제목" + i);
			
			repoB.save(board.get());
		}
	}
	
	@Test
	public void testInsertReplies() {
		Long[] arr = {304L, 303L, 300L};
		
		Arrays.stream(arr).forEach(num -> {
			WebBoard board = new WebBoard();
			board.setBno(num);
			
			IntStream.range(0, 10).forEach(i -> {
				WebReply reply = new WebReply();
				reply.setReplyText("REPLY..."+i);
				reply.setReplyer("replyer"+i);
				reply.setBoard(board);
				
				repo.save(reply);
			});
		});
	}

}
