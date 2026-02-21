CREATE TABLE transactions (
    id BIGSERIAL PRIMARY KEY,
    transaction_date DATE NOT NULL,
    description VARCHAR(255) NOT NULL,
    amount NUMERIC(15,2) NOT NULL,
    bank VARCHAR(50) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    hash VARCHAR(64) NOT NULL
);

CREATE UNIQUE INDEX uk_transactions_hash
ON transactions (hash)
WHERE hash IS NOT NULL;