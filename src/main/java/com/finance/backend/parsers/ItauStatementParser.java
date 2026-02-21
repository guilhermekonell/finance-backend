package com.finance.backend.parsers;

import com.finance.backend.domains.transaction.TransactionDTO;
import com.finance.backend.enums.BankEnum;
import com.finance.backend.interfaces.IBankStatementParser;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class ItauStatementParser implements IBankStatementParser {

    private static final Pattern PADRAO = Pattern.compile(
            "^(\\d{2}/\\d{2}/\\d{4})\\s+(.*?)\\s+(-?\\d{1,3}(?:\\.\\d{3})*,\\d{2})$"
    );

    @Override
    public boolean supports(String text) {
        return text.toLowerCase().contains("itau") || text.toLowerCase().contains("itaú");
    }

    @Override
    public List<TransactionDTO> parse(String text) {
        List<TransactionDTO> list = new ArrayList<>();

        for (String line : text.split("\\r?\\n")) {
            if (line.contains("SALDO")) continue;

            Matcher m = PADRAO.matcher(line);
            if (m.matches()) {
                LocalDate date = LocalDate.parse(m.group(1), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                String description = m.group(2).trim();
                BigDecimal amount = new BigDecimal(
                        m.group(3).replace(".", "").replace(",", ".")
                );

                list.add(new TransactionDTO(
                        date,
                        description,
                        amount,
                        BankEnum.ITAU.name()
                ));
            }
        }
        return list;
    }
}
