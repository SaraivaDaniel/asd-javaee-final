# asd-javaee-final

## Preparar uma instancia do MYSQL
1) se já tiver uma instancia executando, criar o banco sharebroker_dev
2) se não, utilizar docker conforme instruções abaixo:
```
docker run --name mysql -e MYSQL_ALLOW_EMPTY_PASSWORD=yes -d -p 3306:3306 mysql:5.7
# aguardar servidor estar pronto para conexões
docker logs mysql
# criar banco de testes
docker exec -i mysql mysql -uroot <<< "CREATE DATABASE sharebroker_dev;"
``` 
	
## Preparar uma instancia do RabbitMQ
1) se ja tiver uma instancia executando, não precisa realizar nada
2) se não, utilizar docker
```
docker run --name rabbitmq-management -d -p 15672:15672 -p 5671:5671 -p 5672:5672 rabbitmq:management
```

## Preparar a aplicação
1) clonar o repositorio
2) editar os arquivos
  - `resources/application.yaml`: configurar enderecos/portas para mysql e rabbit
  - `resources/config.properties`: configurar endereco e usuario/senha do servidor smtp
3) na raiz do projeto executar:
```
./mvnw install -DskipTests
java -jar target/sharebroker-0.0.1-SNAPSHOT.jar
```

## Utilização da aplicação

### Criação de empresas
`POST /api/v1/companies`

```json
{ 
  "name": "Petrobras" 
}
```

As chamadas a este endpoint retornarão um JSON com o mesmo nome e o ID criado para a empresa:
```json
{ 
  "id": 1,
  "name": "Petrobras" 
}
```

### Emissão de novas ações
`POST /api/v1/shares`

Informar id da empresa, valor inicial de venda e quantidade a emitir
```json
{
  "companyId" : 1,
  "value": 10.00, 
  "quantity": 10 
}
```

### Criação de compradores/acionistas
`POST /api/v1/buyers`

Informar nome e email (para envio da notificação)
```json
{ 
  "name": "Fulano", 
  "email": "fulano@server.com" 
}
```

Será retornado um JSON incluindo o ID do comprador criado.

### Criação de ordem de compra (considerando emissão inicial)
`POST /api/v1/orders/buy`

Informar ID do comprador, ID da empresa, valor a pagar, e quantidade máxima a comprar
```json
{
	"buyerId": 1,
	"companyId": 1,
	"value": 20,
	"quantity": 20
}
```

#### Regras para compra
- a ordem é executada após ser enviada para fila
- o sistema efetivará a compra de no máximo a quantidade informada (pode ser menos, ou nenhuma)
- o valor informado será o valor pago

### Criação de ordem de venda (um comprador vendendo)
`POST /api/v1/orders/sell`
`
Informar ID do comprador, ID da empresa, valor mínimo a cobrar, e quantidade para colocar a venda
```json
{
	"buyerId": 1,
	"companyId": 1,
	"value": 15,
	"quantity": 20
}
```

#### Regras para venda
- a ordem é executada após ser enviada para fila
- as ações que o comprador possui são colocadas a venda imediatamente (até o limite de quantidade)
- o valor informado será o mínimo cobrado do outro comprador

### Outros endpoints
`GET /api/v1/buyers/`: retorna todos os compradores (não inclui lista de ações)

`GET /api/v1/buyers/{id}`: retorna um comprador específico

`GET /api/v1/companies/`: retorna toda sas empresas cadastradas (não inclui lista de ações)

`GET /api/v1/companies/{id}`: retorna uma empresa específica

`GET /api/v1/shares/`: retorna todas as ações disponíveis no sistema

`GET /api/v1/shares/{id}`: retorna uma ação específica

`GET /api/v1/shares/company/{id}`: retorna todas as ações emitidas pela empresa especificada

`GET /api/v1/shares/buyer/{id}`: retorna todas as ações pertencentes ao comprador especificado
