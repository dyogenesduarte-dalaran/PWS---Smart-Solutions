-- 1. Tabela Staging Area (recebe os dados brutos da importação da planilha)
CREATE TABLE IF NOT EXISTS staging_area (
                                            col1 TEXT,
                                            col2 TEXT,
                                            col3 TEXT,
                                            col4 TEXT,
                                            col5 TEXT,
                                            col6 TEXT,
                                            col7 TEXT,
                                            col8 TEXT,
                                            col9 TEXT,
                                            col10 TEXT,
                                            col11 TEXT
);

-- 2. Tabela Products (tabela oficial tratada e limpa)
CREATE TABLE IF NOT EXISTS products (
                                        id SERIAL PRIMARY KEY,
                                        product_code VARCHAR(100) UNIQUE NOT NULL,
    product_name TEXT NOT NULL,
    product_location VARCHAR(100),
    category VARCHAR(100),
    quantity INT DEFAULT 0,
    price NUMERIC(10,2) DEFAULT 0.00
    );