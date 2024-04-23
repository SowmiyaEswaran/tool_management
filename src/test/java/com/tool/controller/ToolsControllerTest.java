package com.tool.controller;

import com.tool.entity.RentalAgreement;
import com.tool.entity.ToolChargeRequest;
import com.tool.service.ToolsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class ToolsControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ToolsService toolsService;

    @InjectMocks
    private ToolsController toolsController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(toolsController).build();
    }

    @Test
    public void testCalculateToolCharge() throws Exception {
        // Given
        ToolChargeRequest request = new ToolChargeRequest();
        request.setToolCode("Hammer");
        request.setRentalDays(5);
        request.setDiscount(10.0);

        RentalAgreement rentalAgreement = new RentalAgreement();
        rentalAgreement.setFinalCharge(50.0); // Example total charge

        when(toolsService.calculateToolCharge(any())).thenReturn(rentalAgreement);

        // When/Then
        mockMvc.perform(post("/toolsCharge")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"toolName\":\"Hammer\",\"rentalDays\":5,\"discount\":10.0}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.finalCharge").value(50.0)); // Example expected total charge

        verify(toolsService, times(1)).calculateToolCharge(any());
    }
}
