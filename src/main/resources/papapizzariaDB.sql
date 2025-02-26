--
-- PostgreSQL database dump
--

-- Dumped from database version 17.3
-- Dumped by pg_dump version 17.2

-- Started on 2025-02-26 01:37:45

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET transaction_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 217 (class 1259 OID 18475)
-- Name: caixa; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.caixa (
    idcaixa integer NOT NULL,
    nrtrans integer NOT NULL,
    tipo character varying NOT NULL,
    operador integer NOT NULL,
    venda integer
);


ALTER TABLE public.caixa OWNER TO postgres;

--
-- TOC entry 218 (class 1259 OID 18480)
-- Name: enderecosadd; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.enderecosadd (
    id integer NOT NULL,
    idcliente integer,
    endereco character varying(255),
    bairro character varying(255),
    numero character varying(5),
    complemento character varying(255),
    observac text
);


ALTER TABLE public.enderecosadd OWNER TO postgres;

--
-- TOC entry 219 (class 1259 OID 18485)
-- Name: enderecosadd_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.enderecosadd_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.enderecosadd_id_seq OWNER TO postgres;

--
-- TOC entry 4997 (class 0 OID 0)
-- Dependencies: 219
-- Name: enderecosadd_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.enderecosadd_id_seq OWNED BY public.enderecosadd.id;


--
-- TOC entry 220 (class 1259 OID 18486)
-- Name: entregas; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.entregas (
    id integer NOT NULL,
    venda integer NOT NULL,
    status character(1) NOT NULL
);


ALTER TABLE public.entregas OWNER TO postgres;

--
-- TOC entry 221 (class 1259 OID 18489)
-- Name: entregas_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.entregas_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.entregas_id_seq OWNER TO postgres;

--
-- TOC entry 4998 (class 0 OID 0)
-- Dependencies: 221
-- Name: entregas_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.entregas_id_seq OWNED BY public.entregas.id;


--
-- TOC entry 222 (class 1259 OID 18490)
-- Name: grupos; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.grupos (
    id integer NOT NULL,
    descricao character varying(255) NOT NULL,
    image text
);


ALTER TABLE public.grupos OWNER TO postgres;

--
-- TOC entry 223 (class 1259 OID 18495)
-- Name: grupos_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.grupos_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.grupos_id_seq OWNER TO postgres;

--
-- TOC entry 4999 (class 0 OID 0)
-- Dependencies: 223
-- Name: grupos_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.grupos_id_seq OWNED BY public.grupos.id;


--
-- TOC entry 224 (class 1259 OID 18496)
-- Name: itens; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.itens (
    id_produto integer NOT NULL,
    venda integer NOT NULL,
    precoproduto numeric(15,3),
    quantidade double precision NOT NULL,
    grupo_produto integer
);


ALTER TABLE public.itens OWNER TO postgres;

--
-- TOC entry 225 (class 1259 OID 18499)
-- Name: ppg; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.ppg (
    id integer NOT NULL,
    descricao character varying(255) NOT NULL,
    tipo character varying(5) NOT NULL
);


ALTER TABLE public.ppg OWNER TO postgres;

--
-- TOC entry 226 (class 1259 OID 18502)
-- Name: ppg_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.ppg_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.ppg_id_seq OWNER TO postgres;

--
-- TOC entry 5000 (class 0 OID 0)
-- Dependencies: 226
-- Name: ppg_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.ppg_id_seq OWNED BY public.ppg.id;


--
-- TOC entry 227 (class 1259 OID 18503)
-- Name: produtos; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.produtos (
    id integer NOT NULL,
    descricao character varying(255) NOT NULL,
    precovenda numeric(15,3) DEFAULT 0.000,
    unidade character varying(3),
    estoque numeric(10,3) DEFAULT 0.000,
    grupos integer,
    image text
);


ALTER TABLE public.produtos OWNER TO postgres;

--
-- TOC entry 228 (class 1259 OID 18510)
-- Name: produtos_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.produtos_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.produtos_id_seq OWNER TO postgres;

--
-- TOC entry 5001 (class 0 OID 0)
-- Dependencies: 228
-- Name: produtos_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.produtos_id_seq OWNED BY public.produtos.id;


