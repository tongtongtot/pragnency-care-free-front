package pregproject.pregproject.professor.daka;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.pregproject.R;

import pregproject.pregproject.other.hide_bar;
import pregproject.pregproject.professor.Expertpost;

public class SubActivity_daka_read_detail extends AppCompatActivity {

    Expertpost expertpost=new Expertpost();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_daka_read_detail);

        /**
         * 隐藏导航栏等，做到全屏
         */
        hide_bar hide_bar = new hide_bar(SubActivity_daka_read_detail.this);
        hide_bar.hide();
         Intent intent = getIntent();
        Integer postid = intent.getIntExtra("postid",0);
        String username=intent.getStringExtra("username");
        String data=intent.getStringExtra("data");
        int pic_num=intent.getIntExtra("pic_cnt",0);
        String create=intent.getStringExtra("create");
        String title=intent.getStringExtra("title");
        String pic_url=intent.getStringExtra("pic_url");
        int commentnum=intent.getIntExtra("commentnum",0);

        /**
         * 找出postid对应的专家帖子的内容，存到变量expertpost里面。-----------------------------------------------------------------
         */

        TextView tv_pro_title = findViewById(R.id.tv_pro_title);
        TextView tv_pro_name=findViewById(R.id.tv_pro_name);
        TextView tv_pro_data=findViewById(R.id.tv_pro_data);
        tv_pro_name.setText(username);
        tv_pro_title.setText(title);
        /**
         * 通过发布者的id找出发布者的用户名，填进下句代码----------------------------------------------------------------
         */
        //tv_pro_name.setText(发布者的用户名);
        tv_pro_data.setText(data);

        Button btn_jumpToSport = findViewById(R.id.btn_jumpToSport);

        btn_jumpToSport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(SubActivity_daka_read_detail.this,SubActivity_daka_sport_a.class));

            }
        });

    }


    public void back(View view) {
        finish();
    }
}