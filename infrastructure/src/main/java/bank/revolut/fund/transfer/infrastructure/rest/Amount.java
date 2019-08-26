package bank.revolut.fund.transfer.infrastructure.rest;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.javamoney.moneta.Money;
import org.javamoney.moneta.spi.MoneyUtils;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.math.BigDecimal;

public class Amount {

    private static final String CURRENCY_CODE_PATTERN = "[A-Z][A-Z][A-Z]";

    @Min(0)
    @NotNull
    private BigDecimal value;
    @Pattern(regexp = CURRENCY_CODE_PATTERN)
    private String currencyCode;

    public Amount() {
    }

    public Amount(BigDecimal value, String currencyCode) {
        this.value = value;
        this.currencyCode = currencyCode;
    }

    public Money toMoney() {
        return Money.of(value, currencyCode);
    }

    public static Amount from(Money money) {
        return new Amount(MoneyUtils.getBigDecimal(money.getNumber()), money.getCurrency().getCurrencyCode());
    }

    @JsonSerialize(using = BigDecimalSerializer.class)
    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }
}
