package com.example.mini_project_b.login.controller;


import com.example.mini_project_b.login.service.PostLikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/post")
public class PostLikeController {
    private final PostLikeService postLikeService;

    // 조회 [Get] 요청받는 DTO 없음. 반환할 DTO 있음

    // 등록 [POST] 요청받는 DTO 있음. 반환할 DTO 있음
    // 수정 [PATCH(o), PUT] 요청 DTO 있음. 반환 DTO 있음

    // 삭제 [DELETE] 요청 DTO 없음. 반환 DTO 없음.

    //하트 카운트 1증가
    @PostMapping("/{postId}/like")
    public ResponseEntity<String> like(
            @PathVariable Long postId,
            Principal principal
    ) {
       postLikeService.saveLikes(postId, principal);

        return ResponseEntity.ok("좋아요 등록 완료");
    }

    //하트 카운트 1감소
    @DeleteMapping("/{postId}/delete")
    public ResponseEntity<String> remove(
            @PathVariable Long postId,
            Principal principal
    ) {
        postLikeService.deleteLikes(postId, principal);

        return ResponseEntity.ok("좋아요 삭제 완료");
    }
}
