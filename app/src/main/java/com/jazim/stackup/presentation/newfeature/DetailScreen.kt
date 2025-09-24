package com.jazim.stackup.presentation.newfeature

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.jazim.stackup.R
import com.jazim.stackup.domain.model.User
import com.jazim.stackup.presentation.ui.components.FollowUnfollowButton
import com.jazim.stackup.presentation.users.UsersViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun DetailScreen(
    usersViewModel: UsersViewModel,
    userId: Int
) {

    val usersState by usersViewModel.usersState.collectAsState()

    val user: User? = usersState.users.find { it.id == userId }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceAround,
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(R.drawable.placeholder),
            contentDescription = "",
            modifier = Modifier.size(250.dp),

        )

        Column(
            horizontalAlignment = Alignment.Start,
            modifier = Modifier.fillMaxSize()
        ) {
            Text(text = user?.displayName ?: "no name", style = MaterialTheme.typography.titleMedium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.widthIn(max = 140.dp)
            )
            Text(user?.reputation.toString())
            Text("Location")
            Text(user?.websiteUrl ?: "unknown website")

        }


        FollowUnfollowButton(
            user?.isFollowed ?: false,
            onClick = {
                usersViewModel.toggleFollow(user!!)
            }
        )
    }
}

