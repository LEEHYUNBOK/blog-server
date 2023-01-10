use jwt;

insert into member(member_id, nickname, password,profile_img,status_message) values ('member_A','hello','1234',false,'굳!!');
insert into member(member_id, nickname, password,profile_img,status_message) values ('member_B','helloee','1234',false,'굳!!');
insert into member_roles(member_id, roles) values (1, 'ADMIN');
insert into member_roles(member_id, roles) values (2, 'USER');

-- follow 임시 저장
insert into follow(follower_Id,following_Id) values (1,2);
insert into follow(follower_Id,following_Id) values (2,1);


-- Post 임시 저장
insert into post(create_date, last_modified_date,title,content, disclosure, img,member_id)
values (CURRENT_DATE, CURRENT_DATE,'스프링 너무 어렵다~~','너무 어려워서 포기하고 싶은데 어떻하나요..?ㅠㅠㅠ', true, 'testImg',1);

insert into post(create_date, last_modified_date,title,content, disclosure,member_id)
values (CURRENT_DATE, CURRENT_DATE,'jpa 너무 빡친다~','너무 어려워도 악으로 깡으로 버티세요! 그럼 언젠가는 빛을 볼거예요!!', true,2);

-- Post_Like 임시 저장

insert into post_like(member_id,post_id)
values (1,1);
insert into post_like(post_id,member_id)
values (2,1);
insert into post_like(post_id,member_id)
values (1,2);
insert into post_like(post_id,member_id)
values (2,2);

-- Hashtag 임시 저장

insert into hashtag(tag)
values ('SpringBoot');

insert into hashtag(tag)
values ('JPA');

-- Post_Hashtag 임시 저장

insert into post_hashtag(post_id,hashtag_id)
values (1,1);

insert into post_hashtag(post_id,hashtag_id)
values (1,2);

insert into post_hashtag(post_id,hashtag_id)
values (2,1);
