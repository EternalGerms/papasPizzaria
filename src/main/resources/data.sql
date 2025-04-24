INSERT INTO usuario (tipo, cpfcnpj, email, login, nome_completo, senha, situacao, telefone)
VALUES (2, '12345678910', 'admin@gmail.com', 'admin', 'Administrador da Silva', '$2a$10$Qzjh2/wbxFu4qSno3NDC6uzSLg.z1s9FGtzQpgt3KYE6T4WmjETKS', 'ATIVO', '149981231208')
ON CONFLICT (id) DO NOTHING;

INSERT INTO usuario (tipo, cpfcnpj, email, login, nome_completo, senha, situacao, telefone)
VALUES (1, '98765432100', 'cliente@gmail.com', 'cliente', 'Cliente da Silva', '$2a$10$Qzjh2/wbxFu4qSno3NDC6uzSLg.z1s9FGtzQpgt3KYE6T4WmjETKS', 'ATIVO', '149988887777')
ON CONFLICT (id) DO NOTHING;

INSERT INTO usuario (tipo, cpfcnpj, email, login, nome_completo, senha, situacao, telefone)
VALUES (1, '98765432101', 'cliente2@gmail.com', 'cliente2', 'Cliente da Silva Junior', '$2a$10$Qzjh2/wbxFu4qSno3NDC6uzSLg.z1s9FGtzQpgt3KYE6T4WmjETKS', 'ATIVO', '149988887777')
ON CONFLICT (id) DO NOTHING;