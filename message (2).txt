--
-- PostgreSQL database dump
--

-- Dumped from database version 14.0
-- Dumped by pg_dump version 14.4

-- Started on 2023-04-09 17:54:24 -03

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

-- SET default_table_access_method = heap;

--
-- TOC entry 212 (class 1259 OID 60756)
-- Name: docente; Type: TABLE; Schema: public; Owner: ehqwefho
--

CREATE TABLE public.docente (
    id_docente integer NOT NULL,
    id_lattes text,
    nome text,
    data_atualizacao date
);


ALTER TABLE public.docente OWNER TO ehqwefho;

--
-- TOC entry 211 (class 1259 OID 60755)
-- Name: docente_id_docente_seq; Type: SEQUENCE; Schema: public; Owner: ehqwefho
--

CREATE SEQUENCE public.docente_id_docente_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.docente_id_docente_seq OWNER TO ehqwefho;

--
-- TOC entry 3651 (class 0 OID 0)
-- Dependencies: 211
-- Name: docente_id_docente_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: ehqwefho
--

ALTER SEQUENCE public.docente_id_docente_seq OWNED BY public.docente.id_docente;


--
-- TOC entry 222 (class 1259 OID 60855)
-- Name: docente_producao; Type: TABLE; Schema: public; Owner: ehqwefho
--

CREATE TABLE public.docente_producao (
    id_docente integer NOT NULL,
    id_producao integer NOT NULL
);


ALTER TABLE public.docente_producao OWNER TO ehqwefho;

--
-- TOC entry 223 (class 1259 OID 60870)
-- Name: docente_tecnica; Type: TABLE; Schema: public; Owner: ehqwefho
--

CREATE TABLE public.docente_tecnica (
    id_docente integer NOT NULL,
    id_tecnica integer NOT NULL
);


ALTER TABLE public.docente_tecnica OWNER TO ehqwefho;

--
-- TOC entry 219 (class 1259 OID 60812)
-- Name: orientacao; Type: TABLE; Schema: public; Owner: ehqwefho
--

CREATE TABLE public.orientacao (
    id_orientacao integer NOT NULL,
    id_docente integer,
    tipo text,
    discente text,
    titulo text,
    ano integer,
    modalidade text,
    instituicao text,
    curso text,
    status text
);


ALTER TABLE public.orientacao OWNER TO ehqwefho;

--
-- TOC entry 218 (class 1259 OID 60811)
-- Name: orientacao_id_orientacao_seq; Type: SEQUENCE; Schema: public; Owner: ehqwefho
--

CREATE SEQUENCE public.orientacao_id_orientacao_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.orientacao_id_orientacao_seq OWNER TO ehqwefho;

--
-- TOC entry 3652 (class 0 OID 0)
-- Dependencies: 218
-- Name: orientacao_id_orientacao_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: ehqwefho
--

ALTER SEQUENCE public.orientacao_id_orientacao_seq OWNED BY public.orientacao.id_orientacao;


--
-- TOC entry 215 (class 1259 OID 60784)
-- Name: producao; Type: TABLE; Schema: public; Owner: ehqwefho
--

CREATE TABLE public.producao (
    id_producao integer NOT NULL,
    tipo text NOT NULL,
    issn_ou_sigla text,
    nome_local text,
    titulo text,
    ano integer,
    qualis text,
    percentile_ou_h5 numeric,
    qtd_grad integer,
    qtd_mestrado integer,
    qtd_doutorado integer
);


ALTER TABLE public.producao OWNER TO ehqwefho;

--
-- TOC entry 3653 (class 0 OID 0)
-- Dependencies: 215
-- Name: COLUMN producao.tipo; Type: COMMENT; Schema: public; Owner: ehqwefho
--

COMMENT ON COLUMN public.producao.tipo IS 'P para periódico e C para congresso';


--
-- TOC entry 3654 (class 0 OID 0)
-- Dependencies: 215
-- Name: COLUMN producao.issn_ou_sigla; Type: COMMENT; Schema: public; Owner: ehqwefho
--

COMMENT ON COLUMN public.producao.issn_ou_sigla IS 'ISSN quando tipo=''P'', SIGLA quando tipo=''C''';


--
-- TOC entry 3655 (class 0 OID 0)
-- Dependencies: 215
-- Name: COLUMN producao.nome_local; Type: COMMENT; Schema: public; Owner: ehqwefho
--

COMMENT ON COLUMN public.producao.nome_local IS 'Local de publicação';


--
-- TOC entry 3656 (class 0 OID 0)
-- Dependencies: 215
-- Name: COLUMN producao.titulo; Type: COMMENT; Schema: public; Owner: ehqwefho
--

