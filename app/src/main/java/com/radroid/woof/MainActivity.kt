package com.radroid.woof

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.StringRes
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.radroid.woof.ui.theme.WoofTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WoofTheme {
                DogList()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WoofTopAppBar(modifier: Modifier = Modifier) {
    CenterAlignedTopAppBar(modifier = Modifier, title = {
        Row(modifier = Modifier, verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(id = R.drawable.img_9), contentDescription = null,
                Modifier
                    .size(50.dp)
                    .padding(8.dp)
            )
            Text(text = "Woof", style = MaterialTheme.typography.displayLarge)
        }
    })
}

@Composable
fun DogItemButton(
    expended: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    IconButton(
        onClick = onClick,
        modifier = modifier
    ) {
        androidx.compose.material3.Icon(
            imageVector = if(expended) Icons.Filled.ExpandMore else Icons.Filled.ExpandLess,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.secondary
        )

    }

}

@Composable
fun DogList(modifier: Modifier = Modifier) {
    Scaffold(topBar = { WoofTopAppBar() }) { it ->
        LazyColumn(
            contentPadding = it,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(DogList.dogs) { dog ->
                DogCard(dog = dog)

            }
        }
    }
}

@Composable
fun DogCard(dog: Dog, modifier: Modifier = Modifier) {
    var expended by remember {
        mutableStateOf(false)
    }
    val color by animateColorAsState(targetValue = if(expended)
        MaterialTheme.colorScheme.tertiaryContainer
    else
        MaterialTheme.colorScheme.primaryContainer, label = ""
    )
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 8.dp, end = 8.dp),
        shape = RoundedCornerShape(topEnd = 16.dp, bottomStart = 16.dp)
    ) {
        Column(modifier= Modifier
//            .padding(8.dp)
            .animateContentSize(
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioNoBouncy,
                    stiffness = Spring.StiffnessMedium
                )
            )
            .background(color = color)) {
            Row(modifier = Modifier.padding(8.dp)) {
                Image(
                    painter = painterResource(id = dog.dogImage),
                    contentDescription = stringResource(id = dog.dogName),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .height(50.dp)
                        .width(70.dp)
                        .padding(end = 16.dp)
                        .clip(RoundedCornerShape(50.dp))
                )
                Column(modifier = Modifier.padding(top = 8.dp)) {
                    Text(
                        text = stringResource(id = dog.dogName),
                        style = MaterialTheme.typography.displayMedium
                    )
                    Text(text = dog.dogAge, style = MaterialTheme.typography.bodyLarge)
                }
                Spacer(modifier = Modifier.weight(1f))
                DogItemButton(expended = expended, onClick = {
                    expended = !expended
                })
            }
            if (expended) {

                Column(modifier = Modifier.padding(8.dp)) {
                    DogHobby(dogHobby = dog.hobbies, modifier = Modifier.padding(8.dp))
                }
            }
        }
    }
}

@Composable
fun DogHobby(@StringRes dogHobby: Int, modifier: Modifier = Modifier) {
    Column(modifier = Modifier) {
        Text(
            text = stringResource(id = R.string.about,modifier.fillMaxWidth()),
            style = MaterialTheme.typography.labelSmall
        )
        Text(
            text = stringResource(id = dogHobby),
            style = MaterialTheme.typography.bodyLarge
        )
    }


}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    WoofTheme(
        darkTheme = false
    ) {
        DogList()
    }
}