package com.akira.advenceui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class SpinnerActivity extends Activity {

//	關連式的下拉選單 城市與地區 只有台北市＆新北市有定義資料
	
	Spinner spCity, spDist; //分開宣告
	ArrayAdapter<CharSequence> CityAdapter;
	ArrayAdapter<CharSequence> DistAdapter;
	Context context;
	TextView tvArea;
	int Dist = 0;
	int style = R.layout.spinnear_text;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_spinner);

		context = this;
		tvArea = (TextView) findViewById(R.id.tvArea);
		new Thread(new ProgressRunnable(context)).start();
		initSpinner();
	}

	public void initSpinner() {
		// CITY

		spCity = (Spinner) findViewById(R.id.spCity);
		CityAdapter = ArrayAdapter.createFromResource(this, R.array.city_cn, style);
		CityAdapter.setDropDownViewResource(style);
		spCity.setAdapter(CityAdapter);
		spCity.setOnItemSelectedListener(CityListener);

		// Dist
		spDist = (Spinner) findViewById(R.id.spDist);
		DistAdapter = ArrayAdapter.createFromResource(this, R.array.nonDist, style);
		DistAdapter.setDropDownViewResource(style);
		spDist.setAdapter(DistAdapter);
	}

	// Spinner Listener
	private AdapterView.OnItemSelectedListener CityListener = new AdapterView.OnItemSelectedListener() {
		@Override
		public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
			int city = spCity.getSelectedItemPosition();

			switch (city) {

			case 0:
				Dist = R.array.nonDist;
				setDist(Dist);
				break;

			case 1:
				// DistAdapter =

				Dist = R.array.Dist_Taipei;
				setDist(Dist);
				break;

			case 2:
				// DistAdapter =

				Dist = R.array.Dist_NewTaipei;
				setDist(Dist);
				break;

			case 3:
				Dist = R.array.Dist_ilian;
				setDist(Dist);

				// case ....
			default:
				break;

			}
		}

		@Override
		public void onNothingSelected(AdapterView<?> parent) {
			Toast.makeText(context, "您沒有選擇任何項目", Toast.LENGTH_SHORT).show();
		}
	};

	// change spinner UI to user selected
	public void setDist(int Dist) {
		DistAdapter = ArrayAdapter.createFromResource(context, Dist, style);
		spDist.setAdapter(DistAdapter);
	}

	// Button Control
	public void btnArea(View view) {
		switch (view.getId()) {

		case R.id.btnOK:
			Log.i("btnArea", spCity.getSelectedItemPosition() + "");

			String Selected = "";

			if (spCity.getSelectedItemPosition() > 0) {
				Selected = "選擇了：" + spCity.getSelectedItem() + spDist.getSelectedItem();

				String Area = String.valueOf(spDist.getSelectedItem());

				Intent intent = new Intent();
				intent.setClass(SpinnerActivity.this, MyListView.class);

				intent.putExtra("Area", Area);
				startActivity(intent);

			} else {
				Selected = "請先選擇地點";
			}

			Toast.makeText(context, Selected, Toast.LENGTH_SHORT).show();
			break;

		case R.id.btnCancle:
			Log.i("btnArea", "Cick Cancle");
			initSpinner();
			Toast.makeText(context, "Cancle", Toast.LENGTH_SHORT).show();

			break;
		}
	}

}
