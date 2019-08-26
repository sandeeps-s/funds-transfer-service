package bank.revolut.fund.transfer.infrastructure.database.typehandlers;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.GregorianCalendar;
import java.util.Objects;

/**
 * @author sandeep.shinde@socgen.com
 */
public class LocalDateTypeHandler extends BaseTypeHandler<LocalDate> {

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, LocalDate parameter, JdbcType jdbcType)
            throws SQLException {
        if (parameter == null) {
            ps.setDate(i, null);
        } else {
            ps.setDate(i, Date.valueOf(parameter),
                    GregorianCalendar.from(ZonedDateTime.of(parameter, LocalTime.MIN, ZoneId.systemDefault())));
        }
    }

    @Override
    public LocalDate getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return getLocalDate(rs.getDate(columnName));
    }

    @Override
    public LocalDate getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return getLocalDate(rs.getDate(columnIndex));
    }

    @Override
    public LocalDate getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {

        return getLocalDate(cs.getDate(columnIndex));
    }

    private static LocalDate getLocalDate(Date date) {
        return Objects.nonNull(date) ? date.toLocalDate() : null;
    }

}
