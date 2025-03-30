package com.api.prices.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PriceOutputDTO {

    @JsonProperty("product_id")
    private Long productID;
    @JsonProperty("brand_id")
    private Long brandID;
    @JsonProperty("price")
    private BigDecimal price;
    @JsonProperty("currency")
    private String currency;
    @JsonProperty("start_date")
    private LocalDateTime startDate;
    @JsonProperty("end_date")
    private LocalDateTime endDate;
    @JsonProperty("price_list")
    private Integer priceList;


}
