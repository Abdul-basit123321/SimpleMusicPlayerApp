package com.abdul.musicplayer.ui.main.view

import android.media.AudioManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.abdul.musicplayer.R
import com.abdul.musicplayer.ui.main.adapter.SongAdapter
import kotlinx.android.synthetic.main.activity_main.*
import android.media.MediaPlayer
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.abdul.musicplayer.data.model.Song
import com.abdul.musicplayer.ui.main.adapter.listeners.SongOnClickListener
import com.abdul.musicplayer.ui.main.viewmodel.MainViewModel
import com.abdul.musicplayer.utils.CommonUtils


class MainActivity : AppCompatActivity(), SongOnClickListener {

    private lateinit var mainViewModel: MainViewModel
    private lateinit var adapter: SongAdapter
    private lateinit var mediaPlayer: MediaPlayer


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViews()
        setListener()
        setupViewHolder()
    }

    private fun setListener() {
        btn_search.setOnClickListener {
            if (CommonUtils.isNetworkAvailable(this)){
                if (!edt_search.text.toString().trim().isNullOrEmpty()){
                    showLoading()
                    searchMusic(edt_search.text.toString())
                }else{
                    edt_search.error = getString(R.string.empty_field)
                }
            }else {
                showError(getString(R.string.no_internet_connection))
            }

        }
    }

    private fun showError(msg:String){
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show()
    }

    private fun setupViewHolder() {
        mainViewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
    }

    private fun searchMusic(artistName:String){
        mainViewModel.getMusic(artistName).observe(this, Observer {
            it.let { songs -> setupList(songs) }
        })
    }

    private fun setupList(songs:List<Song>){
        if (songs.isNotEmpty()){
            rv_player.visibility = View.VISIBLE
            tv_no_data_found.visibility = View.GONE
            adapter.addSongs(songs)
            adapter.notifyDataSetChanged()
        }else {
            rv_player.visibility = View.GONE
            tv_no_data_found.visibility = View.VISIBLE
            tv_no_data_found.text = getString(R.string.no_data_found)
        }
        CommonUtils.hideKeyboard(this, currentFocus!!.windowToken!!)
        hideLoading()
    }

    private fun playAudio(audioUrl:String) {

        if (this::mediaPlayer.isInitialized){
            if(mediaPlayer.isPlaying || mediaPlayer.isLooping){
                mediaPlayer.stop()
                mediaPlayer.reset()
            }
        }
        mediaPlayer = MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        try {
            mediaPlayer.setDataSource(audioUrl);
            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (ex:Exception) {
            ex.printStackTrace();
        }
    }

    private fun initViews() {
        rv_player.layoutManager = LinearLayoutManager(this)
        adapter = SongAdapter(arrayListOf(),this)
        rv_player.addItemDecoration(
                DividerItemDecoration(
                        rv_player.context,
                        (rv_player.layoutManager as LinearLayoutManager).orientation
                )
        )
        rv_player.adapter = adapter
        tv_no_data_found.text = getString(R.string.search_for_artist)
    }

    override fun onClickListener(song: Song,position:Int) {
        adapter.getSongs().filter { song -> song.isPlaying }.map { song -> song.isPlaying = false }
        adapter.getSongs()[position].isPlaying = true
        adapter.notifyDataSetChanged()
        playAudio(song.previewUrl)
    }

    private fun showLoading() {
        pb_loading.visibility = View.VISIBLE
    }

    private fun hideLoading() { pb_loading.visibility = View.GONE }

}