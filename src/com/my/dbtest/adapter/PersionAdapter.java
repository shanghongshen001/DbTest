package com.my.dbtest.adapter;

import java.util.ArrayList;
import java.util.List;

import com.icedcap.dbtest.R;
import com.my.dbtest.model.Persion;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;

public class PersionAdapter extends BaseAdapter {

	public ArrayList<Persion> persions = null;
	Activity context;
	public ArrayList<String> checkedIds;
	
	public PersionAdapter(Activity context) {
		super();
		checkedIds = new ArrayList<String>();
		this.context = context;
		persions = new ArrayList<Persion>();
	}
	public void setPersions(ArrayList<Persion> persions){
		this.persions = persions;
	}
	@Override
	public int getCount() {
		return persions.size();
	}

	@Override
	public Object getItem(int position) {
		return persions.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@SuppressWarnings("static-access")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final ViewHolder holder;
		final Persion item = this.persions.get(position);
		
		if (convertView == null) {
			convertView = LayoutInflater.from(context).from(context).inflate(R.layout.persionitem, null);
			
			holder = new ViewHolder();
			holder.ck = (CheckBox)convertView.findViewById(R.id.ck);
			holder.tv1 = (TextView)convertView.findViewById(R.id.tv1);
			holder.tv2 = (TextView)convertView.findViewById(R.id.tv2);
			holder.tv3 = (TextView)convertView.findViewById(R.id.tv3);
			holder.tv4 = (TextView)convertView.findViewById(R.id.tv4);
			holder.tv5 = (TextView)convertView.findViewById(R.id.tv5);
			holder.ck.setOnCheckedChangeListener(new OnCheckedChangeListener() {
				@Override
				public void onCheckedChanged(CompoundButton buttonView,
						boolean isChecked) {
					if(isChecked){
						checkedIds.add(holder.tv1.getText().toString());
					}else{
						checkedIds.remove(holder.tv1.getText().toString());
					}
					item.CheckState = isChecked;
				}
	        });
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.tv1.setText(item.Id);
		holder.tv2.setText(item.Name);
		holder.tv3.setText(item.Age);
		holder.tv4.setText(item.Height);
		holder.tv5.setText(item.Weight);
		holder.ck.setChecked(item.CheckState);
		//赋值
		return convertView;
	}

	class ViewHolder {
		CheckBox ck;
		TextView tv1;
		TextView tv2;
		TextView tv3;
		TextView tv4;
		TextView tv5;
	}
}