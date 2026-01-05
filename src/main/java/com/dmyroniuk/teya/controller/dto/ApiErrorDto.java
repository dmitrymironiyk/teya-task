package com.dmyroniuk.teya.controller.dto;

import java.time.Instant;

public record ApiErrorDto(String message, Instant timestamp) {}
