package com.example.mini_project_b.login.domain;


import com.example.mini_project_b.login.domain.DTO.MemberJoinDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "member")
@Entity
public class Member implements UserDetails {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long Id;

    @Column(name = "member_id", nullable = false)
    private String memberId;


    @Column(name = "password", nullable = false, length = 100)
    private String password;

    @Column(name="profileImg", nullable = true,length = 200)
    private String profileImg;

    @Column(name="statusMessage",nullable = true,length = 100)
    private String statusMessage;

    @ElementCollection(fetch = FetchType.EAGER)
    @Builder.Default
    private List<String> roles = new ArrayList<>();

    @Column(name="nickname",nullable = false,length = 100)
    private String nickname;

    @OneToMany(mappedBy = "follower",fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Follow> followerList;

    @OneToMany(mappedBy = "followee",fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private  List<Follow> followeeList;

    // post foreignkey 생성
    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY,cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<Post> posts =new ArrayList<>();

    // PostLike forignkey 생성
    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY,cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<PostLike> postLikes =new ArrayList<>();


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    @Override
    public String getUsername() {
        return memberId;
    }
    @Override
    public String getPassword() {
        return password;
    }
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    @Override
    public boolean isEnabled() {
        return true;
    }

    public void update(MemberJoinDto dto) {
        if(dto.getNickname() !=null)
            this.nickname = dto.getNickname();
        if(dto.getStatusMessage() != null)
            this.statusMessage = dto.getStatusMessage();
        if(dto.getProfileImg() != null)
            this.profileImg = dto.getProfileImg();
    }


    public MemberJoinDto toJoinEntity() {
        return MemberJoinDto.builder()
                .nickname(nickname)
                .statusMessage(statusMessage)
                .profileImg(profileImg)
                .build();
    }

}
    /**
     * - user : post = 1 : n
     * - user : user_post_like = 1: n
     * - user : follow = n : m
     * - post : post_like = 1 : n
     */

