package com.furkandogan.service.ımpl;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.furkandogan.dto.CurrencyRatesResponse;
import com.furkandogan.exception.BaseException;
import com.furkandogan.exception.ErrorMessage;
import com.furkandogan.exception.MessageType;
import com.furkandogan.service.ICurrencyRatesService;

@Service
public class CurrencyRatesServiceImpl implements ICurrencyRatesService{
	
	@Value("${evds.api.key}")
	private String apiKey;

	@Cacheable(value = "currencyRates", key = "#startDate + '-' + #endDate")
	@Override
	public CurrencyRatesResponse getCurrencyRates(String startDate, String endDate) {
		
		String rootUrl ="https://evds3.tcmb.gov.tr/igmevdsms-dis/";
		String series = "TP.DK.USD.S.YTL";
		String type = "json";
		
		String endpoint = rootUrl+"series="+series+"&startDate="+startDate+"&endDate="+endDate+"&type="+type;
		
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.set("key", apiKey); 
		
		HttpEntity<?> httpEntity = new HttpEntity<>(httpHeaders);
		

		try {
		
			RestTemplate restTemplate  = new RestTemplate();
			 ResponseEntity<CurrencyRatesResponse> response =	
					 restTemplate.exchange(endpoint, HttpMethod.GET, httpEntity, new ParameterizedTypeReference <CurrencyRatesResponse>() {
				});
			 if (response.getStatusCode().is2xxSuccessful()) {
				 return response.getBody();
			 }
			 
			
		} catch (Exception e) {
			throw new BaseException(new ErrorMessage(MessageType.CURRENCY_RATES_IS_OCCURED,e.getMessage()));
		}
		return null;
		

		
	}

}
