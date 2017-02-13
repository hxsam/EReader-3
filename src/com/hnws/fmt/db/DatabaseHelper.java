package com.hnws.fmt.db;

import java.util.ArrayList;
import java.util.List;

import com.hnws.fmt.entity.BookShelf;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

	private static final int VERSON = 1;// Ĭ�ϵ����ݿ�汾
	
	// �̳�SQLiteOpenHelper�����������Լ��Ĺ��캯��
	// �ù��캯��4��������ֱ�ӵ��ø���Ĺ��캯�������е�һ������Ϊ���౾���ڶ�������Ϊ���ݿ�����֣�
	// ��3�����������������α����ģ�����һ������Ϊnull�������������ݿ�İ汾�š�
	public DatabaseHelper(Context context, String name, CursorFactory factory,
			int verson) {
		super(context, name, factory, verson);
	}

	// �ù��캯����3����������Ϊ�������溯���ĵ�3�������̶�Ϊnull��
	public DatabaseHelper(Context context, String name, int verson) {
		this(context, name, null, verson);
	}

	// �ù��캯��ֻ��2�������������溯�� �Ļ���ɽ���汾�Ź̶���
	public DatabaseHelper(Context context, String name) {
		this(context, name, VERSON);
	}

	// �ú��������ݿ��һ�α�����ʱ����
	@Override
	public void onCreate(SQLiteDatabase arg0) {
		// TODO Auto-generated method stub
		System.out.println("create a sqlite database");
		// execSQL()Ϊִ�в��������SQL��䣬��˲����е������Ҫ����SQL�﷨,�����Ǵ���һ����
		arg0.execSQL("create table bookshelf(_id int, bookname varchar(20),bookpath varchar(150),bookimg varchar(100),isnewfile varchar(5),createtime varchar(14))");
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub
		System.out.println("update a sqlite database");
	}
	
	
	public boolean addBookShelf(String bookname,String bookpath,String  bookimg,String isnewfile) {
		// TODO Auto-generated method stub
		System.out.println("update a sqlite database");
		String createtime = "2015";
		String sql = "insert into bookshelf(bookname,bookpath,bookimg,isnewfile,createtime) "
				+ " values('"+bookname+"','"+bookpath+"','"+bookimg+"','"+isnewfile+"','"+createtime+"')";
		SQLiteDatabase  db = this.getWritableDatabase();
		db.execSQL(sql);
		db.close();
		return true;
	}
	
	public boolean delBookShelf(String bookname) {
		// TODO Auto-generated method stub
		String createtime = "2015";
		String sql = "delete from bookshelf ";
		if(bookname != null )
			sql += " where bookname='"+bookname+"'";
		SQLiteDatabase  db = this.getWritableDatabase();
		db.execSQL(sql);
		db.close();
		return true;
	}
	
	public List<BookShelf> queryBookShelf() {
		// TODO Auto-generated method stub
		List<BookShelf> list = new ArrayList<BookShelf>();
		String createtime = "2015";
		String sql = "select * from bookshelf ORDER BY CREATETIME DESC";
		SQLiteDatabase  db = this.getReadableDatabase();
		Cursor c = db.rawQuery(sql, null);
		while (c.moveToNext()) {
			BookShelf book = new BookShelf();
			book.setBookname(c.getString(c.getColumnIndex("bookname")));
			book.setFilepath(c.getString(c.getColumnIndex("bookpath")));
			book.setImgpath(c.getString(c.getColumnIndex("bookimg")));
			book.setIsnewfile(c.getString(c.getColumnIndex("isnewfile")));
			book.setCreatetime(c.getString(c.getColumnIndex("createtime")));
			list.add(book);
		}
		c.close();
		db.close();
		return list;
	}
	public boolean isBookExsit(String bookname) {
		// TODO Auto-generated method stub
		boolean isBookExsit = false;
		String createtime = "2015";
		String sql = "select * from bookshelf where bookname='"+bookname+"'";
		SQLiteDatabase  db = this.getReadableDatabase();
		Cursor c = db.rawQuery(sql, null);
		if (c.moveToNext()) {
			isBookExsit = true;
		}
		c.close();
		db.close();
		return isBookExsit;
	}
	
}