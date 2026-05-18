# Sistema Bancário (Java)

Projeto de um sistema bancário desenvolvido em Java com foco em POO e persistência de dados. simulando operações básicas de uma instituição financeira. 
O projeto permite a criação e gerenciamento de contas, além da realização de transações como depósitos, saques e consultas de saldo.

# Evolução do projeto
O projeto nasceu como um sistema simples baseado em arquivos textuais (.txt). Posteriormente, evoluiu para uma arquitetura baseada em JDBC + MySQL e, 
atualmente, utiliza a especificação JPA com mapeamento objeto-relacional e gerenciamento automático de transações.
## O que tem no projeto

### Criação de contas

- Conta Corrente
- Conta Empresarial
- Conta Poupança

### Operações

- Depósito
- Saque
- Transferência entre contas

### Histórico de transações

- Mapeamento Bidirecional: Cada conta possui um histórico de transações vinculadas diretamente no banco de dados, 
ordenadas de forma cronológica decrescente.

### Tratamento de erros

- Saldo insuficiente
- Limite excedido
- Exceções de banco de dados

### Persistência de Dados (Atualizado)

- Persistência com MySQL utilizando JPA / Hibernate
- Controle transacional automatizado via EntityManager:
- em.getTransaction().begin() (Substitui o antigo setAutoCommit(false))
- em.getTransaction().commit() (Grava as alterações de forma segura no banco)
- em.getTransaction().rollback() (Desfaz as alterações em caso de erro/exceção)

## Estrutura

- `main.entities` → classes das contas e transações
- `main.dao` → interfaces DAO
- `main.dao.impl` → implementação JPA/Hibernate/JPQL
- `services` → regras de negócio
- `main.dao.db` → conexão com banco de dados
- `main.exceptions` → tratamento de erros
- `main` → execução no console

## Regras de negócio

- Conta Corrente possui taxa fixa por operação
- Conta Empresarial possui limite de saque
- Conta Poupança possui comportamento diferenciado
- Transferências utilizam transações SQL para garantir consistência dos dados

## Conceitos aplicados

- Herança
- Polimorfismo
- Encapsulamento
- Abstração
- Interfaces
- DAO Pattern
- Service Layer
- Injeção de Dependência
- JDBC Transactions
- Separação de responsabilidades
- JPA / Hibernate

## Tecnologias utilizadas

- Java 17+
- JDBC
- MySQL
- Git/GitHub
- JPA / Hibernate
- Maven

## Exemplo de saída

```text
Conta [Corrente]
Titular: Eduardo
Número: 1273
Saldo: 300.00

Transações:
DEPOSITO | R$ 200.00 | Saldo: 200.00
DEPOSITO | R$ 200.00 | Saldo: 400.00
SAQUE | R$ 50.00 | Saldo: 350.00
TRANSFERENCIA | R$ 100.00 | Saldo: 250.00
```

## Como rodar

1. Clonar o repositório
2. Configurar o banco de dados MySQL
3. Ajustar as credenciais de conexão na classe `DB.java`
4. Executar a classe `main.java.Main.java`

## Melhorias futuras

- API REST com Spring Boot
- Interface gráfica
- Testes automatizados
- Uso de BigDecimal para operações financeiras
- Sistema de autenticação de usuários