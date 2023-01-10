package com.example.mini_project_b.login.domain;

import com.example.mini_project_b.login.domain.DTO.MemberJoinDto;
import com.example.mini_project_b.login.domain.DTO.PostDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@Table(name = "post")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Post extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "title", nullable = false, length = 150)
    private String title;


    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "img", nullable = true, length = 300)
    private String img;

    @Column(name = "disclosure", nullable = false)
    private boolean disclosure;


    @ManyToOne(targetEntity = Member.class,fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY,cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<PostLike> postLikes =new ArrayList<>();


    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY,cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<PostHashtag> postHashtags =new ArrayList<>();

//    @OneToMany(mappedBy = "posts", fetch=FetchType.LAZY, cascade={CascadeType.ALL})
//    @JoinTable(name="post_hashtag", joinColumns=@JoinColumn(name="post_id"), inverseJoinColumns=@JoinColumn(name="hashtag_id"))
//    private List<PostHashtag> postHashtags = new ArrayList<PostHashtag>();

    public PostDTO toDTO(){

//        System.out.println();
//        System.out.println("@@@@@@@@@@@@@@@ "+postLikes.get(0).getPost());

        return PostDTO.builder()
                .id(id)
                .title(title)
                .content(content)
                .img(img)
                .disclosure(disclosure)
                .createDate(createDate)
                .lastModifiedDate(lastModifiedDate)
                .memberJoinDto(MemberJoinDto.builder()
                        .memberId(member.getMemberId())
                        .nickname(member.getNickname())
                        .statusMessage(member.getStatusMessage())
                        .profileImg(member.getProfileImg())
                        .build())
                .postLike(false)
                .heartCount(postLikes.size())
                .build();
    }
    
    
//    public int addHeartCount(){
//         return ++heartCount;
//    }
//    public int deleteHeartCount(){return --heartCount;}


    public void update(PostDTO dto) {
        this.title = dto.getTitle();
        this.content = dto.getContent();
        this.img=dto.getImg();
        this.disclosure=dto.isDisclosure();
    }



}
