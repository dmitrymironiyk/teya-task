package com.dmyroniuk.teya;

import com.dmyroniuk.teya.model.Operation;
import com.dmyroniuk.teya.model.OperationType;

import java.math.BigDecimal;

public class OperationTestUtils {
    public static Operation depositOperation(int value) {
        return new Operation(new BigDecimal(value), OperationType.DEPOSIT);
    }

    public static Operation withdrawOperation(int value) {
        return new Operation(new BigDecimal(value), OperationType.WITHDRAW);
    }
}
