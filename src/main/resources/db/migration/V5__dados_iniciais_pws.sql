INSERT INTO localizacao (
    codigo,
    tipo,
    status
)
VALUES (
           'SL',
           'LOGICA',
           'ATIVA'
       );

INSERT INTO papel (nome, descricao)
VALUES
    ('DEV_MASTER', 'Responsável técnico e administrativo pelo PWS'),
    ('ESTOQUISTA', 'Responsável pelas operações e revisão de estoque'),
    ('CONSULTA', 'Usuário com acesso de consulta'),
    ('ADMIN', 'Responsável por funções administrativas'),
    ('FINANCEIRO', 'Responsável por operações financeiras e aprovação de valores');

INSERT INTO permissao (codigo, descricao)
VALUES
    ('CONSULTAR_PRODUTO', 'Permite consultar produtos'),
    ('CONSULTAR_LOCALIZACAO', 'Permite consultar localizações'),
    ('IMPORTAR_PLANILHA', 'Permite iniciar importações'),
    ('CONFIRMAR_IMPORTACAO', 'Permite confirmar uma importação validada'),
    ('REVISAR_INCONSISTENCIA', 'Permite revisar inconsistências'),
    ('ALTERAR_VALOR', 'Permite alterar manualmente o valor de produto'),
    ('CONSULTAR_SAUDE_TECNICA', 'Permite consultar informações técnicas do sistema'),
    ('CONSULTAR_SAUDE_DADOS', 'Permite consultar a saúde dos dados'),
    ('GERENCIAR_USUARIOS', 'Permite gerenciar usuários e acessos');