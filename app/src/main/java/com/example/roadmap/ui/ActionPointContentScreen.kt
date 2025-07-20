package com.example.roadmap.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.roadmap.R
import com.example.roadmap.data.DataProvider
import com.example.roadmap.model.ActionPoint
import com.example.roadmap.ui.theme.RoadmapTheme

@Composable
fun ActionPointsContent(
    actionPoint: ActionPoint,
    isActionPointDone: Boolean,
    isDoneChanged: () -> Unit
) {
    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize(),
    ) {
        if (actionPoint.link != null) {
            Text(text = buildAnnotatedString {
                append(actionPoint.description)

                pushLink(LinkAnnotation.Url(actionPoint.link))
                withStyle(
                    SpanStyle(
                        color = Color.Blue,
                        textDecoration = TextDecoration.Underline
                    )
                ) {
                    append(stringResource(R.string.look_at_link))
                }
                pop()
            })
        } else {
            Text(
                text = actionPoint.description,
                style = MaterialTheme.typography.bodyLarge,
            )
        }

        Spacer(Modifier.weight(1F))

        if (isActionPointDone) {
            OutlinedButton(
                onClick = isDoneChanged,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 10.dp),
            ) {
                Text(text = "Mark as undone")
            }
        } else {
            Button(
                onClick = isDoneChanged,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 10.dp),
            ) {
                Text(text = "I've done it!")
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun RoadmapsListPreview() {
    RoadmapTheme(darkTheme = true) {
        ActionPointsContent(
            actionPoint = DataProvider.roadmaps.first().actionPoints.first(),
            isActionPointDone = true,
            isDoneChanged = {}
        )
    }
}
