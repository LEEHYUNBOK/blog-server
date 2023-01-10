package com.example.mini_project_b.login.service;

import com.example.mini_project_b.login.domain.*;
import com.example.mini_project_b.login.domain.DTO.MemberJoinDto;
import com.example.mini_project_b.login.domain.DTO.PostDTO;
import com.example.mini_project_b.login.jwt.TokenProvider;
import com.example.mini_project_b.login.repository.MemberRepository;
import com.example.mini_project_b.login.repository.PostHashTagRepository;
import com.example.mini_project_b.login.repository.PostLikeRepository;
import com.example.mini_project_b.login.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

    private final PostLikeService postLikeService;

    private final PostHashtagService postHashtagService;

    private final HashtagService hashtagService;

    private final PostHashTagRepository postHashTagRepository;

    // accessToken의 사용자와 {memberId}와 같다면 게시물 생성 가능
    @Transactional
    public Post saveByPostId(Principal principal, String member_id, PostDTO dto){
        if(!member_id.equals(principal.getName()))
//            System.out.println(member_id+" "+principal.getName());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"자신의 블로그에만 등록이 가능합니다.");

        Member member = findEntityByMemberId(member_id);

        System.out.println(member);
        System.out.println(dto.getContent());

        Post post = postRepository.save(
                Post.builder()
                        .title(dto.getTitle())
                        .content(dto.getContent())
                        .img(dto.getImg())
                        .disclosure(dto.isDisclosure())
                        .member(member)
                        .build()
        );

        if(dto.getHashTags().size() != 0) {
            List<Hashtag> hashtags = new ArrayList<>();

            // 해시테그 비존재 시 저장
            for (String tag : dto.getHashTags()) {
//                System.out.println("!@#$!!$@#$!$!$!   " + hashtagService.findHashTagByTag(tag));
                hashtags.add(
                        hashtagService.findHashTagByTag(tag) != null ?
                                hashtagService.findHashTagByTag(tag) :
                                hashtagService.saveHashTag(
                                        Hashtag.builder()
                                                .tag(tag)
                                                .build()
                                )
                );
            }

            // PostHashTag 저장
            for(Hashtag h : hashtags)
                postHashTagRepository.save(
                        PostHashtag.builder()
                                .hashtag(h)
                                .post(post)
                                .build()
                );
        }


        return post;
    }

    @Transactional(readOnly = true)
    public List<PostDTO> findAllisDisclosure(
            Principal principal
    ){
        List<Post> posts = postRepository.findAll();

        for(int i =0; i<posts.size();i++) {
            if (!posts.get(i).isDisclosure())
                posts.remove(i);
        }

        List<PostDTO> postDTOs = postHashtagService.findAllHashtags(
                postLikeService.findAllPostLike(
                        principal,
                        posts.stream()
                                .map(Post::toDTO)
                                .collect(Collectors.toList())
                )
        );


        return postDTOs;
    }

    @Transactional(readOnly = true)
    public List<PostDTO> findAllByMemberId(Principal principal,String member_id){
        Member member = findEntityByMemberId(member_id);

        List<Post> posts = postRepository.findAllByMember(member);

        return addHashTagLike(
                principal,
                posts.stream()
                .map(Post::toDTO)
                .collect(Collectors.toList())
        );
    }
    public List<PostDTO> addHashTagLike(Principal principal,List<PostDTO> posts){
        return postHashtagService.findAllHashtags(
                postLikeService.findAllPostLike(
                        principal,
                        posts
                )
        );
    }




    @Transactional(readOnly = true)
    public PostDTO findByMemberId(Principal principal,String member_id, Long id){
        Post post = findEntityById(id);
        if(!member_id.equals(post.getMember().getMemberId()))
//            System.out.println(member_id+" "+principal.getName());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,member_id+"는 이 게시글을 가지고 있지 않습니다.");

        PostDTO postDTO =
                postHashtagService.findByHashtags(
                    postLikeService.findByPostLike(principal,post.toDTO())
                );

        return postDTO;
    }



    // accessToken의 사용자와 {memberId}와 같다면 게시물 수정 가능
    @Transactional
    public PostDTO updateById(Principal principal, String member_id,Long id, PostDTO dto){

        Post post = findEntityById(id);
        if(!member_id.equals(post.getMember().getMemberId()))
//            System.out.println(member_id+" "+principal.getName());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,member_id+"는 이 게시글을 가지고 있지 않습니다.");

        if(!member_id.equals(principal.getName()))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"자신의 블로그에만 수정이 가능합니다.");

        List<Hashtag> hashtags = new ArrayList<>();

        // PostId와 관련된 PostHashTag 전부 삭제
        postHashTagRepository.deletePostHashtagByPost(post);

        // 해시테그 비존재 시 저장
        for (String tag : dto.getHashTags()) {
//                System.out.println("!@#$!!$@#$!$!$!   " + hashtagService.findHashTagByTag(tag));
            hashtags.add(
                    hashtagService.findHashTagByTag(tag) != null ?
                            hashtagService.findHashTagByTag(tag) :
                            hashtagService.saveHashTag(
                                    Hashtag.builder()
                                            .tag(tag)
                                            .build()
                            )
            );
        }

        // PostHashTag 저장
        for(Hashtag h : hashtags)
            postHashTagRepository.save(
                    PostHashtag.builder()
                            .hashtag(h)
                            .post(post)
                            .build()
            );




        post.update(dto);

        postRepository.saveAndFlush(post);

        return post.toDTO();
    }

    @Transactional
    public void deleteById(Principal principal, String member_id, Long id){
        Post post = findEntityById(id);
        if(!member_id.equals(post.getMember().getMemberId()))
//            System.out.println(member_id+" "+principal.getName());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,member_id+"는 이 게시글을 가지고 있지 않습니다.");
        if(!member_id.equals(principal.getName()))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"자신의 블로그에만 수정이 가능합니다.");


        postRepository.delete(post);
    }

    Member findEntityByMemberId(String member_id){
        return memberRepository.findByMemberId(member_id)
                .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"해당 사용자를 찾을 수 없습니다."));
    }

    Post findEntityById(Long id){
        return postRepository.findById(id)
                .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"해당 게시글은 존재하지 않습니다."));
    }

}
