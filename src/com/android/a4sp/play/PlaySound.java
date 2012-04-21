package com.android.a4sp.play;

import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.a4sp.R;
import com.android.a4sp.impl.MusicImplement;

public class PlaySound extends Activity implements MusicImplement {
	private static final String TAG = "PlaySound";
	private ImageButton btnPlay;
	private MediaPlayer mediaPlayer;
	private ProgressBar progressBar;
	private static int mProgressStatus = 0;
	int currentPosition= 0;
	// private static final int PROGRESS = 0x1;
	private Handler mHandler = new Handler();

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.play);
		mediaPlayer = new MediaPlayer();
		btnPlay = (ImageButton) findViewById(R.id.btnPlay);
		progressBar = (ProgressBar) findViewById(R.id.progresBar);

		Intent intent = getIntent();
		final String dataUrl = intent.getStringExtra("dataUrl");
		Log.i(TAG, "***********" + dataUrl);

		btnPlay.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				Toast.makeText(PlaySound.this, "Please wait..",
						Toast.LENGTH_LONG).show();
				if (!initDataMusic(dataUrl).isPlaying()) {
					initDataMusic(dataUrl).start();
				} else {
					initDataMusic(dataUrl).stop();
				}
				Log.i(TAG, dataUrl);

//				progressBar.setVisibility(ProgressBar.VISIBLE);
//				progressBar.setProgress(0);
//				progressBar.setMax(100);
			
				new Thread(new Runnable() {
					public void run() {
						int total = mediaPlayer.getDuration();
					
						progressBar.setVisibility(ProgressBar.VISIBLE);
			            progressBar.setProgress(0);
			            progressBar.setMax(mediaPlayer.getDuration());
			            new Thread(this).start();
						while (mediaPlayer!=null && currentPosition<total) {
				            try {
				                Thread.sleep(1000);
				                currentPosition= mediaPlayer.getCurrentPosition();
				            } catch (InterruptedException e) {
				                return;
				            } catch (Exception e) {
				                return;
				            }            
				            progressBar.setProgress(currentPosition);
				        }
					}
				}).start();

			}
		});
	}

	

	public MediaPlayer initDataMusic(String url) {
		mediaPlayer = new MediaPlayer();

		try {
			mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
			mediaPlayer.setDataSource(url);
			mediaPlayer.prepare();
			Log.i(TAG, "Request => " + url);
		} catch (IOException e) {
			e.printStackTrace();
			Log.i(TAG, e.getMessage());
		}
		return mediaPlayer;
	}

	public MediaPlayer playMusic(String url) {
		mediaPlayer = initDataMusic(url);
		return mediaPlayer;
	}

	public MediaPlayer stopMusic(String url) {
		mediaPlayer = initDataMusic(url);
		return mediaPlayer;
	}

	public MediaPlayer pauseMusic(String url) {
		mediaPlayer = initDataMusic(url);
		return mediaPlayer;
	}
}
