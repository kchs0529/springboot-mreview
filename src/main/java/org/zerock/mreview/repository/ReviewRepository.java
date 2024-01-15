package org.zerock.mreview.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.zerock.mreview.entity.Member;
import org.zerock.mreview.entity.Movie;
import org.zerock.mreview.entity.Review;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review,Long> {
    @EntityGraph(attributePaths = {"member"})//엔티티를 조회할때 관련된 엔티티들을 함께 로딩하는 방법을 지정하는 어노테이션
    //member엔티티와 연관관계에 있는 엔티티 정보를 함께 조회하게 해준다.
    List<Review> findByMovie(Movie movie);

    void deleteByMember(Member member);

}
