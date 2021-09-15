package com.abdul.musicplayer.data.api

import com.abdul.musicplayer.data.model.MusicResponse
import com.rx2androidnetworking.Rx2AndroidNetworking
import io.reactivex.Single

class ApiServiceImpl:ApiService {

    override fun getMusics(artistName:String): Single<MusicResponse> {
        var dynamicUrl = String.format("https://itunes.apple.com/search?term=%s&entity=musicVideo",artistName)
        return Rx2AndroidNetworking.get(dynamicUrl)
            .build()
            .getObjectSingle(MusicResponse::class.java)
    }
}