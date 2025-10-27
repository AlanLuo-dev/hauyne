package com.luoyx.hauyne.jpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

/**
 * @author Alan.Luo
 * @since 2023/4/27 23:21
 */
@Data()
@EqualsAndHashCode(callSuper = true)
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity extends IdEntity {

    /**
     * 创建人
     */
    @CreatedBy
    @Column(name = "created_by")
    private Long createdBy;

    /**
     * 创建时间
     */
    @CreatedDate
    @Column(name = "created_time")
    private LocalDateTime createdTime;

    /**
     * 最后修改人
     */
    @LastModifiedBy
    @Column(name = "last_updated_by")
    private Long lastUpdatedBy;

    /**
     * 最后修改时间
     */
    @LastModifiedDate
    @Column(name = "last_updated_time")
    private LocalDateTime lastUpdatedTime;
}
