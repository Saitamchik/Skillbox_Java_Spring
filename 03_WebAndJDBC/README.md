# Skillbox_Java_Spring
### Практическое задание по второму модулю.

Запуск программы проходит в три этапа:
1) Перейти в консоли в директорию - cd src/main/resources/docker 
2) docker compose up
3) Запуск приложения через iDea

Программа может следующее:
* Создание нового контакта
* Рудактирование данных контакта
* Удаление контакта



При старте приложения можно включить инициализацию 10 записей контакта

Для этого нужно расскоментировать "@EventListener(ApplicationStartedEvent.class)" в классе DatabaseContactCreator