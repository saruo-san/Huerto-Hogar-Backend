package com.huertohogar.huerto_backend.payment;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import cl.transbank.webpay.webpayplus.WebpayPlus;
import cl.transbank.webpay.webpayplus.responses.WebpayPlusTransactionCommitResponse;
import cl.transbank.webpay.webpayplus.responses.WebpayPlusTransactionCreateResponse;
import cl.transbank.webpay.webpayplus.responses.WebpayPlusTransactionStatusResponse;

@Service
public class WebpayService {

    // Integration credentials provided via properties
    @Value("${transbank.webpay.commerce_code:597055555532}")
    private String commerceCode;

    @Value("${transbank.webpay.api_key:AzIjOGE3ZTRiZjA5YmQ4ODMwN2M3ZjU2YmQyNjU0ZjU=}")
    private String apiKey;

    @Value("${transbank.webpay.return_url:http://localhost:3000/payment/return}")
    private String returnUrl;

    // environment not needed for integration method

    private void configure() {
        // Configure SDK with integration credentials
        WebpayPlus.configureForIntegration(commerceCode, apiKey);
    }

    public WebpayPlusTransactionCreateResponse initTransaction(String buyOrder, String sessionId, double amount) throws Exception {
        configure();
        return new WebpayPlus.Transaction().create(buyOrder, sessionId, amount, returnUrl);
    }

    public WebpayPlusTransactionCommitResponse commitTransaction(String token) throws Exception {
        configure();
        return new WebpayPlus.Transaction().commit(token);
    }

    public WebpayPlusTransactionStatusResponse statusTransaction(String token) throws Exception {
        configure();
        return new WebpayPlus.Transaction().status(token);
    }
}
