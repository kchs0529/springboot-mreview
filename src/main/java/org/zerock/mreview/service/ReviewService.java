package org.zerock.mreview.service;

import org.zerock.mreview.dto.ReviewDTO;
import org.zerock.mreview.entity.Member;
import org.zerock.mreview.entity.Movie;
import org.zerock.mreview.entity.Review;

import java.util.List;

public interface ReviewService {
    List<ReviewDTO> getListOfMovie(Long mno);

    Long register(ReviewDTO reviewDTO);

    default ReviewDTO entityToDto(Review movieReview){

        ReviewDTO movieReviewDTO = ReviewDTO.builder()
                .reviewnum(movieReview.getReviewnum())
                .mno(movieReview.getMovie().getMno())
                .mid(movieReview.getMember().getMid())
                .nickname(movieReview.getMember().getNickname())
                .email(movieReview.getMember().getEmail())
                .grade(movieReview.getGrade())
                .text(movieReview.getText())
                .regDate(movieReview.getRegDate())
                .modDate(movieReview.getModDate())
                .build();

        return movieReviewDTO;
    }

    default Review dtoToEntity(ReviewDTO movieReviewDTO){

        Movie movie = Movie.builder().mno(movieReviewDTO.getMno()).build();
        Member member = Member.builder().mid(movieReviewDTO.getMid()).build();

        Review review = Review.builder()
                .reviewnum(movieReviewDTO.getReviewnum())
                .movie(movie)
                .member(member)
                .grade(movieReviewDTO.getGrade())
                .text(movieReviewDTO.getText())
                .build();

        return review;
    }

}
