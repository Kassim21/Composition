package com.example.composition.presentation

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.composition.R
import com.example.composition.domain.entity.GameResult

@BindingAdapter("requiredAnswers")
fun bindRequiredAnswer(textView: TextView, count: Int){
    textView.text = String.format(
        textView.context.getString(R.string.right_answer_count),
        count
    )
}

@BindingAdapter("countRightAnswers")
fun bindCountRightAnswers(textView: TextView, count: Int){
    textView.text = String.format(
        textView.context.getString(R.string.score),
        count
    )
}

@BindingAdapter("requiredPercent")
fun bindRequiredPercent(textView: TextView, count: Int){
    textView.text = String.format(
        textView.context.getString(R.string.required_percentage),
        count
    )
}

@BindingAdapter("percentRightAnswer")
fun bindPercentRightAnswer(textView: TextView, gameResult: GameResult){
    textView.text = String.format(
        textView.context.getString(R.string.score_percentage),
        getPercent(gameResult)
    )
}

private fun getPercent(gameResult: GameResult): String {
    return ((gameResult.countOfRightAnswers / gameResult.countOfQuestions.toDouble()) * 100)
        .toInt()
        .toString()
}

@BindingAdapter("setImage")
fun bindSetImage(imageView: ImageView, winner: Boolean){
    imageView.setImageResource(setSmileReaction(winner))
}

private fun setSmileReaction(winner: Boolean): Int{
    return if (winner) {
        R.drawable.smile_face
    } else {
        R.drawable.sad_face
    }
}