package com.example.roadmap.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.roadmap.model.ActionPoint
import com.example.roadmap.ui.theme.RoadmapTheme

//TODO: кнопка в шапке добавить новый, переход на новый скрин

@Composable
fun ActionPointsList(
    actionPoints: List<ActionPoint>,
    finishedActionIds: Set<Int>,
    onActionPointSelected: (ActionPoint) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        items(actionPoints) { actionPoint ->
            Card(
                onClick = { onActionPointSelected(actionPoint) },
                modifier = Modifier
                    .fillMaxSize()
                    .padding(5.dp)
                    .heightIn(max = 125.dp)
            ) {
                ListItem(
                    actionPoint = actionPoint,
                    isDone = actionPoint.id in finishedActionIds
                )
            }
        }
    }
}

@Composable
private fun ListItem(
    actionPoint: ActionPoint, isDone: Boolean
) {
    Column(
        modifier = Modifier.padding(16.dp)
    ) {

        Text(
            text = actionPoint.name,
            style =
                if (isDone) MaterialTheme.typography.titleLarge.copy(
                    textDecoration = TextDecoration.LineThrough
                )
                else MaterialTheme.typography.titleLarge
        )
        Spacer(Modifier.height(5.dp))
        Text(
            text = actionPoint.description,
            style = MaterialTheme.typography.bodyMedium,
        )
    }
}

@Preview(showSystemUi = true)
@Composable
private fun RoadmapsListPreview() {
    val actionPoints = listOf(
        ActionPoint(
            id = 0,
            name = "Гугл курс",
            description = "Курс по разработке от гугл: ",
            link = "https://developer.android.com/courses/android-basics-compose/course"
        ),
        ActionPoint(
            id = 1,
            name = "Хранение кредов",
            description = "Научиться хранить чувствительную информацию в безопасном локальном хранилище"
        ),
        ActionPoint(
            id = 2,
            name = "Отправка пушей",
            description = "Научиться отправлять пуши на телефон"
        )
    )

    RoadmapTheme(darkTheme = false) {
        ActionPointsList(
            actionPoints = actionPoints,
            finishedActionIds = emptySet(),
            onActionPointSelected = {}
        )
    }
}

