package com.radait.mariosample.web.api.member.mapper;

import com.radait.mariosample.web.api.member.entity.Member;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface MemberMapper {

    Member findById(@Param("id") Long id);
    int insert(Member member);
}
