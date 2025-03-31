package com.api.prices.services;

import com.api.prices.application.dto.PriceInputDTO;
import com.api.prices.application.dto.PriceOutputDTO;
import com.api.prices.application.exception.PriceNotFoundException;
import com.api.prices.application.service.PriceService;
import com.api.prices.domain.model.Price;
import com.api.prices.domain.repository.PriceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PriceServiceTest {
    @Mock
    private PriceRepository priceRepository;

    @InjectMocks
    private PriceService priceService;

    private static final Long PRODUCT_ID = 35455L;
    private static final Long BRAND_ID = 1L;

    @BeforeEach
    void setUp() {
        Mockito.reset(priceRepository);
    }

    private Price createPrice(LocalDateTime startDate, LocalDateTime endDate, BigDecimal price, int priority, String curr, int priceList) {
        return new Price(null, BRAND_ID, PRODUCT_ID, price, curr,priority,startDate,endDate,priceList);
    }

    // 1. Prueba 1: Realizar una petición a las 10:00 del día 14 para el producto 35455 y la marca 1 (ZARA).
    @Test
    void test1() {
        LocalDateTime dateApplication = LocalDateTime.of(2020, 6, 14, 10, 0);
        Price expectedPrice = createPrice(dateApplication.minusDays(1), dateApplication.plusDays(1), new BigDecimal("35.50"), 1, "EUR",1);

        when(priceRepository.findApplicablePrice(PRODUCT_ID, BRAND_ID, dateApplication))
                .thenReturn(List.of(expectedPrice));

        PriceOutputDTO result = priceService.getPrice(new PriceInputDTO(PRODUCT_ID, BRAND_ID, dateApplication));

        assertThat(result.getPrice()).isEqualTo(expectedPrice.getPrice());
    }

    // 2. Prueba 2: Realizar una petición a las 16:00 del día 14 para el producto 35455 y la marca 1 (ZARA).
    @Test
    void test2() {
        LocalDateTime dateApplication = LocalDateTime.of(2020, 6, 14, 16, 0);
        Price expectedPrice = createPrice(dateApplication.minusDays(1), dateApplication.plusDays(1), new BigDecimal("25.45"), 2, "EUR",1);

        when(priceRepository.findApplicablePrice(PRODUCT_ID, BRAND_ID, dateApplication))
                .thenReturn(List.of(expectedPrice));

        PriceOutputDTO result = priceService.getPrice(new PriceInputDTO(PRODUCT_ID, BRAND_ID, dateApplication));

        assertThat(result.getPrice()).isEqualTo(expectedPrice.getPrice());
    }

    // 3. Prueba 3: Realizar una petición a las 21:00 del día 14 para el producto 35455 y la marca 1 (ZARA).
    @Test
    void test3() {
        LocalDateTime dateApplication = LocalDateTime.of(2020, 6, 14, 21, 0);
        Price expectedPrice = createPrice(dateApplication.minusDays(1), dateApplication.plusDays(1), new BigDecimal("30.50"), 1, "EUR",1);

        when(priceRepository.findApplicablePrice(PRODUCT_ID, BRAND_ID, dateApplication))
                .thenReturn(List.of(expectedPrice));

        PriceOutputDTO result = priceService.getPrice(new PriceInputDTO(PRODUCT_ID, BRAND_ID, dateApplication));

        assertThat(result.getPrice()).isEqualTo(expectedPrice.getPrice());
    }

    // 4. Prueba 4: Realizar una petición a las 10:00 del día 15 para el producto 35455 y la marca 1 (ZARA).
    @Test
    void test4() {
        LocalDateTime dateApplication = LocalDateTime.of(2020, 6, 15, 10, 0);
        Price expectedPrice = createPrice(dateApplication.minusDays(1), dateApplication.plusDays(1), new BigDecimal("35.50"), 1, "EUR",1);

        when(priceRepository.findApplicablePrice(PRODUCT_ID, BRAND_ID, dateApplication))
                .thenReturn(List.of(expectedPrice));

        PriceOutputDTO result = priceService.getPrice(new PriceInputDTO(PRODUCT_ID, BRAND_ID, dateApplication));

        assertThat(result.getPrice()).isEqualTo(expectedPrice.getPrice());
    }

    // 5. Prueba 5: Realizar una petición a las 21:00 del día 16 para el producto 35455 y la marca 1 (ZARA).
    @Test
    void test5() {
        LocalDateTime dateApplication = LocalDateTime.of(2020, 6, 16, 21, 0);
        Price expectedPrice = createPrice(dateApplication.minusDays(1), dateApplication.plusDays(1), new BigDecimal("38.95"), 1, "EUR",1);

        when(priceRepository.findApplicablePrice(PRODUCT_ID, BRAND_ID, dateApplication))
                .thenReturn(List.of(expectedPrice));

        PriceOutputDTO result = priceService.getPrice(new PriceInputDTO(PRODUCT_ID, BRAND_ID, dateApplication));

        assertThat(result.getPrice()).isEqualTo(expectedPrice.getPrice());
    }

    // Prueba para exception no price found
    @Test
    void test6_noPriceFound_shouldThrowException() {
        LocalDateTime dateApplication = LocalDateTime.of(2020, 6, 17, 10, 0);
        when(priceRepository.findApplicablePrice(PRODUCT_ID, BRAND_ID, dateApplication)).thenReturn(List.of());

        assertThatThrownBy(() -> priceService.getPrice(new PriceInputDTO(PRODUCT_ID, BRAND_ID, dateApplication)))
                .isInstanceOf(PriceNotFoundException.class)
                .hasMessageContaining("Not price found for product " + PRODUCT_ID);
    }

}
