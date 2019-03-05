package com.github.vasiliz.vkclient.base.utils;

import java.io.Closeable;
import java.io.IOException;

public class IOUtils {

    private IOUtils(){}

    public static void closeStream(final Closeable pStream){
        if (pStream !=null){
            try {
                pStream.close();
            } catch (final IOException pE) {pE.fillInStackTrace();

            }
        }
    }

}
