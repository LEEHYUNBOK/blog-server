package com.example.mini_project_b.login.service;

import com.example.mini_project_b.login.domain.DTO.MemberJoinDto;
import com.example.mini_project_b.login.domain.DTO.LoginDTO;
import com.example.mini_project_b.login.domain.DTO.TokenDTO;
import com.example.mini_project_b.login.domain.Member;
import com.example.mini_project_b.login.jwt.TokenProvider;
import com.example.mini_project_b.login.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final TokenProvider tokenProvider;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public TokenDTO login(LoginDTO loginRequestDTO) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginRequestDTO.getMemberId(), loginRequestDTO.getPassword());
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        return tokenProvider.createToken(authentication);
    }


    @Transactional
    public void join(MemberJoinDto memberJoinDto) {
        if(memberRepository.findByMemberId(memberJoinDto.getMemberId()).isPresent()) {
            throw new IllegalStateException("이미 존재하는 아이디입니다.");
        }

        memberJoinDto.setPassword(passwordEncoder.encode(memberJoinDto.getPassword()));
        memberRepository.save(memberJoinDto.toEntity());
    }


    @Transactional
    public void profileUpdate(Principal principal, String member_id, MemberJoinDto dto) {

        if(!member_id.equals(principal.getName()))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"자신의 프로필만 수정이 가능합니다.");

        Member member = findEntityByMemberId(member_id);

        member.update(dto);

        memberRepository.saveAndFlush(member);
    }

    @Transactional
    public MemberJoinDto findByUserPostId(String member_id) {
        Member member = findEntityByMemberId(member_id);
        return member.toJoinEntity();
    }


    Member findEntityByMemberId(String member_id){
        return memberRepository.findByMemberId(member_id)
                .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"해당 사용자를 찾을 수 없습니다."));
    }

}