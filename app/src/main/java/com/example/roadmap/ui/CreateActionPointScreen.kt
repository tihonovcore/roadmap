package com.example.roadmap.ui

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.roadmap.R
import com.example.roadmap.model.ActionPoint
import com.example.roadmap.ui.theme.RoadmapTheme

@Composable
fun CreateActionPoint(
    onCreateActionPoint: (ActionPoint) -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(5.dp)
    ) {
        var name by rememberSaveable { mutableStateOf("") }
        var description by rememberSaveable { mutableStateOf("") }

        val context = LocalContext.current
        fun tryCreateActionPoint() {
            if (name.isBlank()) {
                Toast.makeText(context, "Заполните название", Toast.LENGTH_SHORT).show()
            } else if (description.isBlank()) {
                Toast.makeText(context, "Заполните описание", Toast.LENGTH_SHORT).show()
            } else {
                onCreateActionPoint(ActionPoint(0, name, description))
            }
        }

        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            label = { Text(stringResource(R.string.action_point_name)) },
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Next
            )
        )
        Spacer(Modifier.height(5.dp))
        OutlinedTextField(
            value = description,
            onValueChange = { description = it },
            singleLine = false,
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp),
            label = { Text(stringResource(R.string.action_point_description)) },
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = { tryCreateActionPoint() }
            )
        )
        Spacer(Modifier.weight(1F))
        Button(
            onClick = { tryCreateActionPoint() },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 10.dp),
        ) {
            Text(text = stringResource(R.string.create_action_point))
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun RoadmapsListPreview() {
    RoadmapTheme(darkTheme = true) {
        CreateActionPoint(
            onCreateActionPoint = {}
        )
    }
}
