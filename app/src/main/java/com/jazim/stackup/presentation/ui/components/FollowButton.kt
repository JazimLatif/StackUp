package com.jazim.stackup.presentation.ui.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material.icons.filled.PersonRemove
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.jazim.stackup.R

@Composable
fun FollowUnfollowButton(
    isFollowed: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val buttonColors = when (isFollowed) {
        true -> {
            ButtonDefaults.outlinedButtonColors(
                contentColor = MaterialTheme.colorScheme.primary
            )
        }
        false-> {
            ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            )
        }
    }

    val border = when (isFollowed) {
        true -> BorderStroke(1.dp, MaterialTheme.colorScheme.primary)
        false -> null
    }

    Button(
        onClick = onClick,
        modifier = modifier.width(140.dp),
        shape = RoundedCornerShape(6.dp),
        colors = buttonColors,
        border = border
    ) {
        AnimatedContent(targetState = isFollowed, label = "follow-unfollow") { followed ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    when (followed) {
                        true -> stringResource(R.string.unfollow)
                        false -> stringResource(R.string.follow)
                    }
                )
                Spacer(Modifier.size(4.dp))
                Icon(
                    imageVector = when (followed) {
                        true -> Icons.Filled.PersonRemove
                        false -> Icons.Filled.PersonAdd
                    },
                    contentDescription = "Icon to follow or unfollow user",
                    tint = when (followed) {
                        true -> MaterialTheme.colorScheme.primary
                        false -> MaterialTheme.colorScheme.onPrimary
                    },
                    modifier = Modifier.size(18.dp)
                )

            }
        }
    }
}