package com.example.composition.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.composition.data.GameRepositoryImpl
import com.example.composition.domain.entity.Level
import com.example.composition.domain.usecases.GenerateQuestionUseCase
import com.example.composition.domain.usecases.GetGameSettingsUseCase

class GameFragmentViewModel : ViewModel() {

    private val repository = GameRepositoryImpl

    private val generatedQuestionUseCase = GenerateQuestionUseCase(repository)
    private val getGameSettingsUseCase = GetGameSettingsUseCase(repository)

    private var countAnswer = 0
    private var countRightAnswer = 0

    private var _sumOfNumber = MutableLiveData<Int>()
    val sumOfNumber: LiveData<Int> get() = _sumOfNumber

    private var _visibleNumber = MutableLiveData<Int>()
    val visibleNumber: LiveData<Int> get() = _visibleNumber

    private var _optionsNumber = MutableLiveData<List<Int>>()
    val optionsNumber: LiveData<List<Int>> get() = _optionsNumber

    private var _minCountOfRightAnswers = MutableLiveData<Int>()
    val minCountOfRightAnswers: LiveData<Int> get() = _minCountOfRightAnswers

    private var _minPercentOfRightAnswers = MutableLiveData<Int>()
    val minPercentOfRightAnswers: LiveData<Int> get() = _minPercentOfRightAnswers

    private var _gameTimeInSeconds = MutableLiveData<Int>()
    val gameTimeInSeconds: LiveData<Int> get() = _gameTimeInSeconds

    private var _checkAnswer = MutableLiveData<Boolean>()
    val checkAnswer: LiveData<Boolean> get() = _checkAnswer

    fun getQuestion(level: Level) {
        val gameSettings = getGameSettingsUseCase(level)
        val question = generatedQuestionUseCase(gameSettings.maxSumValue)
        with(question) {
            _sumOfNumber.value = sum
            _visibleNumber.value = visibleNumber
            _optionsNumber.value = options
        }
    }

    fun getAndParseGameSettings(level: Level){
        val gameSettings = getGameSettingsUseCase(level)
        with(gameSettings){
            _minCountOfRightAnswers.value = minCountOfRightAnswers
            _minPercentOfRightAnswers.value = minPercentOfRightAnswers
            _gameTimeInSeconds.value = gameTimeInSeconds
        }
    }

    fun restartCountsAnswer(){
        countAnswer = 0
        countRightAnswer = 0
    }

    fun restartCheckedAnswer(){
        _checkAnswer.value = false
    }

}
