package com.example.myapplication.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.widget.RadioGroup;
import android.widget.TabHost;

import com.example.myapplication.R;

public class Index2 extends TabActivity implements RadioGroup.OnCheckedChangeListener {

    private RadioGroup mainTab;
    private TabHost tabhost;
    private Intent home;
    private Intent myinfo;
    private Intent exit;
    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_index2);
        mainTab=(RadioGroup)findViewById(R.id.main_tab);
        mainTab.setOnCheckedChangeListener(this);
        tabhost = getTabHost();

        home = new Intent(this, Home_emp.class);
        tabhost.addTab(tabhost.newTabSpec("home")
                .setIndicator(getResources().getString(R.string.mainindex), getResources().getDrawable(R.drawable.icon_1_n))
                .setContent(home));

        myinfo = new Intent(this, Myinfo.class);
        tabhost.addTab(tabhost.newTabSpec("myinfo")
                .setIndicator(getResources().getString(R.string.myinfo), getResources().getDrawable(R.drawable.icon_3_n))
                .setContent(myinfo));

        exit = new Intent(this, Login.class);
        tabhost.addTab(tabhost.newTabSpec("exit")
                .setIndicator(getResources().getString(R.string.exit), getResources().getDrawable(R.drawable.icon_5_n))
                .setContent(exit));
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {

        switch(checkedId){
            case R.id.radio_button0:
                this.tabhost.setCurrentTabByTag("home");
                break;
            case R.id.radio_button1:
                this.tabhost.setCurrentTabByTag("myinfo");
                break;
            case R.id.radio_button2:
                this.tabhost.setCurrentTabByTag("exit");
                break;

        }
    }
}