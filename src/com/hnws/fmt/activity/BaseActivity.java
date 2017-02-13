package com.hnws.fmt.activity;

import android.app.Activity;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import com.hnws.fmt.R;

public class BaseActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	public void OnClickBack(View view) {
		switch (view.getId()) {
		case R.id.iv_goback:
			finish();
			break;
		}
	}
	
	public String getCurrentVersion() {
		String curVersion = "V1.0.0";
		PackageInfo pInfo;
		try {
			pInfo = this.getPackageManager().getPackageInfo(
					this.getPackageName(), 0);
			curVersion = "°æ±¾£º"+pInfo.versionName+"("+pInfo.versionCode+")";
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return curVersion;
	}
	
	public String getCurrentVersionName() {
		String curVersion = "V1.0.0";
		PackageInfo pInfo;
		try {
			pInfo = this.getPackageManager().getPackageInfo(
					this.getPackageName(), 0);
			curVersion = pInfo.versionName;
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return curVersion;
	}

}
