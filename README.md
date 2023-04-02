# Mini Autorizador
Este é um projeto Java Spring Boot para simular um autorizador de transações com cartão de crédito. A aplicação possui uma API REST que permite criar cartões de crédito, verificar o saldo do cartão e autorizar transações.

## Tecnologias
As tecnologias utilizadas neste projeto são:

Java 11 \
Spring Boot \
Spring Data JPA \
MySQL \
Maven
## Executando a aplicação
Para executar a aplicação, é necessário ter o Java 11 e o Maven instalados.

## Clone o repositório
A aplicação estará disponível na URL `http://localhost:8080`.

## Endpoints
`POST /cartoes`: cria um novo cartão de crédito com um número e senha.
`GET /cartoes/{numeroCartao}`: verifica o saldo do cartão de crédito com o número informado.
`POST /transacoes`: realiza uma transação com um cartão de crédito. O payload deve conter o número do cartão, a senha, o valor da transação e o estabelecimento.
Os payloads esperados e retornados por cada endpoint podem ser encontrados na documentação da API, disponível na URL `http://localhost:8080/swagger-ui/index.html`.

## Funcionamento
Quando uma transação é recebida, o sistema verifica se o número do cartão e a senha estão corretos. Se estiverem corretos, o sistema verifica se o saldo do cartão é suficiente para a transação. Se o saldo for suficiente, a transação é autorizada e o saldo é atualizado. Caso contrário, a transação é negada.

Caso o número do cartão ou a senha estejam incorretos, a transação é negada imediatamente e uma mensagem de erro é retornada.
