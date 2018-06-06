package org.tcc.examples.spring.repository;

import org.tcc.examples.core.repository.JdbcTransactionRepository;
import org.springframework.jdbc.datasource.DataSourceUtils;
import java.sql.Connection;

public class SpringJdbcTransactionRepository extends JdbcTransactionRepository {

    protected Connection getConnection() {
        return DataSourceUtils.getConnection(this.getDataSource());
    }

    protected void releaseConnection(Connection con) {
        DataSourceUtils.releaseConnection(con, this.getDataSource());
    }
}
