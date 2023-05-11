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

import java.util.List;

public class DakaHistoryAdapter  extends ArrayAdapter<ItemDaka> {
    private int resourceId;

    public DakaHistoryAdapter(Context context, int resourceId,List<ItemDaka> objects) {
        super(context, resourceId, objects);
        this.resourceId=resourceId;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ItemDaka DakaItem=getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceId,parent,false);
        TextView tv_id=(TextView) view.findViewById(R.id.pid);
        TextView time1=(TextView) view.findViewById(R.id.tv_qiandao);
        TextView time2=(TextView) view.findViewById(R.id.tv_qiantui);
        TextView result=(TextView) view.findViewById(R.id.tv_result);
        TextView location=(TextView) view.findViewById(R.id.tv_location);

        tv_id.setText( String.valueOf(DakaItem.getId()));
        time1.setText(DakaItem.getTime1());
        time2.setText(DakaItem.getTime2());
        result.setText(DakaItem.getResult());
        location.setText(DakaItem.getLocation());

        return view;

    }
}
