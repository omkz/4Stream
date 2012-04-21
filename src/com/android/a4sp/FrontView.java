package com.android.a4sp;

import com.android.a4sp.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.view.View.OnClickListener;

public class FrontView extends Activity {
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.front);
		ImageButton button = (ImageButton) findViewById(R.id.btnOpen);
		button.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				pindahKelas();
			}
		});

	}

	private void pindahKelas() {
		Intent intent = new Intent(this, A4SPActivity.class);
		startActivity(intent);
	}
}
