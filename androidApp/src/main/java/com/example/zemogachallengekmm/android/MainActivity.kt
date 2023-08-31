package com.example.zemogachallengekmm.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.PullRefreshState
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import com.example.zemogachallengekmm.SharedResources
import com.example.zemogachallengekmm.SplashScreen
import com.example.zemogachallengekmm.utils.Strings
import com.example.zemogachallengekmm.db.DatabaseDriverFactory
import com.example.zemogachallengekmm.viewmodel.PostUiState
import com.example.zemogachallengekmm.viewmodel.PostViewModel
import dev.icerock.moko.resources.StringResource
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    private val viewModel: PostViewModel by viewModels {
        PostViewModelProviderFactory(DatabaseDriverFactory(this))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent { SplashScreen() }
        launchMainScreen()
    }

    private fun launchMainScreen() {
        lifecycleScope.launch {
            delay(3000)
            setContent {
                MainScreen(viewModel)
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MainScreen(
    viewModel: PostViewModel
) {
    val uiState = viewModel.uiState.collectAsState()

    LaunchedEffect(key1 = true) {
        viewModel.getPostsFromDB()
    }

    MyApplicationTheme {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            floatingActionButton = {
                if (uiState.value.posts.isEmpty().not()) {
                    FloatingActionButton(viewModel)
                }
            },
            floatingActionButtonPosition = FabPosition.End
        ) { paddingValues ->
            Surface(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize(),
                color = MaterialTheme.colors.background
            ) {
                val pullRefreshState = rememberPullRefreshState(uiState.value.isLoading, { viewModel.getPostsFromApi() })

                Box(
                    Modifier
                        .padding(paddingValues)
                        .fillMaxSize()
                        .pullRefresh(
                            state = pullRefreshState,
                            enabled = uiState.value.posts.isEmpty()
                        )
                ) {
                    PostsScreen(uiState = uiState)
                    PullToRefreshScreen(uiState = uiState, pullRefreshState =pullRefreshState)
                }
            }
        }
    }
}

@Composable
fun stringResource(id: StringResource, vararg args: Any): String {
    return Strings(LocalContext.current).get(id, args.toList())
}

@Composable
fun FloatingActionButton(viewModel: PostViewModel) {
    FloatingActionButton(
        modifier = Modifier
            .padding(all = 16.dp),
        onClick = { viewModel.deletePosts() }
    ) {
        Icon(
            imageVector = Icons.Rounded.Delete,
            contentDescription = "Delete Posts",
            tint = Color.White,
        )
    }
}

@Composable
fun PostsScreen(uiState: State<PostUiState>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
    ) {
        items(uiState.value.posts) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = it.title,
                    modifier = Modifier
                        .fillMaxWidth(),
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = it.body,
                    modifier = Modifier
                        .fillMaxWidth(),
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )
            }
            Divider(
                color = Color.Black
            )
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PullToRefreshScreen(
    uiState: State<PostUiState>,
    pullRefreshState: PullRefreshState
) {
    if (uiState.value.posts.isEmpty()) {
        Box(
            Modifier
                .fillMaxSize()
                .pullRefresh(state = pullRefreshState, enabled = uiState.value.posts.isEmpty())
        ) {
            PullRefreshIndicator(
                refreshing = uiState.value.isLoading,
                state = pullRefreshState,
                modifier = Modifier
                    .align(Alignment.TopCenter)
            )
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = stringResource(
                        id = SharedResources.strings.pull_down_to_load_label
                    )
                )
                Image(
                    painter = painterResource(
                        id = com.example.zemogachallengekmm.R.drawable.arrow_downward
                    ),
                    contentDescription = null,
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}
