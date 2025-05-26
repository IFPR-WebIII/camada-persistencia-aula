# Camada de Persistência - Aula

Este projeto demonstra a implementação de uma camada de persistência em aplicações, abordando conceitos como repositórios, entidades e integração com bancos de dados.

O roteiro de aula está disponível no AVA.

## Funcionalidades

- CRUD de entidades
- Integração com banco de dados relacional
- Separação de responsabilidades

## Tecnologias Utilizadas

- [Java/Spring Boot]
- [Banco de dados/MySQL]

## Como Executar

0. Configure sua base de dados MySql

- Crie uma base de dados chamada `ifpr-store`
- Execute o script disponível [neste link](https://gist.github.com/jeffersonchaves/2342cea9050e85a1197ed2ad1661c0c4#file-ifpr_store_sellers-sql) 


1. Clone o repositório:
    ```bash
    git clone https://github.com/seu-usuario/camada-persistencia-aula.git
    ```
2. Importe o projeto para seu Editor

3. Configure o acesso ao banco de dados na classe `ConnectionFactory`


## Estrutura do Projeto

```
camada-persistencia-aula/
├── src/
│   ├── main/
│   │   ├── java/
│   │   └── resources/
├── README.md
└── ...
```

## Contribuição

Contribuições são bem-vindas! Abra uma issue ou envie um pull request.

## Licença

[MIT](LICENSE)