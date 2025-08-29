package com.radait.mariosample.web.api.member.dto;

import com.radait.mariosample.web.api.common.enums.YNCode;
import com.radait.mariosample.web.api.member.entity.Member;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class MemberFormDto {

    private Long memberId;
    private String email;
    private String password;
    private String confirmPassword;

    private String memNm;
    private String nickNm;
    private String memberType;

    private LocalDate joinDt;
    private String phoneNumber;
    private String address;
    private String addressDetail;

    private String ipAddress;
    private LocalDateTime regDt;

    public Member toEntity() {
        return Member.builder()
                .memberId(memberId)
                .loginId(email)
                .memNm(memNm)
                .password(password)
                .nickNm(nickNm)
                .memType(memberType)
                .joinDt(joinDt)
                .phoneNo(phoneNumber)
                .addr(address)
                .addrDtl(addressDetail)
                .regDt(LocalDateTime.now())
                .regIp(ipAddress)
                .regId(email)
                .delYn(YNCode.N)
                .build();
    }
}
