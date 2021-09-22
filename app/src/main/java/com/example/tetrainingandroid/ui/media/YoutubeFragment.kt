package com.example.tetrainingandroid.ui.media

import android.os.Bundle
import android.view.View
import androidx.fragment.app.commit
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.tetrainingandroid.R
import com.example.tetrainingandroid.architecture.BaseFragment
import com.example.tetrainingandroid.config.Config
import com.example.tetrainingandroid.data.model.Youtube
import com.example.tetrainingandroid.extensions.toast
import com.example.tetrainingandroid.ui.media.adapter.video.YoutubeHorizontalAdapter
import com.example.tetrainingandroid.ui.media.adapter.video.YoutubeItemClickListener
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerCompatFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.youtube_fragment.*
import kotlinx.coroutines.*
import javax.inject.Inject

@AndroidEntryPoint
class YoutubeFragment: BaseFragment(R.layout.youtube_fragment), YouTubePlayer.OnInitializedListener {

    @Inject lateinit var youtubeAdapter: YoutubeHorizontalAdapter

    private val args: YoutubeFragmentArgs by navArgs()
    private val viewModel: YoutubeViewModel by viewModels()
    private var player: YouTubePlayer? = null

    private lateinit var currentVideo: Youtube
    private val videos = mutableListOf<Youtube>()

    private val listScope = CoroutineScope(Dispatchers.Default)

    private val onItemClickListener = YoutubeItemClickListener { youtube ->
        if (currentVideo == youtube) return@YoutubeItemClickListener
        val index = videos.indexOf(youtube)
        youtubeAdapter.setSelected(index)
        currentVideo = youtube
        player?.loadVideos(videos.map { it.key }, index, 0)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        currentVideo = args.youtube
        viewModel.setMovie(args.movieId)
        initView()
        initYouTubePlayer()
        observerVideos()
    }

    private fun initView() {
        initToolbar()
        setupRvTrailer()
    }

    private fun setupRvTrailer() {
        youtubeAdapter.setListener(onItemClickListener)
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
        toolbar?.setNavigationIcon(R.drawable.ic_back)
        toolbar?.setNavigationOnClickListener {
            activity?.onBackPressed()
        }
        toolbar?.title = currentVideo.name ?: ""
    }

    private fun observerVideos() {
        viewModel.videos.observe(viewLifecycleOwner, { data ->
            if (data.isNullOrEmpty()) {
                nestedScrollView?.visibility = View.GONE
                player?.loadVideo(currentVideo.key)
            } else {
                listScope.launch {
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
            player?.loadVideos(videos.map { it.key }, 0, 0)
        }
    }

    override fun onInitializationSuccess(p0: YouTubePlayer.Provider?, p1: YouTubePlayer?, p2: Boolean) = setupPlayer(p1)

    override fun onInitializationFailure(p0: YouTubePlayer.Provider?, p1: YouTubeInitializationResult?) = toast("Video play failed")

    private fun setupPlayer(p1: YouTubePlayer?) {
        player = p1
        player?.setPlayerStateChangeListener(object: YouTubePlayer.PlayerStateChangeListener {

            override fun onLoading() {}

            override fun onLoaded(p0: String?) {}

            override fun onAdStarted() {}

            override fun onVideoStarted() = setTitle()

            override fun onVideoEnded() = playNext()

            override fun onError(p0: YouTubePlayer.ErrorReason?) = playNext()
        })
    }

    private fun setTitle() {
        toolbar?.title = currentVideo.name
    }

    private fun playNext() {
        if (player?.hasNext() == true) {
            val nextIndex = nextIndex()
            youtubeAdapter.setSelected(nextIndex)
            currentVideo = videos[nextIndex]
            player?.next()
        }
    }

    private fun nextIndex() = (videos.indexOf(currentVideo) + 1) % videos.size

    override fun onDestroy() {
        super.onDestroy()
        listScope.cancel()
    }
}