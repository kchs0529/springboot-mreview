package org.zerock.mreview.repository.search;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.zerock.mreview.entity.Movie;


public interface SearchBoardRepository {

   Page<Object[]> searchPage(String type, String keyword, Pageable pageable);

}
