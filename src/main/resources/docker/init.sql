--
-- PostgreSQL database dump
--

-- Dumped from database version 14.2
-- Dumped by pg_dump version 14.2

-- Started on 2022-05-12 23:51:29

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

SET default_table_access_method = heap;

--
-- TOC entry 212 (class 1259 OID 24625)
-- Name: authorities; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.authorities (
                                    user_id uuid NOT NULL,
                                    role character varying(45) NOT NULL
);


ALTER TABLE public.authorities OWNER TO postgres;

--
-- TOC entry 210 (class 1259 OID 24608)
-- Name: employee; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.employee (
                                 id uuid NOT NULL,
                                 date_birth date,
                                 address character varying(50),
                                 phone_number character(10),
                                 vaccinated boolean,
                                 vaccinate_type character varying(20),
                                 vaccinate_date date,
                                 dose_amount numeric
);


ALTER TABLE public.employee OWNER TO postgres;

--
-- TOC entry 209 (class 1259 OID 24601)
-- Name: person; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.person (
                               id uuid NOT NULL,
                               identification_number character varying(50) NOT NULL,
                               names character varying(50) NOT NULL,
                               surnames character varying(50) NOT NULL,
                               email character varying(40) NOT NULL,
                               employee_id uuid
);


ALTER TABLE public.person OWNER TO postgres;

--
-- TOC entry 211 (class 1259 OID 24615)
-- Name: user; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public."user" (
                               id uuid NOT NULL,
                               username character varying(25) NOT NULL,
                               password character varying(100) NOT NULL,
                               person_id uuid
);


ALTER TABLE public."user" OWNER TO postgres;

--
-- TOC entry 3330 (class 0 OID 24625)
-- Dependencies: 212
-- Data for Name: authorities; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.authorities (user_id, role) FROM stdin;
23fb3057-2962-44e8-b7e4-3d1532b3f9d6	ROLE_ADMIN
\.


--
-- TOC entry 3328 (class 0 OID 24608)
-- Dependencies: 210
-- Data for Name: employee; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.employee (id, date_birth, address, phone_number, vaccinated, vaccinate_type, vaccinate_date, dose_amount) FROM stdin;
03d071e6-4aae-44a6-95b1-a80d5d870bcd	2000-04-26	Juan Montalvo y Eugenio Espejo	000000000 	t	JHONSON_JHONSON	2020-05-19	2
\.


--
-- TOC entry 3327 (class 0 OID 24601)
-- Dependencies: 209
-- Data for Name: person; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.person (id, identification_number, names, surnames, email, employee_id) FROM stdin;
a8b8de8f-17df-4d14-9083-d419324bbb95	0941106445	Christian Manuel	Chunga Bayas	christian98.cb@outlook.es	\N
\.


--
-- TOC entry 3329 (class 0 OID 24615)
-- Dependencies: 211
-- Data for Name: user; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public."user" (id, username, password, person_id) FROM stdin;
23fb3057-2962-44e8-b7e4-3d1532b3f9d6	root	$2a$10$ps2XZGJ4DGuWgC93tE49deUAPtE2ciGJjxKgI12W0Jh8zX11ZhDvy	a8b8de8f-17df-4d14-9083-d419324bbb95
\.


--
-- TOC entry 3176 (class 2606 OID 24607)
-- Name: person UK_PERSON_IDENTIFICATION_NUMBER; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.person
    ADD CONSTRAINT "UK_PERSON_IDENTIFICATION_NUMBER" UNIQUE (identification_number);


--
-- TOC entry 3182 (class 2606 OID 24621)
-- Name: user UK_USER_USERNAME; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."user"
    ADD CONSTRAINT "UK_USER_USERNAME" UNIQUE (username);


--
-- TOC entry 3180 (class 2606 OID 24614)
-- Name: employee employee_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.employee
    ADD CONSTRAINT employee_pkey PRIMARY KEY (id);


--
-- TOC entry 3178 (class 2606 OID 24605)
-- Name: person person_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.person
    ADD CONSTRAINT person_pkey PRIMARY KEY (id);


--
-- TOC entry 3184 (class 2606 OID 24619)
-- Name: user user_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."user"
    ADD CONSTRAINT user_pkey PRIMARY KEY (id);


--
-- TOC entry 3185 (class 2606 OID 24638)
-- Name: person FK_PERSON_EMPLOYEE; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.person
    ADD CONSTRAINT "FK_PERSON_EMPLOYEE" FOREIGN KEY (employee_id) REFERENCES public.employee(id);


--
-- TOC entry 3186 (class 2606 OID 24633)
-- Name: user FK_USER_PERSON; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."user"
    ADD CONSTRAINT "FK_USER_PERSON" FOREIGN KEY (person_id) REFERENCES public.person(id);


--
-- TOC entry 3187 (class 2606 OID 24628)
-- Name: authorities FK_USER_ROLES; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.authorities
    ADD CONSTRAINT "FK_USER_ROLES" FOREIGN KEY (user_id) REFERENCES public."user"(id);


-- Completed on 2022-05-12 23:51:29

--
-- PostgreSQL database dump complete
--

