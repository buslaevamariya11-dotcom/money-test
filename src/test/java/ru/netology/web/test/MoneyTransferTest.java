package ru.netology.web.test;

import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.web.data.DataHelper;
import ru.netology.web.page.LoginPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MoneyTransferTest {

    @BeforeEach
    void setup() {
        open("http://localhost:9999");
    }

    @Test
    void shouldTransferMoney() {
        val loginPage = new LoginPage();
        val authInfo = DataHelper.getAuthInfo();
        val verificationPage = loginPage.validLogin(authInfo);
        val code = DataHelper.getVerificationCodeFor(authInfo);
        val dashboard = verificationPage.validVerify(code);

        val firstCard = DataHelper.getFirstCard();
        val secondCard = DataHelper.getSecondCard();

        int balance1 = dashboard.getCardBalance(firstCard);
        int balance2 = dashboard.getCardBalance(secondCard);

        int amount = balance2 / 2;

        val transferPage = dashboard.selectCard(firstCard);
        val dashboardAfter =
                transferPage.validTransfer(amount, secondCard.getNumberWithoutSpaces());

        assertEquals(balance1 + amount,
                dashboardAfter.getCardBalance(firstCard));
        assertEquals(balance2 - amount,
                dashboardAfter.getCardBalance(secondCard));
    }
}