package com.winpams.core;

import io.github.cdimascio.dotenv.Dotenv;

public class Config {
    private final Dotenv dotenv;

    private Config() {
        dotenv = Dotenv.load();
    }

    private static class Holder {
        private static final Config INSTANCE = new Config();
    }

    public static Config getInstance() {
        return Holder.INSTANCE;
    }

    public String getValue(String key) {
        return dotenv.get(key);
    }
}

