package com.rodait.mariosample.web.api.member.entity;

import com.rodait.mariosample.web.api.common.entity.BaseEntity;
import com.rodait.mariosample.web.api.member.dto.MemberFormDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;

@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class Member extends BaseEntity implements UserDetails {

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

    public void updateForm(MemberFormDto dto, String ipAddress) {

        if (dto.getMemNm() != null) this.memNm = dto.getMemNm();
        if (dto.getNickNm() != null) this.nickNm = dto.getNickNm();
        if (dto.getMemType() != null) this.memType = dto.getMemType();
        if (dto.getPhoneNo() != null) this.phoneNo = dto.getPhoneNo();
        if (dto.getAddr() != null) this.addr = dto.getAddr();
        if (dto.getAddrDtl() != null) this.addrDtl = dto.getAddrDtl();

        this.modId = dto.getLoginId();
        this.modIp = ipAddress;
        this.modDt = LocalDateTime.now();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return AuthorityUtils.createAuthorityList("ROLE_USER");
    }

    @Override
    public String getUsername() {
        return this.loginId;
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }
}
