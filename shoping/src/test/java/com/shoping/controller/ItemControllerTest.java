package com.shoping.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc // Mcokmvc 테스트 할때 필요함
@TestPropertySource(locations = "classpath:application-test.properties")
class ItemControllerTest {

    @Autowired
    MockMvc mockMvc;

    // 권한이 관리자일때 상품등록페이지에 접근 가능한지 테스트
    @Test
    @DisplayName("상품 등록 페이지 권한 테스트")
    @WithMockUser(username = "tlkj1@naver.com", roles = "ADMIN") // 권한이 관리자일때
    public void itemFormTest() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.get("/admin/item/new")) // 상품등록페이지에 get요청
                .andDo(print()) // 요청과 응답 메시지를 확인하기 위해 콘솔창에 출력
                .andExpect(status().isOk()); // 응답 상태 코드가 정상인지 확인
    }

    // 권한이 사용자일때 상품등록페이지에 접근 불가능한지 테스트
    @Test
    @DisplayName("상품 등록 페이지 일반 회원 접근 테스트")
    @WithMockUser(username = "user", roles = "USER") // 권한이 유저일때
    public void ItemFormNotAdminTest() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.get("/admin/item/new"))
                .andDo(print())
                .andExpect(status().isForbidden()); // 상품등록페이지 요청 시 예외(Forbidden) 발생
    }
}