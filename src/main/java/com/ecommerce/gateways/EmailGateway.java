package com.ecommerce.gateways;

import com.ecommerce.domains.Email;

public interface EmailGateway {

    void send(Email email) throws Exception;
}
