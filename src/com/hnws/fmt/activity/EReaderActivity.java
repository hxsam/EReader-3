package com.hnws.fmt.activity;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.hnws.fmt.db.DatabaseHelper;
import com.hnws.fmt.entity.BookShelf;
import com.hnws.fmt.view.MenuFragment;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.hnws.fmt.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.FragmentManager;
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

@SuppressLint("NewApi")
public class EReaderActivity extends BaseActivity {
	
	DatabaseHelper dbHelper = null;
	private final int DB_VERSION = 1;
	private GridView bookShelf;
	ShlefAdapter shlefAdapter;
	private String ROOT_PATH = Environment.getExternalStorageDirectory().getAbsolutePath();
	List<BookShelf> bookList = new ArrayList<BookShelf>();
	
	/** 底部菜单图片 **/
	int[] menu_toolbar_image_array = { R.drawable.menu_eyes_icon1,
			R.drawable.menu_icon_import, R.drawable.menu_icon_skin,
			R.drawable.menu_icon_booksmanage, R.drawable.menu_icon_question,
			R.drawable.menu_icon_about, R.drawable.menu_icon_refresh,
			R.drawable.menu_icon_set };
	/** 底部菜单文字 **/
	private final String[] menu_toolbar_name_array = { "眼睛卫士", "导入图书", "更换皮肤",
			"图书管理", "反馈", "关于", "检查更新", "设置" };

	private final int MENU_ITEM_EYE = 0;
	private final int MENU_ITEM_IMPORT = 1;
	private final int MENU_ITEM_SKIN = 2;
	private final int MENU_ITEM_BOOKMANAGE = 3;
	private final int MENU_ITEM_QUESTION = 4;
	private final int MENU_ITEM_ABOUT = 5;
	private final int MENU_ITEM_REFRESH = 6;
	private final int MENU_ITEM_SET = 7;

