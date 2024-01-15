package org.zerock.mreview.repository;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Commit;
import org.zerock.mreview.entity.Movie;
import org.zerock.mreview.entity.MovieImage;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.IntStream;

@SpringBootTest
public class MovieRepositoryTests{

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private MovieImageRepository imageRepository;

    @Commit
    @Transactional
    @Test
    public void insertMovies(){
        IntStream.rangeClosed(1,100).forEach(i->{
            Movie movie = Movie.builder()
                    .title("Movie...")
                    .build();
            System.out.println("==================");

            movieRepository.save(movie);

            int count=(int)(Math.random()*5)+1;

            for(int j = 0;j<count;j++){
                MovieImage movieImage = MovieImage.builder()
                        .uuid(UUID.randomUUID().toString())//자바라이브러리(UUID)
                        .movie(movie)
                        .imgName("test"+j+".jpg")
                        .build();
                imageRepository.save(movieImage);
            }

            System.out.println("==================");
        });
    }

    @Test
    public void testListPage(){
        Pageable pageable = PageRequest.of(0,10, Sort.by(Sort.Direction.DESC,"mno"));

        Page<Object[]> result = movieRepository.getListPage(pageable);

//        Object arr =(Object)result;
//
//        System.out.println(arr);

        for (Object[] objects : result.getContent()){
            System.out.println((Arrays.toString(objects)));
        }

    }

    @Test
    public void testGetMovieWithAll(){
        List<Object[]> result = movieRepository.getMovieWithAll(92L);

        System.out.println(result);

//        System.out.println(result.get(0)[0]);
//        System.out.println(result.get(0)[1]);
//        System.out.println(result.get(0)[2]);
//        System.out.println(result.get(0)[3]);

        for(Object[] arr : result){
            System.out.println(Arrays.toString(arr));
        }
    }

}
