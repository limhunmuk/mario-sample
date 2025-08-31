package com.radait.mariosample.web.api.member.service;

import com.radait.mariosample.web.api.common.entity.PageResponse;
import com.radait.mariosample.web.api.member.dto.MemberDetailDto;
import com.radait.mariosample.web.api.member.dto.MemberSearchDto;
import com.radait.mariosample.web.api.member.entity.Member;
import com.radait.mariosample.web.api.member.mapper.MemberMapper;
import com.radait.mariosample.web.exception.UserNotFound;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberMapper memberMapper;

    /**
     * 전체 회원 조회
     * @return
     */
    public PageResponse<MemberDetailDto> findAll(MemberSearchDto condition) {

        List<MemberDetailDto> result = memberMapper.findMemberAll(condition).stream()
                .map(MemberDetailDto::of)
                .toList();

        long total = memberMapper.countMemberAll(condition);

        return PageResponse.of(result, condition.getPage(), condition.getSize(), total);
    }

    /**
     * 회원 조회
     * @param id
     * @return
     */
    public MemberDetailDto findById(long id) {
        Member member = memberMapper.findMemberById(id);

        if(member == null) {
            throw new UserNotFound();
        }

        return MemberDetailDto.of(member);
    }
}
