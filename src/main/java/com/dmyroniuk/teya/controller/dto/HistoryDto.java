package com.dmyroniuk.teya.controller.dto;

import com.dmyroniuk.teya.model.Operation;

import java.util.List;

public record HistoryDto(List<Operation> operations) {}
