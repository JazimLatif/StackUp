package com.jazim.stackup.presentation.ui.components

import coil3.compose.rememberAsyncImagePainter
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jazim.stackup.R
import com.jazim.stackup.domain.model.User
import com.jazim.stackup.presentation.ui.theme.StackupProjectTheme
import java.text.NumberFormat
import java.util.Locale

@Composable
fun UserCard(
    user: User,
    onFollowClick: (Int) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Row {
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
                        .size(48.dp)
                        .clip(RoundedCornerShape(6.dp))
                )

                Spacer(modifier = Modifier.width(12.dp))

                Column {

                    Text(text = user.displayName, style = MaterialTheme.typography.titleMedium,
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
                }
            }
            FollowUnfollowButton(
                isFollowed = user.isFollowed,
                { onFollowClick(user.id) }
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun ProfileImagePreview() {
    StackupProjectTheme {
        UserCard(
            User(
                id = 1,
                displayName = "Jeff",
                profileImage = "https://i.sstatic.net/I4fiW.jpg?s=256",
                reputation = 2666,
                location = "",
                websiteUrl = "",
                isFollowed = true
            ),{}
        )
    }
}
