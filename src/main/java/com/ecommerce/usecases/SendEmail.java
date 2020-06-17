package com.ecommerce.usecases;


import com.ecommerce.domains.Email;
import com.ecommerce.domains.Order;
import com.ecommerce.gateways.EmailGateway;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SendEmail {
    private final EmailGateway emailGateway;

    public void execute(Order order) throws Exception {
        var email = new Email(order.getCustomer().getEmail(), "Pedido confirmado",
                "Pedido " + order.getId() + " confirmado");
        emailGateway.send(email);
    }
}
