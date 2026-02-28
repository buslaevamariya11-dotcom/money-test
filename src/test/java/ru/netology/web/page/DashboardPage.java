package ru.netology.web.page;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import ru.netology.web.data.DataHelper;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$$;

public class DashboardPage {

    private ElementsCollection cards = $$(".list__item");

    public TopUpPage selectCard(DataHelper.CardInfo card) {
        String masked = getMaskedNumber(card);

        cards.findBy(text(masked))
                .$("button")
                .click();

        return new TopUpPage();
    }

    public int getCardBalance(String maskedCardNumber) {
        SelenideElement card = cards.findBy(text(maskedCardNumber));
        String value = card.text();
        return extractBalance(value);
    }

    private int extractBalance(String text) {
        String balance = text.substring(text.indexOf("баланс:") + 7, text.indexOf("р")).trim();
        return Integer.parseInt(balance);
    }

    private String getMaskedNumber(DataHelper.CardInfo card) {
        String numberWithoutSpaces = card.getNumber().replace(" ", "");
        String lastFour = numberWithoutSpaces.substring(numberWithoutSpaces.length() - 4);
        return "**** " + lastFour;
    }
}