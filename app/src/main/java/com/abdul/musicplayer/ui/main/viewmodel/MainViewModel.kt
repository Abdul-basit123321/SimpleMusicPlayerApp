package com.abdul.musicplayer.ui.main.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.abdul.musicplayer.data.api.ApiServiceImpl
import com.abdul.musicplayer.data.model.Song
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MainViewModel() : ViewModel() {

    private val compositeDisposable = CompositeDisposable()
    private val apiHelper = ApiServiceImpl()
    private val songs = MutableLiveData<List<Song>>()


    public fun getMusic(artistName: String): LiveData<List<Song>> {
        compositeDisposable.add(
            apiHelper.getMusics(artistName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ musicList ->
                    songs.postValue(musicList.results)
                }, { throwable ->
                    songs.postValue(arrayListOf())
                })
        )
        return songs
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}