package com.placement.measurementservice.controller;

import com.placement.measurementservice.service.MeasurementService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MeasurementController.class)
@Import(MeasurementExceptionHandler.class)
class MeasurementControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MeasurementService measurementService;

    @Test
    void returnsConvertedValue() throws Exception {
        when(measurementService.convert("length", "meter", "centimeter", 2.0)).thenReturn(200.0);

        mockMvc.perform(get("/measure/convert")
                        .param("type", "length")
                        .param("fromUnit", "meter")
                        .param("toUnit", "centimeter")
                        .param("value", "2"))
                .andExpect(status().isOk())
                .andExpect(content().string("200.0"));
    }

    @Test
    void returnsBadRequestForInvalidInput() throws Exception {
        when(measurementService.convert("speed", "meter", "kilometer", 2.0))
                .thenThrow(new IllegalArgumentException("Unknown measurement type: speed"));

        mockMvc.perform(get("/measure/convert")
                        .param("type", "speed")
                        .param("fromUnit", "meter")
                        .param("toUnit", "kilometer")
                        .param("value", "2"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Unknown measurement type: speed"));
    }
}
