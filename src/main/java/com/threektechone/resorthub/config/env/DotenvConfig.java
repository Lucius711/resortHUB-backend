package com.threektechone.resorthub.config.env;

import io.github.cdimascio.dotenv.Dotenv;

public class DotenvConfig {

    public static void load() {
        Dotenv dotenv = Dotenv.configure()
                .ignoreIfMissing()
                .load();

        set("DB_URL", dotenv);
        set("DB_USERNAME", dotenv);
        set("DB_PASSWORD", dotenv);
    }

    private static void set(String key, Dotenv dotenv) {
        String value = dotenv.get(key);

        if (value == null || value.isEmpty()) {
            System.out.println("⚠️ Missing env key: " + key);
            return;
        }

        System.setProperty(key, value);
    }
}