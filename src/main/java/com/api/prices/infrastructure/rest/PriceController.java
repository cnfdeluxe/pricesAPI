package com.api.prices.infrastructure.rest;

import com.api.prices.application.dto.PriceInputDTO;
import com.api.prices.application.dto.PriceOutputDTO;
import com.api.prices.application.service.PriceService;
import com.api.prices.infrastructure.utils.ResponseBuilder;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/prices")
@Tag(name = "Prices API", description = "Api for Inditex price query")
public class PriceController {

    private final PriceService priceService;

    public PriceController(PriceService priceService) {
        this.priceService = priceService;
    }


    @Operation(
            summary = "Get product price.",
            description = "Returns the applicable price for a product of a brand on a specific date."
    )
    @GetMapping("/{product_id}/{brand_id}/{date_application}")
    public ResponseEntity<?> getPrice(@PathVariable("product_id") Long productID,
                                      @PathVariable("brand_id") Long brandID,
                                      @PathVariable("date_application") LocalDateTime dateApplication){

        //Se setea el inputDTO con los parámetros de entrada
        PriceInputDTO inputDTO = new PriceInputDTO(productID, brandID, dateApplication);
        PriceOutputDTO priceOutputDTO = priceService.getPrice(inputDTO);

        return ResponseBuilder.with(HttpStatus.OK, true, priceOutputDTO, "It has been done correctly");
    }


}
