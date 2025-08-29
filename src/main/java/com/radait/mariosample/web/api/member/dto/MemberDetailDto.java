package com.radait.mariosample.web.api.member.dto;

import com.radait.mariosample.web.api.member.entity.Member;
import lombok.Data;

import java.time.LocalDate;

@Data
public class MemberDetailDto {

    private Long memberId;
    private String loginId;
    private String memNm;
    private String nickNm;
    private String memType;
    private LocalDate joinDt;
    private String phoneNo;
    private String addr;
    private String addrDtl;


    public static MemberDetailDto of(Member member) {

        MemberDetailDto dto = new MemberDetailDto();
        dto.setMemberId(member.getMemberId());
        dto.setLoginId(member.getLoginId());
        dto.setMemNm(member.getMemNm());
        dto.setNickNm(member.getNickNm());
        dto.setMemType(member.getMemType());
        dto.setJoinDt(member.getJoinDt() != null ? member.getJoinDt() : null);
        dto.setPhoneNo(member.getPhoneNo());
        dto.setAddr(member.getAddr());
        dto.setAddrDtl(member.getAddrDtl());

        return dto;
    }
}
