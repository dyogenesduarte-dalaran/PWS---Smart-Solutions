CREATE TABLE documento_origem (
                                  id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                                  nome_arquivo VARCHAR(255) NOT NULL,
                                  hash_arquivo VARCHAR(128) NOT NULL,
                                  caminho_armazenamento VARCHAR(1000) NOT NULL,
                                  tamanho_arquivo BIGINT NOT NULL,
                                  criado_em TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                  CONSTRAINT uk_documento_hash UNIQUE (hash_arquivo),
                                  CONSTRAINT ck_documento_tamanho CHECK (tamanho_arquivo >= 0)
);

CREATE TABLE importacao (
                            id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                            documento_origem_id BIGINT NOT NULL,
                            usuario_id BIGINT,
                            importacao_origem_id BIGINT,
                            status VARCHAR(40) NOT NULL DEFAULT 'RECEBIDA',
                            iniciado_em TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                            finalizado_em TIMESTAMP,
                            total_linhas INTEGER NOT NULL DEFAULT 0,
                            linhas_processadas INTEGER NOT NULL DEFAULT 0,
                            linhas_validas INTEGER NOT NULL DEFAULT 0,
                            linhas_quarentena INTEGER NOT NULL DEFAULT 0,
                            mensagem_erro VARCHAR(1000),
                            CONSTRAINT fk_importacao_documento FOREIGN KEY (documento_origem_id) REFERENCES documento_origem(id) ON DELETE RESTRICT ON UPDATE RESTRICT,
                            CONSTRAINT fk_importacao_origem FOREIGN KEY (importacao_origem_id) REFERENCES importacao(id) ON DELETE RESTRICT ON UPDATE RESTRICT,
                            CONSTRAINT ck_importacao_status CHECK (status IN ('RECEBIDA','VALIDANDO','VALIDADA','AGUARDANDO_CONFIRMACAO','PROCESSANDO','CONCLUIDA','FALHOU','CANCELADA')),
                            CONSTRAINT ck_importacao_total_linhas CHECK (total_linhas >= 0),
                            CONSTRAINT ck_importacao_processadas CHECK (linhas_processadas >= 0),
                            CONSTRAINT ck_importacao_validas CHECK (linhas_validas >= 0),
                            CONSTRAINT ck_importacao_quarentena CHECK (linhas_quarentena >= 0)
);

CREATE TABLE registro_staging (
                                  id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                                  importacao_id BIGINT NOT NULL,
                                  numero_linha_origem INTEGER NOT NULL,
                                  part_number_original VARCHAR(255),
                                  descricao_original VARCHAR(500),
                                  localizacao_original VARCHAR(100),
                                  quantidade_original VARCHAR(100),
                                  valor_original VARCHAR(100),
                                  curva_original VARCHAR(100),
                                  status_validacao VARCHAR(30) NOT NULL DEFAULT 'PENDENTE',
                                  motivo_validacao VARCHAR(1000),
                                  criado_em TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                  CONSTRAINT fk_staging_importacao FOREIGN KEY (importacao_id) REFERENCES importacao(id) ON DELETE RESTRICT ON UPDATE RESTRICT,
                                  CONSTRAINT uk_staging_importacao_linha UNIQUE (importacao_id, numero_linha_origem),
                                  CONSTRAINT ck_staging_linha CHECK (numero_linha_origem > 0),
                                  CONSTRAINT ck_staging_status CHECK (status_validacao IN ('PENDENTE','VALIDO','PENDENTE_REVISAO','INVALIDO','CORROMPIDO'))
);

CREATE TABLE quarentena (
                            id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                            registro_staging_id BIGINT NOT NULL,
                            tipo_problema VARCHAR(80) NOT NULL,
                            status VARCHAR(30) NOT NULL DEFAULT 'PENDENTE_REVISAO',
                            motivo VARCHAR(1000) NOT NULL,
                            decisao VARCHAR(1000),
                            usuario_responsavel_id BIGINT,
                            criado_em TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                            resolvido_em TIMESTAMP,
                            CONSTRAINT fk_quarentena_staging FOREIGN KEY (registro_staging_id) REFERENCES registro_staging(id) ON DELETE RESTRICT ON UPDATE RESTRICT,
                            CONSTRAINT uk_quarentena_registro UNIQUE (registro_staging_id),
                            CONSTRAINT ck_quarentena_status CHECK (status IN ('PENDENTE_REVISAO','CORRIGIDO','REJEITADO'))
);