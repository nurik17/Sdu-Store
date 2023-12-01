package com.example.sdustore.ui.home

import android.os.Handler
import android.os.Looper
import android.widget.ProgressBar
import com.example.sdustore.base.BaseFragment
import com.example.sdustore.data.extensions.getParcelableArgumentCompat
import com.example.sdustore.data.entity.MainRecyclerData
import com.example.sdustore.databinding.HistoryPageFragmentBinding

class HistoryPageFragment : BaseFragment<HistoryPageFragmentBinding>(HistoryPageFragmentBinding::inflate) {

    private lateinit var handler: Handler
    private lateinit var progressBars: List<ProgressBar>
    private var currentProgressStatus = 0

    override fun onBindView() {
        super.onBindView()
        logicProgressBar()
        startProgressBar()
        val mainRecyclerData: MainRecyclerData? = getParcelableArgumentCompat("mainRecyclerData")
    }

    private fun logicProgressBar() {
        progressBars = listOf(
            binding.progressBar,
            binding.progressBar2,
            binding.progressBar3
        )
        handler = Handler(Looper.getMainLooper())

        startProgressBar()

        handler.postDelayed(object : Runnable {
            override fun run() {
                if (currentProgressStatus < progressBars.size - 1) {
                    currentProgressStatus++
                    startProgressBar()
                } else {
                    currentProgressStatus = 0
                    // Start the next progress bar after a short delay
                    handler.postDelayed({ startProgressBar() }, 500) // Adjust the delay here
                }
            }
        }, 10000)
    }

    private fun startProgressBar() {
        if (currentProgressStatus < progressBars.size) {
            val progressBar = progressBars[currentProgressStatus]

            handler.postDelayed(object : Runnable {
                private var progressStatus = 0

                override fun run() {
                    progressStatus++
                    progressBar.progress = progressStatus

                    if (progressStatus < progressBar.max) {
                        handler.postDelayed(this, 100)
                    } else {
                        // Reset progress bar when it's complete
                        progressBar.progress = 0
                        // Increment the currentProgressStatus for the next progress bar
                        currentProgressStatus++
                        // Start the next progress bar after a short delay
                        handler.postDelayed({ startProgressBar() }, 500) // Adjust the delay here
                    }
                }
            }, 100)
        }
    }
}
