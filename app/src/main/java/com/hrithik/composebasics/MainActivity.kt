package com.hrithik.composebasics

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hrithik.composebasics.ui.theme.ComposeBasicsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApp {
                MyGreetings()
            }
        }
    }
}

@Composable
fun MyApp(){

    var isSelected by remember { mutableStateOf(false)}

    ComposeBasicsTheme {
        // A surface container using the 'background' color from the theme
        Surface(color = Color.Red) {
            Greeting("Android",isSelected = isSelected, updateSelection = { newSelect -> isSelected = newSelect})
        }
    }
}

@Composable
fun MyApp(content: @Composable () -> Unit) {
    ComposeBasicsTheme {
        Surface(color = Color.Yellow) {
            content()
        }
    }
}

@Composable
fun NameList(
    names: List<String>,
    modifier: Modifier = Modifier,
    horizontalAlignment: Alignment.Horizontal
) {

    LazyColumn(
        modifier = modifier
        , horizontalAlignment = horizontalAlignment
    ) {
        items(
            items = names
        ) { name ->

            var isSelected by remember { mutableStateOf(false)}

            Greeting(
                name = name,
                isSelected = isSelected,
                updateSelection = { newSelect ->
                    isSelected = newSelect
                }
            )

            Divider(color = Color.Black)
        }
    }
}

@Composable
fun MyGreetings(names: List<String> = List(1000) { "Hello Android #$it" }){

    val counterState = remember { mutableStateOf(0) }

    Column (
        modifier = Modifier.fillMaxHeight()
        ,verticalArrangement = Arrangement.Center
        , horizontalAlignment = Alignment.CenterHorizontally
    ){
//        Column(modifier = Modifier.weight(1f)
//            , horizontalAlignment = Alignment.CenterHorizontally) {
//            for (name : String in names){
//                Greeting(name = name)
//                Divider(color = Color.Black)
//            }
//        }

        NameList(
            names = names
            , modifier = Modifier.weight(1f)
            , horizontalAlignment = Alignment.CenterHorizontally
        )

//        Divider(color = Color.Transparent, thickness = 32.dp)
        Counter(
            count = counterState.value,
            updateCount = { newCount ->
                counterState.value = newCount
            }
        )
    }
}

@Composable
fun Counter() {

    val count = remember { mutableStateOf(0) }

    Button(onClick = { count.value++ }, modifier = Modifier.padding(32.dp)) {
        Text("I've been clicked ${count.value} times")
    }
}

@Composable
fun Counter(count: Int, updateCount: (Int) -> Unit) {
    OutlinedButton(
        onClick = { updateCount(count+1) },
        modifier = Modifier.padding(32.dp),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = if (count > 5) Color.Green else Color.White
        )
    ) {

        Text("I've been clicked $count times")

    }
}

@Composable
fun Greeting(name: String, isSelected: Boolean, updateSelection : (Boolean) -> Unit) {

//    var isSelected by remember { mutableStateOf(false)}
    val backgroundColor by animateColorAsState(if(isSelected) Color.Green else Color.Transparent)


    Text(text = "Hello $name!"
        , modifier = Modifier.padding(12.dp)
                    .background(color = backgroundColor)
                    .clickable (onClick = {updateSelection(!isSelected)})
        ,color = Color.Magenta
        ,style = MaterialTheme.typography.h4)
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MyApp {
        MyGreetings()
    }
}