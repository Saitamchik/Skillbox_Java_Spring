# Skillbox_Java_Spring
### Практическое задание по второму модулю.

Запуск программы осуществляется двуумя способами:
1) Локльно из jar файла или самого проекта в IDea
2) В docker контейнере

Запуск в контейнере выполняется с помощью двух команд:
1) docker build -t bootstarter .
2) docker run --name bootstarter -d -i -t bootstarter

Чтобы зайти в контейнер: docker attach bootstarter

Программа поддерживает 4 комманды:
* "print" - Вывод всех студентов
* "create String String age" - Добавить студента
* "remove int" - Удалить студента
* "remove-all" - Удалить всех студентов

Приложение поддерживает два профиля:
* default - стандартный
* init - инициализация данных из файла

Локально выставляется через файл application.properties, поле spring.profiles.active

В контейнере в Dockerfile, поле -Dspring.profiles.active

События при инициализации можно отключить через параметр - event.init.switch=true