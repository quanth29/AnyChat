package com.xwh.anychat.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;

/**
 * Created by 萧文翰 on 2015/3/18.
 */
public class FriendListAdapter extends BaseExpandableListAdapter{



    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        return null;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 0;
    }

    @Override
    public int getChildType(int groupPosition, int childPosition) {
        return super.getChildType(groupPosition, childPosition);
    }

    @Override
    public int getChildTypeCount() {
        return super.getChildTypeCount();
    }

    @Override
    public int getGroupCount() {
        return 0;
    }

    @Override
    public int getGroupType(int groupPosition) {
        return super.getGroupType(groupPosition);
    }

    @Override
    public int getGroupTypeCount() {
        return super.getGroupTypeCount();
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public long getCombinedChildId(long groupId, long childId) {
        return super.getCombinedChildId(groupId, childId);
    }

    @Override
    public long getCombinedGroupId(long groupId) {
        return super.getCombinedGroupId(groupId);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return null;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return null;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        return null;
    }

    @Override
    public boolean isEmpty() {
        return super.isEmpty();
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}
