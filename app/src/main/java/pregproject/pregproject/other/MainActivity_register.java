package pregproject.pregproject.other;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.pregproject.R;

import java.util.HashMap;
import java.util.Map;

public class MainActivity_register extends AppCompatActivity {


    EditText et_name,et_key,et_phone;
    CheckBox cb_user,cb_pro,cb_women,cb_man;
    Integer user_type=1;
    String sex="男";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_register);

        /**
         * 隐藏导航栏等，做到全屏
         */
        hide_bar hide_bar = new hide_bar(MainActivity_register.this);
        hide_bar.hide();

        et_name = findViewById(R.id.et_name);
        et_key = findViewById(R.id.et_key);
        cb_user = findViewById(R.id.cb_mon);
        cb_pro = findViewById(R.id.cb_pro);
        et_phone=findViewById(R.id.et_phone);
        cb_women=findViewById(R.id.cb_women);
        cb_man=findViewById(R.id.cb_man);
        cb_pro.setChecked(false);
        cb_user.setChecked(true);
        cb_man.setChecked(true);
        cb_women.setChecked(false);
        cb_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                user_type = 1;
                cb_pro.setChecked(false);
            }
        });

        cb_pro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                user_type = 2;
                cb_user.setChecked(false);
            }
        });

        cb_women.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sex="女";
                cb_man.setChecked(false);


            }
        });

        cb_man.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                sex="男";
                cb_women.setChecked(false);

            }
        });


    }


    public void back(View view) {
        finish();
    }



    public void register(View view) {
        InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        im.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        SQLiteOpenHelper helper = MySQLiteOpenHelper.getmInstance(MainActivity_register.this);
        SQLiteDatabase w_db = helper.getWritableDatabase();


        String user_name = et_name.getText().toString().trim();//用户名
        String user_pw = et_key.getText().toString().trim();//密码
        String user_phone=et_phone.getText().toString().trim();


        /**
         * 这是注册账号页，现在要把用户信息存进数据库
         */


            /**
             * 将信息插入数据库,变量命名同数据库的属性的命名。
             */
            //请求地址
            String url = "http://114.132.251.166:2233/women/RegistServlet";    //注①
            String tag = "regist";    //注②

            //取得请求队列
            RequestQueue requestQueue = Volley.newRequestQueue(this);

            //防止重复请求，所以先取消tag标识的请求队列
            requestQueue.cancelAll(tag);

            //创建StringRequest，定义字符串请求的请求方式为POST(省略第一个参数会默认为GET方式)

            final StringRequest request = new StringRequest(Request.Method.POST, url,  new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                }

            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    //做自己的响应错误操作，如Toast提示（“请稍后重试”等）
                    Log.e("TAG", error.getMessage(), error);
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> map = new HashMap<String, String>();
                    map.put("phone", user_phone);
                    map.put("password", user_pw);
                    map.put("name", user_name);
                    map.put("type", String.valueOf(user_type));
                    map.put("sex",sex);
                    return map;
                }
            };

            //设置Tag标签
            request.setTag(tag);

            //将请求添加到队列中
            requestQueue.add(request);

            Toast.makeText( MainActivity_register.this,"注册成功", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(MainActivity_register.this,MainActivity_login.class));
            finish();
    }
}