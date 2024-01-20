## Projeto Devsibre

## Sobre o projeto

- Plataforma para gerenciamento de movimentação de Formularios, Agenda, Inventarios e Cobranças.

## Ferramentas:

- JDK 8
- IDE Eclipse (STS4)
- Maven
- Docker/Docker-Compose

## Frameworks e Tecnologias:
- Spring Boot / Data
- Frameworks Spring/jpa
- Hibernate
- Padrão MVC
- Interface web com Bootstrap
- Testes de Integração com JUnit 5
- Thymeleaf como template engine

## Banco de Dados:
- Mysql
- Nome do Banco: devsibredb

## EXECUTE
- mvn clean install
Na rais do diretorio execute o comando a abaixo gerar imagem e o banco via container
- docker-compose up --build --force-recreate

## Acesso ao Frontend do Projeto
- http://localhost:8080/
- http://localhost:8080/agendas_User
- http://localhost:8080/Entrar
<br><br>
## TELA INICIAL
![Tela Inical](https://github.com/Ernilson/devsibre/assets/30840118/2468c14b-1be7-4900-8164-f1289680739f)

## TELA DE LOGIN
![Tela Login](https://github.com/Ernilson/devsibre/assets/30840118/dbe6b679-7184-4ac5-8182-bde2d5f421cf)

## TELA DE AGENDA
![Tela Agenda](https://github.com/Ernilson/devsibre/assets/30840118/3c14cd16-a1d7-4dda-9c80-99a9c0bc25c3)

## TELA CADASTRO
![Tela de Cadastro](https://github.com/Ernilson/devsibre/assets/30840118/85aced31-da8b-4d36-9e4b-f8fa73c94829)

## TELA DE COBRANÇA
![Tela de Cobrança](https://github.com/Ernilson/devsibre/assets/30840118/97b8d018-9853-4e68-bfaf-89e5a694afbc)

## TELA PATRIMONIO
![Tela de Patrimonio](https://github.com/Ernilson/devsibre/assets/30840118/126dbff2-243d-4f2a-90e6-67701c0ebd3a)

## TELA RELATORIO DE CADASTRO
![Tela de Relatorio](https://github.com/Ernilson/devsibre/assets/30840118/def69275-eaf5-4682-b4fe-99379fa65891)

## TELA RELATÓRIO DE DÉBITOS
![Relatorio de Membros](https://github.com/Ernilson/devsibre/assets/30840118/23e86abb-c18d-4187-a7b2-62a02f0ae6e6)

## TELA DE MODELAGEM
![image](https://github.com/Ernilson/devsibre2/assets/30840118/2217b56c-083c-4fd7-8a47-fc0e3f67bb02)

## Comandos Docker:
docker-compose up --build --force-recreate

## Comando para imagem Docker chamada "devsibre" 
docker build --build-arg PROFILE=dev --build-arg ADDITIONAL_OPTS="-agentlib:jdwp=transport=dt_socket,
server=y,suspend=n,address=*:8000" -t devsibre .
