package com.jazim.stackup.presentation.users

import androidx.compose.animation.AnimatedContent
import coil3.compose.rememberAsyncImagePainter
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material.icons.filled.PersonRemove
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.jazim.stackup.R
import com.jazim.stackup.domain.model.User
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
            .padding(8.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Row {
                Image(
                    painter = rememberAsyncImagePainter(user.profileImage),
                    contentDescription = "display image",
                    modifier = Modifier
                        .size(48.dp)
                        .background(
                            MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f),
                            shape = RoundedCornerShape(24.dp)
                        )
                )

                Spacer(modifier = Modifier.width(12.dp))

                Column {
                    Text(text = user.displayName, style = MaterialTheme.typography.titleMedium)
                    Text(text = "Reputation: ${NumberFormat.getNumberInstance(Locale.getDefault()).format(user.reputation)}", style = MaterialTheme.typography.bodyMedium)
                }
            }
            Button(
                onClick = { onFollowClick(user.id) },
                modifier = Modifier.width(120.dp),
                shape = RoundedCornerShape(25),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (user.isFollowed) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.tertiary
                ),
            ) {

                val text = if (user.isFollowed) stringResource(R.string.unfollow) else stringResource(R.string.follow)

                AnimatedContent(
                    targetState = text
                ) { targetText ->
                    Text(targetText)
                }
                Spacer(Modifier.size(4.dp))

                Icon(
                    imageVector = if (user.isFollowed) Icons.Filled.PersonRemove else Icons.Filled.PersonAdd,
                    contentDescription = stringResource(R.string.follow_unfollow_button),
                    tint = Color.White,
                    modifier = Modifier.size(18.dp)
                )

            }
        }
    }
}
