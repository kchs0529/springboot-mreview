package org.zerock.mreview.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.zerock.mreview.dto.ReviewDTO;
import org.zerock.mreview.service.ReviewService;

import java.util.List;

@RestController
@RequestMapping("/reviews")
@Log4j2
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    //해당영화의 전체리뷰조회
    @GetMapping("/{mno}/all")
    public ResponseEntity<List<ReviewDTO>> getList(@PathVariable("mno") Long mno){
        log.info("-----------------list--------------");
        log.info(mno);

        List<ReviewDTO> reviewDTOList = reviewService.getListOfMovie(mno);

        return new ResponseEntity<>(reviewDTOList, HttpStatus.OK);
    }

    @PostMapping("/{mno}")
    public ResponseEntity<Long> addReview(@RequestBody ReviewDTO movieReviewDTO){

        log.info("--------------add MovieReview---------------");
        log.info("reviewDTO: " + movieReviewDTO);

        Long revienum = reviewService.register(movieReviewDTO);

        return new ResponseEntity<>(revienum,HttpStatus.OK);

    }

    @PutMapping("/{mno}/{reviewnum}")
    public ResponseEntity<Long> modifyReview(@PathVariable Long reviewnum ,
                                             @RequestBody ReviewDTO moviewReviewDTO){
        log.info("----modify Review-----" + reviewnum);
        log.info("reveiwDTO : " + moviewReviewDTO );

        reviewService.modify(moviewReviewDTO);

        return new ResponseEntity<>(reviewnum,HttpStatus.OK);
    }

    @DeleteMapping("/{mno}/{reviewnum}")
    public ResponseEntity<Long> removeReview(@PathVariable Long reviewnum){

        log.info("----delete Review-----" + reviewnum);

        reviewService.remove(reviewnum);

        return new ResponseEntity<>(reviewnum,HttpStatus.OK);
    }

}
