# iidflab-test
Приложение для обработки транзакций и установки лимитов на транзакции,
а также получение информации о транзакциях, которые превышают установленные лимиты.

## Технологии
- Java
- Spring Boot
- Gradle
- Liquibase
- PostgreSQL
- Docker
- Docker Compose
- Spring Data JPA
- Spring WebFlux
- SpringDoc OpenAPI

## Запуск
Для запуска приложения через docker compose нужно получить токен от [twelvedata](https://twelvedata.com/) и добавить его в файл в docker-compose.yml.   
Также нужно добавить символы валют, которые будут использоваться в приложении.
Список символов валют можно получить [здесь](https://api.twelvedata.com/forex_pairs).
```yaml
API_KEY: ${API_KEY}
API_SYMBOLS: ${API_SYMBOLS}
```

Также можно использовать dev профиль для запуска приложения без docker и postgres, используя h2.
```bash
./gradlew bootRun --args='--spring.profiles.active=dev'
```

## API
Документация доступна по адресу http://localhost:8080/swagger-ui.html после запуска приложения.

# Endpoints
POST /api/transaction/process
```bash
curl -X 'POST' \
  'http://localhost:8080/api/transaction/process' \
  -H 'accept: */*' \
  -H 'Content-Type: application/json' \
  -d '{
  "accountFromAccountId": 1,
  "accountToAccountId": 2,
  "currencyShortname": "USD",
  "sum": 1000,
  "expenseCategory": "string"
}'
```

GET /api/transaction/limitExceeded
```bash
curl -X 'GET' \
  'http://localhost:8080/api/transaction/limitExceeded?accountId=1' \
  -H 'accept: */*'
```

POST /api/limit
```bash
curl -X 'POST' \
  'http://localhost:8080/api/limit' \
  -H 'accept: */*' \
  -H 'Content-Type: application/json' \
  -d '{
  "limitSum": 2000,
  "limitCurrencyShortname": "USD",
  "expenseCategory": "string",
  "accountId": 1
}'
```