COMMENT ON COLUMN public.producao.titulo IS 'Titulo do artigo';


--
-- TOC entry 3657 (class 0 OID 0)
-- Dependencies: 215
-- Name: COLUMN producao.qualis; Type: COMMENT; Schema: public; Owner: ehqwefho
--

COMMENT ON COLUMN public.producao.qualis IS 'Classificação da CAPES';


--
-- TOC entry 3658 (class 0 OID 0)
-- Dependencies: 215
-- Name: COLUMN producao.percentile_ou_h5; Type: COMMENT; Schema: public; Owner: ehqwefho
--

COMMENT ON COLUMN public.producao.percentile_ou_h5 IS 'percentile quanto tipo=''P'', h5 quando tipo=''C''';


--
-- TOC entry 214 (class 1259 OID 60783)
-- Name: producao_id_producao_seq; Type: SEQUENCE; Schema: public; Owner: ehqwefho
--

CREATE SEQUENCE public.producao_id_producao_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.producao_id_producao_seq OWNER TO ehqwefho;

--
-- TOC entry 3659 (class 0 OID 0)
-- Dependencies: 214
-- Name: producao_id_producao_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: ehqwefho
--

ALTER SEQUENCE public.producao_id_producao_seq OWNED BY public.producao.id_producao;


--
-- TOC entry 220 (class 1259 OID 60825)
-- Name: producao_orientacao; Type: TABLE; Schema: public; Owner: ehqwefho
--

CREATE TABLE public.producao_orientacao (
    id_producao integer NOT NULL,
    id_orientacao integer NOT NULL
);


ALTER TABLE public.producao_orientacao OWNER TO ehqwefho;

--
-- TOC entry 210 (class 1259 OID 60747)
-- Name: programa; Type: TABLE; Schema: public; Owner: ehqwefho
--

CREATE TABLE public.programa (
    id_programa integer NOT NULL,
    nome text
);


ALTER TABLE public.programa OWNER TO ehqwefho;

--
-- TOC entry 213 (class 1259 OID 60765)
-- Name: programa_docente; Type: TABLE; Schema: public; Owner: ehqwefho
--

CREATE TABLE public.programa_docente (
    id_programa integer NOT NULL,
    id_docente integer NOT NULL
);


ALTER TABLE public.programa_docente OWNER TO ehqwefho;

--
-- TOC entry 209 (class 1259 OID 60746)
-- Name: programa_id_programa_seq; Type: SEQUENCE; Schema: public; Owner: ehqwefho
--

CREATE SEQUENCE public.programa_id_programa_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.programa_id_programa_seq OWNER TO ehqwefho;

--
-- TOC entry 3660 (class 0 OID 0)
-- Dependencies: 209
-- Name: programa_id_programa_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: ehqwefho
--

ALTER SEQUENCE public.programa_id_programa_seq OWNED BY public.programa.id_programa;


--
-- TOC entry 217 (class 1259 OID 60798)
-- Name: tecnica; Type: TABLE; Schema: public; Owner: ehqwefho
--

CREATE TABLE public.tecnica (
    id_tecnica integer NOT NULL,
    tipo text,
    titulo text,
    ano integer,
    financiadora text,
    outras_informacaoes text,
    qtd_grad integer,
    qtd_mestrado integer,
    qtd_doutorado integer
);


ALTER TABLE public.tecnica OWNER TO ehqwefho;

--
-- TOC entry 216 (class 1259 OID 60797)
-- Name: tecnica_id_tecnica_seq; Type: SEQUENCE; Schema: public; Owner: ehqwefho
--

CREATE SEQUENCE public.tecnica_id_tecnica_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.tecnica_id_tecnica_seq OWNER TO ehqwefho;

--
-- TOC entry 3661 (class 0 OID 0)
-- Dependencies: 216
-- Name: tecnica_id_tecnica_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: ehqwefho
--

ALTER SEQUENCE public.tecnica_id_tecnica_seq OWNED BY public.tecnica.id_tecnica;


--
-- TOC entry 221 (class 1259 OID 60840)
-- Name: tecnica_orientacao; Type: TABLE; Schema: public; Owner: ehqwefho
--

CREATE TABLE public.tecnica_orientacao (
    id_tecnica integer NOT NULL,
    id_orientacao integer NOT NULL
);


ALTER TABLE public.tecnica_orientacao OWNER TO ehqwefho;

--
-- TOC entry 3472 (class 2604 OID 60759)
-- Name: docente id_docente; Type: DEFAULT; Schema: public; Owner: ehqwefho
--

ALTER TABLE ONLY public.docente ALTER COLUMN id_docente SET DEFAULT nextval('public.docente_id_docente_seq'::regclass);


