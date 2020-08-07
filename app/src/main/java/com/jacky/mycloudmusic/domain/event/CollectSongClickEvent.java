package com.jacky.mycloudmusic.domain.event;

import com.jacky.mycloudmusic.domain.Song;

public class CollectSongClickEvent {
    private Song song;

    public CollectSongClickEvent(Song song) {
        this.song = song;
    }

    public void setSong(Song song) {
        this.song = song;
    }

    public Song getSong() {
        return song;
    }
}
