package test.topia.exchange.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import test.topia.exchange.model.Conversion;
import test.topia.exchange.service.QueryApiService;
import test.topia.exchange.service.DBService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController()
@RequestMapping("/fx")
public class ExchangeController {

    @Autowired
    private QueryApiService queryApiService;

    @Autowired
    private DBService dbService;

    @Value("${query.currencies}")
    private List<String> currencies;

    @Value("${history.noofdays}")
    private int noOfDays;

    @GetMapping()
    public List<Conversion> getConversions() {

        List<Conversion> conversionDBList = dbService.queryDB(currencies);

        if(conversionDBList.size()==currencies.size()) {
            return conversionDBList;
        } else {
            List<Conversion> conversionList = queryApiService.queryAPI(currencies);
            dbService.updateDB(conversionList);
            return conversionList;
        }
    }

    @GetMapping("/{tgtCurrency}")
    public List<Conversion> getConversionHistory(@PathVariable(value = "tgtCurrency") String tgtCurrency) {
        List<Conversion> conversionHistoryDB = dbService.queryDBForDate(tgtCurrency, noOfDays);
        List<Conversion> conversionHistoryResult = new ArrayList<>();
        LocalDate querydate = LocalDate.now();
        Conversion conversion=null;
        while(conversionHistoryResult.size()<noOfDays) {
            Optional<Conversion> optionalConversion = findConversion(conversionHistoryDB, querydate);

            if(optionalConversion.isEmpty()){
                conversion = queryApiService.queryAPIForDate(tgtCurrency, querydate);
                querydate=conversion.getConversionDate().minusDays(1);
                conversionHistoryResult.add(conversion);
                dbService.saveConversion(conversion);
            } else {
                querydate=querydate.minusDays(1);
                conversionHistoryResult.add(optionalConversion.get());
            }
        }
        return conversionHistoryResult;
    }

    private final Optional<Conversion> findConversion(List<Conversion> conversionList, LocalDate queryDate){
        return conversionList.stream().filter(c -> c.getConversionDate().isEqual(queryDate)).findAny();
    }
}
