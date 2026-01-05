package com.dmyroniuk.teya.persistence;

import com.dmyroniuk.teya.model.Operation;
import com.dmyroniuk.teya.model.OperationType;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


@Component
public class InMemoryLedgerStorage implements LedgerStorage{
    private final List<Operation> operations = new ArrayList<>();
    private BigDecimal balance = new BigDecimal(0);

    public BigDecimal getBalance() {
        return balance;
    }

    public void addOperation(Operation operation) {
        operations.add(operation);
        updateBalance(operation);
    }

    public List<Operation> getAllOperations() {
        return operations;
    }

    private void updateBalance(Operation operation) {
        if (operation.operationType() == OperationType.WITHDRAW) {
            balance = balance.subtract(operation.value());
        } else if (operation.operationType() == OperationType.DEPOSIT) {
            balance = balance.add(operation.value());
        } else {
            throw new IllegalArgumentException("Invalid operation type");
        }
    }
}
