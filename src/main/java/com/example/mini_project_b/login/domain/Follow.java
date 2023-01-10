package com.example.mini_project_b.login.domain;

import com.example.mini_project_b.login.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Builder
@NoArgsConstructor
@Entity
@Table(name = "follow")
@AllArgsConstructor
public class Follow {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "followerId")
    private Member follower; // 팔로우를 하는 사람

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "followingId")
    private Member followee; // 팔로우를 받는 사람

    @Builder
    public Follow(Member follower, Member followee) {
        this.follower = follower;
        this.followee = followee;
    }
}
