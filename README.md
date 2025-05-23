# embalagem-api

API para automatizar o processo de embalagem de pedidos, calculando a melhor forma de alocar produtos em caixas de papelão disponíveis.

## Descrição

Este microserviço foi desenvolvido para a avaliação técnica da empresa [L2Code](https://l2code.com.br).  
A API recebe uma lista de pedidos, cada um com seus produtos e dimensões, e retorna a melhor forma de empacotar os produtos nas caixas disponíveis, otimizando o uso do espaço.

## Tecnologias Utilizadas

- Java 17
- Spring Boot 3.5.0
- Maven
- Lombok
- Springdoc OpenAPI (Swagger)
- Docker

## Como rodar o projeto

### Pré-requisitos

- Java 17 ou superior
- Maven 3.8 ou superior
- Docker (opcional, para rodar em container)
- Git

### Passos para rodar localmente

1. **Clone o repositório:**

   ```bash
   git clone https://github.com/seu-usuario/embalagem-api.git
   cd embalagem-api
   ```

2. **Compile o projeto e gere o JAR:**

   ```bash
   mvn clean package
   ```

3. **Execute a aplicação:**

   ```bash
   java -jar target/embalagem-api-0.0.1-SNAPSHOT.jar
   ```

4. **Acesse a documentação Swagger:**

   [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)

### Rodando com Docker

1. **Gere o JAR (caso ainda não tenha feito):**

   ```bash
   mvn clean package
   ```

2. **Construa a imagem Docker:**

   ```bash
   docker build -t embalagem-api .
   ```

3. **Execute o container:**

   ```bash
   docker run -p 8080:8080 embalagem-api
   ```

4. **Acesse a documentação Swagger:**

   [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)

## Exemplo de uso

### Exemplo de entrada (`POST /api/embalagem/calcular`)

```json
[
  {
    "id": "pedido-001",
    "produtos": [
      { "id": "produto-001", "altura": 25, "largura": 35, "comprimento": 55 },
      { "id": "produto-002", "altura": 15, "largura": 20, "comprimento": 30 }
    ]
  }
]
```

### Exemplo de saída

```json
{
  "resultados": [
    {
      "pedidoId": "pedido-001",
      "caixas": [
        {
          "caixaId": 1,
          "altura": 30,
          "largura": 40,
          "comprimento": 80,
          "produtos": [
            { "id": "produto-001", "altura": 25, "largura": 35, "comprimento": 55 },
            { "id": "produto-002", "altura": 15, "largura": 20, "comprimento": 30 }
          ]
        }
      ]
    }
  ]
}
```

## Testes

Para rodar os testes automatizados:

```bash
mvn test
```

## Estrutura das caixas disponíveis

- Caixa 1: 30 x 40 x 80 cm
- Caixa 2: 80 x 50 x 40 cm
- Caixa 3: 50 x 80 x 60 cm
