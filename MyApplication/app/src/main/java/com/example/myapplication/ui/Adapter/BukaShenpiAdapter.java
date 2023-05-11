package com.example.myapplication.ui.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.myapplication.R;
import com.example.myapplication.ui.Entity.Item.ItemBuka;
import com.example.myapplication.ui.Entity.Punch;

import java.util.List;

public class BukaShenpiAdapter extends ArrayAdapter<ItemBuka> {
    private int resourceId;

    public BukaShenpiAdapter(Context context, int resourceId,List<ItemBuka> objects) {
        super(context, resourceId, objects);
        this.resourceId=resourceId;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ItemBuka DakaItem=getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceId,parent,false);
        TextView tv_name=(TextView) view.findViewById(R.id.tv_name);
        TextView result=(TextView) view.findViewById(R.id.tv_result);
        TextView reason=(TextView) view.findViewById(R.id.tv_reason);
        TextView shenpi_result=(TextView) view.findViewById(R.id.tv_shenpi_result);
        TextView tv_applaytime=(TextView) view.findViewById(R.id.tv_applaytime);

        shenpi_result.setText(DakaItem.getShenpi_result());
        result.setText(DakaItem.getPunch_result());
        tv_name.setText(String.valueOf(DakaItem.getName()));
        reason.setText(DakaItem.getReason());
        tv_applaytime.setText(DakaItem.getApplaydate());
        result.setText(DakaItem.getPunch_result());
        return view;

    }
}
