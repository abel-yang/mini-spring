package com.minis.core.io;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author abel
 * @version 1.0
 * @date 2023/4/24 15:11
 */
public interface InputStreamSource {

    InputStream getInputStream() throws IOException;
}
