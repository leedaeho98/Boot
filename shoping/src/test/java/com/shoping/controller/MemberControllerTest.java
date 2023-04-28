package com.shoping.controller;

import com.shoping.dto.MemberFormDto;
import com.shoping.entity.Member;
import com.shoping.service.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;


@SpringBootTest // 스프링부트 테스트 어노테이션
@AutoConfigureMockMvc // MockMvc 테스트 어노테이션
@Transactional // 테스트가 끝났거나 실행 도중 에러가 발생하면 그 자리에서 롤백
@TestPropertySource(locations = "classpath:application-test.properties") // 테스트 소스 가져온다
class MemberControllerTest {

    @Autowired
    private MemberService memberService;

    @Autowired
    private MockMvc mockMvc; // 테스트에 필요한 기능만 가지는 가짜 객체 , MockMvc객체는 웹 브라우저에서 요청하는것처럼 테스트 가능

    @Autowired
    PasswordEncoder passwordEncoder;

    // 테스트 하기 전 로그인 전 회원을 등록하는 메소드
    public Member createMember(String email, String password){
        MemberFormDto memberFormDto = new MemberFormDto();
        memberFormDto.setEmail(email);
        memberFormDto.setName("이대호");
        memberFormDto.setAddress("동탄");
        memberFormDto.setPassword(password);
        Member member = Member.createMember(memberFormDto, passwordEncoder);
        return memberService.saveMember(member);
    }

    @Test
    @DisplayName("로그인 성공 테스트")
    public void loginSuccessTest() throws Exception{
        String email = "tlkj1633@naver.com";
        String password = "123456789";
        this.createMember(email, password);
        mockMvc.perform(formLogin().userParameter("email")
                .loginProcessingUrl("/members/login") // 로그인이 되는지 테스트, userParameter()를 이용하여 이메일을 아이디로 세팅
                .user(email).password(password))
                .andExpect(SecurityMockMvcResultMatchers.authenticated()); // 로그인 성공하면 테스트 코드 통과

    }

    @Test
    @DisplayName("로그인 실패 테스트")
    public void loginFailTest() throws Exception{
        String email = "tlkj@naver.com";
        String password = "1234";
        this.createMember(email, password);
        mockMvc.perform(formLogin().userParameter("email")
                .loginProcessingUrl("/members/login")
                .user(email).password("12345"))
                .andExpect(SecurityMockMvcResultMatchers.unauthenticated());
    }

}