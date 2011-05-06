package com.springsource.greenhouse.events.sessions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.social.greenhouse.api.EventSession;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.springsource.greenhouse.AbstractGreenhouseListActivity;
import com.springsource.greenhouse.R;
import com.springsource.greenhouse.controllers.NavigationManager;
import com.springsource.greenhouse.util.SharedDataManager;

public class EventSessionsListActivity extends AbstractGreenhouseListActivity {
	private List<EventSession> mSessions;
	
	
	//***************************************
	// Activity methods
	//***************************************
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public void onStart() {
		super.onStart();
		downloadSessions();
	}
	
	
	//***************************************
    // ListActivity methods
    //***************************************
	@Override
	protected void  onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		
		SharedDataManager.setCurrentSession(getSessions().get(position));
		NavigationManager.startActivity(v.getContext(), EventSessionDetailsActivity.class);
	}
	
	
	//***************************************
	// Public methods
	//***************************************
	public List<EventSession> getSessions() {
		return mSessions;
	}
	
	public void setSessions(List<EventSession> sessions) {
		mSessions = sessions;
		refreshSessions();
	}
	

	//***************************************
	// Protected methods
	//***************************************
	protected void refreshSessions() {
		if (mSessions == null) {
			return;
		}

		List<Map<String,String>> sessionMaps = new ArrayList<Map<String,String>>();
		
		// TODO: Is there w way to populate the table from a Session instead of a Map?
		for (EventSession session : mSessions) {
			Map<String, String> map = new HashMap<String, String>();			
			map.put("title", session.getTitle());
			map.put("leaders", session.getJoinedLeaders(", "));
			sessionMaps.add(map);
		}		
		
		SimpleAdapter adapter = new SimpleAdapter(
				this,
				sessionMaps,
				R.layout.event_sessions_list_item,
				new String[] { "title", "leaders" },
				new int[] { R.id.title, R.id.subtitle } );
		
		setListAdapter(adapter);
	}
	
	protected void downloadSessions() {
		// override in child class
	}
}