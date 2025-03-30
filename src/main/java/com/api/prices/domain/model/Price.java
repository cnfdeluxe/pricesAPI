package com.api.prices.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "prices")
public class Price {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    @Column(name = "BRAND_ID")
    Long brandId;
    @Column(name = "PRODUCT_ID")
    Long productId;
    @Column(name = "PRICE")
    BigDecimal price;
    @Column(name = "CURR")
    String currency;
    @Column(name = "PRIORITY")
    Integer priority;
    @Column(name = "START_DATE")
    LocalDateTime startDate;
    @Column(name = "END_DATE")
    LocalDateTime endDate;
    @Column(name = "PRICE_LIST")
    Integer priceList;

}
