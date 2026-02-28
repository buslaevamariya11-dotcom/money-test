package ru.netology.web.page;

import com.codeborne.selenide.SelenideElement;
import ru.netology.web.data.DataHelper.CardInfo;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$$;

public class DashboardPage {

    public TopUpPage selectCard(CardInfo card) {
        SelenideElement cardElement =
                $$("li")
                        .find(text(card.getId()));

        cardElement.$("button").click();
        return new TopUpPage();
    }

    public int getCardBalance(CardInfo card) {
        String value =
                $$("li")
                        .find(text(card.getId()))
                        .getText();

        return extractBalance(value);
    }

    private int extractBalance(String text) {
        String balancePart = text.substring(text.indexOf("баланс:") + 7);
        String digitsOnly = balancePart.replaceAll("\\D+", "");
        return Integer.parseInt(digitsOnly);
    }
}