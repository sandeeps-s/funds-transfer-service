package bank.revolut.fund.transfer.infrastructure.database.typehandlers;


import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import javax.money.CurrencyUnit;
import javax.money.Monetary;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public class CurrencyUnitTypeHandler extends BaseTypeHandler<CurrencyUnit> {

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, CurrencyUnit currency, JdbcType jdbcType) throws SQLException {

        if (Objects.isNull(currency)) {
            ps.setString(i, null);
        } else {
            ps.setString(i, currency.getCurrencyCode());
        }
    }

    @Override
    public CurrencyUnit getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return getCurrencyUnit(rs.getString(columnName));
    }

    @Override
    public CurrencyUnit getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return getCurrencyUnit(rs.getString(columnIndex));
    }

    @Override
    public CurrencyUnit getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return getCurrencyUnit(cs.getString(columnIndex));
    }

    private static CurrencyUnit getCurrencyUnit(String currencyCode) {
        return Objects.nonNull(currencyCode) ? Monetary.getCurrency(currencyCode) : null;
    }
}