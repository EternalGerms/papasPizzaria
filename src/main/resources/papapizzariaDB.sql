-- Criação da sequência (caso você precise de forma manual para outras tabelas)
-- CREATE SEQUENCE IF NOT EXISTS public.enderecosadd_id_seq;

-- Criação da tabela
CREATE TABLE IF NOT EXISTS public.enderecosadd (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    rua VARCHAR(255),
    numero VARCHAR(50),
    complemento VARCHAR(255),
    bairro VARCHAR(255),
    cidade VARCHAR(255),
    estado VARCHAR(50),
    cep VARCHAR(20)
);

-- Removido: ALTER TABLE com DEFAULT nextval (erro corrigido) ✅
-- ALTER TABLE ONLY public.enderecosadd ALTER COLUMN id SET DEFAULT nextval('public.enderecosadd_id_seq');

-- Demais inserts ou configurações que você tiver
-- INSERT INTO public.enderecosadd (rua, numero, ...) VALUES ('Rua A', '123', ...);

