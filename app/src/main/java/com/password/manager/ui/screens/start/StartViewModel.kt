package com.password.manager.ui.screens.start

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.password.manager.data.Repository
import com.password.manager.ui.screens.start.model.StartUiAction
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StartViewModel @Inject constructor(
    private val ioDispatcher: CoroutineDispatcher, private val repository: Repository
) : ViewModel() {

    private val _uiState = MutableStateFlow(StartScreenUiState())
    val uiState = _uiState.asStateFlow()

    private val _successfulEntry = Channel<Boolean>()
    val successfulEntry = _successfulEntry.receiveAsFlow()

    init {
        viewModelScope.launch(ioDispatcher) {
            _uiState.update {
                StartScreenUiState(isMasterPasswordSet = repository.isMasterPasswordSet())
            }
        }
    }

    fun onUiAction(action: StartUiAction) {
        when (action) {
            is StartUiAction.EnterMasterPassword -> viewModelScope.launch(ioDispatcher) {
                if (repository.isMasterPasswordSet()) {
                    val correct = repository.isMasterPasswordCorrect(action.password)
                    _uiState.update { uiState.value.copy(isMasterPasswordCorrect = correct) }

                    if (correct) {
                        _successfulEntry.send(true)
                    }
                } else {
                    repository.setMasterPassword(action.password)
                    _successfulEntry.send(true)
                }
            }
            is StartUiAction.UpdateMasterPassword -> viewModelScope.launch {
                _uiState.update { uiState.value.copy(masterPassword = action.text) }
            }
        }
    }
}

data class StartScreenUiState(
    val masterPassword: String = "",
    val isMasterPasswordSet: Boolean? = null,
    val isMasterPasswordCorrect: Boolean = true
)
