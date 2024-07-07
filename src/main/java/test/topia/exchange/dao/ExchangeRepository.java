package test.topia.exchange.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import test.topia.exchange.model.Conversion;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ExchangeRepository extends JpaRepository<Conversion, Long> {

    List<Conversion> findByTgtCurrencyIn(List<String> tgtCurrencies);
    Conversion findByConversionDateAndTgtCurrency(LocalDate conversionDate, String tgtCurrency);

    @Query("select c from Conversion c where conversionDate >= :fromDate and tgtCurrency = :tgtCurrency")
    List<Conversion> findAllWithConversionDateAfter(String tgtCurrency, LocalDate fromDate);

    @Query(value="SELECT * FROM Conversion c where tgt_Currency = :tgtCurrency ORDER BY conversion_Date LIMIT :noOfDays", nativeQuery=true)
    List<Conversion> findByTgtCurrency(String tgtCurrency, int noOfDays);



//    void saveAll(List<Conversion> conversions);
}
