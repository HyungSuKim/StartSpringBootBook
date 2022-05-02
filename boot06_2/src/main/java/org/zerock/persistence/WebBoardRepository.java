package org.zerock.persistence;

import org.springframework.data.repository.CrudRepository;
import org.zerock.domain.WebBoard;

public interface WebBoardRepository  extends CrudRepository<WebBoard, Long>{

}
