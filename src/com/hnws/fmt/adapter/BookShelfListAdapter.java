package com.hnws.fmt.adapter;

import java.util.List;

import com.hnws.fmt.entity.BookShelf;
import com.hnws.fmt.R;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class BookShelfListAdapter extends BaseAdapter {
	
	private Context context;
	private LayoutInflater inflater;
	private List<BookShelf> dataList;

	public BookShelfListAdapter(Context context, List<BookShelf> dataList) {
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
			convertView = inflater.inflate(R.layout.bookshelf_main_item, null);
			holder = new ViewHolder();
			holder.tv_book_name = (TextView) convertView.findViewById(R.id.tv_bookname);
			holder.tv_book_path = (TextView) convertView.findViewById(R.id.tv_bookpath);
			holder.tv_book_img = (TextView) convertView.findViewById(R.id.tv_bookimg);
			holder.tv_book_isnewfile = (TextView) convertView.findViewById(R.id.tv_isnewfile);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.tv_book_name.setText(dataList.get(position).getBookname());
		holder.tv_book_path.setText(dataList.get(position).getFilepath());
		holder.tv_book_img.setText(dataList.get(position).getImgpath());
		holder.tv_book_isnewfile.setText(dataList.get(position).getIsnewfile());
		
		return convertView;
	}
	
	private class ViewHolder {
		TextView tv_book_name;
		TextView tv_book_path;
		TextView tv_book_img;
		TextView tv_book_isnewfile;
		
		
	}

}