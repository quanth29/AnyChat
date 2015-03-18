package com.xwh.anychat.fragment;

/**
 * Created by 萧文翰 on 2015/3/18.
 */
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xwh.anychat.R;

public class MoreOprFragment extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View layoutView = inflater.inflate(R.layout.fragment_moreopr, container, false);
		return layoutView;
	}
}
