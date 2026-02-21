package com.finance.backend.controllers;

import com.finance.backend.domains.transaction.TransactionDTO;
import com.finance.backend.interfaces.IBankStatementParser;
import com.finance.backend.parsers.resolvers.StatementParserResolver;
import com.finance.backend.services.ExtractService;
import com.finance.backend.services.TransactionService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping(value = "extract")
@RequiredArgsConstructor
public class ExtractController {

    private final ExtractService extractService;
    private final TransactionService transactionService;
    private final StatementParserResolver statementParserResolver;

    @PostMapping(path = "import", consumes = "multipart/form-data")
    public ResponseEntity<List<TransactionDTO>> importExtract(@RequestParam("file") MultipartFile file) throws BadRequestException {
        if (file.isEmpty()) {
            throw new BadRequestException("No file uploaded");
        }

        if (!Objects.requireNonNull(file.getOriginalFilename()).endsWith(".pdf")) {
            throw new BadRequestException("Invalid file type. Only PDF files are allowed.");
        }

        String text = extractService.extractDataFromPdf(file);
        IBankStatementParser parser = statementParserResolver.resolve(text);
        List<TransactionDTO> transactions = parser.parse(text);

        transactionService.saveIfNotExists(transactions);

        return ResponseEntity.ok(transactions);
    }

}
