package com.api.prices.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PriceInputDTO {

    @JsonProperty("product_id")
    @Schema(description = "Product ID", example = "35455")
    private Long productID;
    @JsonProperty("brand_id")
    @Schema(description = "Brand ID", example = "1")
    private Long brandID;
    @JsonProperty("date_applitacion")
    @Schema(description = "Application date", example = "2023-06-14T10:00:00")
    private LocalDateTime dateApplication;


}
