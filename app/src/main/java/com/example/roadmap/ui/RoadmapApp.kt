package com.example.roadmap.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.roadmap.R
import com.example.roadmap.data.DataProvider
import com.example.roadmap.model.Roadmap
import com.example.roadmap.ui.theme.RoadmapTheme

enum class RoadmapScreen {
    ListRoadmaps,
    ListActionPoints,
    ActionPointScreen
}

@Composable
fun RoadmapApp() {
    val navController = rememberNavController()
    val viewModel = viewModel<RoadmapViewModel>()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            @OptIn(ExperimentalMaterial3Api::class)
            CenterAlignedTopAppBar(
                title = {
                    Text(text = "Roadmap")
                },
                navigationIcon = {
                    if (false /*TODO*/) {
                        IconButton(onClick = { /*TODO*/ }) {
                            Icon(
                                imageVector = Icons.Filled.ArrowBack,
                                contentDescription = stringResource(R.string.back_button)
                            )
                        }
                    }
                }

            )
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = RoadmapScreen.ListRoadmaps.name,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(route = RoadmapScreen.ListRoadmaps.name) {
                RoadmapsList(
                    onRoadmapSelected = { roadmap ->
                        //TODO: save roadmap to viewmodel
                        navController.navigate(route = RoadmapScreen.ListActionPoints.name)
                    }
                )
            }

            composable(route = RoadmapScreen.ListActionPoints.name) {
                ActionPointsList(
                    roadmap = DataProvider.roadmaps.first(),
                    onActionPointSelected = { actionPoint ->
                        //TODO: save actionPoint to viewmodel
                        navController.navigate(route = RoadmapScreen.ActionPointScreen.name)
                    }
                )
            }

            composable(route = RoadmapScreen.ActionPointScreen.name) {
                ActionPointsContent(actionPoint = DataProvider.roadmaps.first().actionPoints.first())
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun AppPreview() {
    RoadmapTheme {
        RoadmapApp()
    }
}
