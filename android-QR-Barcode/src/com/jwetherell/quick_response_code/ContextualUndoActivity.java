package com.jwetherell.quick_response_code;

import android.content.Intent;
import android.os.Bundle;

public class ContextualUndoActivity extends MyListActivity{

	private ArrayAdapter<String> mAdapter;
	Intent i;
	Database db;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mAdapter = createListAdapter();
		db = new Database(this);
		ContextualUndoAdapter contextualUndoAdapter = new ContextualUndoAdapter(
				mAdapter, R.layout.undo_row, R.id.undo_row_undobutton, db);
		contextualUndoAdapter.setAbsListView(getListView());
		getListView().setAdapter(contextualUndoAdapter);
		contextualUndoAdapter.setDeleteItemCallback(new MyDeleteItemCallback());
	}

	private class MyDeleteItemCallback implements
			ContextualUndoAdapter.DeleteItemCallback {

		@Override
		public void deleteItem(int position,String x) {
			mAdapter.remove(position);
			db.DeleteRecord(x);
			mAdapter.notifyDataSetChanged();
		}

		@Override
		public void deleteItem(int position) {
			// TODO Auto-generated method stub
			
		}
	}
}