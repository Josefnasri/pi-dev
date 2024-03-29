package tn.esprit.crud.services;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;

public class Payment {
    public void processPayment(long price) {
        try {
            // Set your secret key here
            Stripe.apiKey = "sk_test_51OqItkJpHCaKQVp8rTgUXsayGpF8vuQzsFcaFYwjIJRbio0mZjH5jAu2U7zsP1PJ9EASSOY0o7XQJvrMrGHQFfYd00xFymXA51";

            // Create a PaymentIntent with other payment details
            PaymentIntentCreateParams params = PaymentIntentCreateParams.builder()
                    .setAmount(price *100) // Amount in cents (e.g., $10.00)
                    .setCurrency("usd")
                    .build();

            PaymentIntent intent = PaymentIntent.create(params);

            // If the payment was successful, display a success message
            System.out.println("Payment successful. PaymentIntent ID: " + intent.getId());
        } catch (StripeException e) {
            // If there was an error processing the payment, display the error message
            System.out.println("Payment failed. Error: " + e.getMessage());
        }
    }
}