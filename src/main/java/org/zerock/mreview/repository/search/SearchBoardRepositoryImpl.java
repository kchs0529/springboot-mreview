package org.zerock.mreview.repository.search;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.JPQLQuery;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.zerock.mreview.entity.*;


import java.util.List;
import java.util.stream.Collectors;

@Log4j2
public class SearchBoardRepositoryImpl extends QuerydslRepositorySupport implements SearchBoardRepository {

    public SearchBoardRepositoryImpl() {
        super(Movie.class);
    }


    @Override
    public Page<Object[]> searchPage(String type, String keyword, Pageable pageable) {

        log.info("searchPage.............................");

        QMovie movie = QMovie.movie;
        QReview review = QReview.review;
        QMovieImage image = QMovieImage.movieImage;

        JPQLQuery<Movie> jpqlQuery = from(movie);
        jpqlQuery.leftJoin(review).on(review.movie.eq(movie));
        jpqlQuery.leftJoin(image).on(image.movie.eq(movie));

        JPQLQuery<Tuple> tuple = jpqlQuery.select(movie, image, review.grade.avg().coalesce(0.0), review.countDistinct());

        BooleanBuilder booleanBuilder = new BooleanBuilder();
        BooleanExpression exp = movie.mno.gt(0); //movie.mno > 0

        booleanBuilder.and(exp); //AND (movie.mno > 0)
        if(type != null){
            String[] typeArr = type.split("");

            BooleanBuilder searchBuilder = new BooleanBuilder();

            for(String t:typeArr){
                switch (t) {
                    case "t":
                        searchBuilder.or(movie.title.contains(keyword));
                        break;
                    case "w":
                        //searchBuilder.or(movie.writer.contains(keyword));
                        break;
                    case "c":
                        //searchBuilder.or(movie.content.contains(keyword));
                        break;
                }
            }

            booleanBuilder.and(searchBuilder); //Movie.mno > 0 AND (...)
        }

        tuple.where(booleanBuilder);

        //order by
        Sort sort = pageable.getSort();

        //tuple.orderBy(board.bno.desc());

        sort.stream().forEach(order -> {
            Order direction = order.isAscending()? Order.ASC: Order.DESC;
            String prop = order.getProperty();

            PathBuilder orderByExpression = new PathBuilder(Movie.class, "movie");
            tuple.orderBy(new OrderSpecifier(direction, orderByExpression.get(prop)));

        });

        tuple.groupBy(movie); //GROUP BY Movie.mno

        tuple.offset(pageable.getOffset());
        tuple.limit(pageable.getPageSize());

        List<Tuple> result = tuple.fetch();

        //log.info(result);

        long count = tuple.fetchCount();

        return new PageImpl<Object[]>(
                result.stream().map(t -> t.toArray()).collect(Collectors.toList()),
                pageable,
                count);
    }

}
