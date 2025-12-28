@file:OptIn(FlowPreview::class)

package com.tonyxlab.lazypizza.presentation.screens.auth

import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.viewModelScope
import com.tonyxlab.lazypizza.domain.CountdownTimer
import com.tonyxlab.lazypizza.domain.firebase.AuthState
import com.tonyxlab.lazypizza.domain.repository.AuthRepository
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
import timber.log.Timber

typealias AuthBaseViewModel = BaseViewModel<AuthUiState, AuthUiEvent, AuthActionEvent>

class AuthViewModel(
    private val authRepository: AuthRepository
) : AuthBaseViewModel() {

    private var timer: CountdownTimer? = null
    private var verificationId: String? = null

    override val initialState: AuthUiState
        get() = AuthUiState()

    init {
        observePhoneTextField()
        observeOtpInputField()
        observeAuthState()
    }

    override fun onEvent(event: AuthUiEvent) {
        when (event) {
            is AuthUiEvent.ContinueToLoginIn -> {

                val phoneNumber = currentState.phoneInputState.textFieldState.text.toString()

                authRepository.startLogin(
                        phoneNumber = phoneNumber,
                        activity = event.activity
                )
                switchScreenSection(authScreenStep = AuthUiState.AuthScreenStep.OtpInputStep)
            }

            AuthUiEvent.ContinueWithoutLogin -> {
                sendActionEvent(actionEvent = AuthActionEvent.NavigateBackToMenu)
            }

            AuthUiEvent.ConfirmOtp -> {

                val otpCode = currentState.otpInputState.textFieldState.text
                        .filter(Char::isDigit)
                        .take(6)
                        .toString()
                val id = verificationId ?: return
                authRepository.verifyCode(otpCode = otpCode, verificationId = id)
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

        snapshotFlow { currentState.otpInputState.textFieldState.text.toString() }
                .distinctUntilChanged()
                .onEach { text ->
                    updateState {
                        it.copy(
                                otpInputState = it.otpInputState.copy(
                                        confirmEnabled = text.length == 6,
                                        error = false
                                )
                        )
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
        timer?.stop()
        timer = CountdownTimer().also { timer ->

            timer.start()
            timer.remainingSecs.onEach { secs ->

                updateState {
                    it.copy(
                            otpInputState = currentState.otpInputState.copy(
                                    secondsRemaining = secs,
                                    resend = secs == 0
                            )
                    )
                }
            }
                    .launchIn(viewModelScope)
        }

    }

    private fun observeAuthState() {

        authRepository.authState.onEach { authState ->
            when (authState) {
                AuthState.Unauthenticated -> Unit
                is AuthState.OtpCodeSent -> {
                    verificationId = authState.verificationId
                    Timber.tag("AuthViewModel")
                            .i("OtpCode sent: $verificationId")
                }

                AuthState.Loading -> {
                    updateState { it.copy(isLoading = true) }
                }

                AuthState.Authenticated -> {
                    updateState {
                        it.copy(
                                isLoading = false, isSignedIn = true
                                /* otpInputState = currentState.otpInputState.copy(
                                         error = true,
                                         resend = true
                                 )*/
                        )
                    }
                    sendActionEvent(actionEvent = AuthActionEvent.NavigateBackToMenu)
                }

                is AuthState.Error -> {
                    updateState {
                        it.copy(
                                otpInputState = currentState.otpInputState.copy(
                                        error = true,
                                        resend = true
                                )
                        )
                    }
                }
            }

        }
                .launchIn(viewModelScope)
    }
}