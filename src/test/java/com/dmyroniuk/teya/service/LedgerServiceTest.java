package com.dmyroniuk.teya.service;

import com.dmyroniuk.teya.model.Operation;
import com.dmyroniuk.teya.persistence.InMemoryLedgerStorage;
import com.dmyroniuk.teya.service.exception.IllegalOperationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static com.dmyroniuk.teya.OperationTestUtils.depositOperation;
import static com.dmyroniuk.teya.OperationTestUtils.withdrawOperation;
import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class LedgerServiceTest {

    private LedgerService ledgerService;

    @BeforeEach
    public void initLedgerService() {
        ledgerService = new LedgerService(new InMemoryLedgerStorage());
    }

    @Test
    public void testDepositInSyncWithBalance() throws IllegalOperationException {
        BigDecimal balance = ledgerService.makeDeposit(new BigDecimal(1));
        assertEquals(new BigDecimal(1), balance);
        balance = ledgerService.getBalance();
        assertEquals(new BigDecimal(1), balance);
    }

    @Test
    public void testWithdrawalInSyncWithBalance() throws IllegalOperationException {
        ledgerService.makeDeposit(new BigDecimal(1));
        BigDecimal balance = ledgerService.makeWithdraw(new BigDecimal(1));
        assertEquals(new BigDecimal(0), balance);
        balance = ledgerService.getBalance();
        assertEquals(new BigDecimal(0), balance);
    }

    @Test
    public void testGetHistory() throws IllegalOperationException {
        ledgerService.makeDeposit(new BigDecimal(1));
        ledgerService.makeDeposit(new BigDecimal(4));
        ledgerService.makeWithdraw(new BigDecimal(3));
        ledgerService.makeWithdraw(new BigDecimal(2));
        List<Operation> operations = ledgerService.getHistory();
        assertEquals(asList(depositOperation(1), depositOperation(4), withdrawOperation(3),
                withdrawOperation(2)), operations);
    }

    @Test
    public void testFailWhenDepositNegative() {
        assertThrows(IllegalOperationException.class, () -> ledgerService.makeDeposit(new BigDecimal(-1)));
    }

    @Test
    public void testFailWhenWithdrawalNegative() {
        assertThrows(IllegalOperationException.class, () -> ledgerService.makeWithdraw(new BigDecimal(-1)));
    }

    @Test
    public void testFailWhenWithdrawWithZeroBalance() {
        assertThrows(IllegalOperationException.class, () -> ledgerService.makeWithdraw(new BigDecimal(1)));
    }

    @Test
    public void testFailWhenWithdrawMoreThanAvailable() throws IllegalOperationException {
        ledgerService.makeDeposit(new BigDecimal(1));
        assertThrows(IllegalOperationException.class, () -> ledgerService.makeWithdraw(new BigDecimal(2)));
    }
}
