PGDMP         &                {            casi1    14.2    14.2     �           0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                      false            �           0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                      false            �           0    0 
   SEARCHPATH 
   SEARCHPATH     8   SELECT pg_catalog.set_config('search_path', '', false);
                      false            �           1262    40997    casi1    DATABASE     c   CREATE DATABASE casi1 WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE = 'Spanish_Ecuador.1252';
    DROP DATABASE casi1;
                postgres    false            �            1259    41008    empleado    TABLE     �   CREATE TABLE public.empleado (
    cedula character varying NOT NULL,
    nombre character varying,
    apellido character varying,
    fechacon date,
    salario character varying,
    discapacidad character varying,
    horario character varying
);
    DROP TABLE public.empleado;
       public         heap    postgres    false            �          0    41008    empleado 
   TABLE DATA           f   COPY public.empleado (cedula, nombre, apellido, fechacon, salario, discapacidad, horario) FROM stdin;
    public          postgres    false    209   �       \           2606    41014    empleado empleado_pkey 
   CONSTRAINT     X   ALTER TABLE ONLY public.empleado
    ADD CONSTRAINT empleado_pkey PRIMARY KEY (cedula);
 @   ALTER TABLE ONLY public.empleado DROP CONSTRAINT empleado_pkey;
       public            postgres    false    209            �   �   x�E�A�0D���� �6q�ޅM
JJ���'T�Y�7,�{F����6(���}���RȜ�	����r�ӿ�P�Z�J��e���Ndt�-�����=&��q�18u�'�roˎGl�"ڍ��LD_�,e     