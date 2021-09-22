package com.example.tetrainingandroid.ui.media.adapter.video

import com.example.tetrainingandroid.data.model.Youtube

class YoutubeItemClickListener(val listener: (Youtube) -> Unit) {
    fun onClick(youtube: Youtube) = listener(youtube)
}