package com.eagulyi.security.auth.jwt.extractor;

public interface TokenExtractor {
    String extract(String payload);
}
