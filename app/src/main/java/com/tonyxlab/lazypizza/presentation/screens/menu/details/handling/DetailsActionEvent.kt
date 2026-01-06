package com.tonyxlab.lazypizza.presentation.screens.menu.details.handling

import androidx.annotation.StringRes
import com.tonyxlab.lazypizza.R
import com.tonyxlab.lazypizza.presentation.core.base.handling.ActionEvent
import com.tonyxlab.lazypizza.presentation.core.base.handling.UiEvent

sealed interface DetailsActionEvent : ActionEvent {

    data object NavigateBackToMenu : DetailsActionEvent
    data class ShowSnackbar(
        @StringRes
        val messageRes: Int,
        @StringRes
        val actionLabelRes: Int = R.string.blank_text,
        val event: UiEvent? = null

    ) : DetailsActionEvent
}