package com.android.a4sp;

import java.util.ArrayList;
import java.util.HashMap;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import com.android.a4sp.play.PlaySound;
import com.android.a4sp.util.ParsingDataXml;
import android.view.View.OnClickListener;

public class A4SPActivity extends ListActivity {
	private static String TAG = "A4SPActivity";
	private EditText editText;
	private Button btnSearch;
	private String title;
	private boolean mToggleIndeterminate = false;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		setContentView(R.layout.listsearch);

		setProgressBarIndeterminateVisibility(mToggleIndeterminate);
		btnSearch = (Button) findViewById(R.id.btnSearch);
		editText = (EditText) findViewById(R.id.textTitle);

		btnSearch.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				title = editText.getText().toString();
				mToggleIndeterminate = !mToggleIndeterminate;
				setProgressBarIndeterminateVisibility(mToggleIndeterminate);
				setProgress(0);
				downloadData(title);

			}
		});
	}

	public void downloadData(String param) {
		ArrayList<HashMap<String, String>> arrayList = new ArrayList<HashMap<String, String>>();
		String dataXML = ParsingDataXml.getXML(param);
		// Log.i(TAG, dataXML);
		Document document = ParsingDataXml.XMLFromString(dataXML);
		NodeList nodeList = document.getElementsByTagName("file");
		
		
		
		
		for (int i = 0; i < nodeList.getLength(); i++) {
			HashMap<String, String> hashMap = new HashMap<String, String>();
			Element element = (Element) nodeList.item(i);
			
			
			//Log.i("name >> ", ParsingDataXml.getValue(element, "name"));
			
			String judul = ParsingDataXml.getValue(element, "name");
			hashMap.put("name", judul);
//			if (MimeTypeMap.getFileExtensionFromUrl(judul).equals("mp3")) {
//			hashMap.put("name", judul);
//			}
			
			hashMap.put(
					"upload-date",
					"Last update : "
							+ ParsingDataXml.getValue(element, "upload-date"));
			hashMap.put("size",
					"Size : " + ParsingDataXml.getValue(element, "size"));
			hashMap.put("flash-preview-url",
					ParsingDataXml.getValue(element, "flash-preview-url"));
			arrayList.add(hashMap);
		}
		
	

		ListAdapter adapter = new SimpleAdapter(this, arrayList, R.layout.main,
				new String[] { "name", "update", "size" }, new int[] {
						R.id.song_title, R.id.song_update, R.id.song_size });
		setListAdapter(adapter);

		final ListView listView = getListView();
		listView.setTextFilterEnabled(true);
		listView.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> str, View view,
					int position, long id) {
				@SuppressWarnings("unchecked")
				HashMap<String, String> obj = (HashMap<String, String>) listView
						.getItemAtPosition(position);
				String url = obj.get("flash-preview-url").toString();
				Intent intent = new Intent(A4SPActivity.this, PlaySound.class);
				intent.putExtra("dataUrl", url);
				Log.i(TAG, "Data req => " + url);
				startActivityForResult(intent, position);
			}
		});

	}

}