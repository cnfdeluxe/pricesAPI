package com.api.prices.controllers;

import com.api.prices.application.dto.PriceInputDTO;
import com.api.prices.application.dto.PriceOutputDTO;
import com.api.prices.application.service.PriceService;
import com.api.prices.infrastructure.rest.PriceController;
import com.api.prices.infrastructure.utils.ApiResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.util.AssertionErrors.assertEquals;

@ExtendWith(MockitoExtension.class)
public class PriceControllerTest {

    @Mock
    private PriceService priceService;

    @InjectMocks
    private PriceController priceController;

    @Test
    public void testGetPrice() {
        // Datos de entrada
        Long productID = 35455L;
        Long brandID = 1L;
        String dateApplication = "2020-06-14T16:00:00";

        // Datos esperados
        PriceOutputDTO mockResponse = new PriceOutputDTO(35455L, 1L, new BigDecimal("25.45"), "EUR",
                LocalDateTime.parse("2020-06-14T16:00:00"),
                LocalDateTime.parse("2020-06-14T18:00:00"), 2);

        // Mock de la respuesta del servicio
        when(priceService.getPrice(any(PriceInputDTO.class))).thenReturn(mockResponse);

        // Llamamos al método del controlador
        ResponseEntity<?> response = priceController.getPrice(productID, brandID, dateApplication);

        // Obtenemos la respuesta real
        ApiResponse<PriceOutputDTO> apiResponse = (ApiResponse<PriceOutputDTO>) response.getBody();

        // Verificamos la respuesta
        assertEquals("The status code should be 200 OK", HttpStatus.OK, response.getStatusCode());
        assertEquals("The product_id should be 35455", 35455L, apiResponse.getData().getProductID());
        assertEquals("The brand_id should be 1", 1L, apiResponse.getData().getBrandID());
        assertEquals("The price_list should be 2", 2, apiResponse.getData().getPriceList());
        assertEquals("The price should be 25.45", new BigDecimal("25.45"), apiResponse.getData().getPrice());
    }


}
