package com.radait.mariosample.web.api.member.entity;

import com.radait.mariosample.web.api.common.enums.entity.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class Member extends BaseEntity {

    private Long memberId;
    private String loginId;
    private String password;
    private String memNm;
    private String nickNm;
    private String memType;
    private LocalDate joinDt;
    private String phoneNo;
    private String addr;
    private String addrDtl;

    public Member(String loginId, String password, String memNm, String nickNm, String memberType,
                  LocalDate joinDateTime, String phoneNumber, String address, String addressDetail, String regId, String regIp) {

        this.loginId = loginId;
        this.password = password;
        this.memNm = memNm;
        this.nickNm = nickNm;
        this.memType = memberType;
        this.joinDt = joinDateTime;
        this.phoneNo = phoneNumber;
        this.addr = address;
        this.addrDtl = addressDetail;
        this.regId = regId;
        this.regIp = regIp;
    }

    public void updateMember(String nickNm,
                           String phoneNumber, String address, String addressDetail, String modId, String modIp) {

        this.nickNm = nickNm;
        this.phoneNo = phoneNumber;
        this.addr = address;
        this.addrDtl = addressDetail;
        this.modId = modId;
        this.modDt = LocalDateTime.now();
        this.modIp = modIp;
    }
}
