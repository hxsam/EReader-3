package com.hnws.fmt.activity;


import com.hnws.fmt.common.SharedConfig;
import com.hnws.fmt.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;

public class MainActivity extends BaseActivity {
	private boolean first;
	private View view;
	private Context context;
	private Animation animation;
	private SharedPreferences shared;
	private static int TIME = 1000; 									
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		view = View.inflate(this, R.layout.activity_main, null);
		setContentView(view);
		context = this;						
		shared = new SharedConfig(context).GetConfig(); 	
	}

	@Override
	protected void onResume() {
		into();
		super.onResume();
	}

	public void onPause() {
		super.onPause();
	}
	private void into() {
			first = shared.getBoolean(getCurrentVersionName(), true);

			animation = AnimationUtils.loadAnimation(this, R.anim.alpha);
			view.startAnimation(animation);
			animation.setAnimationListener(new AnimationListener() {
				@Override
				public void onAnimationStart(Animation arg0) {
				}

				@Override
				public void onAnimationRepeat(Animation arg0) {
				}

				@Override
				public void onAnimationEnd(Animation arg0) {
					new Handler().postDelayed(new Runnable() {
						@Override
						public void run() {
							Intent intent;
							if (first) {
								intent = new Intent(MainActivity.this,
										WelcomeActivity.class);
							} else {
								intent = new Intent(MainActivity.this,
										EReaderActivity.class);
							}
							startActivity(intent);
							overridePendingTransition(R.anim.in_from_right,
									R.anim.out_to_left);
							MainActivity.this.finish();
						}
					}, TIME);
				}
			});
	}
}
