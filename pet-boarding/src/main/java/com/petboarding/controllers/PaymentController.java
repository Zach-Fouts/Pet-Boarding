package com.petboarding.controllers;


import com.petboarding.models.dto.CreatePayment;
import com.petboarding.models.dto.CreatePaymentResponse;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@RestController
public class PaymentController{

//    successful payment 4242424242424242
//    Generic decline	4000000000000002	card_declined
//    Insufficient funds decline	4000000000009995
//    Lost card decline	4000000000009987
//    Stolen card decline	4000000000009979
//    Expired card decline	4000000000000069
//    Incorrect CVC decline	4000000000000127
//    Processing error decline	4000000000000119
//    Incorrect number decline	4242424242424241

    @Value("${stripe.api.secureKey}")
    private String stripeApiKey;

    @PostMapping("/create-payment-intent")
    public CreatePaymentResponse createPaymentIntent(@RequestBody CreatePayment createPayment) throws StripeException {
        Stripe.apiKey = stripeApiKey;//"sk_test_51MYex8AVgyan8JxQcbStGLELy2iB7XIVgLnGfoWiKwqA5v2oY2DXDffQGUA0HTSusy1gH9tQrsUAhjbdwCjOAGLt00ip3tBW6D";
            PaymentIntentCreateParams params = PaymentIntentCreateParams.builder()
                    .setCurrency("usd")
                    .setAmount(100 * 100L) //product amount
                    .build();

            // Create a PaymentIntent with the order amount and currency
            PaymentIntent paymentIntent = PaymentIntent.create(params);

            return new CreatePaymentResponse(paymentIntent.getClientSecret());

    }
}
