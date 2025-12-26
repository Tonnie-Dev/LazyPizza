@file:OptIn(FlowPreview::class)

package com.tonyxlab.lazypizza.presentation.screens.auth

import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.viewModelScope
import com.tonyxlab.lazypizza.domain.CountdownTimer
import com.tonyxlab.lazypizza.presentation.core.base.BaseViewModel
import com.tonyxlab.lazypizza.presentation.screens.auth.handling.AuthActionEvent
import com.tonyxlab.lazypizza.presentation.screens.auth.handling.AuthUiEvent
import com.tonyxlab.lazypizza.presentation.screens.auth.handling.AuthUiState
import com.tonyxlab.lazypizza.utils.isValidInternationalPhone
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach

typealias AuthBaseViewModel = BaseViewModel<AuthUiState, AuthUiEvent, AuthActionEvent>

class AuthViewModel : AuthBaseViewModel() {

    private var timer: CountdownTimer? = null

    override val initialState: AuthUiState
        get() = AuthUiState()

    init {
        observePhoneTextField()
        observeOtpInputField()
    }

    override fun onEvent(event: AuthUiEvent) {
        when (event) {
            AuthUiEvent.ContinueToLoginIn -> {
                switchScreenSection(authScreenStep = AuthUiState.AuthScreenStep.OtpInputStep)
                sendActionEvent(actionEvent = AuthActionEvent.NavigateToOtp)
            }

            AuthUiEvent.ContinueWithoutLogin -> {
                sendActionEvent(actionEvent = AuthActionEvent.NavigateBackToMenu)
            }

            AuthUiEvent.ConfirmOtp -> {
                startTimer()
            }


        }
    }

    private fun observePhoneTextField() {

        val textFlow = snapshotFlow {
            currentState.phoneInputState.textFieldState.text.toString()
        }

        textFlow
                .map { it.trim() }
                .debounce(300)
                .distinctUntilChanged()
                .onEach { input ->
                    val isValidNumber = input.isValidInternationalPhone()
                    updateState {
                        it.copy(
                                phoneInputState = currentState.phoneInputState.copy(
                                        continueEnabled = isValidNumber
                                )
                        )
                    }
                }
                .launchIn(viewModelScope)
    }

    private fun observeOtpInputField() {

        val textFlow = snapshotFlow {
            currentState.otpInputState.textFieldState.text.toString()
        }

        textFlow
                .map { rawText ->

                    rawText.filter { it.isDigit() }
                            .take(6)
                }

                .distinctUntilChanged()
                .onEach { sanitized ->

                    updateState {
                        it.copy(
                                otpInputState = currentState.otpInputState.copy(
                                        confirmEnabled = sanitized.length == 6
                                )
                        )

                    }
                    if (sanitized != currentState.otpInputState.textFieldState.text.toString()) {
                        currentState.otpInputState.textFieldState.edit {
                            replace(0, length, sanitized)
                        }
                    }
                }
                .launchIn(viewModelScope)
    }

    private fun switchScreenSection(authScreenStep: AuthUiState.AuthScreenStep) {

        updateState {
            it.copy(
                    authScreenStep = authScreenStep
            )
        }

    }

    private fun startTimer() {
        timer?.stop() // Clean up the old timer
        timer = CountdownTimer().also { timer ->

            timer.start()
            timer.remainingSecs.onEach { secs ->

                updateState {
                    it.copy(
                            otpInputState = currentState.otpInputState.copy(
                                    secondsRemaining = secs,
                                    resend = secs ==0
                            )
                    )
                }
            }.launchIn(viewModelScope)
        }

    }
}