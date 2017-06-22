package com.my.dbtest;

import java.util.ArrayList;

import com.icedcap.dbtest.R;
import com.my.dbtest.adapter.PersionAdapter;
import com.my.dbtest.helper.DbHelper;
import com.my.dbtest.model.Persion;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

@SuppressLint("NewApi")
public class MainActivity extends Activity implements OnClickListener {

	private Button add, delete, update, query,reset;

	private ListView lstPersion;
	private EditText edtId, edtName, edtAge, edtHeight, edtWeight;// 编号，姓名，年龄，身高，体重
																	// edt
	private static final String DB = "ddd";
	private DbHelper dbHelper = null;
	private PersionAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		dbHelper = new DbHelper(this, DB, null, 1);
		dbHelper.inItDb(GetCreateString());
		adapter = new PersionAdapter(this);
		initView();
	}

	/**
	 * 创建表的字符串
	 * 
	 * @return
	 */
	private String GetCreateString() {
		return "Create Table Persion( Id text not null, Name text not null,	Age text,	Weight text,	Height text )";
	}

	/**
	 * 初始化控件
	 */
	private void initView() {
		edtId = (EditText) findViewById(R.id.edtId);
		edtName = (EditText) findViewById(R.id.edtName);
		edtAge = (EditText) findViewById(R.id.edtAge);
		edtHeight = (EditText) findViewById(R.id.edtHeight);
		edtWeight = (EditText) findViewById(R.id.edtWeight);

		// 曾删改查
		add = (Button) findViewById(R.id.add);
		add.setOnClickListener(this);
		delete = (Button) findViewById(R.id.delete);
		delete.setOnClickListener(this);
		update = (Button) findViewById(R.id.update);
		update.setOnClickListener(this);
		query = (Button) findViewById(R.id.query);
		query.setOnClickListener(this);
		reset = (Button) findViewById(R.id.reset);
		reset.setOnClickListener(this);
		
		// 绑定ListView
		lstPersion = (ListView) this.findViewById(R.id.lstPersion);
		lstPersion.setAdapter(adapter);
		updateList();
	}

	private void updateList() {
		try {
			adapter.persions = queryAll();
			adapter.notifyDataSetChanged();
		} catch (Exception e) {
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.add:
			insert();
			break;
		case R.id.delete:
			delete();
			break;
		case R.id.update:
			update();
			break;
		case R.id.query:
			query();
			break;
		case R.id.reset:
			reset();
			break;
		default:
			break;
		}
	}

	private void reset() {
		 edtId.setText("");
		 edtName.setText("");
		 edtAge.setText("");
		 edtHeight.setText("");
		 edtWeight.setText("");
	}

	boolean checkInput() {
		if (edtId.getText().toString().trim().equals("")) {
			ShowConfim("提示", "请输入编号", "确认");
			return false;
		}
		if (this.edtName.getText().toString().trim().equals("")) {
			ShowConfim("提示", "请输入姓名", "确认");
			return false;
		}
		if (this.edtAge.getText().toString().trim().equals("")) {
			ShowConfim("提示", "请输入年龄", "确认");
			return false;
		}
		if (this.edtHeight.getText().toString().trim().equals("")) {
			ShowConfim("提示", "请输入身高", "确认");
			return false;
		}
		if (this.edtWeight.getText().toString().trim().equals("")) {
			ShowConfim("提示", "请输入体重", "确认");
			return false;
		}
		return true;
	}

	public void ShowConfim(String title, String msg, String yesBtnName) {
		android.app.AlertDialog.Builder window = new AlertDialog.Builder(this);
		window = window.setTitle(title).setMessage(msg)
				.setPositiveButton(yesBtnName, null);
		window.show();
	}

	// 插入操作
	// @SuppressLint("NewApi")
	private void insert() {
		if (!checkInput())
			return;

		ContentValues values = new ContentValues();
		values.put("id", edtId.getText().toString());
		values.put("name", edtName.getText().toString());
		values.put("age", edtAge.getText().toString());
		values.put("height", edtHeight.getText().toString());
		values.put("weight", edtWeight.getText().toString());
		dbHelper.insert(values, TabNames.Persion);
		 updateList();
	}

	// // 删除某一行需要填写id
	private void delete() {
		for (int i = 0; i < adapter.checkedIds.size(); i++) {
			dbHelper.delete(adapter.checkedIds.get(i), TabNames.Persion);
		}
		updateList();
	}

	//
	// // 修改操作需要按顺序填写各个字段的内容并用，隔开并且最后要填写修改某一行的id
	private void update() {
		if (!checkInput())
			return;

		ContentValues values = new ContentValues();
		values.put("id", edtId.getText().toString());
		values.put("name", edtName.getText().toString());
		values.put("age", edtAge.getText().toString());
		values.put("height", edtHeight.getText().toString());
		values.put("weight", edtWeight.getText().toString());
		dbHelper.update(values, edtId.getText().toString(), TabNames.Persion);
		
		updateList();
	}

	// 按id查询某一行
	private void query() {
		if (edtId.getText().toString().trim().equals("")) {
			ShowConfim("提示", "请输入编号", "确认");
			return;
		}

		Cursor cursor = dbHelper.query(edtId.getText().toString().trim(),
				TabNames.Persion);
		Persion persion = null;
		if (cursor.moveToFirst()) {
			while (!cursor.isAfterLast()) {
				persion = new Persion();
				persion.Id = cursor.getString(cursor.getColumnIndex("Id"));
				persion.Name = cursor.getString(cursor.getColumnIndex("Name"));
				persion.Age = cursor.getString(cursor.getColumnIndex("Age"));
				persion.Height = cursor.getString(cursor
						.getColumnIndex("Height"));
				persion.Weight = cursor.getString(cursor
						.getColumnIndex("Weight"));
				break;
			}
		}
		if(persion==null){
			ShowConfim("提示", "没有查到", "确认");
			return;
		}
		this.edtId.setText(persion.Id);
		this.edtName.setText(persion.Name);
		this.edtAge.setText(persion.Age);
		this.edtHeight.setText(persion.Height);
		this.edtWeight.setText(persion.Weight);
	}

	// 显示所有数据
	private ArrayList<Persion> queryAll() {
		ArrayList<Persion> rst = new ArrayList<Persion>();
		// 解析游标
		Cursor cursor = dbHelper.query(TabNames.Persion);
		Persion persion = null;
		if (cursor.moveToFirst()) {
			while (!cursor.isAfterLast()) {

				persion = new Persion();
				persion.Id = cursor.getString(cursor.getColumnIndex("Id"));
				persion.Name = cursor.getString(cursor.getColumnIndex("Name"));
				persion.Age = cursor.getString(cursor.getColumnIndex("Age"));
				persion.Height = cursor.getString(cursor
						.getColumnIndex("Height"));
				persion.Weight = cursor.getString(cursor
						.getColumnIndex("Weight"));

				rst.add(persion);

				cursor.moveToNext();
			}
		}
		return rst;
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		if (dbHelper != null) {
			dbHelper.close();
			dbHelper = null;
		}
	}

	public class TabNames {
		public static final String Persion = "Persion";
	}
}
