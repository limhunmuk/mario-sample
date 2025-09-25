package com.rodait.mariosample.web.api.member.dto;

import com.rodait.mariosample.web.api.member.entity.Member;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

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
    private LocalDateTime regDt;


    public static MemberDetailDto of(Member member) {

        MemberDetailDto dto = new MemberDetailDto();
        dto.setMemberId(member.getMemberId());
        dto.setLoginId(member.getLoginId());
        dto.setMemNm(member.getMemNm());
        dto.setNickNm(member.getNickNm());
        dto.setMemType(member.getMemType());
        dto.setJoinDt(member.getJoinDt());
        dto.setPhoneNo(member.getPhoneNo());
        dto.setAddr(member.getAddr());
        dto.setAddrDtl(member.getAddrDtl());
        dto.setRegDt(member.getRegDt());
        return dto;
    }
}
