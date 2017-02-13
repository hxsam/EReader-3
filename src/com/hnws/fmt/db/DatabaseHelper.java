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

	private static final int VERSON = 1;// 默认的数据库版本
	
	// 继承SQLiteOpenHelper类的类必须有自己的构造函数
	// 该构造函数4个参数，直接调用父类的构造函数。其中第一个参数为该类本身；第二个参数为数据库的名字；
	// 第3个参数是用来设置游标对象的，这里一般设置为null；参数四是数据库的版本号。
	public DatabaseHelper(Context context, String name, CursorFactory factory,
			int verson) {
		super(context, name, factory, verson);
	}

	// 该构造函数有3个参数，因为它把上面函数的第3个参数固定为null了
	public DatabaseHelper(Context context, String name, int verson) {
		this(context, name, null, verson);
	}

	// 该构造函数只有2个参数，在上面函数 的基础山将版本号固定了
	public DatabaseHelper(Context context, String name) {
		this(context, name, VERSON);
	}

	// 该函数在数据库第一次被建立时调用
	@Override
	public void onCreate(SQLiteDatabase arg0) {
		// TODO Auto-generated method stub
		System.out.println("create a sqlite database");
		// execSQL()为执行参数里面的SQL语句，因此参数中的语句需要符合SQL语法,这里是创建一个表
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