--
-- TOC entry 3475 (class 2604 OID 60815)
-- Name: orientacao id_orientacao; Type: DEFAULT; Schema: public; Owner: ehqwefho
--

ALTER TABLE ONLY public.orientacao ALTER COLUMN id_orientacao SET DEFAULT nextval('public.orientacao_id_orientacao_seq'::regclass);


--
-- TOC entry 3473 (class 2604 OID 60787)
-- Name: producao id_producao; Type: DEFAULT; Schema: public; Owner: ehqwefho
--

ALTER TABLE ONLY public.producao ALTER COLUMN id_producao SET DEFAULT nextval('public.producao_id_producao_seq'::regclass);


--
-- TOC entry 3471 (class 2604 OID 60750)
-- Name: programa id_programa; Type: DEFAULT; Schema: public; Owner: ehqwefho
--

ALTER TABLE ONLY public.programa ALTER COLUMN id_programa SET DEFAULT nextval('public.programa_id_programa_seq'::regclass);


--
-- TOC entry 3474 (class 2604 OID 60801)
-- Name: tecnica id_tecnica; Type: DEFAULT; Schema: public; Owner: ehqwefho
--

ALTER TABLE ONLY public.tecnica ALTER COLUMN id_tecnica SET DEFAULT nextval('public.tecnica_id_tecnica_seq'::regclass);


--
-- TOC entry 3479 (class 2606 OID 60763)
-- Name: docente docente_pk; Type: CONSTRAINT; Schema: public; Owner: ehqwefho
--

ALTER TABLE ONLY public.docente
    ADD CONSTRAINT docente_pk PRIMARY KEY (id_docente);


--
-- TOC entry 3493 (class 2606 OID 60859)
-- Name: docente_producao docente_producao_pk; Type: CONSTRAINT; Schema: public; Owner: ehqwefho
--

ALTER TABLE ONLY public.docente_producao
    ADD CONSTRAINT docente_producao_pk PRIMARY KEY (id_docente, id_producao);


--
-- TOC entry 3495 (class 2606 OID 60874)
-- Name: docente_tecnica docente_tecnica_pk; Type: CONSTRAINT; Schema: public; Owner: ehqwefho
--

ALTER TABLE ONLY public.docente_tecnica
    ADD CONSTRAINT docente_tecnica_pk PRIMARY KEY (id_docente, id_tecnica);


--
-- TOC entry 3487 (class 2606 OID 60819)
-- Name: orientacao orientacao_pk; Type: CONSTRAINT; Schema: public; Owner: ehqwefho
--

ALTER TABLE ONLY public.orientacao
    ADD CONSTRAINT orientacao_pk PRIMARY KEY (id_orientacao);


--
-- TOC entry 3489 (class 2606 OID 60829)
-- Name: producao_orientacao producao_orientacao_pk; Type: CONSTRAINT; Schema: public; Owner: ehqwefho
--

ALTER TABLE ONLY public.producao_orientacao
    ADD CONSTRAINT producao_orientacao_pk PRIMARY KEY (id_producao, id_orientacao);


--
-- TOC entry 3483 (class 2606 OID 60791)
-- Name: producao producao_pk; Type: CONSTRAINT; Schema: public; Owner: ehqwefho
--

ALTER TABLE ONLY public.producao
    ADD CONSTRAINT producao_pk PRIMARY KEY (id_producao);


--
-- TOC entry 3481 (class 2606 OID 60782)
-- Name: programa_docente programa_docente_pk; Type: CONSTRAINT; Schema: public; Owner: ehqwefho
--

ALTER TABLE ONLY public.programa_docente
    ADD CONSTRAINT programa_docente_pk PRIMARY KEY (id_programa, id_docente);


--
-- TOC entry 3477 (class 2606 OID 60754)
-- Name: programa programa_pk; Type: CONSTRAINT; Schema: public; Owner: ehqwefho
--

ALTER TABLE ONLY public.programa
    ADD CONSTRAINT programa_pk PRIMARY KEY (id_programa);


--
-- TOC entry 3491 (class 2606 OID 60844)
-- Name: tecnica_orientacao tecnica_orientacao_pk; Type: CONSTRAINT; Schema: public; Owner: ehqwefho
--

ALTER TABLE ONLY public.tecnica_orientacao
    ADD CONSTRAINT tecnica_orientacao_pk PRIMARY KEY (id_tecnica, id_orientacao);


--
-- TOC entry 3485 (class 2606 OID 60805)
-- Name: tecnica tecnica_pk; Type: CONSTRAINT; Schema: public; Owner: ehqwefho
--

