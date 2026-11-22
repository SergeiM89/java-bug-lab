# Java API Lab

Java 21 / Spring Boot, Maven. Без БД и внешних сервисов.

Dockerfile multi-stage: Maven build → JRE runtime. Порт 8080; `GET /health` → 200.
Runtime: `java -jar app.jar`. UI: `/`.

- `GET /api/divide?a=12&b=3` → `{"result":4}`; b=0 должен давать штатный 4xx.
- `GET /api/parse?value=42` → `{"result":42}`; value=hello должен давать штатный 4xx.
- Stack traces: stdout/stderr. HTTP access log: `/tmp/access.log`, перечитывать после ротации.
- Tests внутри Maven builder: `mvn -o -B test`. В JRE image Maven отсутствует;
  минимальная runtime-регрессия — отрицательные и корректные HTTP probes.

## Запуск в Docker

```sh
docker build -t java-bug-lab .
docker run --rm -p 127.0.0.1:8081:8080 java-bug-lab
```

Откройте http://localhost:8081. Исходная версия возвращает 500 при делении на ноль
и разборе `hello`; корректные запросы должны возвращать 200 с результатами 4 и 42.

Регрессионные тесты в builder-контейнере:

```sh
docker build --target build -t java-bug-lab-tests .
docker run --rm java-bug-lab-tests mvn -o -B test
```

В исходной версии два отрицательных теста намеренно завершаются ошибками:
цель задания — исправить приложение.

## Проверка агентом

URL: https://github.com/SergeiM89/java-bug-lab. Ветка по умолчанию `main`
и ветка `demo/runtime` содержат два воспроизводимых дефекта.
`baseline/healthy` — исправный контрольный вариант.

Исправления сначала выполняются и проверяются локально. Публикация PR — отдельное
действие после проверки результата и подключения GitHub с правом записи.