--
-- TOC entry 229 (class 1259 OID 18511)
-- Name: status_entrega; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.status_entrega (
    id integer NOT NULL,
    descricao character varying(255)
);


ALTER TABLE public.status_entrega OWNER TO postgres;

--
-- TOC entry 230 (class 1259 OID 18514)
-- Name: status_entrega_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.status_entrega_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.status_entrega_id_seq OWNER TO postgres;

--
-- TOC entry 5002 (class 0 OID 0)
-- Dependencies: 230
-- Name: status_entrega_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.status_entrega_id_seq OWNED BY public.status_entrega.id;


--
-- TOC entry 234 (class 1259 OID 18751)
-- Name: usuario; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.usuario (
    id bigint NOT NULL,
    cpfcnpj character varying(255) NOT NULL,
    email character varying(255) NOT NULL,
    login character varying(255) NOT NULL,
    nome_completo character varying(255) NOT NULL,
    senha character varying(255) NOT NULL,
    situacao character varying(255),
    telefone character varying(255) NOT NULL,
    CONSTRAINT usuario_situacao_check CHECK (((situacao)::text = ANY ((ARRAY['ATIVO'::character varying, 'INATIVO'::character varying, 'PENDENTE'::character varying])::text[])))
);


ALTER TABLE public.usuario OWNER TO postgres;

--
-- TOC entry 233 (class 1259 OID 18750)
-- Name: usuario_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

