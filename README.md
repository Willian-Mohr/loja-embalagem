# Loja de Embalagens - Microsserviço de Empacotamento

Este projeto é responsável por empacotar pedidos automaticamente com base nas dimensões dos produtos e nas caixas
disponíveis.

## Visão Geral

O sistema é baseado em microsserviços e contém os seguintes módulos:

* **core**: Contém a lógica de negócio principal da aplicação.
* **application**: Define os casos de uso e orquestra as chamadas entre os componentes do domínio.
* **usecase**: Implementa os casos de uso definidos no módulo application.
* **infrastructure**: Implementa as interfaces com o mundo externo (REST controllers, configurações de segurança,
  mapeamentos, etc.). Também é onde ocorre o empacotamento em imagem docker.

## Princípios Aplicados

* **Clean Architecture**: Separação clara entre as camadas de domínio, aplicação e infraestrutura.
* **DDD (Domain Driven Design)**: As entidades e regras de negócio são modeladas de forma rica e expressiva.
* **Spring Boot 3.5**: Framework principal para construção da API REST.
* **OAuth2 com Keycloak**: Para autenticação e autorização.
* **Swagger / OpenAPI**: Para documentação interativa da API.

## Pré-requisitos

* Java 17+
* Maven 3.9+
* Docker
* Docker Compose
* Keycloak 24.0.3 (utilizado via container Docker)

### Configuração de Host

É necessário mapear o domínio `keycloak` para `localhost` para funcionamento do fluxo OAuth2 no Swagger:

#### Linux / MacOS

Adicione a seguinte linha ao arquivo `/etc/hosts`:

```sh
127.0.0.1 keycloak
```

#### Windows

Adicione a mesma linha ao arquivo `C:\Windows\System32\drivers\etc\hosts` com privilégios de administrador:

```
127.0.0.1 keycloak
```

## Build e Execução

Para construir e empacotar o projeto, e gerar a imagem Docker da aplicação, execute:

```sh
mvn clean package -pl infrastructure -am -Pdocker
```

Para subir o ambiente completo com Keycloak e a aplicação:

```sh
docker-compose up --build
```

A API ficará disponível em: [http://localhost:8081](http://localhost:8081)

Swagger UI: [http://localhost:8081/swagger-ui/index.html](http://localhost:8081/swagger-ui/index.html)

Keycloak: [http://localhost:8080](http://localhost:8080)

## Autenticação com Keycloak

* A importação do realm do Keycloak ocorre automaticamente via volume `./keycloak`.
* O client `loja-api` e um usuário de testes devem estar previamente configurados no realm importado.

### Credenciais do Client

* **Client ID:** `loja-api`
* **Client Secret:** `123456`

Com essas credenciais, você poderá autenticar via Swagger utilizando o fluxo Client Credentials, ou gerar tokens via
endpoint do Keycloak:

```http
POST http://localhost:8080/realms/loja-embalagens/protocol/openid-connect/token
Content-Type: application/x-www-form-urlencoded

client_id=loja-api&client_secret=123456&grant_type=client_credentials
```

## Executando chamadas autenticadas via Swagger UI

1. Acesse [http://localhost:8081/swagger-ui/index.html](http://localhost:8081/swagger-ui/index.html)
2. Clique no botão **Authorize** (ícone de cadeado no topo direito).
3. Preencha os campos:

    * **client\_id**: `loja-api`
    * **client\_secret**: `123456`
    * **Realm**: `loja-embalagens`
    * **Token URL**: `http://localhost:8080/realms/loja-embalagens/protocol/openid-connect/token`
    * **Scopes**: deixe em branco (se não houver configuração específica)
4. Clique em **Authorize** novamente.
5. Após autorizado, você poderá executar os endpoints protegidos normalmente através do Swagger UI.

---
