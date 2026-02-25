package com.example.propertyview.IT;

import com.example.propertyview.repository.HotelRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class HotelControllerIT {

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private Long createdHotelId;

    @BeforeEach
    void setup() throws Exception {

        Map<String, Object> hotel = Map.of(
                "name", "DoubleTree by Hilton Minsk",
                "description", "Nice hotel",
                "brand", "Hilton",
                "address", Map.of(
                        "houseNumber", 9,
                        "street", "Pobediteley Avenue",
                        "city", "Minsk",
                        "country", "Belarus",
                        "postCode", "220004"
                ),
                "contacts", Map.of(
                        "phone", "+375 17 309-80-00",
                        "email", "doubletreeminsk.info@hilton.com"
                ),
                "arrivalTime", Map.of(
                        "checkIn", "14:00",
                        "checkOut", "12:00"
                )
        );

        String response = mockMvc.perform(post("/property-view/hotels")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(hotel)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        createdHotelId = objectMapper.readTree(response).get("id").asLong();
    }

    @Test
    void shouldReturnAllHotels() throws Exception {
        mockMvc.perform(get("/property-view/hotels"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").exists());
    }

    @Test
    void shouldReturnHotelById() throws Exception {
        mockMvc.perform(get("/property-view/hotels/" + createdHotelId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.brand").value("Hilton"))
                .andExpect(jsonPath("$.address.city").value("Minsk"));
    }

    @Test
    void shouldSearchByCityIgnoreCase() throws Exception {
        mockMvc.perform(get("/property-view/search")
                        .param("city", "minsk"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("DoubleTree by Hilton Minsk"));
    }

    @Test
    void shouldAddAmenities() throws Exception {

        List<String> amenities = List.of("Free WiFi", "Fitness center");

        mockMvc.perform(post("/property-view/hotels/" + createdHotelId + "/amenities")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(amenities)))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/property-view/hotels/" + createdHotelId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.amenities.length()").value(2));
    }

    @Test
    void shouldReturnHistogramByCity() throws Exception {
        mockMvc.perform(get("/property-view/histogram/city"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.Minsk").value(1));
    }
}