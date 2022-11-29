package ng.temire.mecash.utils;


import ng.temire.mecash.data.dto.CurrencyDTO;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Properties;

import static java.lang.System.out;

public class CurrencyConverter {

    public BigDecimal convert(String currency1, String currency2, double amount){
        double conversionRate = Double.parseDouble(getRate(currency1+"-"+currency2));
        return new BigDecimal(amount * conversionRate);
    }

    public static String getRate(String propertyName) {
        String value = "";
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream stream = null;
        try {
            stream = classLoader.getResourceAsStream("conversion.properties");
        } catch (Exception e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        if (stream != null) {
            Properties p = new Properties();
            try {
                p.load(stream);
                value = p.getProperty(propertyName).trim();
            } catch (IOException e) {
                return null;
            }
        }
        out.println("Property Retreieved: "+value);
        return value;
    }
}
