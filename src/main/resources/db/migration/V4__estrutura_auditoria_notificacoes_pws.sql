CREATE TABLE inconsistencia (
                                id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                                tipo VARCHAR(80) NOT NULL,
                                status VARCHAR(30) NOT NULL DEFAULT 'PENDENTE_REVISAO',
                                severidade VARCHAR(20) NOT NULL DEFAULT 'ATENCAO',
                                produto_id BIGINT,
                                posicao_estoque_id BIGINT,
                                localizacao_id BIGINT,
                                importacao_id BIGINT,
                                registro_staging_id BIGINT,
                                descricao VARCHAR(1000) NOT NULL,
                                justificativa VARCHAR(1000),
                                usuario_responsavel_id BIGINT,
                                detectado_em TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                resolvido_em TIMESTAMP,
                                atualizado_em TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                CONSTRAINT fk_inconsistencia_produto FOREIGN KEY (produto_id) REFERENCES produto(id) ON DELETE RESTRICT ON UPDATE RESTRICT,
                                CONSTRAINT fk_inconsistencia_posicao FOREIGN KEY (posicao_estoque_id) REFERENCES posicao_estoque(id) ON DELETE RESTRICT ON UPDATE RESTRICT,
                                CONSTRAINT fk_inconsistencia_localizacao FOREIGN KEY (localizacao_id) REFERENCES localizacao(id) ON DELETE RESTRICT ON UPDATE RESTRICT,
                                CONSTRAINT fk_inconsistencia_importacao FOREIGN KEY (importacao_id) REFERENCES importacao(id) ON DELETE RESTRICT ON UPDATE RESTRICT,
                                CONSTRAINT fk_inconsistencia_staging FOREIGN KEY (registro_staging_id) REFERENCES registro_staging(id) ON DELETE RESTRICT ON UPDATE RESTRICT,
                                CONSTRAINT fk_inconsistencia_usuario FOREIGN KEY (usuario_responsavel_id) REFERENCES usuario(id) ON DELETE RESTRICT ON UPDATE RESTRICT,
                                CONSTRAINT ck_inconsistencia_status CHECK (status IN ('PENDENTE_REVISAO','INCONSISTENTE','CORRIGIDO','ACEITO_COMO_EXCECAO')),
                                CONSTRAINT ck_inconsistencia_severidade CHECK (severidade IN ('INFO','ATENCAO','CRITICO'))
);

CREATE TABLE historico (
                           id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                           tipo_evento VARCHAR(100) NOT NULL,
                           produto_id BIGINT,
                           posicao_estoque_id BIGINT,
                           localizacao_id BIGINT,
                           importacao_id BIGINT,
                           inconsistencia_id BIGINT,
                           usuario_id BIGINT,
                           campo_alterado VARCHAR(100),
                           valor_anterior VARCHAR(1000),
                           valor_novo VARCHAR(1000),
                           origem VARCHAR(30) NOT NULL,
                           motivo VARCHAR(1000),
                           ocorrido_em TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                           registrado_em TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                           CONSTRAINT fk_historico_produto FOREIGN KEY (produto_id) REFERENCES produto(id) ON DELETE RESTRICT ON UPDATE RESTRICT,
                           CONSTRAINT fk_historico_posicao FOREIGN KEY (posicao_estoque_id) REFERENCES posicao_estoque(id) ON DELETE RESTRICT ON UPDATE RESTRICT,
                           CONSTRAINT fk_historico_localizacao FOREIGN KEY (localizacao_id) REFERENCES localizacao(id) ON DELETE RESTRICT ON UPDATE RESTRICT,
                           CONSTRAINT fk_historico_importacao FOREIGN KEY (importacao_id) REFERENCES importacao(id) ON DELETE RESTRICT ON UPDATE RESTRICT,
                           CONSTRAINT fk_historico_inconsistencia FOREIGN KEY (inconsistencia_id) REFERENCES inconsistencia(id) ON DELETE RESTRICT ON UPDATE RESTRICT,
                           CONSTRAINT fk_historico_usuario FOREIGN KEY (usuario_id) REFERENCES usuario(id) ON DELETE RESTRICT ON UPDATE RESTRICT,
                           CONSTRAINT ck_historico_origem CHECK (origem IN ('IMPORTACAO','ACAO_MANUAL','CORRECAO','REVISAO','PROCESSO_SISTEMA'))
);

CREATE TABLE notificacao (
                             id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                             tipo VARCHAR(80) NOT NULL,
                             severidade VARCHAR(20) NOT NULL DEFAULT 'INFO',
                             titulo VARCHAR(255) NOT NULL,
                             mensagem VARCHAR(1000) NOT NULL,
                             inconsistencia_id BIGINT,
                             importacao_id BIGINT,
                             criado_em TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                             resolvido_em TIMESTAMP,
                             CONSTRAINT fk_notificacao_inconsistencia FOREIGN KEY (inconsistencia_id) REFERENCES inconsistencia(id) ON DELETE RESTRICT ON UPDATE RESTRICT,
                             CONSTRAINT fk_notificacao_importacao FOREIGN KEY (importacao_id) REFERENCES importacao(id) ON DELETE RESTRICT ON UPDATE RESTRICT,
                             CONSTRAINT ck_notificacao_severidade CHECK (severidade IN ('INFO','ATENCAO','CRITICO'))
);

CREATE TABLE notificacao_destinatario (
                                          notificacao_id BIGINT NOT NULL,
                                          usuario_id BIGINT NOT NULL,
                                          lida_em TIMESTAMP,
                                          CONSTRAINT pk_notificacao_destinatario PRIMARY KEY (notificacao_id, usuario_id),
                                          CONSTRAINT fk_notificacao_destinatario_notificacao FOREIGN KEY (notificacao_id) REFERENCES notificacao(id) ON DELETE RESTRICT ON UPDATE RESTRICT,
                                          CONSTRAINT fk_notificacao_destinatario_usuario FOREIGN KEY (usuario_id) REFERENCES usuario(id) ON DELETE RESTRICT ON UPDATE RESTRICT
);