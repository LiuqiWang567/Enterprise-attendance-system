package com.example.myapplication.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.myapplication.R;

public class MainShenpi extends AppCompatActivity {

  private TextView btn_toshenpi_buka;
  private TextView  btn_toshenpi_leave;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_shenpi);
        btn_toshenpi_buka  =findViewById(R.id.btn_toshenpi_buka);
        btn_toshenpi_leave=findViewById(R.id.btn_toshenpi_leave);
        btn_toshenpi_buka.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(MainShenpi.this, Bukashenpi.class);
                startActivity(intent2);
            }
        });
        btn_toshenpi_leave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(MainShenpi.this, Leaveshenpi.class);
                startActivity(intent2);
            }
        });
    }
}