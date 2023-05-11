package com.example.myapplication.ui.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.ui.Entity.Item.ItemLeave;
import com.example.myapplication.ui.Entity.Leave;

import java.util.List;

public class LeaveShenpiAdapter extends ArrayAdapter<ItemLeave> {
    private int resourceId;

    public LeaveShenpiAdapter(Context context, int resourceId, List<ItemLeave> objects) {
        super(context, resourceId, objects);
        this.resourceId = resourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ItemLeave leave = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
        TextView name = (TextView) view.findViewById(R.id.tv_name);
        TextView tv_leavetype = (TextView) view.findViewById(R.id.tv_leavetype);
        TextView tv_ltime1 = (TextView) view.findViewById(R.id.tv_ltime1);
        TextView tv_ltime2 = (TextView) view.findViewById(R.id.tv_ltime2);
        TextView tv_reason = (TextView) view.findViewById(R.id.tv_reason);
        TextView tv_shenpi_result = (TextView) view.findViewById(R.id.tv_shenpi_result);


        name.setText( String.valueOf(leave.getName()));
        tv_leavetype.setText(leave.getLeavetype());
        tv_ltime1.setText(leave.getTime1());
        tv_ltime2.setText(leave.getTime2());
        tv_reason.setText(leave.getReason());
        tv_shenpi_result.setText(leave.getShenpi_result());


        return view;

    }
}