package com.dmyroniuk.teya.persistence;

import com.dmyroniuk.teya.model.Operation;

import java.math.BigDecimal;
import java.util.List;

public interface LedgerStorage {
    BigDecimal getBalance();

    void addOperation(Operation operation);

    List<Operation> getAllOperations();
}
