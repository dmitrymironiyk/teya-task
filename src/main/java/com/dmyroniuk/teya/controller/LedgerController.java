package com.dmyroniuk.teya.controller;

import com.dmyroniuk.teya.controller.dto.BalanceDto;
import com.dmyroniuk.teya.controller.dto.HistoryDto;
import com.dmyroniuk.teya.controller.dto.OperationDto;
import com.dmyroniuk.teya.service.LedgerService;
import com.dmyroniuk.teya.service.exception.IllegalOperationException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.dmyroniuk.teya.controller.ApiConstants.BALANCE_API_CALL_URL;
import static com.dmyroniuk.teya.controller.ApiConstants.DEPOSIT_API_CALL_URL;
import static com.dmyroniuk.teya.controller.ApiConstants.HISTORY_API_CALL_URL;
import static com.dmyroniuk.teya.controller.ApiConstants.LEDGER_API_URL;
import static com.dmyroniuk.teya.controller.ApiConstants.WITHDRAW_API_CALL_URL;


@RestController
@RequestMapping(LEDGER_API_URL)
@Validated
public class LedgerController {

    private final LedgerService ledgerService;

    public LedgerController(@Autowired LedgerService ledgerService) {
        this.ledgerService = ledgerService;
    }

    @GetMapping(HISTORY_API_CALL_URL)
    public HistoryDto history() {
        return new HistoryDto(ledgerService.getHistory());
    }

    @GetMapping(BALANCE_API_CALL_URL)
    public BalanceDto balance() {
        return new BalanceDto(ledgerService.getBalance());
    }

    @PostMapping(DEPOSIT_API_CALL_URL)
    public BalanceDto deposit(@NotNull @Valid @RequestBody OperationDto operationDto) throws IllegalOperationException {
        return new BalanceDto(ledgerService.makeDeposit(operationDto.value()));
    }

    @PostMapping(WITHDRAW_API_CALL_URL)
    public BalanceDto withdraw(@NotNull @Valid @RequestBody OperationDto operationDto) throws IllegalOperationException {
        return new BalanceDto(ledgerService.makeWithdraw(operationDto.value()));
    }
}
