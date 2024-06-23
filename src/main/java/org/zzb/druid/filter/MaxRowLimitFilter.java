package org.zzb.druid.filter;

import com.alibaba.druid.filter.FilterAdapter;
import com.alibaba.druid.filter.FilterChain;
import com.alibaba.druid.proxy.jdbc.ResultSetProxy;
import org.zzb.druid.config.RowLimitConfig;

import java.sql.SQLException;

public class MaxRowLimitFilter extends FilterAdapter {

    private RowLimitConfig rowLimitConfig;


    public MaxRowLimitFilter(RowLimitConfig rowLimitConfig) {
        this.rowLimitConfig = rowLimitConfig;
    }


    @Override
    public boolean resultSet_next(FilterChain chain, ResultSetProxy resultSet) throws SQLException {
        boolean res = super.resultSet_next(chain, resultSet);
        if (!res) {
            return res;
        }
        int fetchRowCount = resultSet.getFetchRowCount();
        // 超过最大限制行抛出异常
        if (fetchRowCount >= rowLimitConfig.getMaxLimit()) {
            throw new RuntimeException(rowLimitConfig.getMessage());
        }
        return res;
    }
}

