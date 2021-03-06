package com.example.tetrainingandroid.ui.media

import android.os.Bundle
import android.view.View
import androidx.fragment.app.commit
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.example.tetrainingandroid.R
import com.example.tetrainingandroid.architecture.LoadingDataFragment
import com.example.tetrainingandroid.config.Config
import com.example.tetrainingandroid.data.model.Youtube
import com.example.tetrainingandroid.extensions.toast
import com.example.tetrainingandroid.ui.media.adapter.video.YoutubeSelectableAdapter
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerCompatFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.youtube_fragment.*
import kotlinx.coroutines.*
import javax.inject.Inject

@AndroidEntryPoint
class YoutubeFragment: LoadingDataFragment<YoutubeViewModel>(R.layout.youtube_fragment), YouTubePlayer.OnInitializedListener {

    @Inject lateinit var youtubeAdapter: YoutubeSelectableAdapter

    private val args: YoutubeFragmentArgs by navArgs()
    override val viewModel: YoutubeViewModel by viewModels()
    private var player: YouTubePlayer? = null

    private lateinit var currentVideo: Youtube
    private val videos = mutableListOf<Youtube>()

    private var hasData = false
    private var isPlayingBeforeInvisible = false

    override fun onViewCreatedFirstTime(view: View, savedInstanceState: Bundle?) {
        super.onViewCreatedFirstTime(view, savedInstanceState)
        currentVideo = args.youtube
        savedInstanceState?.putInt("movieId", args.movieId)
        initView()
        initYouTubePlayer()
        observerVideos()
    }

    private fun initView() {
        initToolbar()
        setupRvTrailer()
    }

    private fun setupRvTrailer() {
        youtubeAdapter.setListener { youtube ->
            if (currentVideo == youtube) return@setListener
            playAt(videos.indexOf(youtube))
        }
        rvTrailer?.adapter = youtubeAdapter
    }

    private fun initYouTubePlayer() {
        val fragment = YouTubePlayerCompatFragment.newInstance()
        fragment.initialize(Config.YOUTUBE_API_KEY, this)
        childFragmentManager.commit {
            setReorderingAllowed(true)
            add(R.id.container, fragment ,"youtube_tag")
        }
    }

    private fun initToolbar() {
        toolbar?.title = currentVideo.name ?: ""
    }

    private fun observerVideos() {
        viewModel.videos.observe(viewLifecycleOwner, { data ->
            hasData = true
            if (data.isNullOrEmpty()) {
                nestedScrollView?.visibility = View.GONE
                videos.clear()
                videos.add(currentVideo)
                initLoadVideos()
            } else {
                lifecycleScope.launch(Dispatchers.Default) {
                    filterListVideo(data)
                    withContext(Dispatchers.Main) {
                        submitAdapter()
                    }
                }

            }
        })
    }

    private fun filterListVideo(data: List<Youtube>) {
        videos.apply {
            clear()
            add(currentVideo)
            addAll(data.filter { it.key != currentVideo.key })
        }
    }

    private fun submitAdapter() {
        youtubeAdapter.setSelected(0)
        youtubeAdapter.submitList(videos) {
            initLoadVideos()
        }
    }

    override fun onInitializationSuccess(
        p0: YouTubePlayer.Provider?,
        p1: YouTubePlayer?, p2: Boolean
    ) = setupPlayer(p1)

    override fun onInitializationFailure(
        p0: YouTubePlayer.Provider?,
        p1: YouTubeInitializationResult?
    ) = toast("Video play failed")

    private fun setupPlayer(p1: YouTubePlayer?) {
        player = p1
        initLoadVideos()
        player?.addFullscreenControlFlag(YouTubePlayer.FULLSCREEN_FLAG_CONTROL_ORIENTATION)
        player?.addFullscreenControlFlag(YouTubePlayer.FULLSCREEN_FLAG_ALWAYS_FULLSCREEN_IN_LANDSCAPE)
        player?.setPlaylistEventListener(object: YouTubePlayer.PlaylistEventListener {
            override fun onPrevious() = playPrevious()

            override fun onNext() = playNext()

            override fun onPlaylistEnded() {}
        })
    }

    private fun onCurrentVideoChanged(index: Int) {
        currentVideo = videos[index]
        youtubeAdapter.setSelected(index)
        setTitle()
        player?.loadVideos(videos.map { it.key }, index, 0)
    }

    private fun setTitle() {
        toolbar?.title = currentVideo.name
    }

    private fun playNext() = onCurrentVideoChanged(nextIndex())
    private fun playPrevious() = onCurrentVideoChanged(previousIndex())
    private fun playAt(index: Int) = onCurrentVideoChanged(index)

    private fun nextIndex() = (videos.indexOf(currentVideo) + 1) % videos.size

    private fun previousIndex(): Int {
        val currentIndex = videos.indexOf(currentVideo)
        return if (currentIndex > 0)  {
            currentIndex - 1
        } else {
            videos.size -1
        }
    }

    private fun initLoadVideos() {
        if (hasData && player != null) {
            player?.loadVideos(videos.map { it.key }, 0, 0)
        }
    }

    override fun onResume() {
        super.onResume()
        if (isPlayingBeforeInvisible) {
            resumePlayer()
        }
    }

    override fun onPause() {
        super.onPause()
        if (player?.isPlaying == true) {
            isPlayingBeforeInvisible = true
        }
    }

    private fun resumePlayer() {
        lifecycleScope.launch(Dispatchers.Default) {
            delay(200)
            withContext(Dispatchers.Main) {
                player?.play()
            }
        }
        isPlayingBeforeInvisible = false
    }
}