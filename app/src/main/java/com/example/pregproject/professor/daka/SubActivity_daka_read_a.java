package com.example.pregproject.professor.daka;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.example.pregproject.R;
import com.example.pregproject.other.MainActivity_login;
import com.example.pregproject.other.hide_bar;

public class SubActivity_daka_read_a extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_daka);

        /**
         * 隐藏导航栏等，做到全屏
         */
        hide_bar hide_bar = new hide_bar(SubActivity_daka_read_a.this);
        hide_bar.hide();


        LinearLayout ll_pro_daka_r=findViewById(R.id.ll_pro_daka_r);

        ll_pro_daka_r.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(SubActivity_daka_read_a.this,SubActivity_daka_read_lv.class));

            }
        });

    }

    public void back(View view) {
        finish();
    }
}