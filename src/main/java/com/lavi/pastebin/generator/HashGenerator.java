package com.lavi.pastebin.generator;

import com.lavi.pastebin.api.services.PostInfoService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.ThreadLocalRandom;

@Component
@Data
public class HashGenerator {
    private static final char[] base64;
    private static final int length = 6;
    @Autowired
    private PostInfoService service;

    static {
        base64 = new char[64];
        for (int i = 0; i < 63; i++) {
            if (i < 26) {
                base64[i] = (char) ('a' + i);
            } else if (i < 52) {
                base64[i] = (char) ('A' + i - 26);
            } else if (i < 62) {
                base64[i] = (char) ('0' + i - 52);
            } else {
                base64[i] = '+';
                base64[i + 1] = '!';
            }
        }
    }

    public String getHash() {
        String hash = generateHash();
        while (service.hasHash(hash)) {
            hash = generateHash();
        }
        return hash;
    }

    private String generateHash() {
        StringBuilder hash = new StringBuilder();
        for (int i = 0; i < length; i++) {
            hash.append(base64[ThreadLocalRandom.current().nextInt(base64.length)]);
        }
        return hash.toString();
    }
}
