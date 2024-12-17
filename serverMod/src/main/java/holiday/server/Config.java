package holiday.server;

import net.fabricmc.loader.api.FabricLoader;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

public record Config(
    String discordWebhookUrl
) {
    public static Config loadConfig() throws IOException {
        Path path = FabricLoader.getInstance().getConfigDir().resolve("holiday_server.properties");
        Properties properties = new Properties();

        if (Files.exists(path)) {
            properties.load(Files.newBufferedReader(path));
        }

        var config = new Config(
            get(properties, "discordWebhookUrl", "https://discord.com/api/webhooks/EXAMPLE/EXAMPLE")
        );

        try (OutputStream os = Files.newOutputStream(path)) {
            properties.store(os, "Holiday Server Config");
        }

        return config;
    }

    private static String get(Properties properties, String key, String defaultValue) {
        if (!properties.containsKey(key)) {
            properties.setProperty(key, defaultValue);
        }

        return properties.getProperty(key);
    }
}
