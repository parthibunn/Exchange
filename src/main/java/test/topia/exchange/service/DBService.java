package test.topia.exchange.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import test.topia.exchange.dao.ExchangeRepository;
import test.topia.exchange.model.Conversion;

import java.time.LocalDate;
import java.util.List;

@Service
public class DBService {

    @Autowired
    private ExchangeRepository exchangeRepository;

    public List<Conversion> queryDB(List<String> currencies) {
        return exchangeRepository.findByTgtCurrencyIn(currencies);
    }

    public List<Conversion> queryDBForDate(String tgtCurrency, int noOfDays) {
        return exchangeRepository.findByTgtCurrency(tgtCurrency, noOfDays);
    }

    public void updateDB(List<Conversion> conversions) {
        for(Conversion conversion:conversions) {
            Conversion fromDB = exchangeRepository.findByConversionDateAndTgtCurrency(conversion.getConversionDate(), conversion.getTgtCurrency());
            if(fromDB==null) {
                exchangeRepository.save(conversion);
            }
        }
    }

    public void saveConversion(Conversion conversion) {
        Conversion fromDB = exchangeRepository.findByConversionDateAndTgtCurrency(conversion.getConversionDate(), conversion.getTgtCurrency());
        if(fromDB==null) {
            exchangeRepository.save(conversion);
        }
    }
}
