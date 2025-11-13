package com.tonyxlab.lazypizza.presentation.core.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.tonyxlab.lazypizza.R
import com.tonyxlab.lazypizza.presentation.core.utils.spacing
import com.tonyxlab.lazypizza.presentation.theme.Body1Regular
import com.tonyxlab.lazypizza.presentation.theme.Body3Medium
import com.tonyxlab.lazypizza.presentation.theme.LazyPizzaTheme
import com.tonyxlab.lazypizza.presentation.theme.TextSecondary8

@Composable
fun AppTopBarOne(
    phoneNumber: String,
    onCallClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
            modifier = modifier
                    .statusBarsPadding()
                    .height(MaterialTheme.spacing.spaceExtraLarge)
                    .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
                modifier = Modifier,
                horizontalArrangement = Arrangement.spacedBy(
                        space = MaterialTheme.spacing.spaceDoubleDp * 3
                )
        ) {

            Image(
                    modifier = Modifier.size(MaterialTheme.spacing.spaceTen * 2),
                    painter = painterResource(R.drawable.icon_logo),
                    contentDescription = stringResource(R.string.cds_text_pizza_logo)
            )

            Text(
                    text = stringResource(R.string.topbar_text_lazy_pizza),
                    style = MaterialTheme.typography.Body3Medium.copy(
                            color = MaterialTheme.colorScheme.onSurface,
                            fontWeight = FontWeight.W700
                    )
            )
        }
        Row(
                modifier = Modifier
                        .weight(1f)
                        .clickable {

                            onCallClick()
                        },
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                    modifier = Modifier.size(MaterialTheme.spacing.spaceMedium),
                    painter = painterResource(R.drawable.icon_phone),
                    contentDescription = stringResource(R.string.cds_text_phone),
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.width(MaterialTheme.spacing.spaceExtraSmall))
            Text(
                    text = phoneNumber,
                    style = MaterialTheme.typography.Body1Regular.copy(
                            color = MaterialTheme.colorScheme.onSurface
                    )
            )
        }
    }
}

@Composable
fun AppTopBarTwo(modifier: Modifier = Modifier) {

    Row(
            modifier = modifier
                    .fillMaxWidth()
                    .height(MaterialTheme.spacing.spaceExtraLarge)
                    //   .padding(horizontal = MaterialTheme.spacing.spaceTen)
                    .padding(vertical = MaterialTheme.spacing.spaceSmall),
            horizontalArrangement = Arrangement.Start
    ) {

        Box(
                modifier = Modifier
                        .clip(CircleShape)
                        .background(color = TextSecondary8)
                        .size(MaterialTheme.spacing.spaceDoubleDp * 22)
                        .padding(all = MaterialTheme.spacing.spaceSmall),
                contentAlignment = Alignment.Center
        ) {

            Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = stringResource(id = R.string.cds_text_back),
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@PreviewLightDark
@Composable
private fun AppTopBar_Preview() {

    LazyPizzaTheme {

        Column(

                modifier = Modifier
                        .background(MaterialTheme.colorScheme.background)
                        .fillMaxSize()
                        .padding(MaterialTheme.spacing.spaceMedium)
        ) {

            AppTopBarOne(phoneNumber = "0723 445 813", onCallClick = {})
            AppTopBarTwo()

        }
    }
}
