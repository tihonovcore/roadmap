package com.example.roadmap.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.roadmap.R
import com.example.roadmap.model.ReactionToRemainder
import com.example.roadmap.ui.theme.RoadmapTheme

enum class RoadmapScreen(val route: String) {
    ListRoadmaps(route = "ListRoadmaps"),
    ListActionPoints(route = "ListActionPoints/{roadmapId}"),
    ActionPointScreen(route = "ActionPointScreen/{actionPointId}"),
    CreateActionPointScreen(route = "CreateActionPointScreen/{roadmapId}");

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
fun RoadmapApp(
    reaction: ReactionToRemainder = ReactionToRemainder()
) {
    val navController = rememberNavController()

    LaunchedEffect(reaction) {
        if (reaction.isPresent()) {
            val targetRoute = RoadmapScreen.ActionPointScreen.withArgs(reaction.selectedActionPoint)
            navController.navigate(targetRoute)
        }
    }

    var title by rememberSaveable { mutableStateOf(value = "") }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            RoadmapTopBar(
                navController = navController,
                title = title,
            )
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = RoadmapScreen.ListRoadmaps.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(route = RoadmapScreen.ListRoadmaps.route) {
                val localViewModel: RoadmapsListViewModel = viewModel(
                    factory = RoadmapsListViewModel.Factory
                )
                val roadmaps by localViewModel.roadmaps.collectAsState()
                val blurText by localViewModel.blurText.collectAsState()

                title = stringResource(R.string.roadmap)

                RoadmapsList(
                    roadmaps = roadmaps,
                    onRoadmapSelected = { roadmap ->
                        navController.navigate(route = RoadmapScreen.ListActionPoints.withArgs(roadmap.id))
                    },
                    blurText = blurText,
                )
            }

            composable(
                route = RoadmapScreen.ListActionPoints.route,
                arguments = listOf(navArgument("roadmapId") { type = NavType.IntType })
            ) {
                val localViewModel: ActionPointsListViewModel = viewModel(
                    factory = ActionPointsListViewModel.Factory
                )

                val roadmapName by localViewModel.roadmapName.collectAsState()
                val actionPoints by localViewModel.actionPoints.collectAsState()
                val finishedActionIds by localViewModel.finishedActionIdsState.collectAsState()

                val doneActionPointsCount = actionPoints.count { it.id in finishedActionIds }
                title = "$roadmapName $doneActionPointsCount/${actionPoints.size}"

                ActionPointsList(
                    actionPoints = actionPoints,
                    finishedActionIds = finishedActionIds,
                    onActionPointSelected = { actionPoint ->
                        navController.navigate(route = RoadmapScreen.ActionPointScreen.withArgs(actionPoint.id))
                    }
                )
            }

            composable(
                route = RoadmapScreen.ActionPointScreen.route,
                arguments = listOf(navArgument("actionPointId") { type = NavType.IntType })
            ) {
                val localViewModel: ActionPointContentViewModel = viewModel(
                    factory = ActionPointContentViewModel.Factory
                )
                val selectedActionPoint by localViewModel.selectedActionPoint.collectAsState()
                val finishedActionIds by localViewModel.finishedActionIdsState.collectAsState()

                title = selectedActionPoint.name

                ActionPointsContent(
                    actionPoint = selectedActionPoint,
                    isActionPointDone = selectedActionPoint.id in finishedActionIds,
                    isDoneChanged = { localViewModel.changeDoneStatus(selectedActionPoint) }
                )
            }

            composable(
                route = RoadmapScreen.CreateActionPointScreen.route,
                arguments = listOf(navArgument("roadmapId") { type = NavType.IntType })
            ) {
                val localViewMode: CreateActionPointViewModel = viewModel(
                    factory = CreateActionPointViewModel.Factory
                )

                title = stringResource(R.string.add_action_point)

                CreateActionPoint(
                    onCreateActionPoint = { name, description ->
                        localViewMode.addActionPointToCurrentRoadmap(name, description)
                        navController.navigateUp()
                    }
                )
            }
        }
    }
}

@Composable
private fun RoadmapTopBar(
    navController: NavController, title: String
) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = RoadmapScreen.parse(
        backStackEntry?.destination?.route ?: RoadmapScreen.ListRoadmaps.route
    )

    @OptIn(ExperimentalMaterial3Api::class)
    CenterAlignedTopAppBar(
        title = { Text(text = title) },
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
                        val roadmapId = backStackEntry?.arguments?.getInt("roadmapId") ?: error("No roadmapId")
                        navController.navigate(route = RoadmapScreen.CreateActionPointScreen.withArgs(roadmapId))
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
