package pregproject.pregproject.professor.daka;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.example.pregproject.R;

import pregproject.pregproject.other.hide_bar;

public class SubActivity_daka_sport_a extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_daka_sport_a);

        /**
         * 隐藏导航栏等，做到全屏
         */
        hide_bar hide_bar = new hide_bar(SubActivity_daka_sport_a.this);
        hide_bar.hide();

        LinearLayout ll_pro_daka_r=findViewById(R.id.ll_pro_daka_r);
        ll_pro_daka_r.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(SubActivity_daka_sport_a.this,SubActivity_daka_sports_detail.class));

            }
        });

    }

    public void back(View view) {
        finish();
    }
}