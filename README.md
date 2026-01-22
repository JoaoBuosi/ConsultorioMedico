🏥 Sistema de Gerenciamento de Consultório Médico

Este projeto é um sistema completo para gerenciar um consultório médico, contemplando pacientes, médicos, consultas, prontuários e relatórios.
O objetivo é oferecer funcionalidades de CRUD, agendamento de consultas, controle de histórico médico e geração de estatísticas de atendimentos.

O sistema é desenvolvido em Java com JavaFX para interface gráfica e utiliza banco de dados relacional para persistência.
Este projeto é ideal para portfólio, aplicando POO, Design Patterns e boas práticas de programação.

💡 Funcionalidades
Cadastro de Usuários

Tipos: Médicos, Pacientes e Administradores

Autenticação com senha (hash básica)

Gerenciamento de Pacientes

CRUD completo: criar, atualizar, deletar e consultar pacientes

Histórico médico completo

Pesquisa avançada por idade, condição ou consultas

Gerenciamento de Médicos

Cadastro de especialidades

Agenda de disponibilidade

Estatísticas de consultas realizadas

Agendamento de Consultas

Evita conflitos de horários

Histórico completo de consultas

Notificações futuras (planejado)

Prontuário Eletrônico

Anotações de consultas

Prescrição de medicamentos

Upload de arquivos (PDF e imagens)

Relatórios e Estatísticas

Consultas por médico, paciente ou período

Médicos mais requisitados

Pacientes mais ativos

Interface Gráfica

Desenvolvido com JavaFX

Dashboard administrativo

Visualização de agenda em calendário

Banco de Dados

MySQL ou PostgreSQL

Mapeamento com JPA / Hibernate

Principais relacionamentos:

Paciente ↔ Consulta ↔ Médico

Médico ↔ Especialidade

Usuário ↔ Permissões

Extras Avançados

Multithreading para notificações ou agendamento automático

Relatórios em PDF usando iText

Testes unitários com JUnit

Design Patterns aplicados:

Singleton (DB)

DAO

Factory

Observer (notificações)

🛠️ Tecnologias Usadas

Linguagem: Java

Interface Gráfica: JavaFX

Banco de Dados: MySQL ou PostgreSQL

Persistência: JPA / Hibernate

Testes: JUnit

PDF/Relatórios: iText

Design Patterns: Singleton, DAO, Factory, Observer
