package com.example.roadmap.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.roadmap.data.DataProvider
import com.example.roadmap.model.ActionPoint
import com.example.roadmap.model.Roadmap
import com.example.roadmap.ui.theme.RoadmapTheme

//TODO: кнопка добавить новый

@Composable
fun ActionPointsList(
    roadmap: Roadmap,
    onActionPointSelected: (ActionPoint) -> Unit
) {
    //TODO: писать в шапку что выполнено 3/10
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        items(roadmap.actionPoints) { actionPoint ->
            Card(
                onClick = { onActionPointSelected(actionPoint) },
                modifier = Modifier
                    .fillMaxSize()
                    .padding(5.dp)
                    .heightIn(max = 125.dp)
            ) {
                ListItem(actionPoint)
            }
        }
    }
}

@Composable
private fun ListItem(
    actionPoint: ActionPoint
) {
    Row {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            //TODO: галку для выполненных
            Text(
                text = actionPoint.name,
                style = MaterialTheme.typography.titleLarge,
            )
            Spacer(Modifier.height(5.dp))
            Text(
                text = actionPoint.description,
                style = MaterialTheme.typography.bodyMedium,
            )
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun RoadmapsListPreview() {
    RoadmapTheme(darkTheme = false) {
        ActionPointsList(
            roadmap = DataProvider.roadmaps.first(),
            onActionPointSelected = {}
        )
    }
}

