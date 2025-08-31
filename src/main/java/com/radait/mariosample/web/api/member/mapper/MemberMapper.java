package com.radait.mariosample.web.api.member.mapper;

import com.radait.mariosample.web.api.member.dto.MemberSearchDto;
import com.radait.mariosample.web.api.member.entity.Member;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MemberMapper {

    List<Member> findMemberAll(@Param("cond") MemberSearchDto condition);
    Long countMemberAll(@Param("cond") MemberSearchDto condition);

    Member findMemberById(@Param("id") Long id);
    int insertMember(Member member);
}
