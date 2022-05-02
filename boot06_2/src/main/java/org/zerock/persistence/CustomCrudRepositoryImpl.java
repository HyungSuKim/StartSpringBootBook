package org.zerock.persistence;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.zerock.domain.QWebBoard;
import org.zerock.domain.QWebReply;
import org.zerock.domain.WebBoard;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.JPQLQuery;

import lombok.extern.java.Log;

@Log
public class CustomCrudRepositoryImpl extends QuerydslRepositorySupport implements CustomWebBoard {
	
	public CustomCrudRepositoryImpl() {
		super(WebBoard.class);
	}
	
	/*@Override
	public Page<Object[]> getCustomPage(String type, String keyword, Pageable page) {
		log.info("==========================================");
		log.info("Type : " + type);
		log.info("Keyword : " + keyword);
		log.info("Page : " + page);
		log.info("==========================================");
		
		QWebBoard b = QWebBoard.webBoard;
		
		JPQLQuery<WebBoard> query = from(b);
		
		JPQLQuery<Tuple> tuple = query.select(b.bno, b.Title);
		
		tuple.where(b.bno.gt(0L));
		
		tuple.orderBy(b.bno.desc());
		
		tuple.offset(page.getOffset());
		tuple.limit(page.getPageSize());
		
		List<Tuple> list = tuple.fetch();
		
		List<Object[]> resultList = new ArrayList<>();
		
		list.forEach(t -> {
			resultList.add(t.toArray());
		});
		
		long total = tuple.fetchCount();
		
		return new PageImpl<>(resultList, page, total);
	}*/
	
	@Override
	public Page<Object[]> getCustomPage(String type, String keyword, Pageable page) {
		log.info("==========================================");
		log.info("Type : " + type);
		log.info("Keyword : " + keyword);
		log.info("Page : " + page);
		log.info("==========================================");
		
		QWebBoard b = QWebBoard.webBoard;
		QWebReply r = QWebReply.webReply;
		
		JPQLQuery<WebBoard> query = from(b);
		
		JPQLQuery<Tuple> tuple = query.select(b.bno, b.Title, r.count());
		
		tuple.leftJoin(r);
		tuple.on(b.bno.eq(r.board.bno));
		tuple.where(b.bno.gt(0L));
		
		if(type != null) {
			switch(type.toLowerCase()) {
			case "t":
				tuple.where(b.Title.like("%" + keyword + "%"));
				break;
			}
		}
		
		tuple.groupBy(b.bno);
		tuple.orderBy(b.bno.desc());
		
		tuple.offset(page.getOffset());
		tuple.limit(page.getPageSize());
		
		List<Tuple> list = tuple.fetch();
		
		List<Object[]> resultList = new ArrayList<>();
		
		list.forEach(t -> {
			resultList.add(t.toArray());
		});
		
		long total = tuple.fetchCount();
		
		return new PageImpl<>(resultList, page, total);
	}
}
