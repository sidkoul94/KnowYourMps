package edu.mvit.kumps;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class Details extends Activity implements OnItemClickListener{

	static String[] options = { "Constituency Details!!", "MP Details",
			"Party Details" };


	static String[] optionInfo = {
			"Curious to know about ur Constituency..",
			"Wanna Know stuff about MPs..Click here.",
			"Get your hands on some of the Party details..!!" };

	static String[] inputtype = { "Constituency Name", "MP Name", "Party Name" };

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.details);
		createListView();
		ActionBar bar = getActionBar();
		bar.setBackgroundDrawable(new ColorDrawable(Color.DKGRAY));
	}

	private void createListView() {
		ListView lv = (ListView) findViewById(R.id.lv);
		NewAdapter adapter = new NewAdapter();
		lv.setAdapter(adapter);
		lv.setOnItemClickListener(this);
	}

	private class NewAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return 3;
		}

		@Override
		public Object getItem(int position) {
			return options[position];
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View item = convertView;
			if (item == null) {
				item = getLayoutInflater().inflate(R.layout.listoption, parent,
						false);
			}

			TextView LT = (TextView) item.findViewById(R.id.tv1);
			TextView ST = (TextView) item.findViewById(R.id.text2);
			TextView IT = (TextView) item.findViewById(R.id.tv3);
			
			LT.setText(options[position]);
			ST.setText(optionInfo[position]);
			IT.setText(inputtype[position]);
			return item;

		}

	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		try {
			Intent i = new Intent(Details.this , Info.class);
			Bundle basket = new Bundle();
			basket.putInt("data", position);
			i.putExtras(basket);
			startActivity(i);
		} catch (Exception e) {
			AlertDialog.Builder adb = new AlertDialog.Builder(Details.this);
			adb.setTitle("Error!!")
					.setMessage(" Intent Exception Raised!")
					.show();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.menu, menu);

		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		return super.onOptionsItemSelected(item);
	}

}
