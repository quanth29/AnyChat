package com.xwh.anychat.fragment;

import java.util.ArrayList;
import java.util.List;

import org.jivesoftware.smackx.search.ReportedData.Row;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.xwh.anychat.BaseActivity;
import com.xwh.anychat.R;
import com.xwh.anychat.adapter.SearchResultListAdapter;
import com.xwh.anychat.biz.FriendBiz;
import com.xwh.anychat.biz.impl.FriendBizImpl;
import com.xwh.anychat.config.Constants;
import com.xwh.anychat.entity.SearchResultEntity;
import com.xwh.anychat.util.DebugUtil;

public class SearchFriendFragment extends Fragment implements OnClickListener, OnItemClickListener {

	private EditText searchFriEt;
	private Button startSearchBtn;
	private ListView searchResultLv;

	private FriendBiz friendBiz;
	private List<Row> resultRowList;

	private List<SearchResultEntity> searchResultEntities;
	private SearchResultListAdapter searchResultListAdapter;

	private ResultListHandler resultListHandler;

	private VCardResultDialogFragment vCardResultDialogFragment;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View layoutView = inflater.inflate(R.layout.fragment_add_new_friends, container, false);
		searchFriEt = (EditText) layoutView.findViewById(R.id.search_searchet);
		startSearchBtn = (Button) layoutView.findViewById(R.id.search_startsearchbtn);
		startSearchBtn.setOnClickListener(this);
		searchResultLv = (ListView) layoutView.findViewById(R.id.search_resultlv);
		searchResultLv.setOnItemClickListener(this);
		return layoutView;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.search_startsearchbtn:
			if (searchFriEt.getText().toString().equals("")) {
				return;
			}
			BaseActivity.globalHandler.sendEmptyMessage(Constants.GLOBAL_CODE_COMMON_PROGRESS_DIALOG_SHOW);
			if (resultListHandler == null) {
				resultListHandler = new ResultListHandler();
			}
			if (friendBiz == null) {
				friendBiz = new FriendBizImpl();
			}
			if (searchResultEntities == null) {
				searchResultEntities = new ArrayList<SearchResultEntity>();
			}
			searchResultEntities.clear();
			new Thread(new Runnable() {

				@Override
				public void run() {
					resultRowList = friendBiz.searchPerson(getActivity(), searchFriEt.getText().toString());
					if (resultRowList == null) {
						resultListHandler.sendEmptyMessage(Constants.SEARCH_FRIENDS_ERR);
					} else {
						parserSearchResult(searchResultEntities, resultRowList);
						resultListHandler.sendEmptyMessage(Constants.SEARCH_FRIENDS_OK);
					}
					BaseActivity.globalHandler.sendEmptyMessage(Constants.GLOBAL_CODE_COMMON_PROGRESS_DIALOG_DISMISS);
				}
			}).start();

			break;

		default:
			break;
		}
	}

	// 将搜索结果转换为SearchResultEntity对象以便显示
	private void parserSearchResult(List<SearchResultEntity> searchResultEntities, List<Row> resultRows) {
		if (searchResultEntities == null) {
			searchResultEntities = new ArrayList<SearchResultEntity>();
		}
		for (int i = 0; i < resultRows.size(); i++) {
			SearchResultEntity searchResultEntity = new SearchResultEntity(resultRows.get(i));
			searchResultEntities.add(searchResultEntity);
			DebugUtil.Log("Search result " + searchResultEntity.toString() + " has been displayed");
		}
	}

	class ResultListHandler extends Handler {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case Constants.SEARCH_FRIENDS_OK:
				if (resultRowList.size() == 0) {
					Toast.makeText(getActivity(), getResources().getString(R.string.search_noresult), Toast.LENGTH_SHORT).show();
					searchResultListAdapter.notifyDataSetChanged();
					return;
				}
				if (searchResultListAdapter == null) {
					searchResultListAdapter = new SearchResultListAdapter(getActivity(), searchResultEntities);
				} else {
					searchResultListAdapter.notifyDataSetChanged();
				}
				searchResultLv.setAdapter(searchResultListAdapter);
				break;
			case Constants.SEARCH_FRIENDS_ERR:
				Toast.makeText(getActivity(), getResources().getString(R.string.search_resulterr), Toast.LENGTH_SHORT).show();
				break;
			default:
				break;
			}
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		if (vCardResultDialogFragment == null) {
			vCardResultDialogFragment = new VCardResultDialogFragment();
		}
		Bundle bundle = new Bundle();
		bundle.putString(Constants.VCARD_USERNAME, searchResultEntities.get(position).getUsername());
		vCardResultDialogFragment.setArguments(bundle);
		vCardResultDialogFragment.show(getChildFragmentManager(), "VcardInfo");
	}

}
