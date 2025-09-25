package com.rodait.mariosample.web.api.common.entity;

import com.rodait.mariosample.web.api.common.enums.YNCode;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@SuperBuilder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SimpleBaseEntity {

    @CreatedBy
    protected String regId;

    protected String regIp;

    @CreatedDate
    protected LocalDateTime regDt;

    private YNCode delYn;

    public SimpleBaseEntity(String regId, String regIp, YNCode delYn) {
        this.regId = regId;
        this.regIp = IpHolder.getIp();
        this.delYn = delYn;
        this.regDt = LocalDateTime.now();
    }
}
