package com.hnws.fmt.activity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import com.hnws.fmt.R;
import com.hnws.fmt.R.id;
import com.hnws.fmt.R.layout;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.webkit.WebSettings.TextSize;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * 打开文件的方法
 * @author Administrator
 *
 */
public class ReadBookActivity extends BaseActivity {
	
	private String filenameString;
	private static final String gb2312 = "GB2312";
	private static final String utf8 = "UTF-8";
	private static final String defaultCode = gb2312;
	RelativeLayout rl_readbook;
	TextView tv_contents; 
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_readbook);
		rl_readbook = (RelativeLayout) findViewById(R.id.rl_readbook);
		tv_contents = (TextView) findViewById(R.id.view_contents);
		
		rl_readbook.setBackgroundResource(R.drawable.bookshelf_header_bg_shelf);		
		
		try {
			Bundle bunde = this.getIntent().getExtras();
			filenameString = bunde.getString("fileName");
//			filenameString = Environment.getExternalStorageDirectory().getPath()+"/zangql/PDA.txt";
			refreshGUI(defaultCode);
		} catch (Exception e) {
		}
	}
	

	private void refreshGUI(String code)
	{
		String fileContent = getStringFromFile(code);
		tv_contents.setText(fileContent);
//		tv_contents.setTextSize(TextSize.NORMAL);
	}
	
	public String getStringFromFile(String code)
	{
		try {
			StringBuffer sBuffer = new StringBuffer();
			FileInputStream fInputStream = new FileInputStream(filenameString);
			InputStreamReader inputStreamReader = new InputStreamReader(fInputStream, code);
			BufferedReader in = new BufferedReader(inputStreamReader);
			if(!new File(filenameString).exists())
			{
				return null;
			}
			while (in.ready()) {
				sBuffer.append(in.readLine() + "\n");
			}
			in.close();
			return sBuffer.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
