package com.kantox.beverages.domain;


import com.kantox.beverages.domain.entities.Product;
import lombok.AllArgsConstructor;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;
import java.util.Locale;

@AllArgsConstructor
@Service
public class OrderDiscountService {


    private final KieContainer kieContainer;

    public Double calculatePrice(List<Product> products) {

        Double totalPrice = 0.0;

        for (Product product : products) {
            product.setDiscount(round(product.getDiscount()));
            totalPrice = totalPrice + (product.getPrice() * product.getQuantity()) - product.getDiscount();
        }

        return round(totalPrice);
    }

    public Product getDiscount(Product product) {

        KieSession kieSession = kieContainer.newKieSession();
        kieSession.setGlobal("productProcessed", product);
        kieSession.insert(product);
        kieSession.fireAllRules();
        kieSession.dispose();
        return product;
    }

    private Double round(Double doubleToRound) {
        Locale currentLocale = Locale.getDefault();
        DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols(currentLocale);
        otherSymbols.setDecimalSeparator('.');
        DecimalFormat df = new DecimalFormat("0.00", otherSymbols);
        return Double.parseDouble(df.format(doubleToRound));
    }
}
