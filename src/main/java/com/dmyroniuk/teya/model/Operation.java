package com.dmyroniuk.teya.model;

import java.math.BigDecimal;

public record Operation(BigDecimal value, OperationType operationType) {}
