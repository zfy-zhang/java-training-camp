package com.acme.biz.api.micrometer;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author: <a href="mailto:zfy_zang@163.com">elisha</a>
 * @since:
 */
public abstract class Micrometers {

    private static final ExecutorService asyncExecutor = Executors.newSingleThreadExecutor();

    public static void async(Runnable runnable) {
        asyncExecutor.execute(runnable);
    }
}
