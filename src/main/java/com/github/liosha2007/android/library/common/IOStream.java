package com.github.liosha2007.android.library.common;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * @author Aleksey Permyakov (20.01.2015).
 */
public class IOStream extends ByteArrayOutputStream implements AutoCloseable {
    private ByteArrayInputStream inputStream = null;

    public IOStream() {
        super();
    }

    public IOStream(byte[] data) {
        this();
        inputStream = new ByteArrayInputStream(data);
    }

    @Override
    public void close() throws IOException {
        if (inputStream != null) {
            inputStream.close();
        }
        super.close();
    }

    public ByteArrayInputStream getInputStream() throws Exception {
        if (inputStream != null) {
            inputStream.close();
        }
        return (inputStream = new ByteArrayInputStream(super.toByteArray()));
    }
}
