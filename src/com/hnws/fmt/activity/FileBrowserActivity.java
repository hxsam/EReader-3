package com.hnws.fmt.activity;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.hnws.fmt.db.DatabaseHelper;
import com.hnws.fmt.R;

/*展现全部文件的Activity*/
public class FileBrowserActivity extends BaseActivity {
	
	DatabaseHelper dbHelper = null;
	private final int DB_VERSION = 1;
	private TextView tv_filepath;
	private Button btn_lastpage;
	private ListView lv_filelist;

	private List<File> fileNameList;
	private Bundle bundle;
	private String fileNameKey = "fileName";
	private File ROOT_PATH = Environment.getExternalStorageDirectory();
	private File CURRENT_PATH = android.os.Environment
			.getExternalStorageDirectory();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_filebrowser);
		btn_lastpage = (Button) findViewById(R.id.btn_lastpage);
		tv_filepath = (TextView) findViewById(R.id.tv_filepath);
		lv_filelist = (ListView) findViewById(R.id.lv_filelist);
		initFileList();
		lv_filelist.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(FileBrowserActivity.this,
						ReadBookActivity.class);
				bundle = new Bundle();
				File file = fileNameList.get(arg2);
				if (file.isDirectory()) {
					if (file.canRead()) {
						CURRENT_PATH = file;
						File[] f = file.listFiles();
						fill(f);
					}
				} else {
					dbHelper = new DatabaseHelper(FileBrowserActivity.this, "ereader.db", null, DB_VERSION);
					String filenamesave =  file.getName();
					
					filenamesave = filenamesave.substring(0, filenamesave.length()-4);
					if(!dbHelper.isBookExsit(filenamesave))
					{
						dbHelper.addBookShelf(filenamesave, file.getAbsolutePath(), "", "1");
					}
					
					bundle.putString(fileNameKey, file.getAbsolutePath());
					intent.putExtras(bundle);
					startActivity(intent);
					finish();
				}
			}
			
		});
		
		
		btn_lastpage.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				gotoLastpage();
			}
		});
	}

	private void initFileList() {
		// File path = android.os.Environment.getExternalStorageDirectory();
		File[] f = ROOT_PATH.listFiles();
		fill(f);
	}

	private boolean gotoLastpage() {
		if (CURRENT_PATH.getParentFile() != null ) {
			CURRENT_PATH = CURRENT_PATH.getParentFile();
			fill(CURRENT_PATH.listFiles());
			return true;
		} else
			return false;
	}

	// 读取文件列表,并设置listview
	private void fill(File[] files) {
		tv_filepath.setText(CURRENT_PATH.getPath());
		fileNameList = new ArrayList<File>();
		List<HashMap> allfiles = new ArrayList<HashMap>();
		for (File file : files) {
			HashMap filemap = new HashMap();
			if (isValidFileOrDir(file)) {
				if (file.isDirectory())
					filemap.put("img", R.drawable.file_type_folder + "");
				else
					filemap.put("img", R.drawable.file_type_txt + "");
				filemap.put("filename", file.getName());
				// filemap.put("file", file);
				allfiles.add(filemap);
				fileNameList.add(file);
			}
		}
		FileBrowserAdapter fileBrowserAdapter = new FileBrowserAdapter(this,
				allfiles);
//		setListAdapter(fileBrowserAdapter);
		lv_filelist.setAdapter(fileBrowserAdapter);
	}

	/* 检查是否为合法的文件名，或者是否为路径 */
	private boolean isValidFileOrDir(File file) {
		if (file.isDirectory() && file.canRead()) {
			return true;
		} else {
			String fileName = file.getName().toLowerCase();
			if (fileName.endsWith(".txt")) {
				return true;
			}
		}
		return false;
	}

	public class FileBrowserAdapter extends BaseAdapter {

		private Context context;
		private LayoutInflater inflater;
		private List<HashMap> dataList;

		public FileBrowserAdapter(Context context, List<HashMap> dataList) {
			super();
			this.context = context;
			this.dataList = dataList;
			inflater = LayoutInflater.from(context);
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return dataList.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return dataList.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			ViewHolder holder = null;
			if (convertView == null) {
				convertView = inflater.inflate(R.layout.item_filebrowser, null);
				holder = new ViewHolder();
				holder.filebrowser_item_image = (ImageView) convertView
						.findViewById(R.id.filebrowser_item_image);
				holder.filebrowser_item_filename = (TextView) convertView
						.findViewById(R.id.filebrowser_item_filename);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			int imgid = Integer.parseInt(dataList.get(position).get("img")
					.toString());
			holder.filebrowser_item_image.setBackgroundResource(imgid);
			holder.filebrowser_item_filename.setText(dataList.get(position)
					.get("filename").toString());

			return convertView;
		}

		private class ViewHolder {
			ImageView filebrowser_item_image;
			TextView filebrowser_item_filename;

		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
			if (gotoLastpage())
				return true;
		}
		return super.onKeyDown(keyCode, event);
	}

}
