package ru.netology.web.config;

import com.codeborne.selenide.Configuration;

public class WebDriverProvider {

    public static void setup() {

        Configuration.browser = "chrome";
        Configuration.browserSize = "1366x768";

        Configuration.headless = Boolean.getBoolean("selenide.headless");

        Configuration.browserCapabilities.setCapability("goog:chromeOptions",
                new org.openqa.selenium.chrome.ChromeOptions()
                        .addArguments("--no-sandbox")
                        .addArguments("--disable-dev-shm-usage")
                        .addArguments("--disable-gpu")
                        .addArguments("--remote-allow-origins=*")
        );
    }
}