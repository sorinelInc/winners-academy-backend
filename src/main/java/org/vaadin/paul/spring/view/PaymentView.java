package org.vaadin.paul.spring.view;

import com.braintreegateway.*;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.Notification.Position;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.Command;
import elemental.json.JsonArray;
import elemental.json.JsonObject;
import elemental.json.impl.JreJsonFactory;
import org.vaadin.jonni.PaymentRequest;
import org.vaadin.jonni.PaymentRequest.PaymentResponse;

@Route(value = "payment", layout = MainView.class)
public class PaymentView extends VerticalLayout {

    private final String CHARGED_AMOUNT = "10";
    private final String CURRENCY = "EUR";

    private BraintreeGateway gateway = new BraintreeGateway(
            Environment.SANDBOX,
            "f6zwpq97wpp4z2y6",
            "cmzjbmn43wncwgf2",
            "5910517ee419c504a6af75cb1f522bda"
    );


    private Button button;

    public PaymentView() {
        PaymentRequest.queryIsSupported(isSupported -> {
            if (isSupported) {
                addPaymentRequestHandlerToButton();
            } else {
                button.addClickListener(click -> Notification
                        .show("Payment collection is not supported on your browser! Please try a different browser!", 9000, Position.MIDDLE));
            }
        });
        button = new Button("Pay");
        add(new H1("Vaadin 10 Payment Request API demo"), button,
                new Paragraph("Please do not use real credit card information on this site!"),
                new Paragraph(new Text("You can find some valid format "),
                        new Anchor("http://www.getcreditcardnumbers.com/", "test"), new Text(" "),
                        new Anchor("https://stripe.com/docs/testing#cards", "cards"), new Text(" "),
                        new Anchor("https://developer.paypal.com/developer/creditCardGenerator/", "online"),
                        new Text(".")));
    }

    private void addPaymentRequestHandlerToButton() {
        JsonArray supportedPaymentMethods = getSupportedMethods();
        JsonObject paymentDetails = getPaymentDetails();

        PaymentRequest paymentRequest = new PaymentRequest(supportedPaymentMethods, paymentDetails);
        paymentRequest.setPaymentResponseCallback((paymentResponse) -> {
            JsonObject eventData = paymentResponse.getEventData();
            Notification.show("Please wait a moment while we finish the payment via our payment gateway.", 9000,
                    Position.MIDDLE);

            Command onPaymentGatewayRequestComplete = () -> {
                paymentResponse.complete();
                String cardNumber = eventData.getObject("details").getString("cardNumber");
                String cardEnding = cardNumber.substring(cardNumber.length() - 4);
                Notification
                        .show("Purchase complete! We have charged the total " + CHARGED_AMOUNT + " " + CURRENCY + " from your credit card ending in "
                                + cardEnding, 9000, Position.MIDDLE);
            };

            Command onPaymentGatewayRequestFailed = () -> {
                paymentResponse.complete();
                Notification
                        .show("Purchase has failed! ", 9000, Position.MIDDLE);
            };

            startPaymentGatewayQuery(paymentResponse, eventData, onPaymentGatewayRequestComplete, onPaymentGatewayRequestFailed)
            ;
        });
        paymentRequest.install(button);

    }

    private void startPaymentGatewayQuery(PaymentResponse paymentResponse,
                                          JsonObject eventData,
                                          Command onPaymentGatewayRequestComplete,
                                          Command onPaymentGatewayRequestFailed) {
        UI ui = UI.getCurrent();

        String clientToken = gateway.clientToken().generate();


        TransactionRequest request = new TransactionRequest()
                .paymentMethodToken(clientToken)
                .amount(eventData.get("amount"))
                .options()
                .submitForSettlement(true)
                .done();


        Result<Transaction> result = gateway.transaction().sale(request);
        if (result.isSuccess()) {
            System.out.println("OK");
            ui.access(onPaymentGatewayRequestComplete);
        } else if (result.getTransaction() != null) {
            Transaction transaction = result.getTransaction();
            ui.access(onPaymentGatewayRequestFailed);
            System.out.println(transaction);
        } else {
            StringBuilder errorString = new StringBuilder();
            for (ValidationError error : result.getErrors().getAllDeepValidationErrors()) {
                errorString.append("Error: ").append(error.getCode()).append(": ").append(error.getMessage()).append("\n");
            }
            ui.access(onPaymentGatewayRequestFailed);
            System.out.println(errorString.toString());
        }


    }

    private JsonArray getSupportedMethods() {
        JreJsonFactory jsonFactory = new JreJsonFactory();
        JsonArray supportedPaymentMethods = jsonFactory.createArray();
        JsonObject basicCard = jsonFactory.createObject();
        basicCard.put("supportedMethods", "basic-card");
        supportedPaymentMethods.set(0, basicCard);
        return supportedPaymentMethods;
    }

    private JsonObject getPaymentDetails() {
        JreJsonFactory jsonFactory = new JreJsonFactory();
        JsonObject paymentDetails = jsonFactory.createObject();

        JsonObject total = jsonFactory.createObject();
        total.put("label", "Cart (10 items)");
        JsonObject totalAmount = jsonFactory.createObject();
        totalAmount.put("currency", CURRENCY);
        totalAmount.put("value", CHARGED_AMOUNT);
        total.put("amount", totalAmount);
        paymentDetails.put("total", total);
        return paymentDetails;
    }
}