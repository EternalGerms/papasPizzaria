INSERT INTO usuario (tipo, cpfcnpj, email, login, nome_completo, senha, situacao, telefone)
VALUES (2, '12345678910', 'admin@gmail.com', 'admin', 'Administrador da Silva', '$2a$10$Qzjh2/wbxFu4qSno3NDC6uzSLg.z1s9FGtzQpgt3KYE6T4WmjETKS', 'ATIVO', '149981231208')
ON CONFLICT (id) DO NOTHING;