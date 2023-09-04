package pregproject.pregproject.professor.daka;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pregproject.R;
import pregproject.pregproject.other.MainActivity;
import pregproject.pregproject.other.hide_bar;

public class SubActivity_daka_sports_detail extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_daka_sports_detail);

        /**
         * 隐藏导航栏等，做到全屏
         */
        hide_bar hide_bar = new hide_bar(SubActivity_daka_sports_detail.this);
        hide_bar.hide();

        Chronometer clock=findViewById(R.id.clock);
        TextView tv=findViewById(R.id.tv);
        Button btn_back=findViewById(R.id.btn_back);
        ImageView iv_done=findViewById(R.id.iv_done);
        clock.start();
        clock.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {

                if(SystemClock.elapsedRealtime()-chronometer.getBase()>=900000){
                    //超过则停止计时,并显示15分钟已用完
                    chronometer.stop();
                    Toast.makeText(SubActivity_daka_sports_detail.this,"运动完成！",Toast.LENGTH_LONG).show();
                    tv.setVisibility(View.VISIBLE);
                    btn_back.setVisibility(View.VISIBLE);
                    iv_done.setVisibility(View.VISIBLE);
                }

            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SubActivity_daka_sports_detail.this, MainActivity.class));
            }
        });

    }

    public void back(View view) {
        finish();
    }
}