package com.jiajia.mypractisedemos.module.compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jiajia.mypractisedemos.module.compose.ui.theme.MyPractiseDemosTheme
import com.jiajia.mypractisedemos.module.kotlin.util.ToastUtils

class ComposeMainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { MyApp() }
    }
}

@Composable
fun MyApp() {
    return MyPractiseDemosTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {
            Scaffold(
                topBar = { BuildTopBar()},
                content = { BuildBody()},
                floatingActionButton = { BuildFloatActionButton()}
            )
        }
    }
}

@Composable
fun BuildTopBar() {
    return TopAppBar(
        title = { Text("Simple TopAppBar")},
        navigationIcon = {
            IconButton(onClick = {ToastUtils.showToast("点我干啥")}) {
                Icon(Icons.Filled.Menu,  contentDescription =  null)
            }
        },
        actions = {
            IconButton(onClick = { ToastUtils.showToast("点我了") }) {
                Icon(
                    Icons.Filled.Favorite, contentDescription = null
                )
            }
            IconButton(onClick = { ToastUtils.showToast("也点我了") }) {
                Icon(
                    Icons.Filled.Call, contentDescription = null
                )
            }
        }
    )
}

@Composable
fun BuildBody() {
    return Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Greeting("World")
        Spacer(modifier = Modifier.size(20.dp))
        Greeting("Android")
        Spacer(modifier = Modifier.size(20.dp))
        Button(
            onClick = { ToastUtils.showToast("点击了Favorite")},
            contentPadding = PaddingValues(
                horizontal = 20.dp,
                vertical = 12.dp
            )
        ) {
            Icon(
                imageVector = Icons.Filled.Favorite,
                contentDescription = "Favorite",
                modifier = Modifier.size(ButtonDefaults.IconSize)
            )
            Spacer(modifier = Modifier.size(ButtonDefaults.IconSpacing))
            Text("Like")
        }
    }
}

@Composable
fun BuildFloatActionButton() {
    return FloatingActionButton(onClick = { ToastUtils.showToast("我是底部悬浮按钮")}) {
        Icon(Icons.Filled.Add, contentDescription = null)
    }
}



@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = false)
@Composable
fun DefaultPreview() {
    MyApp()
}