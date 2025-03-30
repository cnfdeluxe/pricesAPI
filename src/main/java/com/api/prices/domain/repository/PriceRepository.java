package com.api.prices.domain.repository;

import com.api.prices.domain.model.Price;

import java.time.LocalDateTime;
import java.util.List;


public interface PriceRepository {

    List<Price> findApplicablePrice(Long productId, Long brandId, LocalDateTime dateApplication);

}
