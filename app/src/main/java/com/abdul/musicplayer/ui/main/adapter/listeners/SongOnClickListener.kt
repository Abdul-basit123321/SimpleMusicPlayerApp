package com.abdul.musicplayer.ui.main.adapter.listeners

import com.abdul.musicplayer.data.model.Song

interface SongOnClickListener {
    fun onClickListener(song:Song,position:Int)
}