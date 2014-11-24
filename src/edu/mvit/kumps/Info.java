package edu.mvit.kumps;

import java.io.IOException;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

public class Info extends Activity {

	boolean ans = true;
	String[] arr = new String[3];
	Cursor cur;
	int cnt = 0;
	int val;
	Spinner dropdown;
	String cname;
	DBHelper helper;
	int count ;
	SQLiteDatabase db;
	String[] table = { "CONSTITUENCY", "MP", "PARTY" };
	String[] column = { "CONS_NAME", "MP_NAME", "PARTY_NAME" };
	String[] options;
	int[] img = { R.drawable.lotus, R.drawable.palm, R.drawable.woman };

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.info);
		Bundle basket = new Bundle();
		basket = getIntent().getExtras();
		val = basket.getInt("data");
		
		helper = new DBHelper(getApplicationContext());
		try {
			helper.createDataBase();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		db = helper.getReadableDatabase();
		dropdown = (Spinner) findViewById(R.id.spinner1);
		populate();
		
		dropdown.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				cname = parent.getItemAtPosition(position).toString();
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub

			}

		});

	}

	public void populate() {
		String tab = table[val];
		String col = column[val];
		try {
			Cursor cur = db.query(tab, new String[] { col }, null, null, null,
					null, col);
			cur.moveToFirst();
			count = cur.getCount();
			options = new String[cur.getCount()];
			int i = 0;
			do {
				options[i] = cur.getString(0);
				i++;
			} while (cur.moveToNext());

			anotherAdapter adapter = new anotherAdapter();
			dropdown.setAdapter(adapter);
		
		} catch (Exception e) {
			AlertDialog.Builder adb = new AlertDialog.Builder(Info.this);
			adb.setTitle("Error!!").setMessage("populate Exception Raised!")
					.show();
		}
	}

	public void display(View btn) {
		if (val == 0) {
			constituency();
		} else if (val == 1) {
			mp();
		} else {
			party();
		}
	}

	public void constituency() {
		String[] Headers = { "Constituency Name", "Ruling Party", "Ruling MP",
				"Number Of People" };
		String[] values = new String[4];
		try {
			String msg = "SELECT CONS_NAME,MP_NAME,P_NAME,NO_OF_PEOPLE FROM "
					+ "CONSTITUENCY , MP WHERE CONS_NAME = C_NAME AND CONS_NAME = \""
					+ cname + "\";";
			Cursor cur = db.rawQuery(msg, null);

			cur.moveToFirst();
			
			if (cur.getCount() != 0) {
				values[0] = cur.getString(0);
				values[1] = cur.getString(2);
				values[2] = cur.getString(1);
				values[3] = cur.getString(3);

				ListView lv = (ListView) findViewById(R.id.infov);
				NewAdapter adapter = new NewAdapter(Headers, values, 0);
				lv.setAdapter(adapter);
				lv.requestFocus();

			} else {
				AlertDialog.Builder adb = new AlertDialog.Builder(Info.this);
				adb.setTitle("Error!!")
						.setMessage("Constituency Not Present in the Database")
						.show();
			}

		} catch (SQLException e) {
			AlertDialog.Builder adb = new AlertDialog.Builder(Info.this);
			adb.setTitle("Error!!")
					.setMessage("Constituency Not Present in the Database")
					.show();
		} catch (Exception e) {
			AlertDialog.Builder adb = new AlertDialog.Builder(Info.this);
			adb.setTitle("Error!!").setMessage("Exception Raised!").show();
		}
	}

	public void mp() {
		String[] Headers = { "MP Name", "Party Name", "Constituency Name",
				"Education", "Pending Criminal Case", "No Of Elections",
				"Assets" };
		String[] values = new String[7];
		try {
			String msg = "SELECT MP_NAME,P_NAME,C_NAME,"
					+ "EDUCATION,PENDING_CRIMINAL_CASE,NO_OF_ELECTIONS,TOTAL_ASSESTS"
					+ " FROM MP,BACKGROUND,ASSESTS WHERE "
					+ "MP_NAME = MP_NAM AND MP_NAME = MP_N AND MP_NAME = \""
					+ cname + "\";";
			Cursor cur = db.rawQuery(msg, null);

			cur.moveToFirst();

			if (cur.getCount() != 0) {

				values[0] = cur.getString(0);
				values[1] = cur.getString(1);
				values[2] = cur.getString(2);
				values[3] = cur.getString(3);
				values[4] = cur.getString(4);
				values[5] = cur.getString(5);
				values[6] = cur.getString(6);

				ListView lv = (ListView) findViewById(R.id.infov);
				NewAdapter adapter = new NewAdapter(Headers, values, 0);
				lv.setAdapter(adapter);
				lv.requestFocus();

			} else {
				AlertDialog.Builder adb = new AlertDialog.Builder(Info.this);
				adb.setTitle("Error!!")
						.setMessage("Constituency Not Present in the Database")
						.show();
			}

		} catch (SQLException e) {
			AlertDialog.Builder adb = new AlertDialog.Builder(Info.this);
			adb.setTitle("Error!!")
					.setMessage("Constituency Not Present in the Database")
					.show();
		} catch (Exception e) {
			AlertDialog.Builder adb = new AlertDialog.Builder(Info.this);
			adb.setTitle("Error!!").setMessage("Exception Raised!").show();
		}
	}

	public void party() {

		String[] Headers = { "Party Name", "Party Symbol", "Party Head",
				"No of Constituencies" };

		String[] values = new String[4];
		try {
			String msg = " SELECT _id ,PARTY_NAME , PARTY_SYMBOL , PARTY_HEAD FROM PARTY "
					+ "WHERE  PARTY_NAME = \"" + cname + "\";";
			Cursor cur = db.rawQuery(msg, null);

			String msg2 = " SELECT COUNT(*) AS SUM FROM PARTY , MP ,CONSTITUENCY WHERE "
					+ "CONS_NAME = C_NAME AND P_NAME = PARTY_NAME AND P_NAME =\""
					+ cname + "\";";
			Cursor curSum = db.rawQuery(msg2, null);
			cur.moveToFirst();
			curSum.moveToFirst();

			if (cur.getCount() != 0) {
				int img = (int) cur.getLong(0);
				values[0] = cur.getString(1);
				values[1] = cur.getString(2);
				values[2] = cur.getString(3);
				values[3] = curSum.getString(0);

				ListView lv = (ListView) findViewById(R.id.infov);
				NewAdapter adapter = new NewAdapter(Headers, values, img - 1);
				lv.setAdapter(adapter);
				lv.requestFocus();

			} else {
				AlertDialog.Builder adb = new AlertDialog.Builder(Info.this);
				adb.setTitle("Error!!").setMessage("Cursor zero").show();
			}

		} catch (SQLException e) {
			AlertDialog.Builder adb = new AlertDialog.Builder(Info.this);
			adb.setTitle("Error!!")
					.setMessage("Constituency Not Present in the Database")
					.show();
		} catch (Exception e) {
			AlertDialog.Builder adb = new AlertDialog.Builder(Info.this);
			adb.setTitle("Error!!").setMessage("Exception Raised!").show();
		}

	}

	private class NewAdapter extends BaseAdapter {

		String[] values;
		String[] headers;
		int index;

		public NewAdapter(String[] head, String[] val, int ind) {
			values = val;
			headers = head;
			index = ind;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return values.length;
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return values[position];
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View item = convertView;
			if (item == null) {
				item = getLayoutInflater().inflate(R.layout.infooption, parent,
						false);
			}

			TextView st = (TextView) item.findViewById(R.id.st);
			TextView lt = (TextView) item.findViewById(R.id.lt);
			ImageView iv = (ImageView) item.findViewById(R.id.iv1);

			if (val == 2 && position == 1) {
				iv.setImageResource(img[index]);
			} else {
				iv.setVisibility(View.INVISIBLE);
			}
			st.setText(headers[position]);
			lt.setText(values[position]);
			return item;
		}

	}
	
	private class anotherAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return options.length;
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return options[position];
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			View item = convertView ;
			if(item == null){
				item = getLayoutInflater().inflate(R.layout.dropdown, parent, false);
			}
			
			TextView tv = (TextView)item.findViewById(R.id.op1);
			tv.setText(options[position]);
			return item;
		}
		
	}

	

}
