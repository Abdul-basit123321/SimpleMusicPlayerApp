package com.abdul.musicplayer.ui.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.abdul.musicplayer.R
import com.abdul.musicplayer.data.model.Song
import com.abdul.musicplayer.ui.main.adapter.listeners.SongOnClickListener
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.main_row.view.*

class SongAdapter(private val songs:ArrayList<Song>,private val clickListener:SongOnClickListener) : RecyclerView.Adapter<SongAdapter.DataViewHolder>()  {

    public var musicPosition : Int = -1
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = DataViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.main_row,parent,false)
    )

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        holder.bind(songs[position],position,clickListener)
    }

    override fun getItemCount(): Int {
        return songs.size
    }

    fun addSongs(list: List<Song>) {
        songs.clear()
        songs.addAll(list)
    }

    fun getSongs():List<Song> {
        return songs;
    }

    class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        fun bind(song:Song,position: Int,clickListener: SongOnClickListener){
            itemView.tv_song_name.text = song.trackName
            itemView.tv_artist.text = song.artistName
            itemView.tv_album.text = song.collectionName
            if (song.isPlaying)
                itemView.img_gif_player.visibility = View.VISIBLE
            else
                itemView.img_gif_player.visibility = View.GONE

            try {
                Glide.with(itemView.img_artist.context).load(song.imageUrl).placeholder(R.drawable.ic_launcher_background).into(itemView.img_artist)
                Glide.with(itemView.img_gif_player.context).load(R.raw.music_player).into(itemView.img_gif_player)
            }catch (ex:Exception){
                ex.printStackTrace()
            }

            itemView.setOnClickListener {
                clickListener.onClickListener(song,position)
            }
        }
    }

}