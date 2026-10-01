package com.furkandogan.controller;

import com.furkandogan.dto.CurrencyRatesResponse;

public interface IRestCurrencyRatesController {

	public RootEntity<CurrencyRatesResponse> getCurrencyRates (String startDate , String endDate);
}
