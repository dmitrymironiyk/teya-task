package com.dmyroniuk.teya.service;

import com.dmyroniuk.teya.model.Operation;
import com.dmyroniuk.teya.model.OperationType;
import com.dmyroniuk.teya.persistence.LedgerStorage;
import com.dmyroniuk.teya.service.exception.IllegalOperationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;


@Service
public class LedgerService {
    private final LedgerStorage ledgerStorage;

    public LedgerService(@Autowired LedgerStorage ledgerStorage) {
        this.ledgerStorage = ledgerStorage;
    }

    public List<Operation> getHistory() {
        return ledgerStorage.getAllOperations();
    }

    public BigDecimal getBalance() {
        return ledgerStorage.getBalance();
    }

    public BigDecimal makeDeposit(BigDecimal value) throws IllegalOperationException {
        if (isPositiveNumber(value)) {
            throw new IllegalOperationException("Deposit value should be more than zero");
        }
        ledgerStorage.addOperation(new Operation(value, OperationType.DEPOSIT));
        return ledgerStorage.getBalance();
    }

    public BigDecimal makeWithdraw(BigDecimal value) throws IllegalOperationException {
        if (isPositiveNumber(value)) {
            throw new IllegalOperationException("Withdrawn value should be more than zero");
        }
        if (isEnoughBalance(value)) {
            throw new IllegalOperationException("Insufficient balance to make withdrawal");
        }
        ledgerStorage.addOperation(new Operation(value, OperationType.WITHDRAW));
        return ledgerStorage.getBalance();
    }

    private boolean isEnoughBalance(BigDecimal value) {
        return value.compareTo(ledgerStorage.getBalance()) > 0;
    }

    private static boolean isPositiveNumber(BigDecimal value) {
        return value.compareTo(new BigDecimal(0)) <= 0;
    }
}
