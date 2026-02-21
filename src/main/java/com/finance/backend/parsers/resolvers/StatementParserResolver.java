package com.finance.backend.parsers.resolvers;

import com.finance.backend.interfaces.IBankStatementParser;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class StatementParserResolver {

    private final List<IBankStatementParser> parsers;

    public StatementParserResolver(List<IBankStatementParser> parsers) {
        this.parsers = parsers;
    }

    public IBankStatementParser resolve(String text) {
        return parsers.stream()
                .filter(p -> p.supports(text))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Nenhum parser encontrado para o texto fornecido."));
    }

}
