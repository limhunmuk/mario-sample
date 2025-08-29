package com.radait.mariosample.web.api.member.service;

import com.radait.mariosample.web.api.member.dto.MemberDetailDto;
import com.radait.mariosample.web.api.member.entity.Member;
import com.radait.mariosample.web.api.member.mapper.MemberMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberMapper memberMapper;

    /**
     * 회원 조회
     * @param id
     * @return
     */
    public MemberDetailDto findById(long id) {
        Member member = memberMapper.findById(id);
        //LocalDate localDate = LocalDate.of(2022, 10, 22);
        //System.out.println("localDate = " + localDate);

        return MemberDetailDto.of(member);
    }
}
