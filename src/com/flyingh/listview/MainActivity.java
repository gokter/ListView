package com.flyingh.listview;

import java.util.ArrayList;
import java.util.List;

import com.flyingh.service.DataService;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends ActionBarActivity {

	private static final int TOTAL_PAGE_NUMBER = 2;
	private static final int MAX_SIZE = 20;
	private ListView listView;
	private final List<String> datas = new ArrayList<>();
	private View footerView;
	private ArrayAdapter<String> adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		listView = (ListView) findViewById(R.id.listView);
		adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, datas);
		footerView = getLayoutInflater().inflate(R.layout.footer_view, null);
		listView.addFooterView(footerView);
		listView.setAdapter(adapter);
		listView.removeFooterView(footerView);
		listView.setOnScrollListener(new OnScrollListener() {

			private boolean isLoaded = true;

			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
				final int currentPage = totalItemCount % MAX_SIZE == 0 ? totalItemCount / MAX_SIZE : totalItemCount
						/ MAX_SIZE + 1;
				int lastVisiblePosition = listView.getLastVisiblePosition();
				if (currentPage < TOTAL_PAGE_NUMBER && lastVisiblePosition == totalItemCount - 1 && isLoaded) {
					new AsyncTask<Integer, Void, List<String>>() {

						@Override
						protected void onPreExecute() {
							isLoaded = false;
							listView.addFooterView(footerView);
						}

						@Override
						protected List<String> doInBackground(Integer... params) {
							return DataService.getData(currentPage, MAX_SIZE);
						}

						@Override
						protected void onPostExecute(java.util.List<String> result) {
							isLoaded = true;
							datas.addAll(result);
							adapter.notifyDataSetChanged();
							listView.removeFooterView(footerView);
						}
					}.execute(currentPage);
				}
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
