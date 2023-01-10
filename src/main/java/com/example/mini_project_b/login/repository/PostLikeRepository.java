package com.example.mini_project_b.login.repository;

import com.example.mini_project_b.login.domain.Member;
import com.example.mini_project_b.login.domain.Post;
import com.example.mini_project_b.login.domain.PostLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostLikeRepository extends JpaRepository<PostLike, Long> {
    List<PostLike> findByPostId(Long postId);


    // 왜 얘만 안되는지 모르겠음
    List<PostLike> findPostLikesByMember(Member member);
}
