package com.rodait.mariosample.web.api.member.service;

import com.rodait.mariosample.web.api.common.entity.PageResponse;
import com.rodait.mariosample.web.api.member.dto.MemberDetailDto;
import com.rodait.mariosample.web.api.member.dto.MemberFormDto;
import com.rodait.mariosample.web.api.member.dto.MemberSearchDto;
import com.rodait.mariosample.web.api.member.entity.Member;
import com.rodait.mariosample.web.api.member.mapper.MemberMapper;
import com.rodait.mariosample.web.api.common.entity.IpHolder;
import com.rodait.mariosample.web.exception.InvalidRequest;
import com.rodait.mariosample.web.exception.UserNotFound;
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

        List<MemberDetailDto> result = memberMapper.selectMemberList(condition)
                .stream()
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
    public MemberDetailDto insertMember(MemberFormDto formDto) {

        Member entity = formDto.toEntity(IpHolder.getIp());
        memberMapper.insertMember(entity);
        System.out.println("entity.getMemberId() = " + entity.getMemberId());

        return MemberDetailDto.of(memberMapper.selectMemberById(entity.getMemberId()));
    }

    /**
     * 회원 수정
     */
    public int updateMember(long id, MemberFormDto formDto) {

        Member member = memberMapper.selectMemberById(id);

        // 유효성 검사 -> 비즈니스단
        Boolean validate = formDto.validate(member, formDto);
        if(validate) {
            throw new InvalidRequest();
        }

        member.updateForm(formDto, IpHolder.getIp());
        return memberMapper.updateMember(member);
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
