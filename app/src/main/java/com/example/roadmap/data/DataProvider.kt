package com.example.roadmap.data

import com.example.roadmap.model.ActionPoint
import com.example.roadmap.model.Roadmap

//NOTE: do not save strings to resources, it should be on backend-server
object DataProvider {

    val roadmaps = listOf(
        Roadmap(
            name = "Android",
            description = "Разработка мобильных приложений",
            picture = 0, //TODO
            actionPoints = listOf(
                ActionPoint(
                    name = "Гугл курс",
                    description = "Курс по разработке от гугл: https://developer.android.com/courses/android-basics-compose/course"
                ),
                ActionPoint(
                    name = "Хранение кредов",
                    description = "Научиться хранить чувствительную информацию в безопасном локальном хранилище"
                ),
                ActionPoint(
                    name = "Отправка пушей",
                    description = "Научиться отправлять пуши на телефон"
                ),
                ActionPoint(
                    name = "Доступ к железу",
                    description = "Получить из приложения доступ к датчикам, камере, файлам"
                ),
                ActionPoint(
                    name = "Lorem Ipsum 1",
                    description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nunc sit amet sollicitudin urna. Aliquam commodo luctus interdum. Sed in interdum enim, quis aliquet sem. Sed semper dapibus massa, eu lobortis arcu auctor ut. Mauris varius dui a congue ullamcorper. Fusce in varius nisl, non facilisis est."
                ),
                ActionPoint(
                    name = "Lorem Ipsum 2",
                    description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nunc sit amet sollicitudin urna. Aliquam commodo luctus interdum. Sed in interdum enim, quis aliquet sem. Sed semper dapibus massa, eu lobortis arcu auctor ut. Mauris varius dui a congue ullamcorper. Fusce in varius nisl, non facilisis est."
                ),
                ActionPoint(
                    name = "Lorem Ipsum 3",
                    description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nunc sit amet sollicitudin urna. Aliquam commodo luctus interdum. Sed in interdum enim, quis aliquet sem. Sed semper dapibus massa, eu lobortis arcu auctor ut. Mauris varius dui a congue ullamcorper. Fusce in varius nisl, non facilisis est."
                ),
                ActionPoint(
                    name = "Lorem Ipsum 4",
                    description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nunc sit amet sollicitudin urna. Aliquam commodo luctus interdum. Sed in interdum enim, quis aliquet sem. Sed semper dapibus massa, eu lobortis arcu auctor ut. Mauris varius dui a congue ullamcorper. Fusce in varius nisl, non facilisis est."
                ),
            )
        ),
        Roadmap(
            name = "Телеграм бот",
            description = "Создание ботов для телеграма",
            picture = 0, //TODO
            actionPoints = listOf(
                ActionPoint(
                    name = "Создать бот",
                    description = "Найти обучающие ресурсы"
                ),
            )
        ),
        Roadmap(
            name = "Gamedev",
            description = "Научиться делать компьютерные игры",
            picture = 0, //TODO
            actionPoints = listOf(
                ActionPoint(
                    name = "Разработка под unity",
                    description = "Изучить образовательные ресурсы от Unity https://learn.unity.com/pathways"
                ),
            )
        ),
        Roadmap(
            name = "Операционные системы",
            description = "Изучать как все работает на низком уровне",
            picture = 0, //TODO
            actionPoints = listOf(
                ActionPoint(
                    name = "Своя ОС",
                    description = "Создать свою мини-ОС"
                ),
                ActionPoint(
                    name = "Свой планировщик",
                    description = "Разработать планировщик процессов для игрушечной ОС"
                ),
                ActionPoint(
                    name = "Своя файловая система",
                    description = "Разработать файловую систему для игрушечной ОС"
                ),
                ActionPoint(
                    name = "Модификация Linux",
                    description = "Сделать форк линукса, внести минимальные правки и собрать"
                ),
            )
        ),
        Roadmap(
            name = "Quantum computing",
            description = "Познакомиться с областью квантовых вычислений",
            picture = 0, //TODO
            actionPoints = listOf(
                ActionPoint(
                    name = "Изучить книжку",
                    description = "Изучить книжку Programming Quantum Computers"
                ),
                ActionPoint(
                    name = "Создать бот",
                    description = "Попробовать каты от Microsoft https://quantum.microsoft.com/en-us/tools/quantum-katas"
                ),
            )
        ),
        Roadmap(
            name = "Фронтенд",
            description = "Разработка UI для веба",
            picture = 0, //TODO
            actionPoints = listOf(
                ActionPoint(
                    name = "React",
                    description = "Найти гайд на сайте реакта и пройти"
                ),
            )
        ),
        Roadmap(
            name = "Бэкенд",
            description = "Разработка серверных приложений",
            picture = 0, //TODO
            actionPoints = listOf(
                ActionPoint(
                    name = "Свой сайт http",
                    description = "Поднять свой сайт на Nginx"
                ),
                ActionPoint(
                    name = "Свой сайт https",
                    description = "Настроить TLS"
                ),
            )
        ),
        Roadmap(
            name = "Desktop",
            description = "Разработка приложений для ПК",
            picture = 0, //TODO
            actionPoints = listOf(
                ActionPoint(
                    name = "Markdown",
                    description = "Реализовать редактор Markdown"
                ),
                ActionPoint(
                    name = "Железяки",
                    description = "Придумать как использовать железо"
                ),
            )
        ),
        Roadmap(
            name = "ML",
            description = "Эксперименты в области машинного обучения",
            picture = 0, //TODO
            actionPoints = listOf(
            )
        ),
        Roadmap(
            name = "Embedded",
            description = "Разработка для ардуино и прочих холодильников",
            picture = 0, //TODO
            actionPoints = listOf(
            )
        ),
        Roadmap(
            name = "Blockchain",
            description = "Изучение технологий blockchain",
            picture = 0, //TODO
            actionPoints = listOf(
            )
        ),
        Roadmap(
            name = "Плагин для хрома",
            description = "Ну плагин и плагин",
            picture = 0, //TODO
            actionPoints = listOf(
            )
        ),
        Roadmap(
            name = "Приложение с ChatGPT",
            description = "Улучшение приложений за счет интеграции инструментов ChatGPT",
            picture = 0, //TODO
            actionPoints = listOf(
            )
        )
    )
}