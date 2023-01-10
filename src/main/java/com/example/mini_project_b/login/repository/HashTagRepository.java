package com.example.mini_project_b.login.repository;


import com.example.mini_project_b.login.domain.Hashtag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HashTagRepository extends JpaRepository<Hashtag, Long> {

    Hashtag findHashTagByTag(String tag);
    //void deleteByPostsId(Long postId);

}
