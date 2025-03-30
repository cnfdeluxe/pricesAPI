package com.api.prices.application.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PriceInputDTO {

    private Long productID;
    private Long brandID;
    private LocalDateTime dateApplication;


}
