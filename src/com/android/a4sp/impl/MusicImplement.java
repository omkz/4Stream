package com.android.a4sp.impl;

import android.media.MediaPlayer;

public interface MusicImplement {
	
	public MediaPlayer playMusic(String url);
	public MediaPlayer stopMusic(String url);
	public MediaPlayer pauseMusic(String url);
	
	public MediaPlayer initDataMusic(String url);

}
