package ru.netology.web.page;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class TopUpPage {

    private SelenideElement amount = $("[data-test-id=amount] input");
    private SelenideElement from = $("[data-test-id=from] input");
    private SelenideElement transferButton = $("[data-test-id=action-transfer]");
    private SelenideElement error = $("[data-test-id=error-notification]");

    public DashboardPage validTransfer(int value, String number) {
        amount.setValue(String.valueOf(value));
        from.setValue(number);
        transferButton.click();
        return new DashboardPage();
    }
}
