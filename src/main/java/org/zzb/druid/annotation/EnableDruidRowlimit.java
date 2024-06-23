package org.zzb.druid.annotation;


import com.alibaba.druid.spring.boot.autoconfigure.stat.DruidFilterConfiguration;
import org.springframework.context.annotation.Import;
import org.zzb.druid.config.RowLimitAutoConfigure;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

@Import({RowLimitAutoConfigure.class, DruidFilterConfiguration.class})
@Target(ElementType.TYPE)
public @interface EnableDruidRowlimit {

}
