package com.abdul.musicplayer.data.api

import com.abdul.musicplayer.data.model.MusicResponse
import io.reactivex.Single

interface ApiService {
    fun getMusics(artistName:String):Single<MusicResponse>
}