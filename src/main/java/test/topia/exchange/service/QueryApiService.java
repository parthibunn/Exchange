package test.topia.exchange.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import test.topia.exchange.model.Conversion;
import test.topia.exchange.model.QueryResult;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Component
public class QueryApiService {

    @Autowired
    private RestTemplate restTemplate;

    public List<Conversion> queryAPI(List<String> currencies) {
        List<Conversion> conversionList = new ArrayList<>();

        Conversion conversion = new Conversion();

        QueryResult result = restTemplate.getForObject("https://api.frankfurter.app/latest", QueryResult.class);

        Double eurToUsd = result.getRates().get("USD");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate formattedDate = LocalDate.parse(result.getDate(), formatter);

        conversion.setConversionDate(formattedDate);
        conversion.setSrcCurrency("USD");
        //First currency is always EUR
        conversion.setTgtCurrency(currencies.get(0));
        conversion.setExgRate(String.format("%.4f", 1/eurToUsd));

        conversionList.add(conversion);

        for(int i=1; i<currencies.size(); i++) {
            conversion = new Conversion();
            conversion.setConversionDate(formattedDate);
            conversion.setSrcCurrency("USD");
            conversion.setTgtCurrency(currencies.get(i));
            conversion.setExgRate(String.format("%.4f", result.getRates().get(currencies.get(i))/eurToUsd));
            conversionList.add(conversion);
        }

        return conversionList;
    }

    public Conversion queryAPIForDate(String tgtCurrency, LocalDate queryDate) {
        Conversion conversion;
        QueryResult eurToTgtConversion = restTemplate.getForObject("https://api.frankfurter.app/" + queryDate+ "?to=" + tgtCurrency, QueryResult.class);
        QueryResult eurToUSDConversion = restTemplate.getForObject("https://api.frankfurter.app/" + queryDate+ "?to=USD", QueryResult.class);
        conversion = new Conversion();
        if(eurToTgtConversion!=null) {
            Double eurToUsd = eurToUSDConversion.getRates().get("USD");
            Double eurToTgt = eurToTgtConversion.getRates().get(tgtCurrency);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate formattedDate = LocalDate.parse(eurToTgtConversion.getDate(), formatter);
            conversion.setConversionDate(formattedDate);
            conversion.setTgtCurrency(tgtCurrency);
            conversion.setSrcCurrency("USD");
            if(tgtCurrency=="EUR") {
                conversion.setExgRate(String.format("%.4f", 1/eurToUsd));
            } else {
                conversion.setExgRate(String.format("%.4f", eurToTgt/eurToUsd));
            }
            return conversion;
        } else {
            return conversion;
        }

    }

}
