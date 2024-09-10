# Agenda de Reunião
Tema da disciplina de Persistência a Objetos, a Agenda de Reunião visa o gerenciamento de uma empresa no quesito reuniões.

## Objetivo
Praticar os conceitos de persistência transparente de objetos, através do
desenvolvimento de protótipo de um sistema de informação, usando Java e o bancos 
de dados, tanto para objetos (db4o), quanto para relacional (postgreSQL) ou documental (MongoDB).

## Funcionalidades
- Agendamento de reuniões
- Cadastro de pessoas na reunião
- Listagem de todas as pessoas da reunião específica
- Listagem de todas as reuniões da pessoa específica
- Consultar dados das reuniões

## Requisitos para funcionar

### Configuração do Projeto

1. **Banco de Dados (PostgreSQL ou MySQL)**:
	- O projeto está configurado para utilizar bancos de dados relacionais. Para que o projeto funcione corretamente, é necessário alterar
    as configurações do banco de dados e suas credenciais no arquivo `util.properties`:
     ```
     sgbd=nome_do_sgbd
	 banco=nome_do_banco
	 ip1=endereço_ip_1
	 ip2=endereco_ip_2 #opicional
	 ip3=endereco_ip_3 #opicional
     ```

   - **Passos para criar o banco de dados no PostgreSQL**:
     1. Abra o PostgreSQL e conecte-se com seu usuário.
     2. Crie o banco de dados usando o nome especificado no arquivo `util.properties`. Exemplo:
        ```sql
        CREATE DATABASE nome_do_banco;
        ```
     3. Execute os scripts de inicialização do banco para criar as tabelas e entidades necessárias.

2. **Configuração do db4o**:
   - db4o é um banco de dados orientado a objetos que não requer a criação explícita de um banco, pois ele cria automaticamente o arquivo para persistência de objetos. No entanto, é necessário garantir que o arquivo de banco esteja corretamente configurado no ambiente.
   - **Como configurar o db4o**:
     - Verifique se o arquivo de configuração `util.properties` contém o caminho correto para o arquivo de banco de dados db4o:
       ```properties
       db4o.filepath=path/para/db4o/agenda.db4o
       ```
     - Para inicializar e rodar a aplicação com db4o, certifique-se de incluir as dependências necessárias no `pom.xml` (ou no classpath, se não estiver usando Maven). db4o requer bibliotecas específicas, que podem ser adicionadas ao projeto.

3. **MongoDB**:
   - Para usar MongoDB, também será necessário configurar o `util.properties`:
     ```
     sgbd=nome_do_sgbd
	 banco=nome_do_banco
	 ip1=endereço_ip_1
	 ip2=endereco_ip_2 #opicional
	 ip3=endereco_ip_3 #opicional
     ```
   - Instale e configure o MongoDB localmente ou em um servidor e crie o banco de dados de acordo com o nome especificado no arquivo de propriedades.

### Dependências

- As dependências do projeto estão configuradas no arquivo `pom.xml`. Certifique-se de que ele está atualizado com as dependências necessárias para PostgreSQL, db4o, MongoDB e JPA. Execute o seguinte comando Maven para baixar as dependências:
  ```bash
  mvn clean install
  ```

## Executando o Projeto

### Banco de dados PostgreSQL:

- Certifique-se de que o PostgreSQL esteja rodando e que o banco foi criado com o nome especificado no arquivo util.properties.
- Configure a conexão no arquivo util.properties e execute a aplicação.

### Banco de dados db4o:

- Verifique se o arquivo agenda.db4o foi criado corretamente no caminho especificado no arquivo de propriedades.
- Execute a aplicação e o db4o tratará a persistência dos objetos automaticamente.

### Banco de dados MongoDB:

- Certifique-se de que o MongoDB está rodando e que o banco de dados foi configurado no arquivo util.properties.
- Ao executar a aplicação, a persistência será gerenciada pelo MongoDB.

## Contribuição ao Projeto

1. Clone o repositório:
```bash
git clone https://github.com/AllanSmithll/agenda-reunioes.git
```

2. Crie um branch para suas alterações:
```bash
git checkout -b minha-alteracao
```

3. Faça o commit de suas alterações:
```bash
git commit -m "Descrição das alterações"
```

4. Envie para o repositório remoto:
```bash
git push origin minha-alteracao
```
## Contribuidores
- Allan Amâncio - https://github.com/AllanSmithll;
- Márcio José - https://github.com/ImMarcio.

## Licença
Este projeto está licenciado sob a [Licença MIT](https://opensource.org/license/mit/).

## Preview

![Preview da tela principal, sem imagem](image.png)
