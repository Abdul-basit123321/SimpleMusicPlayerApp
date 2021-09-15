package com.abdul.musicplayer.data.model

import com.google.gson.annotations.SerializedName

data class Song(val artistId:Int,val trackName:String,val artistName:String,val collectionName:String,val previewUrl:String,
                @SerializedName("artworkUrl100")
                val imageUrl:String,var isPlaying:Boolean = false)
