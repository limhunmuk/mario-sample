package com.radait.mariosample.web.api.member.service;

import com.radait.mariosample.web.api.common.entity.PageResponse;
import com.radait.mariosample.web.api.member.dto.MemberDetailDto;
import com.radait.mariosample.web.api.member.dto.MemberFormDto;
import com.radait.mariosample.web.api.member.dto.MemberSearchDto;
import com.radait.mariosample.web.api.member.entity.Member;
import com.radait.mariosample.web.api.member.mapper.MemberMapper;
import com.radait.mariosample.web.api.common.entity.IpHolder;
import com.radait.mariosample.web.exception.InvalidRequest;
import com.radait.mariosample.web.exception.UserNotFound;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberMapper memberMapper;

    /**
     * 회원 목록 조회
     * @return
     */
    public PageResponse<MemberDetailDto> selectMemberList(MemberSearchDto condition) {

        List<MemberDetailDto> result = memberMapper.selectMemberList(condition).stream()
                .map(MemberDetailDto::of)
                .toList();

        long total = memberMapper.countMemberList(condition);

        return PageResponse.of(result, condition.getPage(), condition.getSize(), total);
    }

    /**
     * 회원 조회
     * @param id
     * @return
     */
    public MemberDetailDto selectMemberById(long id) {
        Member member = memberMapper.selectMemberById(id);

        if(member == null) {
            throw new UserNotFound();
        }

        return MemberDetailDto.of(member);
    }

    /**
     * 화원등록
     */
    public int insertMember(MemberFormDto formDto) {

        Member entity = formDto.toEntity(IpHolder.getIp());
        return memberMapper.insertMember(entity);
    }

    /**
     * 회원 수정
     */
    public int updateMember(long id, MemberFormDto formDto) {

        Member oldMember = memberMapper.selectMemberById(id);

        Boolean validate = formDto.validate(oldMember, formDto);
        if(validate) {
            throw new InvalidRequest();
        }

        oldMember.updateMember(formDto);
        return memberMapper.updateMember(oldMember);
    }

    /**
     * 회원 삭제
     * @param id
     * @return
     */
    public int deleteMemberById(long id) {
        Member member = memberMapper.selectMemberById(id);
        if(member == null) {
            throw new InvalidRequest();
        }

        return memberMapper.deleteMemberById(id, member.getLoginId(), IpHolder.getIp());
    }
}
