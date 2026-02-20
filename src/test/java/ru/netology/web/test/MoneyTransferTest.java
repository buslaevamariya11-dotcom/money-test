package ru.netology.web.test;

import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.netology.web.config.WebDriverProvider;
import ru.netology.web.data.DataHelper;
import ru.netology.web.page.DashboardPage;
import ru.netology.web.page.LoginPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MoneyTransferTest {

    DashboardPage dashboard;

    @BeforeEach
    void setup() {
        WebDriverProvider.setup();
        open("http://localhost:9999");

        val loginPage = new LoginPage();
        val authInfo = DataHelper.getAuthInfo();
        val verificationPage = loginPage.validLogin(authInfo);
        val verificationCode = DataHelper.getVerificationCodeFor(authInfo);

        dashboard = verificationPage.validVerify(verificationCode);
    }

    @Test
    @DisplayName("Перевод со 2-й карты на 1-ю")
    void shouldTransferFromSecondToFirst() {

        String firstMasked = DataHelper.getMasked(DataHelper.getFirstCard().getCardNumber());
        String secondMasked = DataHelper.getMasked(DataHelper.getSecondCard().getCardNumber());

        int balance1 = dashboard.getCardBalance(firstMasked);
        int balance2 = dashboard.getCardBalance(secondMasked);

        int amount = balance2 / 4;

        val topUp = dashboard.selectCardToTopUp(firstMasked);
        val dashboard2 = topUp.doValidTransfer(amount, DataHelper.getSecondCard().getCardNumber());

        assertEquals(balance1 + amount, dashboard2.getCardBalance(firstMasked));
        assertEquals(balance2 - amount, dashboard2.getCardBalance(secondMasked));
    }

    @Test
    @DisplayName("Перевод с 1-й карты на 2-ю")
    void shouldTransferFromFirstToSecond() {

        String firstMasked = DataHelper.getMasked(DataHelper.getFirstCard().getCardNumber());
        String secondMasked = DataHelper.getMasked(DataHelper.getSecondCard().getCardNumber());

        int balance1 = dashboard.getCardBalance(firstMasked);
        int balance2 = dashboard.getCardBalance(secondMasked);

        int amount = balance1 / 4;

        val topUp = dashboard.selectCardToTopUp(secondMasked);
        val dashboard2 = topUp.doValidTransfer(amount, DataHelper.getFirstCard().getCardNumber());

        assertEquals(balance1 - amount, dashboard2.getCardBalance(firstMasked));
        assertEquals(balance2 + amount, dashboard2.getCardBalance(secondMasked));
    }

    @Test
    @DisplayName("Перевод сверх баланса")
    void shouldShowErrorWhenTransferMoreThanBalance() {

        String firstMasked = DataHelper.getMasked(DataHelper.getFirstCard().getCardNumber());
        String secondMasked = DataHelper.getMasked(DataHelper.getSecondCard().getCardNumber());

        int balance1Before = dashboard.getCardBalance(firstMasked);
        int balance2Before = dashboard.getCardBalance(secondMasked);

        int amount = balance1Before + 5000;

        val topUp = dashboard.selectCardToTopUp(secondMasked);

        DashboardPage dashboardAfterError =
                topUp.doInvalidTransfer(amount, DataHelper.getFirstCard().getCardNumber());

        int balance1After = dashboardAfterError.getCardBalance(firstMasked);
        int balance2After = dashboardAfterError.getCardBalance(secondMasked);

        assertEquals(balance1Before, balance1After);
        assertEquals(balance2Before, balance2After);
    }
}