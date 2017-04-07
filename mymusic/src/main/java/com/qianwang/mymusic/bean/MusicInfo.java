package com.qianwang.mymusic.bean;

import java.util.List;

/**
 * Created by sky on 2017/4/4.
 */

public class MusicInfo {

    private String song_artist;  //歌手
    private String song_title;   //标题
    private String song_album;   //专辑
    private long  song_offset;   //偏移量
    public static List<LineInfo>  song_line;

    public MusicInfo() {

    }

    public MusicInfo(String song_artist, String song_title, String song_album, long song_offset, List<LineInfo> song_line) {
        this.song_artist = song_artist;
        this.song_title = song_title;
        this.song_album = song_album;
        this.song_offset = song_offset;
        this.song_line = song_line;
    }

    public String getSong_artist() {
        return song_artist;
    }

    public void setSong_artist(String song_artist) {
        this.song_artist = song_artist;
    }

    public String getSong_title() {
        return song_title;
    }

    public void setSong_title(String song_title) {
        this.song_title = song_title;
    }

    public String getSong_album() {
        return song_album;
    }

    public void setSong_album(String song_album) {
        this.song_album = song_album;
    }

    public long getSong_offset() {
        return song_offset;
    }

    public void setSong_offset(long song_offset) {
        this.song_offset = song_offset;
    }

    public List<LineInfo> getSong_line() {
        return song_line;
    }

    public void setSong_line(List<LineInfo> song_line) {
        this.song_line = song_line;
    }

    @Override
    public String toString() {
        return "MusicInfo{" +
                "song_artist='" + song_artist + '\'' +
                ", song_title='" + song_title + '\'' +
                ", song_album='" + song_album + '\'' +
                ", song_offset=" + song_offset +
                ", song_line=" + song_line +
                '}';
    }
}
