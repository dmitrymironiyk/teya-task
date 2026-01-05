package com.dmyroniuk.teya.controller.dto;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record OperationDto(@NotNull BigDecimal value) {}
