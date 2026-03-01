package ru.netology.web.test;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeAll;

public class BaseTest {

    @BeforeAll
    static void setupAll() {
        Configuration.browser = "chrome";
        Configuration.headless = false;

        Configuration.browserCapabilities.setCapability(
                "goog:chromeOptions",
                new java.util.HashMap<String, Object>() {{
                    put("args", java.util.Arrays.asList(
                            "--disable-popup-blocking",
                            "--disable-notifications"
                    ));
                    put("prefs", new java.util.HashMap<String, Object>() {{
                        put("profile.password_manager_leak_detection", false);
                        put("credentials_enable_service", false);
                        put("profile.password_manager_enabled", false);
                    }});
                }}
        );
    }
}