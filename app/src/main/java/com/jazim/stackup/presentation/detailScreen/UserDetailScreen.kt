package com.jazim.stackup.presentation.detailScreen

import android.text.Html
import android.widget.Space
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLinkStyles
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withLink
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.rememberAsyncImagePainter
import com.jazim.stackup.R
import com.jazim.stackup.domain.model.Badges
import com.jazim.stackup.domain.model.User
import com.jazim.stackup.presentation.ui.components.FollowUnfollowButton
import com.jazim.stackup.presentation.ui.components.UserCard
import com.jazim.stackup.presentation.ui.theme.StackupProjectTheme
import java.text.NumberFormat
import java.util.Locale


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
        Row {
            Icon(
                painter = painterResource(R.drawable.crown),
                contentDescription = "Reputation Crown icon",
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(18.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = NumberFormat.getNumberInstance(Locale.getDefault())
                    .format(user.reputation),
                style = MaterialTheme.typography.bodyMedium
            )
        }
        Text("Location: ${Html.fromHtml(user.location, Html.FROM_HTML_MODE_LEGACY)}")

        val uriHandler = LocalUriHandler.current

        Text(
            buildAnnotatedString {
                append("Website: ")
                withLink(
                    LinkAnnotation.Url(
                        user.websiteUrl!!,
                        TextLinkStyles(style = SpanStyle(color = Color.Blue))
                    )
                ) {
                    append(user.websiteUrl)
                }
            }
        )

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