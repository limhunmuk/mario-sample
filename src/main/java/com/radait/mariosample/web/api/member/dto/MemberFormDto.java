package com.radait.mariosample.web.api.member.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.radait.mariosample.web.api.common.enums.YNCode;
import com.radait.mariosample.web.api.member.entity.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Jacksonized
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "loginId", "password", "confirmPassword",
        "memNm", "memType", "nickNm",
        "addr", "addrDtl", "phoneNo",
        "joinDt", "memberId"
})
public class MemberFormDto {

    private String memberId;

    //@NotBlank(message = "로그인 아이디는 필수입니다.")
    private String loginId;

    //@NotBlank(message = "비밀번호는 필수 입니다")
    private String password;
    private String confirmPassword;

    private String memNm;
    private String nickNm;
    private String memType;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate joinDt;
    private String phoneNo;
    private String addr;
    private String addrDtl;

    /**
     * 엔티티 변환
     * @return
     */
    public Member toEntity(String ipAddress) {

        LocalDate now = LocalDate.now();

        return Member.builder()
                .loginId(loginId)
                .memNm(memNm)
                .password(password)
                .nickNm(nickNm)
                .memType(memType)
                .joinDt(now)
                .phoneNo(phoneNo)
                .addr(addr)
                .addrDtl(addrDtl)
                .regDt(LocalDateTime.now())
                .regIp(ipAddress)
                .regId(loginId)
                .modDt(LocalDateTime.now())
                .modIp(ipAddress)
                .modId(loginId)
                .delYn(YNCode.N)
                .build();
    }

    /**
     * 유효성 검사
     * @param oldMember
     * @param newMember
     * @return
     */
    public Boolean validate(Member oldMember, MemberFormDto newMember) {

        if (!oldMember.getMemberId().equals(newMember.getMemberId())) {
            return false;
        }

        if(!oldMember.getLoginId().equals(newMember.getLoginId())) {
            return false;
        }

        return true;
    }
}
