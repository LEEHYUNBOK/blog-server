package com.example.mini_project_b.login.service;


import com.example.mini_project_b.login.domain.DTO.PostDTO;
import com.example.mini_project_b.login.domain.Hashtag;
import com.example.mini_project_b.login.domain.PostHashtag;
import com.example.mini_project_b.login.repository.HashTagRepository;
import com.example.mini_project_b.login.repository.PostHashTagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class HashtagService {
    private final HashTagRepository hashTagRepository;

    private final PostHashTagRepository postHashTagRepository;

    public List<Hashtag> getAllHashTags() {
        return hashTagRepository.findAll();
    }

    public Hashtag saveHashTag(Hashtag hashTag) {
        return hashTagRepository.save(hashTag);
    }

    public Hashtag findHashTagByTag(String tag) {
        return hashTagRepository.findHashTagByTag(tag);
    }

}