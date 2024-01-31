package com.wanted.preonboarding.ticket.domain.entity;

import com.wanted.preonboarding.ticket.domain.dto.PerformanceInfo;
import com.wanted.preonboarding.ticket.domain.dto.PerformanceType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.UUID;

@Entity
@Table
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Performance {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private Integer price;
    @Column(nullable = false)
    private Integer round;
    @Column(nullable = false)
    private Integer type;
    @Column(nullable = false, name = "start_date")
    private Timestamp startDate;
    @Column(columnDefinition = "varchar(255) default 'disable'", nullable = false, name = "is_reserve")
    private String isReserve;

    public static Performance of (PerformanceInfo info) {
        return Performance.builder()
                .id(info.getPerformanceId())
                .name(info.getPerformanceName())
                .price(info.getPrice())
                .round(info.getRound())
                .type(convertNameToCode(info.getPerformanceType()))
                .startDate(info.getStartDate())
                .isReserve(info.getIsReserve())
                .build();
    }

    private static Integer convertNameToCode(String name) {
        return Arrays.stream(PerformanceType.values())
                .filter(value -> value.name().equals(name))
                .findFirst()
                .map(PerformanceType::getCategory)
                .orElse(PerformanceType.NONE.getCategory());
    }
}
