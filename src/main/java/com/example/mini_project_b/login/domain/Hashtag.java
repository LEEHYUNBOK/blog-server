package com.example.mini_project_b.login.domain;


import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@Table(name = "hashtag")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Hashtag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    @Setter(AccessLevel.NONE)
    private Long id;

    @Column(name = "tag", nullable = false, length = 150)
    private String tag;
 // ![JPA 다대다 관계 모델 @OneToMany로 구현하여 Column 추가하기(@IdClass 사용)](https://cjh5414.github.io/jpa-manytomany-relationship-with-@onetomany-and-compositekey/)
        // https://sasca37.tistory.com/164#%EC%--%B-%EA%B-%--%EA%B-%--%EA%B-%--%--%EB%A-%A-%ED%--%--%--%EC%-B%-C%--%EA%B-%A-%EB%A-%A-%EC%--%AC%ED%--%AD

//    @OneToMany(mappedBy = "hashtags", fetch=FetchType.LAZY, cascade= {CascadeType.ALL})
//    @JoinTable(name="post_hashtag", joinColumns=@JoinColumn(name="hashtag_id"), inverseJoinColumns=@JoinColumn(name="post_id"))
//    private List<PostHashtag> postHashtags = new ArrayList<PostHashtag>();
}