ALTER TABLE ONLY public.tecnica
    ADD CONSTRAINT tecnica_pk PRIMARY KEY (id_tecnica);


--
-- TOC entry 3503 (class 2606 OID 60860)
-- Name: docente_producao docente_producao_fk; Type: FK CONSTRAINT; Schema: public; Owner: ehqwefho
--

ALTER TABLE ONLY public.docente_producao
    ADD CONSTRAINT docente_producao_fk FOREIGN KEY (id_docente) REFERENCES public.docente(id_docente);


--
-- TOC entry 3504 (class 2606 OID 60865)
-- Name: docente_producao docente_producao_fk_1; Type: FK CONSTRAINT; Schema: public; Owner: ehqwefho
--

ALTER TABLE ONLY public.docente_producao
    ADD CONSTRAINT docente_producao_fk_1 FOREIGN KEY (id_producao) REFERENCES public.producao(id_producao);


--
-- TOC entry 3505 (class 2606 OID 60875)
-- Name: docente_tecnica docente_tecnica_fk; Type: FK CONSTRAINT; Schema: public; Owner: ehqwefho
--

ALTER TABLE ONLY public.docente_tecnica
    ADD CONSTRAINT docente_tecnica_fk FOREIGN KEY (id_docente) REFERENCES public.docente(id_docente);


--
-- TOC entry 3506 (class 2606 OID 60880)
-- Name: docente_tecnica docente_tecnica_fk_1; Type: FK CONSTRAINT; Schema: public; Owner: ehqwefho
--

ALTER TABLE ONLY public.docente_tecnica
    ADD CONSTRAINT docente_tecnica_fk_1 FOREIGN KEY (id_tecnica) REFERENCES public.tecnica(id_tecnica);


--
-- TOC entry 3498 (class 2606 OID 60820)
-- Name: orientacao orientacao_fk; Type: FK CONSTRAINT; Schema: public; Owner: ehqwefho
--

ALTER TABLE ONLY public.orientacao
    ADD CONSTRAINT orientacao_fk FOREIGN KEY (id_docente) REFERENCES public.docente(id_docente);


--
-- TOC entry 3499 (class 2606 OID 60830)
-- Name: producao_orientacao producao_orientacao_fk; Type: FK CONSTRAINT; Schema: public; Owner: ehqwefho
--

ALTER TABLE ONLY public.producao_orientacao
    ADD CONSTRAINT producao_orientacao_fk FOREIGN KEY (id_producao) REFERENCES public.producao(id_producao);


--
-- TOC entry 3500 (class 2606 OID 60835)
-- Name: producao_orientacao producao_orientacao_fk_1; Type: FK CONSTRAINT; Schema: public; Owner: ehqwefho
--

ALTER TABLE ONLY public.producao_orientacao
    ADD CONSTRAINT producao_orientacao_fk_1 FOREIGN KEY (id_orientacao) REFERENCES public.orientacao(id_orientacao);


--
-- TOC entry 3496 (class 2606 OID 60771)
-- Name: programa_docente programa_docente_fk; Type: FK CONSTRAINT; Schema: public; Owner: ehqwefho
--

ALTER TABLE ONLY public.programa_docente
    ADD CONSTRAINT programa_docente_fk FOREIGN KEY (id_programa) REFERENCES public.programa(id_programa);


--
-- TOC entry 3497 (class 2606 OID 60776)
-- Name: programa_docente programa_docente_fk_1; Type: FK CONSTRAINT; Schema: public; Owner: ehqwefho
--

ALTER TABLE ONLY public.programa_docente
    ADD CONSTRAINT programa_docente_fk_1 FOREIGN KEY (id_docente) REFERENCES public.docente(id_docente);


--
-- TOC entry 3501 (class 2606 OID 60845)
-- Name: tecnica_orientacao tecnica_orientacao_fk; Type: FK CONSTRAINT; Schema: public; Owner: ehqwefho
--

ALTER TABLE ONLY public.tecnica_orientacao
    ADD CONSTRAINT tecnica_orientacao_fk FOREIGN KEY (id_tecnica) REFERENCES public.tecnica(id_tecnica);


--
-- TOC entry 3502 (class 2606 OID 60850)
-- Name: tecnica_orientacao tecnica_orientacao_fk_1; Type: FK CONSTRAINT; Schema: public; Owner: ehqwefho
--

ALTER TABLE ONLY public.tecnica_orientacao
    ADD CONSTRAINT tecnica_orientacao_fk_1 FOREIGN KEY (id_orientacao) REFERENCES public.orientacao(id_orientacao);


-- Completed on 2023-04-09 17:54:25 -03

--
-- PostgreSQL database dump complete
--

