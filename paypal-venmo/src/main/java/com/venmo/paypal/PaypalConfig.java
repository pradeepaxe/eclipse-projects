package com.venmo.paypal;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.paypal.base.exception.PayPalException;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.OAuthTokenCredential;
import com.paypal.base.rest.PayPalRESTException;

@Configuration
public class PaypalConfig {
	
	@Value("${paypal.client.id}")
	private String clientId;

	@Value("${paypal.client.secret}")
	private String clientSecret;
	
	@Value("${paypal.mode}")
	private String mode;
	
	@Bean
	public Map<String,String> paypalSDKConfig(){
		Map<String,String> configMap  = new HashMap<>();
		configMap.put("mode", mode);
		return configMap;
	}
	
	@Bean
	public OAuthTokenCredential oAuthTokenCredential() {
		
		return new OAuthTokenCredential(clientId, clientSecret, paypalSDKConfig());
		
	}
	
	@Bean
	public APIContext apiContext() throws PayPalException, PayPalRESTException{
		APIContext context =  new APIContext(oAuthTokenCredential().getAccessToken());
		context.setConfigurationMap(paypalSDKConfig());
		return context;
	}
	
	
}
