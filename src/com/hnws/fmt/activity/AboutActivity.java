package com.hnws.fmt.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.hnws.fmt.R;
import com.hnws.fmt.R.anim;
import com.hnws.fmt.R.drawable;
import com.hnws.fmt.R.id;
import com.hnws.fmt.R.layout;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.SlidingDrawer;
import android.widget.TextView;
import android.widget.Toast;

public class AboutActivity extends BaseActivity {
	TextView titlename,tv_version;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.about);
		titlename = (TextView) findViewById(R.id.titlename);
		tv_version = (TextView) findViewById(R.id.tv_version);
		titlename.setText("¹ØÓÚ");
		tv_version.setText(getCurrentVersion());
	}

	

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		return super.onKeyDown(keyCode, event);
	}
}
