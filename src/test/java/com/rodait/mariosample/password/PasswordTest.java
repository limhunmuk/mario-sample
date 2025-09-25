package com.rodait.mariosample.password;

import com.rodait.mariosample.web.api.member.entity.Member;
import com.rodait.mariosample.web.api.member.mapper.MemberMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class PasswordTest {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    MemberMapper memberMapper;

    @Test
    @DisplayName("패스워드 생성테스트")
    @Transactional
    public void test() {

        // given
        String inputPassword = "1234";

        // when
        Member member = memberMapper.selectMemberLogin("limhunmuk@gmail.com");

        // then
        assertNotNull(inputPassword);
        assertNotNull(Objects.requireNonNull(member).getPassword());

        // password 일치 확인
        boolean matches = passwordEncoder.matches(inputPassword, member.getPassword());
        assertTrue(matches);
    }

}
