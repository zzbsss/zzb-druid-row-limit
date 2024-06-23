package org.zzb.druid.config;


import com.alibaba.druid.filter.Filter;
import com.alibaba.druid.filter.FilterAdapter;
import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import com.baomidou.dynamic.datasource.DynamicRoutingDataSource;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.zzb.druid.filter.MaxRowLimitFilter;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Configuration
@ConditionalOnClass({DruidDataSource.class, FilterAdapter.class})
@AutoConfigureAfter({DruidDataSourceAutoConfigure.class})
public class RowLimitAutoConfigure implements InitializingBean {

    @Autowired(required = false)
    private Map<String, DataSource> dataSourceMap;

    @Autowired(required = false)
    private List<Filter> filters;

    @Bean
    @ConfigurationProperties(prefix = "zzb.row.limit")
    public RowLimitConfig rowLimitConfig() {
       return new RowLimitConfig();
    }


    @Bean
    public MaxRowLimitFilter maxRowLimitFilter(RowLimitConfig rowLimitConfig) {
        return new MaxRowLimitFilter(rowLimitConfig);
    }


    @Override
    public void afterPropertiesSet() {
        // 兼容 多数据源
        if (dataSourceMap.get("dataSource") instanceof DynamicRoutingDataSource) {
            dataSourceMap = ((DynamicRoutingDataSource) dataSourceMap.get("dataSource")).getCurrentDataSources();
        }
        Set<Map.Entry<String, DataSource>> entries = dataSourceMap.entrySet();
        for (Map.Entry<String, DataSource> entry : entries) {
            DataSource dataSource = entry.getValue();
            if(dataSource instanceof DruidDataSource) {
                ((DruidDataSource)dataSource).getProxyFilters().addAll(filters);
            }
        }
    }

}
