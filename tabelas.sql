-- Table: public.cliente

-- DROP TABLE IF EXISTS public.cliente;

CREATE TABLE IF NOT EXISTS public.cliente
(
    id_cliente integer NOT NULL DEFAULT 'nextval('cliente_id_cliente_seq'::regclass)',
    nome_completo character varying(50) COLLATE pg_catalog."default" NOT NULL,
    cpf character(11) COLLATE pg_catalog."default" NOT NULL,
    email character varying(50) COLLATE pg_catalog."default" NOT NULL,
    telefone character(11) COLLATE pg_catalog."default",
    usuario character varying(30) COLLATE pg_catalog."default" NOT NULL,
    senha character varying(20) COLLATE pg_catalog."default" NOT NULL,
    datanasc date NOT NULL,
    CONSTRAINT cliente_pkey PRIMARY KEY (id_cliente)
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.cliente
    OWNER to fsbnypxo;


-- Table: public.carrinho

-- DROP TABLE IF EXISTS public.carrinho;

CREATE TABLE IF NOT EXISTS public.carrinho
(
    cliente integer NOT NULL,
    produto integer NOT NULL,
    quantidade integer NOT NULL,
    CONSTRAINT carrinho_pkey PRIMARY KEY (cliente),
    CONSTRAINT carrinho_cliente_fkey FOREIGN KEY (cliente)
        REFERENCES public.cliente (id_cliente) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT carrinho_produto_fkey FOREIGN KEY (produto)
        REFERENCES public.produto (id_produto) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.carrinho
    OWNER to postgres;


-- Table: public.produto

-- DROP TABLE IF EXISTS public.produto;

CREATE TABLE IF NOT EXISTS public.produto
(
    id_produto integer NOT NULL DEFAULT nextval('produto_id_produto_seq'::regclass),
    nome character varying(50) COLLATE pg_catalog."default" NOT NULL,
    preco double precision NOT NULL,
    categoria character varying(50) COLLATE pg_catalog."default" NOT NULL,
    estoque integer NOT NULL,
    descricao character varying(200) COLLATE pg_catalog."default" NOT NULL,
    fabricante character varying(50) COLLATE pg_catalog."default" NOT NULL,
    desconto integer DEFAULT 0,
    CONSTRAINT produto_pkey PRIMARY KEY (id_produto)
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.produto
    OWNER to postgres;


-- Table: public.pedido

-- DROP TABLE IF EXISTS public.pedido;

CREATE TABLE IF NOT EXISTS public.pedido
(
    id_pedido integer NOT NULL DEFAULT nextval('pedido_id_pedido_seq'::regclass),
    produtos integer NOT NULL,
    cliente integer NOT NULL,
    status character varying(15) COLLATE pg_catalog."default" NOT NULL,
    data_criacao date NOT NULL,
    valor_total double precision NOT NULL,
    forma_pagamento character varying(15) COLLATE pg_catalog."default" NOT NULL,
    data_envio date NOT NULL,
    tipo_envio integer NOT NULL,
    valor_frete double precision NOT NULL,
    endereco integer NOT NULL,
    CONSTRAINT pedido_pkey PRIMARY KEY (id_pedido),
    CONSTRAINT pedido_cliente_fkey FOREIGN KEY (cliente)
        REFERENCES public.cliente (id_cliente) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT pedido_endereco_fkey FOREIGN KEY (endereco)
        REFERENCES public.endereco (id_endereco) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT pedido_produtos_fkey FOREIGN KEY (produtos)
        REFERENCES public.carrinho (cliente) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT pedido_tipo_envio_fkey FOREIGN KEY (tipo_envio)
        REFERENCES public.envio (id_envio) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.pedido
    OWNER to postgres;


-- Table: public.envio

-- DROP TABLE IF EXISTS public.envio;

CREATE TABLE IF NOT EXISTS public.envio
(
    id_envio integer NOT NULL DEFAULT nextval('envio_id_envio_seq'::regclass),
    tipo character varying(20) COLLATE pg_catalog."default" NOT NULL,
    custo double precision NOT NULL,
    CONSTRAINT envio_pkey PRIMARY KEY (id_envio)
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.envio
    OWNER to postgres;


-- Table: public.endereco

-- DROP TABLE IF EXISTS public.endereco;

CREATE TABLE IF NOT EXISTS public.endereco
(
    id_endereco integer NOT NULL DEFAULT nextval('endereco_id_endereco_seq'::regclass),
    cliente integer NOT NULL,
    estado character varying(40) COLLATE pg_catalog."default" NOT NULL,
    cidade character varying(40) COLLATE pg_catalog."default" NOT NULL,
    bairro character varying(40) COLLATE pg_catalog."default" NOT NULL,
    rua character varying(40) COLLATE pg_catalog."default" NOT NULL,
    numero integer NOT NULL,
    complemento character varying(15) COLLATE pg_catalog."default" NOT NULL,
    cep character(8) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT endereco_pkey PRIMARY KEY (id_endereco),
    CONSTRAINT endereco_cliente_fkey FOREIGN KEY (cliente)
        REFERENCES public.cliente (id_cliente) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.endereco
    OWNER to postgres;