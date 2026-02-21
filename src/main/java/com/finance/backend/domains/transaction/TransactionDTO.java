package com.finance.backend.domains.transaction;

import java.math.BigDecimal;
import java.time.LocalDate;

public record TransactionDTO(LocalDate date, String description, BigDecimal amount, String bank) {}
