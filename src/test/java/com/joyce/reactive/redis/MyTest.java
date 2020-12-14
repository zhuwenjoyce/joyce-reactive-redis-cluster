package com.joyce.reactive.redis;


import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.math.BigDecimal;

@Slf4j
public class MyTest {

    @Test
    public void getCPU核心数() {
        log.info("JVM虚拟机总内存 = "
                + new BigDecimal(Runtime.getRuntime().totalMemory()) // bytes
                .divide(new BigDecimal(1024)) // kb
                .divide(new BigDecimal(1024)) // mb
//                .divide(new BigDecimal(1024)) // gb
                + " MB "
        );
        log.info("JVM虚拟机最大使用内存 = "
                + new BigDecimal(Runtime.getRuntime().maxMemory()) // bytes
                .divide(new BigDecimal(1024)) // kb
                .divide(new BigDecimal(1024)) // mb
//                .divide(new BigDecimal(1024)) // gb
                + " MB "
        );
        log.info("JVM虚拟机剩余可用内存 = "
                + new BigDecimal(Runtime.getRuntime().freeMemory()) // bytes
                .divide(new BigDecimal(1024)) // kb
                .divide(new BigDecimal(1024)) // mb
//                .divide(new BigDecimal(1024)) // gb
                + " MB "
        );
         log.info("CPU核心数 = " + Runtime.getRuntime().availableProcessors());
    }

}
