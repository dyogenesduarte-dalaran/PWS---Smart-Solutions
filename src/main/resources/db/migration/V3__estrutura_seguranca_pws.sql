CREATE TABLE usuario (
                         id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                         nome VARCHAR(255) NOT NULL,
                         login VARCHAR(150) NOT NULL,
                         senha_hash VARCHAR(255) NOT NULL,
                         status_conta VARCHAR(20) NOT NULL DEFAULT 'ATIVA',
                         criado_em TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                         atualizado_em TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                         CONSTRAINT uk_usuario_login UNIQUE (login),
                         CONSTRAINT ck_usuario_status CHECK (status_conta IN ('ATIVA','INATIVA','BLOQUEADA'))
);

CREATE TABLE papel (
                       id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                       nome VARCHAR(80) NOT NULL,
                       descricao VARCHAR(500),
                       ativo BOOLEAN NOT NULL DEFAULT TRUE,
                       criado_em TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                       atualizado_em TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                       CONSTRAINT uk_papel_nome UNIQUE (nome)
);

CREATE TABLE permissao (
                           id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                           codigo VARCHAR(120) NOT NULL,
                           descricao VARCHAR(500),
                           criado_em TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                           CONSTRAINT uk_permissao_codigo UNIQUE (codigo)
);

CREATE TABLE usuario_papel (
                               usuario_id BIGINT NOT NULL,
                               papel_id BIGINT NOT NULL,
                               atribuido_em TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                               CONSTRAINT pk_usuario_papel PRIMARY KEY (usuario_id, papel_id),
                               CONSTRAINT fk_usuario_papel_usuario FOREIGN KEY (usuario_id) REFERENCES usuario(id) ON DELETE RESTRICT ON UPDATE RESTRICT,
                               CONSTRAINT fk_usuario_papel_papel FOREIGN KEY (papel_id) REFERENCES papel(id) ON DELETE RESTRICT ON UPDATE RESTRICT
);

CREATE TABLE papel_permissao (
                                 papel_id BIGINT NOT NULL,
                                 permissao_id BIGINT NOT NULL,
                                 atribuido_em TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                 CONSTRAINT pk_papel_permissao PRIMARY KEY (papel_id, permissao_id),
                                 CONSTRAINT fk_papel_permissao_papel FOREIGN KEY (papel_id) REFERENCES papel(id) ON DELETE RESTRICT ON UPDATE RESTRICT,
                                 CONSTRAINT fk_papel_permissao_permissao FOREIGN KEY (permissao_id) REFERENCES permissao(id) ON DELETE RESTRICT ON UPDATE RESTRICT
);

ALTER TABLE importacao
    ADD CONSTRAINT fk_importacao_usuario
        FOREIGN KEY (usuario_id)
            REFERENCES usuario(id)
            ON DELETE RESTRICT
            ON UPDATE RESTRICT;

ALTER TABLE quarentena
    ADD CONSTRAINT fk_quarentena_usuario
        FOREIGN KEY (usuario_responsavel_id)
            REFERENCES usuario(id)
            ON DELETE RESTRICT
            ON UPDATE RESTRICT;