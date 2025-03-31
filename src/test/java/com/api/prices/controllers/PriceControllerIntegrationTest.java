package com.api.prices.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class PriceControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;


    @Test
    public void testPriceAt10onJune14() throws Exception {
        mockMvc.perform(get("/api/prices")
                        .param("product_id", "35455")
                        .param("brand_id", "1")
                        .param("date_application", "2020-06-14T10:00:00"))
                .andDo(print())  // Imprime la respuesta en la consola
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.product_id").value(35455))
                .andExpect(jsonPath("$.data.brand_id").value(1))
                .andExpect(jsonPath("$.data.price_list").value(1))
                .andExpect(jsonPath("$.data.price").value(35.50));
    }


    @Test
    public void testPriceAt4PMOnJune14() throws Exception {
        mockMvc.perform(get("/api/prices")
                        .param("product_id", "35455")
                        .param("brand_id", "1")
                        .param("date_application", "2020-06-14T16:00:00"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.product_id").value(35455))
                .andExpect(jsonPath("$.data.brand_id").value(1))
                .andExpect(jsonPath("$.data.price_list").value(2))
                .andExpect(jsonPath("$.data.price").value(25.45));
    }

    @Test
    public void testPriceAt9PMOnJune14() throws Exception {
        mockMvc.perform(get("/api/prices")
                        .param("product_id", "35455")
                        .param("brand_id", "1")
                        .param("date_application", "2020-06-14T21:00:00"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.product_id").value(35455))
                .andExpect(jsonPath("$.data.brand_id").value(1))
                .andExpect(jsonPath("$.data.price_list").value(1))
                .andExpect(jsonPath("$.data.price").value(35.50));
    }

    @Test
    public void testPriceAt10AMOnJune15() throws Exception {
        mockMvc.perform(get("/api/prices")
                        .param("product_id", "35455")
                        .param("brand_id", "1")
                        .param("date_application", "2020-06-15T10:00:00"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.product_id").value(35455))
                .andExpect(jsonPath("$.data.brand_id").value(1))
                .andExpect(jsonPath("$.data.price_list").value(3))
                .andExpect(jsonPath("$.data.price").value(30.50));
    }

    @Test
    public void testPriceAt9PMOnJune16() throws Exception {
        mockMvc.perform(get("/api/prices")
                        .param("product_id", "35455")
                        .param("brand_id", "1")
                        .param("date_application", "2020-06-16T21:00:00"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.product_id").value(35455))
                .andExpect(jsonPath("$.data.brand_id").value(1))
                .andExpect(jsonPath("$.data.price_list").value(4))
                .andExpect(jsonPath("$.data.price").value(38.95));
    }



}
