package com.example.roadmap.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.roadmap.R
import com.example.roadmap.model.Roadmap
import com.example.roadmap.ui.theme.RoadmapTheme

@Composable
fun RoadmapsList(
    roadmaps: List<Roadmap>,
    onRoadmapSelected: (Roadmap) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        items(roadmaps) { roadmap ->
            Card(
                onClick = { onRoadmapSelected(roadmap) },
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp)
            ) {
                ListItem(roadmap)
            }
        }
    }
}

@Composable
private fun ListItem(
    roadmap: Roadmap
) {
    Row {
        //TODO: use roadmap.picture
        Image(
            painter = painterResource(R.drawable.ic_launcher_background),
            contentDescription = null,
            modifier = Modifier
                .padding(10.dp)
                .clip(CircleShape)
        )
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = roadmap.name,
                style = MaterialTheme.typography.titleLarge,
            )
            Spacer(Modifier.height(5.dp))
            Text(
                text = roadmap.description,
                style = MaterialTheme.typography.bodyMedium,
            )
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun RoadmapsListPreview() {
    val roadmaps = listOf(
        Roadmap(
            id = 0,
            name = "Blockchain",
            description = "Изучение технологий blockchain",
            picture = "",
            actionPoints = listOf()
        ),
        Roadmap(
            id = 1,
            name = "Плагин для хрома",
            description = "Ну плагин и плагин",
            picture = "",
            actionPoints = listOf()
        ),
        Roadmap(
            id = 2,
            name = "Приложение с ChatGPT",
            description = "Улучшение приложений за счет интеграции инструментов ChatGPT",
            picture = "",
            actionPoints = listOf()
        )
    )

    RoadmapTheme(darkTheme = false) {
        RoadmapsList(
            roadmaps = roadmaps,
            onRoadmapSelected = {}
        )
    }
}
