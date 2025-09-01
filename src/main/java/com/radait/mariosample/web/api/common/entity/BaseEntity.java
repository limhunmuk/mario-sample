package com.radait.mariosample.web.api.common.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

/**
 * @author hunmuk
 */
@SuperBuilder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BaseEntity extends SimpleBaseEntity {

    @LastModifiedBy
    protected String modId;

    protected String modIp;

    @LastModifiedDate
    protected LocalDateTime modDt;

    public BaseEntity(String modId, String modIp, LocalDateTime modDt) {
        this.modId = modId;
        this.modIp = IpHolder.getIp();
        this.modDt = modDt;
    }

}
