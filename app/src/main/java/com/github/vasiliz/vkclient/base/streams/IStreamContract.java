package com.github.vasiliz.vkclient.base.streams;

import java.io.IOException;
import java.io.InputStream;

public interface IStreamContract<T> {
        InputStream get(T pT) throws IOException;
}
