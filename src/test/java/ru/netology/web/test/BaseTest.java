package ru.netology.web.test;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;

import static com.codeborne.selenide.Selenide.closeWebDriver;

public class BaseTest {

    @BeforeAll
    static void setupAll() {
        Configuration.browser = "chrome";
        Configuration.headless = Boolean.parseBoolean(System.getProperty("selenide.headless", "true"));
        Configuration.browserSize = "1366x768";

        Configuration.browserCapabilities.setCapability(
                "goog:chromeOptions",
                java.util.Map.of(
                        "args", java.util.List.of(
                                "--no-sandbox",
                                "--disable-dev-shm-usage",
                                "--remote-allow-origins=*",
                                "--disable-popup-blocking",
                                "--disable-notifications"
                        ),
                        "prefs", java.util.Map.of(
                                "profile.password_manager_leak_detection", false,
                                "credentials_enable_service", false,
                                "profile.password_manager_enabled", false
                        )
                )
        );
    }

    @AfterEach
    void tearDown() {
        closeWebDriver();
    }
}