package ru.netology.web.page;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$$;

public class DashboardPage {

    private ElementsCollection cards = $$("[data-test-id=action-deposit]");

    public TopUpPage selectCard(int index) {
        cards.get(index).click();
        return new TopUpPage();
    }

    public int getCardBalance(String text) {
        SelenideElement card = $$("li").find(text(text));
        String value = card.text();
        return extractBalance(value);
    }

    private int extractBalance(String text) {
        String balance = text.substring(text.indexOf("баланс:") + 7, text.indexOf("р")).trim();
        return Integer.parseInt(balance);
    }
}
