package com.radait.mariosample.web.api.member.entity;

import com.radait.mariosample.web.api.common.entity.BaseEntity;
import com.radait.mariosample.web.api.member.dto.MemberFormDto;
import com.radait.mariosample.web.api.common.entity.IpHolder;
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

    public void updateMember(MemberFormDto dto) {

        if (dto.getMemNm() != null) this.memNm = dto.getMemNm();
        if (dto.getNickNm() != null) this.nickNm = dto.getNickNm();
        if (dto.getMemType() != null) this.memType = dto.getMemType();
        if (dto.getPhoneNo() != null) this.phoneNo = dto.getPhoneNo();
        if (dto.getAddr() != null) this.addr = dto.getAddr();
        if (dto.getAddrDtl() != null) this.addrDtl = dto.getAddrDtl();

        this.modId = dto.getLoginId();
        this.modIp = IpHolder.getIp();
        this.modDt = LocalDateTime.now();
    }
}
