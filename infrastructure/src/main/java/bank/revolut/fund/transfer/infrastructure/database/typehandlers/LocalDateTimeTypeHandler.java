package bank.revolut.fund.transfer.infrastructure.database.typehandlers;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * @author oliver.osterholz@gefa.de
 */
public class LocalDateTimeTypeHandler extends BaseTypeHandler<LocalDateTime> {

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, LocalDateTime parameter, JdbcType jdbcType)
            throws SQLException {

        if (Objects.isNull(parameter)) {
            ps.setTimestamp(i, null);
        } else {
            ps.setTimestamp(i, Timestamp.valueOf(parameter));
        }
    }

    @Override
    public LocalDateTime getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return getLocalDateTime(rs.getTimestamp(columnName));
    }

    @Override
    public LocalDateTime getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return getLocalDateTime(rs.getTimestamp(columnIndex));
    }

    @Override
    public LocalDateTime getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return getLocalDateTime(cs.getTimestamp(columnIndex));
    }

    private static LocalDateTime getLocalDateTime(Timestamp timestamp) {
        return Objects.nonNull(timestamp) ? timestamp.toLocalDateTime() : null;
    }
}
