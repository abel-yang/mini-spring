package com.minis.core.io;

import com.minis.beans.util.Assert;
import com.minis.beans.util.ObjectUtils;
import com.minis.lang.Nullable;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;

/**
 * @author abel
 * @version 1.0
 * @date 2023/4/24 15:21
 */
public class EncodedResource implements InputStreamSource{

    private final Resource resource;
    private final String encoding;
    private final Charset charset;

    public EncodedResource(Resource resource) {
        this(resource, null, null);
    }

    public EncodedResource(Resource resource, String encoding) {
        this(resource, encoding, null);
    }

    public EncodedResource(Resource resource, Charset charset) {
        this(resource, null, charset);
    }

    public EncodedResource(Resource resource, String encoding, Charset charset) {
        Assert.notNull(resource, "Resource must not be null");
        this.resource = resource;
        this.encoding = encoding;
        this.charset = charset;
    }

    /**
     * Return the {@code Resource} held by this {@code EncodedResource}.
     */
    public final Resource getResource() {
        return this.resource;
    }

    /**
     * Return the encoding to use for reading from the {@linkplain #getResource() resource},
     * or {@code null} if none specified.
     */
    @Nullable
    public final String getEncoding() {
        return this.encoding;
    }

    /**
     * Return the {@code Charset} to use for reading from the {@linkplain #getResource() resource},
     * or {@code null} if none specified.
     */
    @Nullable
    public final Charset getCharset() {
        return this.charset;
    }

    /**
     * Determine whether a {@link Reader} is required as opposed to an {@link InputStream},
     * i.e. whether an {@linkplain #getEncoding() encoding} or a {@link #getCharset() Charset}
     * has been specified.
     * @see #getReader()
     * @see #getInputStream()
     */
    public boolean requiresReader() {
        return (this.encoding != null || this.charset != null);
    }

    /**
     * Open a {@code java.io.Reader} for the specified resource, using the specified
     * {@link #getCharset() Charset} or {@linkplain #getEncoding() encoding}
     * (if any).
     * @throws IOException if opening the Reader failed
     * @see #requiresReader()
     * @see #getInputStream()
     */
    public Reader getReader() throws IOException {
        if (this.charset != null) {
            return new InputStreamReader(this.resource.getInputStream(), this.charset);
        }
        else if (this.encoding != null) {
            return new InputStreamReader(this.resource.getInputStream(), this.encoding);
        }
        else {
            return new InputStreamReader(this.resource.getInputStream());
        }
    }

    /**
     * Open an {@code InputStream} for the specified resource, ignoring any specified
     * {@link #getCharset() Charset} or {@linkplain #getEncoding() encoding}.
     * @throws IOException if opening the InputStream failed
     * @see #requiresReader()
     * @see #getReader()
     */
    @Override
    public InputStream getInputStream() throws IOException {
        return this.resource.getInputStream();
    }


    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof EncodedResource)) {
            return false;
        }
        EncodedResource otherResource = (EncodedResource) other;
        return (this.resource.equals(otherResource.resource) &&
                ObjectUtils.nullSafeEquals(this.charset, otherResource.charset) &&
                ObjectUtils.nullSafeEquals(this.encoding, otherResource.encoding));
    }

    @Override
    public int hashCode() {
        return this.resource.hashCode();
    }

    @Override
    public String toString() {
        return this.resource.toString();
    }
}
