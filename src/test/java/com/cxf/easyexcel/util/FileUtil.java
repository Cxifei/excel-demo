package com.cxf.easyexcel.util;

import java.io.InputStream;

/**
 * @author always_on_the_way
 * @date 2019-07-23
 */
public class FileUtil {

    public static InputStream getResourcesFileInputStream(String fileName) {
        return Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);
    }
}
