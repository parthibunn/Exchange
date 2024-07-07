package test.topia.exchange.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDate;

@Entity
public class Conversion {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;
    private LocalDate conversionDate;
    private String srcCurrency;
    private String tgtCurrency;
    private String exgRate;

    public LocalDate getConversionDate() {
        return conversionDate;
    }

    public void setConversionDate(LocalDate conversionDate) {
        this.conversionDate = conversionDate;
    }

    public String getSrcCurrency() {
        return srcCurrency;
    }

    public void setSrcCurrency(String srcCurrency) {
        this.srcCurrency = srcCurrency;
    }

    public String getTgtCurrency() {
        return tgtCurrency;
    }

    public void setTgtCurrency(String tgtCurrency) {
        this.tgtCurrency = tgtCurrency;
    }

    public String getExgRate() {
        return exgRate;
    }

    public void setExgRate(String exgRate) {
        this.exgRate = exgRate;
    }
}
