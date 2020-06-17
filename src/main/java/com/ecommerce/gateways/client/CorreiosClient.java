package com.ecommerce.gateways.client;

import com.ecommerce.domains.Cart;
import com.ecommerce.exceptions.FreightPriceException;
import com.ecommerce.exceptions.HttpErrorException;
import com.ecommerce.gateways.ShippingCompany;
import com.ecommerce.gateways.impl.HttpService;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RequiredArgsConstructor
public class CorreiosClient implements ShippingCompany {
    private static final String URL = "http://ws.correios.com.br/calculador/CalcPrecoPrazo.aspx?nVlPeso=0.3&nVlComprimento=30&nVlAltura=2&nVlLargura=15&nVlDiametro=0.0&nCdFormato=1&sCdMaoPropria=N&sCdAvisoRecebimento=N&nVlValorDeclarado=0,00&nCdEmpresa=&sDsSenha=&StrRetorno=xml";
    private final HttpService httpService;

    @Override
    public BigDecimal getFreightPrice(Cart cart) throws FreightPriceException {
        String url = URL +
                "&sCepOrigem=" + cart.getAddress().getCep() +
                "&sCepDestino=" + cart.getCustomer().getAddress().getCep() +
                "&nCdServico=" + cart.getShippingCode().getCode();
        try {
            return httpService.doGet(url)
                    .map(line -> Pattern.compile("(?:\\<Valor\\>)(.+?)(?:\\<\\/Valor\\>)").matcher(line))
                    .filter(Matcher::find)
                    .map(matcher -> matcher.group(1))
                    .map(value -> value.replace(",", "."))
                    .map(value -> new BigDecimal(value, MathContext.DECIMAL32))
                    .findFirst()
                    .orElseThrow(() -> new FreightPriceException("freight price cannot be calculated due to price not present in response"));
        } catch (HttpErrorException e) {
            throw new FreightPriceException("freight price cannot be calculated due to http error");
        }
    }
}
