package ru.netology.web.page;

import com.codeborne.selenide.SelenideElement;
import ru.netology.web.data.DataHelper;
import ru.netology.web.data.DataHelper.CardInfo;

import static com.codeborne.selenide.Selenide.$;

public class DashboardPage {

    public TopUpPage selectCard(CardInfo card) {
        SelenideElement cardElement =
                $("[data-test-id='" + DataHelper.getCardTestId(card) + "']");

        cardElement.$("button").click();
        return new TopUpPage();
    }

    public int getCardBalance(CardInfo card) {
        SelenideElement cardElement =
                $("[data-test-id='" + DataHelper.getCardTestId(card) + "']");

        String text = cardElement.getText();
        return extractBalance(text);
    }

    private int extractBalance(String text) {
        String balance = text.substring(text.indexOf("баланс:") + 7);
        balance = balance.replaceAll("[^0-9]", "");
        return Integer.parseInt(balance);
    }
}