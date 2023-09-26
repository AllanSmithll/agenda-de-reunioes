# Requisitos de Software - Sistema de Agendamento de Reuniões

<p>Versão 2.0</p>

## Requisitos Funcionais

### Implementação do Modelo de Negócio

1. Implementar as classes do modelo de negócio para o tema fornecido.

### Classes de Aplicação (Console)

2. Implementar as seguintes 5 classes de aplicação (console):

   - **Cadastrar.java**: Permite o cadastro de vários objetos do modelo de dados.
   - **Listar.java**: Permite listar todos os objetos cadastrados.
   - **Alterar.java**: Permite alterar o relacionamento entre dois objetos.
   - **Deletar.java**: Permite apagar um objeto que possua relacionamentos.
   - **Consultar.java**: Implementa consultas utilizando a API Soda.
   
### Implementar consultas interessantes
- quais as reunioes na data X
- quais as reunioes com a pessoa de nome X
- quais as pessoas que tem mais de N reunioes

### Implementação das Classes DAO

3. Implementar as classes DAO específicas para as classes de negócio e incluir as 3 consultas do projeto1.

### Fachada

4. Implementar na classe Fachada os seguintes requisitos:

   - Criar, alterar, excluir, localizar e listar objetos de cada classe de negócio.
   - Deve-se ser criadas no sistema reuniões no dia do cadastro ou em dias futuros.

### Aplicações Gráficas

5. Implementar as aplicações gráficas para interação com o usuário.

### Apresentação do Projeto

6. Realizar a apresentação do projeto, destacando suas funcionalidades e características.

## Requisitos Não Funcionais

### Restrição de Reuniões

1. Não pode existir, na base de dados, reunião com menos de duas pessoas.
