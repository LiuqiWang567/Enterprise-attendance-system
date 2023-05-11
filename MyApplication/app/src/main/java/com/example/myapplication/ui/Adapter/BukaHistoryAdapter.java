package com.example.myapplication.ui.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.myapplication.R;
import com.example.myapplication.ui.Entity.Item.ItemDaka;
import com.example.myapplication.ui.Entity.Punch;

import java.util.List;

public class BukaHistoryAdapter  extends ArrayAdapter<Punch> {
    private int resourceId;

    public BukaHistoryAdapter(@NonNull Context context, int resourceId, @NonNull List<Punch> objects) {
        super(context, resourceId, objects);
        this.resourceId=resourceId;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Punch DakaItem=getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceId,parent,false);
        TextView id=(TextView) view.findViewById(R.id.tv_id);
        TextView result=(TextView) view.findViewById(R.id.tv_result);
        TextView reason=(TextView) view.findViewById(R.id.tv_reason);
        TextView shenpi_result=(TextView) view.findViewById(R.id.tv_shenpi_result);
        TextView createtime=(TextView) view.findViewById(R.id.tv_createtime);
        shenpi_result.setText(DakaItem.getShenpi_result());
        id.setText(String.valueOf(DakaItem.getId()));
        reason.setText(DakaItem.getReason());
        createtime.setText(DakaItem.getCreatetime());
        result.setText(DakaItem.getPunch_result());
        return view;

    }
}
