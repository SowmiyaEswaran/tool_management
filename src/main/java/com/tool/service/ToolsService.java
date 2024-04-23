package com.tool.service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tool.entity.Charges;
import com.tool.entity.RentalAgreement;
import com.tool.entity.ToolChargeRequest;
import com.tool.entity.Tools;
import com.tool.repository.ChargesRepository;
import com.tool.repository.RentalAgreementRepository;
import com.tool.repository.ToolsRepository;

@Service
public class ToolsService {
	
	@Autowired
	private ToolsRepository toolsRepo;
	
	@Autowired
	private ChargesRepository chargeRepo;
	
	@Autowired 
	private RentalAgreementRepository rentalAgreementRepository;

	public RentalAgreement calculateToolCharge(ToolChargeRequest request) {
        Tools tool = toolsRepo.findById(request.getToolCode()).orElseThrow(() -> new RuntimeException("Tool not found"));
        
        Optional<Charges> charges = chargeRepo.findById(tool.getToolCode());
        
        List<LocalDate> holidays = getHolidays(request.getCheckoutDate().getYear());

        LocalDate dueDate = request.getCheckoutDate().plusDays(request.getRentalDays());
        int weekendDays = 0;
        int holidayDays = 0;

        for (LocalDate date = request.getCheckoutDate(); 
        		date.isBefore(dueDate); date = date.plusDays(1)) {
            DayOfWeek dayOfWeek = date.getDayOfWeek();
            if (dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY) {
                weekendDays++;
            }else if (holidays.contains(date)) {
                holidayDays++;
            }
        }

        int chargeDays = request.getRentalDays() - weekendDays  - holidayDays;
        double dailyCharge = charges.get().getDailyCharge();
        double totalCharge = dailyCharge * chargeDays;

        double discountAmount  = request.getDiscount() * totalCharge /100;
        
        
        RentalAgreement agreement = new RentalAgreement();
        agreement.setToolCode(request.getToolCode());
        agreement.setToolType(tool.getToolType());
        agreement.setToolBrand(tool.getBrand());
        agreement.setDicountPercent(request.getDiscount());
        agreement.setCheckoutDate(request.getCheckoutDate());
        agreement.setDueDate(request.getCheckoutDate().plusDays(request.getRentalDays()));
        agreement.setRentalDays(request.getRentalDays());
        agreement.setPreDiscountCharge(totalCharge);
        agreement.setDiscountAmount(discountAmount);
        agreement.setFinalCharge(totalCharge -  discountAmount);
        agreement.setChargeDates(chargeDays);
        agreement.setDailyRentalCharge(dailyCharge);
        
        rentalAgreementRepository.save(agreement);
        return agreement;
    }

    public List<LocalDate> getHolidays(int year) {
        List<LocalDate> holidays = new ArrayList<>();

        // Independence Day - July 4th or observed on closest weekday
        LocalDate independenceDay = LocalDate.of(year, 7, 4);
        if (independenceDay.getDayOfWeek() == DayOfWeek.SATURDAY) {
            independenceDay = independenceDay.minusDays(1); // Friday before
        } else if (independenceDay.getDayOfWeek() == DayOfWeek.SUNDAY) {
            independenceDay = independenceDay.plusDays(1); // Monday after
        }
        holidays.add(independenceDay);

        // Labor Day - First Monday in September
        LocalDate laborDay = LocalDate.of(year, 9, 1);
        while (laborDay.getDayOfWeek() != DayOfWeek.MONDAY) {
            laborDay = laborDay.plusDays(1);
        }
        holidays.add(laborDay);

        return holidays;
    }
}
