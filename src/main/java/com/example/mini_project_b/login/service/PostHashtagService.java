package com.example.mini_project_b.login.service;

import com.example.mini_project_b.login.domain.DTO.PostDTO;
import com.example.mini_project_b.login.domain.Member;
import com.example.mini_project_b.login.domain.PostHashtag;
import com.example.mini_project_b.login.domain.PostLike;
import com.example.mini_project_b.login.repository.PostHashTagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostHashtagService {
    private final PostHashTagRepository postHashTagRepository;

    @Transactional(readOnly = true)
    public List<PostDTO> findAllHashtags(List<PostDTO> dto){
        for (PostDTO p : dto) {
            // System.out.println("@@@@@@@@@@@+ "+p.getId());
            List<PostHashtag> postHashtags = postHashTagRepository.findByPostId(p.getId());

            List<String> hashtags=new ArrayList<>();

            for(PostHashtag ph : postHashtags)
                hashtags.add(ph.getHashtag().getTag());

            p.setHashTags(hashtags);
        }
        return dto;
    }

    @Transactional(readOnly = true)
    public PostDTO findByHashtags(PostDTO dto){
        List<PostHashtag> postHashtags = postHashTagRepository.findByPostId(dto.getId());

        List<String> hashtags=new ArrayList<>();

        for(PostHashtag ph : postHashtags)
            hashtags.add(ph.getHashtag().getTag());

        dto.setHashTags(hashtags);
        return dto;
    }



}
