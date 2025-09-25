package com.jazim.stackup.presentation.detailScreen

import android.widget.Space
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.rememberAsyncImagePainter
import com.jazim.stackup.R
import com.jazim.stackup.domain.model.Badges
import com.jazim.stackup.domain.model.User
import com.jazim.stackup.presentation.ui.components.FollowUnfollowButton
import com.jazim.stackup.presentation.ui.components.UserCard
import com.jazim.stackup.presentation.ui.theme.StackupProjectTheme


@Composable
fun UserDetailScreen(
    user: User,
    onFollowed: (Int)-> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceAround,
        modifier = Modifier
            .fillMaxHeight(0.75f)
            .fillMaxWidth()
    ) {

        // since profileImage is defaulted to "" if the profileImage was null in UserDto (via the mapper)
        val painter = if (user.profileImage.isNotEmpty()) {
            rememberAsyncImagePainter(user.profileImage)
        } else {
            painterResource(R.drawable.placeholder)
        }

        Image(
            painter = painter,
            contentDescription = "display image",
            modifier = Modifier
                .size(250.dp)
                .clip(RoundedCornerShape(6.dp))
        )


        Text(
            text = user.displayName, style = MaterialTheme.typography.titleMedium,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.widthIn(max = 140.dp)
        )
        Text(user.reputation.toString())
        Text(user.location ?: "Unknown Location")
        Text(user.websiteUrl ?: "Unknown website")




        FollowUnfollowButton(user.isFollowed, onClick = { onFollowed(user.id) })

    }
}

@Preview(showBackground = true)
@Composable
fun PreviewUserDetailScreen() {
    StackupProjectTheme {
        UserDetailScreen(
            user = User(
                id = 1,
                displayName = "Jeff",
                profileImage = "",
                reputation = 2666,
                location = "Nepal",
                websiteUrl = "www.google.com",
                badges = Badges(1, 2, 3),
                isFollowed = true
            ),
            onFollowed = { TODO() }
        )
    }
}
//
//@Preview(showBackground = true)
//@Composable
//fun ProfileImagePreview() {
//    StackupProjectTheme {
//        UserCard(
//            User(
//                id = 1,
//                displayName = "Jeff",
//                profileImage = "",
//                reputation = 2666,
//                location = "",
//                websiteUrl = "",
//                badges = Badges(1, 2, 3),
//                isFollowed = true
//            ),
//            onFollowClick = {},
//            onCardClick = {  },
//        )
//    }
//}