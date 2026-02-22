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

        int amount = 5000;

        int balance1 = dashboard.getCardBalance("0001");

        int balance2 = dashboard.getCardBalance("0002");

        val topUp = dashboard.selectCard(0);
        val dashboardAfter = topUp.validTransfer(amount, DataHelper.getSecondCard().getNumber());

        assertEquals(balance1 + amount, dashboardAfter.getCardBalance("0001"));
        assertEquals(balance2 - amount, dashboardAfter.getCardBalance("0002"));
    }
}