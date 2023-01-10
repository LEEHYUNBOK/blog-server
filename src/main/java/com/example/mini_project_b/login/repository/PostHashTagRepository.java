package com.example.mini_project_b.login.repository;

import com.example.mini_project_b.login.domain.Hashtag;
import com.example.mini_project_b.login.domain.Post;
import com.example.mini_project_b.login.domain.PostHashtag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostHashTagRepository extends JpaRepository<PostHashtag, Long> {
    List<PostHashtag> findByPostId(Long postId);

    List<PostHashtag> findByHashtag_Id(Long hashtagId);

    void deletePostHashtagByPost(Post post);

    List<PostHashtag> findAllByHashtag_Tag(String hashtagName);

}
