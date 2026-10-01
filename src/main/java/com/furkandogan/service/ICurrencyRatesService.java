package com.furkandogan.service;

import com.furkandogan.dto.CurrencyRatesResponse;

public interface ICurrencyRatesService {

	public CurrencyRatesResponse getCurrencyRates (String startDate , String endDate);
}
