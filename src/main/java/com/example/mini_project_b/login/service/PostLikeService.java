package com.example.mini_project_b.login.service;

import com.example.mini_project_b.login.domain.DTO.PostDTO;
import com.example.mini_project_b.login.domain.Member;
import com.example.mini_project_b.login.domain.Post;
import com.example.mini_project_b.login.domain.PostLike;
import com.example.mini_project_b.login.repository.MemberRepository;
import com.example.mini_project_b.login.repository.PostLikeRepository;
import com.example.mini_project_b.login.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostLikeService {

    private final MemberRepository memberRepository;
    private final PostRepository postRepository;

    private final PostLikeRepository postLikeRepository;


    @Transactional(readOnly = true)
    public List<PostDTO> findAllPostLike(Principal principal,List<PostDTO> dto){
        if(principal != null) {
            Member member = memberRepository.findByMemberId(principal.getName())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 사용자를 찾을 수 없습니다."));

            List<PostLike> postLikes = postLikeRepository.findPostLikesByMember(member);

            for (PostDTO p : dto) {
                for (PostLike pl : postLikes)
                    if (p.getId() == pl.getPost().getId())
                        p.setPostLike(true);

            }
        }

        return dto;
    }

    @Transactional(readOnly = true)
    public PostDTO findByPostLike(Principal principal,PostDTO dto){
        if(principal != null) {
            Member member = memberRepository.findByMemberId(principal.getName())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 사용자를 찾을 수 없습니다."));

            List<PostLike> postLikes = postLikeRepository.findPostLikesByMember(member);

            for (PostLike pl : postLikes)
                if (dto.getId() == pl.getPost().getId())
                    dto.setPostLike(true);
        }

        return dto;
    }



    @Transactional
    public void saveLikes(Long postId, Principal principal) {

        Post post = postRepository.findById(postId).orElseThrow(() -> {
            throw new IllegalArgumentException("해당 게시글은 존재하지 않습니다.");
        });

        Member member = memberRepository.findByMemberId(principal.getName()).orElseThrow(() -> {
            throw new IllegalArgumentException("해당 사용자는 존재하지 않습니다.");
        });

        List<PostLike> postLikes = postLikeRepository.findByPostId(postId);



        for(PostLike p : postLikes)
            if(p.getMember().getId() == member.getId())
                throw new IllegalArgumentException("이미 좋아요를 누르셨습니다.");

        PostLike postLike = PostLike.builder()
                .post(post)
                .member(member)
                .build();

        postLikeRepository.save(postLike);
//        return post.addHeartCount();
    }

    @Transactional
    public void deleteLikes(Long postId, Principal principal) {

        Post post = postRepository.findById(postId).orElseThrow(() -> {
            throw new IllegalArgumentException("해당 게시글은 존재하지 않습니다.");
        });

        Member member = memberRepository.findByMemberId(principal.getName()).orElseThrow(() -> {
            throw new IllegalArgumentException("해당 사용자는 존재하지 않습니다.");
        });

        List<PostLike> postLikes = postLikeRepository.findByPostId(postId);

        for(PostLike p : postLikes){
            if(p.getMember().getId() == member.getId()) {
                postLikeRepository.delete(p);
                return;
            }
        }
        throw new IllegalArgumentException("좋아요를 누르지 않으셨습니다.");
        

//        return post.deleteHeartCount();
    }
}
