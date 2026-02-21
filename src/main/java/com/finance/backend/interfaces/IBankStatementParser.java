package com.finance.backend.interfaces;

import com.finance.backend.domains.transaction.TransactionDTO;

import java.util.List;

public interface IBankStatementParser {

    boolean supports(String text);
    List<TransactionDTO> parse(String text);

}