ALTER TABLE public.usuario ALTER COLUMN id ADD GENERATED BY DEFAULT AS IDENTITY (
    SEQUENCE NAME public.usuario_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- TOC entry 231 (class 1259 OID 18521)
-- Name: vendas; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.vendas (
    venda integer NOT NULL,
    id_cliente integer NOT NULL,
    id_produto integer NOT NULL,
    id_ppg integer NOT NULL,
    precototal numeric(15,3),
    id_vendedor integer NOT NULL,
    data_venda date NOT NULL,
    desconto numeric(15,3),
    acrescimo numeric(15,3),
    observacao character varying(255),
    retirada character(1) NOT NULL,
    status character(1) NOT NULL,
    id_endereco integer
);


ALTER TABLE public.vendas OWNER TO postgres;

--
-- TOC entry 232 (class 1259 OID 18524)
-- Name: vendas_venda_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.vendas_venda_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.vendas_venda_seq OWNER TO postgres;

--
-- TOC entry 5003 (class 0 OID 0)
-- Dependencies: 232
-- Name: vendas_venda_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.vendas_venda_seq OWNED BY public.vendas.venda;


--
-- TOC entry 4785 (class 2604 OID 18525)
-- Name: enderecosadd id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.enderecosadd ALTER COLUMN id SET DEFAULT nextval('public.enderecosadd_id_seq'::regclass);


--
-- TOC entry 4786 (class 2604 OID 18526)
-- Name: entregas id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.entregas ALTER COLUMN id SET DEFAULT nextval('public.entregas_id_seq'::regclass);


--
-- TOC entry 4787 (class 2604 OID 18527)
-- Name: grupos id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.grupos ALTER COLUMN id SET DEFAULT nextval('public.grupos_id_seq'::regclass);


--
-- TOC entry 4788 (class 2604 OID 18528)
-- Name: ppg id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.ppg ALTER COLUMN id SET DEFAULT nextval('public.ppg_id_seq'::regclass);


--
-- TOC entry 4789 (class 2604 OID 18529)
-- Name: produtos id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.produtos ALTER COLUMN id SET DEFAULT nextval('public.produtos_id_seq'::regclass);


--
-- TOC entry 4792 (class 2604 OID 18530)
-- Name: status_entrega id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.status_entrega ALTER COLUMN id SET DEFAULT nextval('public.status_entrega_id_seq'::regclass);


--
-- TOC entry 4793 (class 2604 OID 18532)
-- Name: vendas venda; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.vendas ALTER COLUMN venda SET DEFAULT nextval('public.vendas_venda_seq'::regclass);


--
-- TOC entry 4974 (class 0 OID 18475)
-- Dependencies: 217
-- Data for Name: caixa; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.caixa (idcaixa, nrtrans, tipo, operador, venda) FROM stdin;
\.


--
-- TOC entry 4975 (class 0 OID 18480)
-- Dependencies: 218
-- Data for Name: enderecosadd; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.enderecosadd (id, idcliente, endereco, bairro, numero, complemento, observac) FROM stdin;
\.


--
-- TOC entry 4977 (class 0 OID 18486)
-- Dependencies: 220
-- Data for Name: entregas; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.entregas (id, venda, status) FROM stdin;
\.


--
-- TOC entry 4979 (class 0 OID 18490)
-- Dependencies: 222
-- Data for Name: grupos; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.grupos (id, descricao, image) FROM stdin;
\.


--
-- TOC entry 4981 (class 0 OID 18496)
-- Dependencies: 224
-- Data for Name: itens; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.itens (id_produto, venda, precoproduto, quantidade, grupo_produto) FROM stdin;
\.


--
-- TOC entry 4982 (class 0 OID 18499)
-- Dependencies: 225
-- Data for Name: ppg; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.ppg (id, descricao, tipo) FROM stdin;
\.


--
-- TOC entry 4984 (class 0 OID 18503)
-- Dependencies: 227
-- Data for Name: produtos; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.produtos (id, descricao, precovenda, unidade, estoque, grupos, image) FROM stdin;
\.


--
-- TOC entry 4986 (class 0 OID 18511)
-- Dependencies: 229
-- Data for Name: status_entrega; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.status_entrega (id, descricao) FROM stdin;
\.


--
-- TOC entry 4991 (class 0 OID 18751)
-- Dependencies: 234
-- Data for Name: usuario; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.usuario (id, cpfcnpj, email, login, nome_completo, senha, situacao, telefone) FROM stdin;
\.


--
-- TOC entry 4988 (class 0 OID 18521)
-- Dependencies: 231
-- Data for Name: vendas; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.vendas (venda, id_cliente, id_produto, id_ppg, precototal, id_vendedor, data_venda, desconto, acrescimo, observacao, retirada, status, id_endereco) FROM stdin;
\.


--
-- TOC entry 5004 (class 0 OID 0)
-- Dependencies: 219
-- Name: enderecosadd_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.enderecosadd_id_seq', 1, false);


--
-- TOC entry 5005 (class 0 OID 0)
-- Dependencies: 221
-- Name: entregas_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.entregas_id_seq', 1, false);


--
-- TOC entry 5006 (class 0 OID 0)
-- Dependencies: 223
-- Name: grupos_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.grupos_id_seq', 1, false);


--
-- TOC entry 5007 (class 0 OID 0)
-- Dependencies: 226
-- Name: ppg_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.ppg_id_seq', 1, false);


--
-- TOC entry 5008 (class 0 OID 0)
-- Dependencies: 228
-- Name: produtos_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.produtos_id_seq', 1, false);


--
-- TOC entry 5009 (class 0 OID 0)
-- Dependencies: 230
-- Name: status_entrega_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.status_entrega_id_seq', 1, false);


--
-- TOC entry 5010 (class 0 OID 0)
-- Dependencies: 233
-- Name: usuario_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.usuario_id_seq', 1, false);


--
-- TOC entry 5011 (class 0 OID 0)
-- Dependencies: 232
-- Name: vendas_venda_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.vendas_venda_seq', 1, false);


--
-- TOC entry 4797 (class 2606 OID 18534)
-- Name: enderecosadd enderecosadd_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.enderecosadd
    ADD CONSTRAINT enderecosadd_pkey PRIMARY KEY (id);


--
-- TOC entry 4799 (class 2606 OID 18536)
-- Name: entregas entregas_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.entregas
    ADD CONSTRAINT entregas_pkey PRIMARY KEY (id);


--
-- TOC entry 4801 (class 2606 OID 18538)
-- Name: grupos grupos_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.grupos
    ADD CONSTRAINT grupos_pkey PRIMARY KEY (id);


--
-- TOC entry 4803 (class 2606 OID 18540)
-- Name: ppg ppg_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.ppg
    ADD CONSTRAINT ppg_pkey PRIMARY KEY (id);


--
-- TOC entry 4805 (class 2606 OID 18542)
-- Name: produtos produtos_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.produtos
    ADD CONSTRAINT produtos_pkey PRIMARY KEY (id);


--
-- TOC entry 4807 (class 2606 OID 18544)
-- Name: status_entrega status_entrega_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.status_entrega
    ADD CONSTRAINT status_entrega_pkey PRIMARY KEY (id);


--
-- TOC entry 4813 (class 2606 OID 18760)
-- Name: usuario usuario_cpfcnpj_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.usuario
    ADD CONSTRAINT usuario_cpfcnpj_key UNIQUE (cpfcnpj);


--
-- TOC entry 4815 (class 2606 OID 18762)
-- Name: usuario usuario_email_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.usuario
    ADD CONSTRAINT usuario_email_key UNIQUE (email);


--
-- TOC entry 4817 (class 2606 OID 18764)
-- Name: usuario usuario_login_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.usuario
    ADD CONSTRAINT usuario_login_key UNIQUE (login);


--
-- TOC entry 4819 (class 2606 OID 18758)
-- Name: usuario usuario_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.usuario
    ADD CONSTRAINT usuario_pkey PRIMARY KEY (id);


--
-- TOC entry 4811 (class 2606 OID 18548)
-- Name: vendas vendas_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.vendas
    ADD CONSTRAINT vendas_pkey PRIMARY KEY (venda);


--
-- TOC entry 4795 (class 1259 OID 18549)
-- Name: fki_fk_id_Vendedor; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX "fki_fk_id_Vendedor" ON public.caixa USING btree (operador);


--
-- TOC entry 4808 (class 1259 OID 18550)
-- Name: fki_fk_id_endereco; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX fki_fk_id_endereco ON public.vendas USING btree (id_endereco);


--
-- TOC entry 4809 (class 1259 OID 18551)
-- Name: fki_fk_id_vendedor; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX fki_fk_id_vendedor ON public.vendas USING btree (id_vendedor);


--
-- TOC entry 4822 (class 2606 OID 18552)
-- Name: itens fk_grupo_produto; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.itens
    ADD CONSTRAINT fk_grupo_produto FOREIGN KEY (grupo_produto) REFERENCES public.grupos(id);


--
-- TOC entry 4825 (class 2606 OID 18557)
-- Name: produtos fk_grupos; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.produtos
    ADD CONSTRAINT fk_grupos FOREIGN KEY (grupos) REFERENCES public.grupos(id);


--
-- TOC entry 4826 (class 2606 OID 18577)
-- Name: vendas fk_id_endereco; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.vendas
    ADD CONSTRAINT fk_id_endereco FOREIGN KEY (id_endereco) REFERENCES public.enderecosadd(id) NOT VALID;


--
-- TOC entry 4827 (class 2606 OID 18582)
-- Name: vendas fk_id_ppg; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.vendas
    ADD CONSTRAINT fk_id_ppg FOREIGN KEY (id_ppg) REFERENCES public.ppg(id);


--
-- TOC entry 4828 (class 2606 OID 18587)
-- Name: vendas fk_id_produto; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.vendas
    ADD CONSTRAINT fk_id_produto FOREIGN KEY (id_produto) REFERENCES public.produtos(id);


--
-- TOC entry 4823 (class 2606 OID 18592)
-- Name: itens fk_id_produto; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.itens
    ADD CONSTRAINT fk_id_produto FOREIGN KEY (id_produto) REFERENCES public.produtos(id);


--
-- TOC entry 4824 (class 2606 OID 18602)
-- Name: itens fk_venda; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.itens
    ADD CONSTRAINT fk_venda FOREIGN KEY (venda) REFERENCES public.vendas(venda);


--
-- TOC entry 4821 (class 2606 OID 18607)
-- Name: entregas fk_venda; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.entregas
    ADD CONSTRAINT fk_venda FOREIGN KEY (venda) REFERENCES public.vendas(venda);


--
-- TOC entry 4820 (class 2606 OID 18612)
-- Name: caixa fk_venda; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.caixa
    ADD CONSTRAINT fk_venda FOREIGN KEY (venda) REFERENCES public.vendas(venda);


-- Completed on 2025-02-26 01:37:45

--
-- PostgreSQL database dump complete
--

