package com.furkandogan.controller.ımpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.furkandogan.controller.IRestCurrencyRatesController;
import com.furkandogan.controller.RestBaseController;
import com.furkandogan.controller.RootEntity;
import com.furkandogan.dto.CurrencyRatesResponse;
import com.furkandogan.service.ICurrencyRatesService;

@RestController
@RequestMapping ("/rest/api/")
public class RestCurrencyRatesControllerImpl extends RestBaseController implements IRestCurrencyRatesController{

	@Autowired
	private ICurrencyRatesService currencyRatesService;
	
	@GetMapping ("/currency-rates")
	@Override
	public RootEntity<CurrencyRatesResponse> getCurrencyRates(@RequestParam("startDate") String startDate, 
			@RequestParam ("endDate") String endDate) {
		
		return ok(currencyRatesService.getCurrencyRates(startDate, endDate));
	}

}
