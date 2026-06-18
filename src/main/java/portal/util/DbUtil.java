package portal.util;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class DbUtil {

    // context.xmlのResource name="jdbc/mysql"に対応するJNDI名
    private static final String JNDI_NAME = "java:/comp/env/jdbc/Pbl2026DB";

    public static Connection getConnection() throws SQLException {
        try {
            Context context = new InitialContext();
            DataSource dataSource = (DataSource) context.lookup(JNDI_NAME);
            return dataSource.getConnection();
        } catch (NamingException e) {
            throw new SQLException("DataSourceの取得に失敗しました。", e);
        }
    }
}