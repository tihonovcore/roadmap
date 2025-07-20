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
    )
}