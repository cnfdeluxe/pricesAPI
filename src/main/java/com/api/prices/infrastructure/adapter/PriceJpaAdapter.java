package com.api.prices.infrastructure.adapter;

import com.api.prices.domain.model.Price;
import com.api.prices.domain.repository.PriceRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PriceJpaAdapter extends JpaRepository<Price, Long>, PriceRepository {

    @Query("SELECT p FROM Price p WHERE p.productId = :productId AND p.brandId = :brandId " +
            "AND :dateApplication BETWEEN p.startDate AND p.endDate ORDER BY p.priority DESC")
    List<Price> findApplicablePrice(Long productId, Long brandId, LocalDateTime dateApplication);

}
