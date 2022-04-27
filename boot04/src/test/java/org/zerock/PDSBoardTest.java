package org.zerock;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.domain.PDSBoard;
import org.zerock.domain.PDSFile;
import org.zerock.persistence.PDSBoardRepository;

import lombok.extern.java.Log;

@RunWith(SpringRunner.class)
@SpringBootTest
@Log
@Commit
public class PDSBoardTest {
	
	@Autowired
	PDSBoardRepository repo;
	
	@Test
	public void testInsertPDS() {
		PDSBoard pds = new PDSBoard();
		pds.setPname("Document");
		
		PDSFile file1 = new PDSFile();
		file1.setPdsfile("file1.doc");
		
		PDSFile file2 = new PDSFile();
		file2.setPdsfile("file2.doc");
		
		pds.setFiles(Arrays.asList(file1, file2));
		
		log.info("try to save pds");
		
		repo.save(pds);
	}

	@Transactional
	@Test
	public void testUpdateFileName1() {
		Long fno = 1L;
		String newName = "updateFile1.doc";
		
		int count = repo.updatePDSFile(fno, newName);
		
		log.info("update count: " + count);
	}
	
	@Transactional
	@Test
	public void testUpdateFileName2() {
		String newName = "updatedFile2.doc";
		// fno check
		Optional<PDSBoard> result = repo.findById(3L);
		
		result.ifPresent(pds -> {
			log.info("data exist and try update");
			
			PDSFile target = new PDSFile();
			target.setFno(2L);
			target.setPdsfile(newName);
			
			int idx = pds.getFiles().indexOf(target);
			
			if (idx > -1) {
				List<PDSFile> list = pds.getFiles();
				list.remove(idx);
				list.add(target);
				pds.setFiles(list);
			}
			
			repo.save(pds);
		});
	}
	
	@Transactional
	@Test
	public void deletePDSFile() {
		Long fno = 2L;
		
		int count = repo.deletePDSFile(fno);
		
		log.info("Delete PDSFile: " + count);
	}
	
	@Test
	public void insertDummies() {
		List<PDSBoard> list = new ArrayList<>();
		
		IntStream.range(1, 100).forEach(i -> {
			PDSBoard pds = new PDSBoard();
			pds.setPname("자료 " + i);
			
			PDSFile file1 = new PDSFile();
			file1.setPdsfile("file1.doc");
			
			PDSFile file2 = new PDSFile();
			file2.setPdsfile("file2.doc");
			
			pds.setFiles(Arrays.asList(file1, file2));
			
			log.info("try to save pds");
			
			list.add(pds);
		});
		
		repo.saveAll(list);
	}
	
	@Transactional
	@Test
	public void viewSummary() {
		repo.getSummary().forEach(arr -> log.info(Arrays.toString(arr)));
	}
}
