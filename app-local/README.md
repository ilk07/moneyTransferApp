#  Сервис перевода денег с карты на карту

REST-сервис предоставляет интерфейс перевода денег между банковскими картами.
Разработан в соответствии с протоколом Transfer Money API версии 3.0.0, ведет журнал операций (лог переводов) в отдельном файле.

## Установка и запуск приложения
Для запуска приложения используется Docker-контейнер. Из коробки предлагается два варианта сборки REST-сервиса:
1. Скачайте папку "local" целиком
2. Перейдите в корневую директорию скачанной папки
3. Откройте терминал
4. Выполните команду <code>docker-composer up</code>

> В результате будут запущены два Docker контейнера: REST сервис и UI. Сразу после запуска контейнеров UI станет доступен по адресу http://localhost:3000, а REST-сервис будет обрабатывать поступающие с этого адреса запросы на переворд средств.

*Docker должен быть заранее установлен на ПК для корректной работы приложения