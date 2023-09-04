package pregproject.pregproject.publicc.daily;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.FrameLayout;

import com.example.pregproject.R;
import pregproject.pregproject.other.hide_bar;

public class SubActivity_new_daily extends AppCompatActivity {

    private FrameLayout fl_n_d;
    private CheckBox cb_daily,cb_feeling;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_new_daily);

        /**
         * 隐藏导航栏等，做到全屏
         */
        hide_bar hide_bar = new hide_bar(SubActivity_new_daily.this);
        hide_bar.hide();


        fl_n_d=findViewById(R.id.fl_n_d);
        cb_daily=findViewById(R.id.cb_daily);
        cb_feeling=findViewById(R.id.cb_feeling);


        BlankFragment_new_daily blankFragment_new_daily = BlankFragment_new_daily.newInstance();
        BlankFragment_new_feeling blankFragment_new_feeling = BlankFragment_new_feeling.newInstance();

        cb_daily.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                cb_feeling.setChecked(false);
                cb_daily.setChecked(true);

                FragmentManager fragmentManager=getSupportFragmentManager();//获取管理类
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fl_n_d,blankFragment_new_daily);//用一个BlankFragment1实例替换到R.id.fl这个控件里面去//就不用.xml中用name来静态绑定fragment了
                fragmentTransaction.commit();//需要提交才能执行

            }
        });


        cb_feeling.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                cb_feeling.setChecked(true);
                cb_daily.setChecked(false);

                FragmentManager fragmentManager=getSupportFragmentManager();//获取管理类
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fl_n_d,blankFragment_new_feeling);//用一个BlankFragment1实例替换到R.id.fl这个控件里面去//就不用.xml中用name来静态绑定fragment了
                fragmentTransaction.commit();//需要提交才能执行

            }
        });


    }





    public void back(View view) {
        finish();
    }
}



