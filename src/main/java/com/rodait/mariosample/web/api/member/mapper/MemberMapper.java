package com.rodait.mariosample.web.api.member.mapper;

import com.rodait.mariosample.web.api.member.dto.MemberSearchDto;
import com.rodait.mariosample.web.api.member.entity.Member;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MemberMapper {

    List<Member> selectMemberList(@Param("cond") MemberSearchDto condition);
    Long countMemberList(@Param("cond") MemberSearchDto condition);

    Member selectMemberById(@Param("id") Long id);
    Member selectMemberLogin(@Param("username") String username);

    int insertMember(Member member);
    int updateMember(Member member);
    int deleteMemberById(@Param("id") Long id, @Param("modId") String modId, @Param("modIp") String modIp);
}
