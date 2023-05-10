package com.example.composition.presentation

import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.composition.R
import com.example.composition.databinding.FragmentGameBinding
import com.example.composition.domain.entity.GameResult
import com.example.composition.domain.entity.Level

class GameFragment: Fragment() {

    private lateinit var level: Level
    private lateinit var viewModel: GameFragmentViewModel

    private var _binding: FragmentGameBinding? = null
    private val binding: FragmentGameBinding
        get() = _binding ?: throw RuntimeException("FragmentGameBinding == null")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parseArgs()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGameBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[GameFragmentViewModel::class.java]
        loadingSetting()
        loadingNextQuestion()
        observeViewModelForQuestion()
        viewModel.minCountOfRightAnswers.observe(viewLifecycleOwner){
            val countAnswer = getString(R.string.progress_answer, START_COUNT, it.toString())
            binding.tvAnswerProgress.text = countAnswer
        }
        binding.tvOption1.setOnClickListener {
            loadingNextQuestion()
        }
        binding.tvOption2.setOnClickListener {
            loadingNextQuestion()
        }
        binding.tvOption3.setOnClickListener {
            loadingNextQuestion()
        }
        binding.tvOption4.setOnClickListener {
            loadingNextQuestion()
        }
        binding.tvOption5.setOnClickListener {
            loadingNextQuestion()
        }
        binding.tvOption6.setOnClickListener {
            loadingNextQuestion()
        }
    }

    private fun observeViewModelForQuestion() {
        viewModel.sumOfNumber.observe(viewLifecycleOwner) {
            binding.tvSum.text = it.toString()
        }
        viewModel.visibleNumber.observe(viewLifecycleOwner) {
            binding.tvLeftNumber.text = it.toString()
        }
        viewModel.optionsNumber.observe(viewLifecycleOwner) {
            with(binding) {
                tvOption1.text = it[0].toString()
                tvOption2.text = it[1].toString()
                tvOption3.text = it[2].toString()
                tvOption4.text = it[3].toString()
                tvOption5.text = it[4].toString()
                tvOption6.text = it[5].toString()
            }
        }
    }

    private fun loadingSetting(){
        viewModel.getAndParseGameSettings(level)
    }

    private fun loadingNextQuestion(){
        viewModel.getQuestion(level)
    }

    private fun launchGameFinishedFragment(gameResult: GameResult){
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.main_container, GameFinishedFragment.newInstance(gameResult))
            .addToBackStack(null)
            .commit()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun parseArgs(){
        requireArguments().parcelable<Level>(KEY_LEVEL)?.let {
            level = it
        }
    }

    private inline fun <reified T : Parcelable> Bundle.parcelable(key: String): T? = when {
        Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU -> getParcelable(key, T::class.java)
        else -> @Suppress("DEPRECATION") getParcelable(key) as? T
    }

    companion object{

        private const val KEY_LEVEL = "level"
        private const val START_COUNT = "0"
        const val NAME = "GameFragment"

        fun newInstance(level: Level): GameFragment{
            return GameFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(KEY_LEVEL, level)
                }
            }
        }
    }
}