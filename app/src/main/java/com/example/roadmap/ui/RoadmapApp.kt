package com.example.roadmap.ui

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.roadmap.R
import com.example.roadmap.model.RoadmapState
import com.example.roadmap.ui.theme.RoadmapTheme

enum class RoadmapScreen(val route: String) {
    ListRoadmaps(route = "ListRoadmaps"),
    ListActionPoints(route = "ListActionPoints/{roadmapId}"),
    ActionPointScreen(route = "ActionPointScreen"),
    CreateActionPointScreen(route = "CreateActionPointScreen");

    fun withArgs(vararg args: Any): String {
        var routeWithArgs = route
        args.forEach { arg ->
            routeWithArgs = routeWithArgs.replaceFirst(Regex("""\{.*?\}"""), arg.toString())
        }
        return routeWithArgs
    }

    companion object {
        fun parse(route: String): RoadmapScreen {
            return valueOf(route.split("/")[0])
        }
    }
}

@Composable
fun RoadmapApp() {
    val navController = rememberNavController()
    val viewModel = viewModel<RoadmapViewModel>(factory = RoadmapViewModel.Factory)
    val uiState by viewModel.uiState.collectAsState()

    val roadmaps by viewModel.roadmaps.collectAsState()
    val finishedActionIds by viewModel.finishedActionIdsState.collectAsState()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            RoadmapTopBar(
                navController = navController,
                uiState = uiState,
                finishedActionIds = finishedActionIds,
            )
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = RoadmapScreen.ListRoadmaps.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(route = RoadmapScreen.ListRoadmaps.route) {
                RoadmapsList(
                    roadmaps = roadmaps,
                    onRoadmapSelected = { roadmap ->
                        viewModel.chooseRoadmap(roadmap)
                        navController.navigate(route = RoadmapScreen.ListActionPoints.withArgs(roadmap.id))
                    }
                )
            }

            composable(
                route = RoadmapScreen.ListActionPoints.route,
                arguments = listOf(navArgument("roadmapId") { type = NavType.IntType })
            ) {
                ActionPointsList(
                    roadmap = uiState.selectedRoadmap!!,
                    finishedActionIds = finishedActionIds,
                    onActionPointSelected = { actionPoint ->
                        viewModel.chooseActionPoint(actionPoint)
                        navController.navigate(route = RoadmapScreen.ActionPointScreen.route)
                    }
                )
            }

            composable(route = RoadmapScreen.ActionPointScreen.route) {
                val selectedActionPoint = uiState.selectedActionPoint!!
                ActionPointsContent(
                    actionPoint = selectedActionPoint,
                    isActionPointDone = selectedActionPoint.id in finishedActionIds,
                    isDoneChanged = { viewModel.changeDoneStatus(selectedActionPoint) }
                )
            }

            composable(route = RoadmapScreen.CreateActionPointScreen.route) {
                CreateActionPoint(
                    onCreateActionPoint = { name, description ->
                        viewModel.addActionPointToCurrentRoadmap(name, description)
                        navController.navigateUp()
                    }
                )
            }
        }
    }
}

@Composable
private fun RoadmapTopBar(
    navController: NavController, uiState: RoadmapState, finishedActionIds: Set<Int>
) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = RoadmapScreen.parse(
        backStackEntry?.destination?.route ?: RoadmapScreen.ListRoadmaps.route
    )

    @OptIn(ExperimentalMaterial3Api::class)
    CenterAlignedTopAppBar(
        title = {
            when (currentScreen) {
                RoadmapScreen.ListRoadmaps -> {
                    Text(text = stringResource(R.string.roadmap))
                }
                RoadmapScreen.ListActionPoints -> {
                    val roadmap = uiState.selectedRoadmap!!

                    val done = roadmap.actionPoints.count { it.id in finishedActionIds }
                    val total = roadmap.actionPoints.size

                    Row {
                        Text(text = roadmap.name)
                        Spacer(Modifier.width(5.dp))
                        Text(text = "$done/$total")
                    }
                }
                RoadmapScreen.ActionPointScreen -> {
                    Text(text = uiState.selectedActionPoint!!.name)
                }
                RoadmapScreen.CreateActionPointScreen -> {
                    Text(text = "Добавление целевого действия")
                }
            }
        },
        navigationIcon = {
            if (currentScreen != RoadmapScreen.ListRoadmaps) {
                IconButton(onClick = { navController.navigateUp() }) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back_button)
                    )
                }
            }
        },
        actions = {
            if (currentScreen == RoadmapScreen.ListActionPoints) {
                IconButton(
                    onClick = {
                        navController.navigate(route = RoadmapScreen.CreateActionPointScreen.name)
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = stringResource(R.string.add_action_point)
                    )
                }
            }
        }
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun AppPreview() {
    RoadmapTheme {
        RoadmapApp()
    }
}
