CREATE TABLE IF NOT EXISTS CREDIT_ANALYSIS (
    id uuid NOT NULL,
    client_id uuid NOT NULL,
    cpf VARCHAR(11) UNIQUE,
    approved BOOLEAN,
    approved_limit DOUBLE PRECISION,
    requested_amount DOUBLE PRECISION,
    monthly_income DOUBLE PRECISION,
    withdraw DOUBLE PRECISION,
    annual_interest DECIMAL(5, 2),
    date TIMESTAMP,
    PRIMARY KEY (id)
);