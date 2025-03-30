package com.api.prices.application.service;

import com.api.prices.application.dto.PriceInputDTO;
import com.api.prices.application.dto.PriceOutputDTO;
import com.api.prices.application.exception.PriceNotFoundException;
import com.api.prices.domain.model.Price;
import com.api.prices.domain.repository.PriceRepository;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class PriceService {

    private final PriceRepository priceRepository;

    public PriceService(PriceRepository priceRepository) {
        this.priceRepository = priceRepository;
    }

    public PriceOutputDTO getPrice(PriceInputDTO inputDTO){

        List<Price> prices = priceRepository.findApplicablePrice(inputDTO.getProductID(),
                                                                 inputDTO.getBrandID(),
                                                                 inputDTO.getDateApplication());

        return prices.stream()
                .max(Comparator.comparingInt(Price::getPriority))
                .map(price -> new PriceOutputDTO(price.getProductId(), price.getBrandId(), price.getPrice(),
                                                        price.getCurrency(), price.getStartDate(), price.getEndDate(),
                                                        price.getPriceList()))
                .orElseThrow(() -> new PriceNotFoundException("Not price found for product " + inputDTO.getProductID()));
    }


}
