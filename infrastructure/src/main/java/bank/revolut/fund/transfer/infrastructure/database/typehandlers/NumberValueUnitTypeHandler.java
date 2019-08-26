package bank.revolut.fund.transfer.infrastructure.database.typehandlers;


import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.javamoney.moneta.spi.DefaultNumberValue;
import org.javamoney.moneta.spi.MoneyUtils;

import javax.money.NumberValue;
import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public class NumberValueUnitTypeHandler extends BaseTypeHandler<NumberValue> {

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, NumberValue numberValue, JdbcType jdbcType) throws SQLException {

        if (Objects.isNull(numberValue)) {
            ps.setBigDecimal(i, null);
        } else {
            ps.setBigDecimal(i, MoneyUtils.getBigDecimal(numberValue));
        }
    }

    @Override
    public NumberValue getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return getNumberValue(rs.getBigDecimal(columnName));
    }

    @Override
    public NumberValue getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return getNumberValue(rs.getBigDecimal(columnIndex));
    }

    @Override
    public NumberValue getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return getNumberValue(cs.getBigDecimal(columnIndex));
    }

    private static NumberValue getNumberValue(BigDecimal number) {
        return Objects.nonNull(number) ? new DefaultNumberValue(number) : null;
    }
}