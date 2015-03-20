package com.xwh.anychat.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.xwh.anychat.R;
import com.xwh.anychat.entity.RosterEntity;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by 萧文翰 on 2015/3/18.
 */
public class FriendListAdapter extends BaseExpandableListAdapter{

    private Context context;
    private ArrayList<RosterEntity.GroupRosterEntity> groupRosterEntity;

    public FriendListAdapter() {
        super();
    }

    public FriendListAdapter(Context context, ArrayList<RosterEntity.GroupRosterEntity> groupRosterEntity) {
        this.context = context;
        this.groupRosterEntity = groupRosterEntity;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public ArrayList<RosterEntity.GroupRosterEntity> getGroupRosterEntity() {
        return groupRosterEntity;
    }

    public void setGroupRosterEntity(ArrayList<RosterEntity.GroupRosterEntity> groupRosterEntity) {
        this.groupRosterEntity = groupRosterEntity;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ChildViewHolder viewHolder=new ChildViewHolder();
        if(convertView!=null&&convertView.getTag()!=null){
            viewHolder=(ChildViewHolder)convertView.getTag();
        }else{
            convertView= LayoutInflater.from(context).inflate(R.layout.item_friendlist_child, null);
            viewHolder = new ChildViewHolder();
            viewHolder.childNameTv = (TextView) convertView.findViewById(R.id.friendlist_child_name_tv);
            viewHolder.childJIdTv = (TextView) convertView.findViewById(R.id.friendlist_child_jid_tv);
            viewHolder.childStatusTv=(TextView)convertView.findViewById(R.id.friendlist_child_status_tv);
            convertView.setTag(viewHolder);
        }
        viewHolder.childNameTv.setText(groupRosterEntity.get(groupPosition).getSingleRosterList().get(childPosition).getName());
        viewHolder.childJIdTv.setText(groupRosterEntity.get(groupPosition).getSingleRosterList().get(childPosition).getUser());
        viewHolder.childStatusTv.setText(groupRosterEntity.get(groupPosition).getSingleRosterList().get(childPosition).getStatus());
        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        if(groupRosterEntity!=null&&groupRosterEntity.get(groupPosition)!=null&&groupRosterEntity.get(groupPosition).getSingleRosterList()!=null){
            return groupRosterEntity.get(groupPosition).getSingleRosterList().size();
        }else{
            return 0;
        }
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
        if(groupRosterEntity==null){
            return 0;
        }
        return groupRosterEntity.size();
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
        return childPosition;
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
        return groupPosition;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return groupRosterEntity.get(groupPosition).getSingleRosterList().get(childPosition);
    }

    @Override
    public Object getGroup(int groupPosition) {
        if(groupRosterEntity==null){
            return null;
        }
        return groupRosterEntity.get(groupPosition);
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        GroupViewHolder viewHolder=new GroupViewHolder();
        if(convertView!=null&&convertView.getTag()!=null){
            viewHolder=(GroupViewHolder)convertView.getTag();
        }else{
            convertView= LayoutInflater.from(context).inflate(R.layout.item_friendlist_parent, null);
            viewHolder = new GroupViewHolder();
            viewHolder.groupNameTv = (TextView) convertView.findViewById(R.id.friendlist_parent_tv);
            convertView.setTag(viewHolder);
        }
        viewHolder.groupNameTv.setText(groupRosterEntity.get(groupPosition).getGroupName());
        return convertView;
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

    private class GroupViewHolder{
        private TextView groupNameTv;
    }

    private class ChildViewHolder{
        private TextView childNameTv;
        private TextView childJIdTv;
        private TextView childStatusTv;
    }

}
