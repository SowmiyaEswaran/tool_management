package com.tool.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.tool.entity.RentalAgreement;
import com.tool.entity.ToolChargeRequest;
import com.tool.service.ToolsService;



@RestController
public class ToolsController {
	
	@Autowired
	private ToolsService toolsService;
	
	@PostMapping("/toolsCharge")
	public RentalAgreement calculateToolCharge(@RequestBody ToolChargeRequest request) {
		return toolsService.calculateToolCharge(request);
	}

}