	private GridView gv_menu;
	private Button btn_menu_open;
	RelativeLayout rl_menu;
	private SlidingMenu slidingMenu;
	private ImageView iv_logo;

	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.act_ereader);
		initView();
		initBookShelf();
		initMainMenu();
		initSlidingMenu();
	}

	private void queryBookList() {
		dbHelper = new DatabaseHelper(this, "ereader.db", null, DB_VERSION);
//		dbHelper.addBookShelf("天龙八部", ROOT_PATH+"/Hankooktire/PDA数据库操作.txt", "", "1");
//		dbHelper.delBookShelf(null);
//		dbHelper.addBookShelf("天龙八部", ROOT_PATH+"/zangql/PDA.txt", "", "1");
		bookList = dbHelper.queryBookShelf();
	}

	private void initView() {
		bookShelf = (GridView) findViewById(R.id.bookShelf);
		btn_menu_open = (Button) findViewById(R.id.btn_menu_open);
		gv_menu = (GridView) findViewById(R.id.gv_menu);
		iv_logo = (ImageView) findViewById(R.id.iv_logo);
	}

	private void initBookShelf() {
		queryBookList();
		shlefAdapter = new ShlefAdapter();
		bookShelf.setAdapter(shlefAdapter);
		bookShelf.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				if (arg2 < bookList.size()) {
					Intent in = new Intent(EReaderActivity.this,
							ReadBookActivity.class);
					 Bundle bundle = new Bundle();
					 bundle.putString("fileName", bookList.get(arg2).getFilepath());
					 in.putExtras(bundle);
					startActivity(in);
					overridePendingTransition(R.anim.anim_bookshelf_menu_enter,
							R.anim.out_to_left);
				} else if (arg2 == bookList.size()) {
					Intent subintent = new Intent(EReaderActivity.this,
							FileBrowserActivity.class);
					startActivity(subintent);
				}
			}
		});
	}
	
	private void refreshBookShelf()
	{
		queryBookList();
		shlefAdapter.notifyDataSetChanged();
	}

	private void initMainMenu() {
		btn_menu_open.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				toggleBottomMenu();
			}
		});
		gv_menu.setNumColumns(4);
		gv_menu.setGravity(Gravity.CENTER);
		gv_menu.setVerticalSpacing(50);
		gv_menu.setHorizontalSpacing(10);
		gv_menu.setAdapter(getMenuAdapter(menu_toolbar_name_array,
				menu_toolbar_image_array));
		gv_menu.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Intent subintent;
				toggleBottomMenu();
				switch (arg2) {
				case MENU_ITEM_EYE:
					break;
				case MENU_ITEM_IMPORT:
					subintent = new Intent(EReaderActivity.this,
							FileBrowserActivity.class);
					startActivity(subintent);
					overridePendingTransition(R.anim.in_from_right,
							R.anim.out_to_left);
					break;
				case MENU_ITEM_SKIN:
					break;
				case MENU_ITEM_BOOKMANAGE:
					break;
				case MENU_ITEM_QUESTION:
					subintent = new Intent(EReaderActivity.this,
							FeedBackActivity.class);
					startActivity(subintent);
					overridePendingTransition(R.anim.in_from_right,
							R.anim.out_to_left);
					break;
				case MENU_ITEM_ABOUT:
					subintent = new Intent(EReaderActivity.this,
							AboutActivity.class);
					startActivity(subintent);
					overridePendingTransition(R.anim.in_from_right,
							R.anim.out_to_left);
					break;
				case MENU_ITEM_REFRESH:
					ConnectivityManager cm = (ConnectivityManager) EReaderActivity.this
							.getSystemService(EReaderActivity.this.CONNECTIVITY_SERVICE);
					NetworkInfo networkInfo = cm.getActiveNetworkInfo();
					if (networkInfo == null) {
						Toast.makeText(EReaderActivity.this, "没有发现网络",
								Toast.LENGTH_SHORT).show();
					} else {
						Toast.makeText(EReaderActivity.this,
								"当前网络类型：" + networkInfo.getTypeName(),
								Toast.LENGTH_SHORT).show();
					}
					break;
				case MENU_ITEM_SET:
					subintent = new Intent(EReaderActivity.this,
							SettingActivity.class);
					startActivity(subintent);
					overridePendingTransition(R.anim.in_from_right,
							R.anim.out_to_left);
					break;
				}
			}
		});

	}

	private void toggleBottomMenu() {
		if (gv_menu.getVisibility() == View.GONE) {
			gv_menu.setVisibility(View.VISIBLE);
			btn_menu_open
					.setBackgroundResource(R.drawable.bookshelf_menu_close);
		} else {
			gv_menu.setVisibility(View.GONE);
			btn_menu_open.setBackgroundResource(R.drawable.bookshelf_menu_open);
		}
	}

	private SimpleAdapter getMenuAdapter(String[] menuNameArray,
			int[] imageResourceArray) {
		ArrayList<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();
		for (int i = 0; i < menuNameArray.length; i++) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("itemImage", imageResourceArray[i]);
			map.put("itemText", menuNameArray[i]);
			data.add(map);
		}
		SimpleAdapter simperAdapter = new SimpleAdapter(this, data,
				R.layout.item_menu, new String[] { "itemImage", "itemText" },
				new int[] { R.id.item_image, R.id.item_text });
		return simperAdapter;
	}

	class ShlefAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return bookList.size() + 1 ;
		}

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return arg0;
		}

		@Override
		public long getItemId(int arg0) {
			// TODO Auto-generated method stub
			return arg0;
		}

		@Override
		public View getView(int position, View contentView, ViewGroup arg2) {
			// TODO Auto-generated method stub

			contentView = LayoutInflater.from(getApplicationContext()).inflate(
					R.layout.book_main_item, null);

			TextView tv_bookname = (TextView) contentView
					.findViewById(R.id.tv_bookname);

			
			if (bookList.size() > position) {
				BookShelf book = bookList.get(position);
//				if(!"导入图书".equals(book.getBookname()))
//				{	
				if (book.getBookname()!=null) {
					tv_bookname.setText(book.getBookname());
				} else
					tv_bookname.setText("");
//				if(book.getImgpath()!=null)
//				{
//					tv_bookname.setBackground(Drawable.createFromPath(book.getImgpath()));
//				}
//				else
					tv_bookname.setBackgroundResource(R.drawable.cover_txt);
//				}else
//				{
//					tv_bookname.setText("");
//					tv_bookname.setBackgroundResource(R.drawable.cover_net);
//				}
					
			} else if (bookList.size() == position) {
				tv_bookname.setText("");
				tv_bookname.setBackgroundResource(R.drawable.cover_net);
			} else {
//				tv_bookname.setBackgroundResource(data[0]);
				tv_bookname.setClickable(false);
				tv_bookname.setVisibility(View.INVISIBLE);
			}
			return contentView;
		}

	}

	public void initSlidingMenu() {
		slidingMenu = new SlidingMenu(this);
		slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE); // 滑动方式
		slidingMenu.setShadowDrawable(R.drawable.shadow_right); // 阴影
		// slidingMenu.setBehindOffset(80); // 前面的视图剩下多少
		slidingMenu.setMode(SlidingMenu.LEFT); // 左滑出不是右滑出
		slidingMenu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
		slidingMenu.setMenu(R.layout.menu_frame); // 设置menu容器
		FragmentManager fm = getSupportFragmentManager();
		fm.beginTransaction().replace(R.id.menu_frame, new MenuFragment())
				.commit();
		// 设置滑动阴影的宽度
		slidingMenu.setShadowWidthRes(R.dimen.shadow_width);
		slidingMenu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		slidingMenu.setFadeDegree(0.35f);
		slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
	}

	public void OnClickSlidMenu(View view) {
		Intent subintent;
		switch (view.getId()) {
		case R.id.rl_slidmenu_scan:
			subintent = new Intent(EReaderActivity.this,
					BarCodeScannerActivity.class);
			startActivity(subintent);
			overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
			break;
		case R.id.iv_logo:
			slidingMenu.toggle();
			break;
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (gv_menu.getVisibility() == View.VISIBLE) {
				gv_menu.setVisibility(View.GONE);
				btn_menu_open
						.setBackgroundResource(R.drawable.bookshelf_menu_open);
				return true;
			}
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setMessage("你确定退出吗？")
					.setCancelable(false)
					.setPositiveButton("残忍退出",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {
									finish();
								}
							})
					.setNegativeButton("继续High！",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {
									dialog.cancel();
								}
							});
			AlertDialog alert = builder.create();
			alert.show();
			return true;
		}
		if (keyCode == KeyEvent.KEYCODE_MENU) {
			toggleBottomMenu();
			return true;
		}

		return super.onKeyDown(keyCode, event);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		refreshBookShelf();
	}
	
	
}
