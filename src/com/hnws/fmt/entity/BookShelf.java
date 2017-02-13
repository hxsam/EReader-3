package com.hnws.fmt.entity;

public class BookShelf {
	private String bookname;
	private String filepath;
	private String imgpath;
	private String isnewfile;
	private String createtime;

	public BookShelf() {
		super();
		// TODO Auto-generated constructor stub
	}


	public BookShelf(String bookname, String filepath, String imgpath,
			String isnewfile,String createtime) {
		super();
		this.bookname = bookname;
		this.filepath = filepath;
		this.imgpath = imgpath;
		this.isnewfile = isnewfile;
		this.createtime = createtime;
	}


	public String getCreatetime() {
		return createtime;
	}


	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}


	public String getFilepath() {
		return filepath;
	}


	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}


	public String getImgpath() {
		return imgpath;
	}


	public void setImgpath(String imgpath) {
		this.imgpath = imgpath;
	}


	public String getIsnewfile() {
		return isnewfile;
	}


	public void setIsnewfile(String isnewfile) {
		this.isnewfile = isnewfile;
	}


	public String getBookname() {
		return bookname;
	}

	public void setBookname(String bookname) {
		this.bookname = bookname;
	}
	
}
