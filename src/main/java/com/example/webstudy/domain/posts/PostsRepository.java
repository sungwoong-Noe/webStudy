package com.example.webstudy.domain.posts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostsRepository extends JpaRepository<Posts, Long> {

    //전체 게시글 조회 : id 내림차순 정렬
    @Query("select p from Posts p order by p.id DESC")
    List<Posts> findAllDecs();

}
