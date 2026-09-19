CREATE TABLE produto (
                         id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                         part_number_original VARCHAR(255) NOT NULL,
                         part_number_normalizado VARCHAR(255) NOT NULL,
                         descricao_original VARCHAR(500) NOT NULL,
                         descricao_normalizada VARCHAR(500) NOT NULL,
                         valor NUMERIC(15,2) NOT NULL,
                         curva VARCHAR(50) NOT NULL DEFAULT 'SEM_CLASSIFICACAO',
                         status_produto VARCHAR(20) NOT NULL DEFAULT 'ATIVO',
                         criado_em TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                         atualizado_em TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                         CONSTRAINT uk_produto_identidade UNIQUE (part_number_normalizado, descricao_normalizada),
                         CONSTRAINT ck_produto_valor CHECK (valor >= 0),
                         CONSTRAINT ck_produto_status CHECK (status_produto IN ('ATIVO', 'INATIVO'))
);

CREATE TABLE localizacao (
                             id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                             codigo VARCHAR(100) NOT NULL,
                             tipo VARCHAR(20) NOT NULL,
                             status VARCHAR(20) NOT NULL DEFAULT 'ATIVA',
                             criado_em TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                             atualizado_em TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                             CONSTRAINT uk_localizacao_codigo UNIQUE (codigo),
                             CONSTRAINT ck_localizacao_tipo CHECK (tipo IN ('FISICA', 'LOGICA')),
                             CONSTRAINT ck_localizacao_status CHECK (status IN ('ATIVA', 'INATIVA'))
);

CREATE TABLE posicao_estoque (
                                 id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                                 produto_id BIGINT NOT NULL,
                                 localizacao_id BIGINT NOT NULL,
                                 quantidade INTEGER NOT NULL,
                                 status_qualidade VARCHAR(30) NOT NULL DEFAULT 'REGULAR',
                                 criado_em TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                 atualizado_em TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                 CONSTRAINT fk_posicao_produto FOREIGN KEY (produto_id) REFERENCES produto(id) ON DELETE RESTRICT ON UPDATE RESTRICT,
                                 CONSTRAINT fk_posicao_localizacao FOREIGN KEY (localizacao_id) REFERENCES localizacao(id) ON DELETE RESTRICT ON UPDATE RESTRICT,
                                 CONSTRAINT uk_posicao_produto_localizacao UNIQUE (produto_id, localizacao_id),
                                 CONSTRAINT ck_posicao_quantidade CHECK (quantidade >= 0),
                                 CONSTRAINT ck_posicao_status_qualidade CHECK (status_qualidade IN ('REGULAR', 'PENDENTE_REVISAO', 'INCONSISTENTE'))
);