package com.venmo.paypal.client.token;

import java.math.BigDecimal;
import java.util.List;
import java.util.logging.Logger;

import com.braintreegateway.*;
import com.braintreegateway.exceptions.BraintreeException;

public class BraintreeFactory {
	
	private static Logger logger = Logger.getLogger(BraintreeFactory.class.getName());
	
	private static BraintreeGateway gateway=null;
	private static String publicKey="6hxt9f474f4854zv";
	private static String privateKey="cb4f3640a3a80821b01a10c20dcdfb23";
	private static String merchantId = "srg93kz92n6tx4vb";

	public static void main(String[] args) {
		// Initialize Braintree Connection
		gateway = connectBrainTreeGateway();
		braintreeProcessing();
	}


	public static void braintreeProcessing()  {
	    System.out.println(" ----- BrainTree Implementation Starts --- ");

		String clientToken = generateClientToken();
	    System.out.println(" Client Token : " +clientToken);

		String nonceFromTheClient = receivePaymentMethodNonce();

		BigDecimal amount = new BigDecimal("5.10");

		doPaymentTransaction(nonceFromTheClient, amount);
	    
	}


	// Make payment
	public static void  doPaymentTransaction(String paymentMethodNonce, BigDecimal amount) {

		Boolean flag = true;
		TransactionRequest request = new TransactionRequest();
		request.amount(amount);
		request.paymentMethodNonce(paymentMethodNonce);

		CustomerRequest customerRequest = request.customer();
		customerRequest.email("kbog1983@gmail.com");
		customerRequest.firstName("kali");
		customerRequest.lastName("b");

		TransactionOptionsRequest options = request.options();
		options.submitForSettlement(flag);

		// Done the transaction request
		options.done();

		// Create transaction ...
		Result<Transaction> result = gateway.transaction().sale(request);
		boolean isSuccess = result.isSuccess();

		if (isSuccess) {
			Transaction transaction = result.getTarget();
			displayTransactionInfo(transaction);
		} else {
			ValidationErrors errors = result.getErrors();
			validationError(errors);
		}
	}

		private  static void displayTransactionInfo(Transaction transaction) {
			System.out.println(" ------ Transaction Info ------ ");
			System.out.println(" Transaction Id  : " +transaction.getId());
			System.out.println(" Processor Response Text : " +transaction.getProcessorResponseText());
		}

		private static void validationError(ValidationErrors errors) {
			List<ValidationError> error = errors.getAllDeepValidationErrors();
			for (ValidationError ers : error) {
				System.out.println(" error code : " + ers.getCode());
				System.out.println(" error message  : " + ers.getMessage());
			}

	}




	// Make an endpoint which receive payment method nonce from client and do payment.
	public static String receivePaymentMethodNonce() {
	     String nonceFromTheClient =  "fake-valid-amex-nonce";
	     return nonceFromTheClient;
	}
	
	public static BraintreeGateway connectBrainTreeGateway() {
		 BraintreeGateway braintreeGateway = new BraintreeGateway(
		            Environment.SANDBOX, merchantId, publicKey, privateKey);
		    return braintreeGateway;
		
		
	}
	
	public static String generateClientToken() {
	    // client token will be generated at server side and return to client
	    String clientToken = gateway.clientToken().generate();
	    return clientToken;
	}


}
