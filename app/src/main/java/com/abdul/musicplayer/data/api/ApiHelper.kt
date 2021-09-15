package com.abdul.musicplayer.data.api

class ApiHelper(private val apiService: ApiService) {
    fun getMusics(artistName:String) = apiService.getMusics(artistName)
}