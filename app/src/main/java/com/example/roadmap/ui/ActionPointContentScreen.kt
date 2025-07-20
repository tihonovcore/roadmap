package com.example.roadmap.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.roadmap.data.DataProvider
import com.example.roadmap.model.ActionPoint
import com.example.roadmap.ui.theme.RoadmapTheme

@Composable
fun ActionPointsContent(actionPoint: ActionPoint) {
    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize(),
    ) {
        Text(
            text = actionPoint.name,
            style = MaterialTheme.typography.displayMedium,
        )
        Spacer(Modifier.height(10.dp))
        Text(
            text = actionPoint.description,
            style = MaterialTheme.typography.bodyLarge,
        )
        Spacer(Modifier.weight(1F))
        Button(
            onClick = {},
            modifier = Modifier.fillMaxWidth().padding(bottom = 10.dp)
        ) {
            Text(text = "I've done it!")
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun RoadmapsListPreview() {
    RoadmapTheme(darkTheme = true) {
        ActionPointsContent(DataProvider.roadmaps.first().actionPoints.first())
    }
}
