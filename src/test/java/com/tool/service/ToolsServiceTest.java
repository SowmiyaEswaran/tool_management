package com.tool.service;

import com.tool.entity.Charges;
import com.tool.entity.RentalAgreement;
import com.tool.entity.ToolChargeRequest;
import com.tool.entity.Tools;
import com.tool.repository.ChargesRepository;
import com.tool.repository.RentalAgreementRepository;
import com.tool.repository.ToolsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class ToolsServiceTest {

    @Mock
    private ToolsRepository toolsRepo;

    @Mock
    private ChargesRepository chargeRepo;

    @Mock
    private RentalAgreementRepository rentalAgreementRepository;

    @InjectMocks
    private ToolsService toolsService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCalculateToolCharge_Scenario1() {
        testCalculateToolChargeScenario("JAKR", LocalDate.of(2015, 9, 3), 5, 101.0);
    }

    @Test
    public void testCalculateToolCharge_Scenario2() {
        testCalculateToolChargeScenario("LADW", LocalDate.of(2020, 7, 2), 3, 10.0);
    }

    @Test
    public void testCalculateToolCharge_Scenario3() {
        testCalculateToolChargeScenario("CHNS", LocalDate.of(2015, 7, 2), 5, 25.0);
    }

    @Test
    public void testCalculateToolCharge_Scenario4() {
        testCalculateToolChargeScenario("JAKD", LocalDate.of(2015, 9, 3), 6, 0.0);
    }

    @Test
    public void testCalculateToolCharge_Scenario5() {
        testCalculateToolChargeScenario("JAKR", LocalDate.of(2015, 7, 2), 9, 0.0);
    }

    @Test
    public void testCalculateToolCharge_Scenario6() {
        testCalculateToolChargeScenario("JAKR", LocalDate.of(2020, 7, 2), 4, 50.0);
    }

    private void testCalculateToolChargeScenario(String toolCode, LocalDate checkoutDate, int rentalDays, double discount) {
        // Given
        ToolChargeRequest request = new ToolChargeRequest();
        request.setToolCode(toolCode);
        request.setCheckoutDate(checkoutDate);
        request.setRentalDays(rentalDays);
        request.setDiscount(discount);

        Tools tool = new Tools();
        tool.setToolCode(toolCode);
        tool.setToolType("Type");
        tool.setBrand("Brand");

        Charges charges = new Charges();
        charges.setTool(toolCode);
        charges.setDailyCharge(10.0); // Example daily charge

        when(toolsRepo.findById(toolCode)).thenReturn(Optional.of(tool));
        when(chargeRepo.findById(toolCode)).thenReturn(Optional.of(charges));

        // When
        RentalAgreement result = toolsService.calculateToolCharge(request);

        // Then
        assertNotNull(result);
        assertEquals(toolCode, result.getToolCode());
        assertEquals("Type", result.getToolType());
        assertEquals("Brand", result.getToolBrand());
        assertEquals(10.0, result.getDailyRentalCharge());

        verify(toolsRepo, times(1)).findById(toolCode);
        verify(chargeRepo, times(1)).findById(toolCode);
        verify(rentalAgreementRepository, times(1)).save(any());
    }
}
