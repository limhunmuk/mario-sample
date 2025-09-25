package com.radait.mariosample.member;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.radait.mariosample.web.api.member.dto.MemberFormDto;
import com.radait.mariosample.web.api.member.service.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs(uriScheme = "https", uriHost = "hunmuk.api.com", uriPort = 443)
@Transactional // 테스트 후 롤백
@DisplayName("회원 컨트롤러 API 문서화 테스트")
public class MemberControllerDocTest {

    @Autowired
    private MemberService memberService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private String loginAndGetAccessToken(String loginId, String rawPassword) throws Exception {
        // ① 요청 바디용 맵 구성
        Map<String, String> loginRequest = Map.of(
                "loginId", loginId,
                "password", rawPassword
        );

        // ② JSON 문자열로 직렬화
        String loginJson = objectMapper.writeValueAsString(loginRequest);

        // ③ 로그인 요청 → 토큰 추출
        var mvcResult = mockMvc.perform(RestDocumentationRequestBuilders.post("/api/login")
                        .contentType(APPLICATION_JSON)
                        .content(loginJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accessToken").exists())
                .andExpect(jsonPath("$.refreshToken").exists())
                .andExpect(jsonPath("$.member").exists())
                .andReturn();

        // ④ 응답 JSON 파싱해서 accessToken 값 꺼내기
        JsonNode node = objectMapper.readTree(mvcResult.getResponse().getContentAsString());
        return node.get("accessToken").asText();
    }

    @Test
    @DisplayName("회원등록")
    public void saveMember() throws Exception {

        //String loginId = "limhunmuk@gmail.com";
        //String rawPassword = "1234";

        // 로그인 토큰 발급 -> 회원등록 시에는 토큰 발급 필요 없음
        //String accessToken = loginAndGetAccessToken(loginId, rawPassword);
        //System.out.println("accessToken = " + accessToken);

        //given
           MemberFormDto body = MemberFormDto
                    .builder()
                    .loginId("testId")
                    .password("1234")
                    .confirmPassword("1234")
                    .memNm("홍길동")
                    .memType("일반")
                    .nickNm("별명")
                    .addr("주소 1")
                    .addrDtl("주소 2")
                    .phoneNo("010-1234-5678")
                    .build();


        String json  = objectMapper.writeValueAsString(body);

        //expected
        this.mockMvc.perform(RestDocumentationRequestBuilders.post("/api/members")
                        //.header("Authorization", "Bearer " + accessToken)
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON)
                        .content(json)
                )
                .andDo(print())
                .andExpect(status().isCreated())
                .andDo(document("member-create",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        //relaxedRequestFields(
                        requestFields(
                                fieldWithPath("loginId").description("로그인 아이디"),
                                fieldWithPath("password").description("비밀번호"),
                                fieldWithPath("confirmPassword").description("비밀번호 확인"),
                                fieldWithPath("memNm").description("회원명"),
                                fieldWithPath("nickNm").description("닉네임").optional(),
                                fieldWithPath("memType").description("회원 유형 코드"),
                                fieldWithPath("phoneNo").description("전화번호"),
                                fieldWithPath("addr").description("주소").optional(),
                                fieldWithPath("addrDtl").description("상세 주소").optional()
                        ),
                        responseFields(
                                fieldWithPath("memberId").description("신규 회원 ID"),
                                fieldWithPath("loginId").description("로그인 아이디"),
                                fieldWithPath("memNm").description("회원명"),
                                fieldWithPath("nickNm").description("닉네임"),
                                fieldWithPath("memType").description("회원 유형 코드"),
                                fieldWithPath("phoneNo").description("전화번호"),
                                fieldWithPath("addr").description("주소"),
                                fieldWithPath("addrDtl").description("상세 주소"),
                                fieldWithPath("joinDt").description("가입일 (yyyy-MM-dd)"),
                                fieldWithPath("regDt").description("등록일시 (yyyy-MM-dd HH:mm:ss)")
                        )
                ));
    }
}
