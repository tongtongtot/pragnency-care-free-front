package pregproject.pregproject.other;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
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
import pregproject.pregproject.identity_Pro.MainActivity_pro;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity_login extends AppCompatActivity {


    Button btn_login;
    EditText et_phone,et_key;
    public static String user;
    public static Integer userId;
    public static User nowuser =new User();;
    //全局user类对象
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_login);

        /**
         * 隐藏导航栏等，做到全屏
         */
        hide_bar hide_bar = new hide_bar(MainActivity_login.this);
        hide_bar.hide();

        btn_login=findViewById(R.id.btn_login);
        et_phone=findViewById(R.id.et_name);
        et_key=findViewById(R.id.et_key);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String phone=et_phone.getText().toString().trim();//trim是去除空格
                String password=et_key.getText().toString().trim();
                InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                im.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                if(TextUtils.isEmpty(phone)||TextUtils.isEmpty(password)){//判空
                    Toast.makeText(MainActivity_login.this, "用户名或密码为空", Toast.LENGTH_SHORT).show();
                }
                else {
                    //请求地址
                    String url = "http://114.132.251.166:2233/women/LoginServlet";    //注①
                    String tag = "regist";    //注②

                    //取得请求队列
                    RequestQueue requestQueue = Volley.newRequestQueue(getBaseContext());

                    //防止重复请求，所以先取消tag标识的请求队列
                    requestQueue.cancelAll(tag);

                    //创建StringRequest，定义字符串请求的请求方式为POST(省略第一个参数会默认为GET方式)

                    final StringRequest request = new StringRequest(Request.Method.POST, url,  new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            System.out.println("服务器端返回的消息是："+response);
                                if(!response.equals("#")){
                                    //登录成功

                                                      //保存当前用户名称和当前用户id号
                                 try {
                                     JSONObject jsonObject =  new JSONObject(response);

                                        nowuser.user_id=jsonObject.getInt("user_id");
                                        nowuser.user_type=jsonObject.getInt("user_type");
                                        nowuser.love_id=jsonObject.getInt("love_id");
                                        nowuser.user_name=jsonObject.getString("user_name");
                                        nowuser.sex=jsonObject.getString("sex");
                                        nowuser.friendlist=jsonObject.getString("friendlist");

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    System.out.println("******************************username id:"+nowuser.user_name+nowuser.user_id);
                                    Toast.makeText( MainActivity_login.this,"登录成功", Toast.LENGTH_SHORT).show();

                                    if(nowuser.user_type==1){
                                        startActivity(new Intent(MainActivity_login.this,MainActivity.class));
                                    }else{
                                        startActivity(new Intent(MainActivity_login.this, MainActivity_pro.class));
                                    }
                                    finish();
                                    //startActivity(new Intent(MainActivity_login.this,MainActivity.class));
                                }else{
                                    Toast.makeText( MainActivity_login.this,"用户名或密码错误", Toast.LENGTH_SHORT).show();

                                }
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
                            map.put("phone", phone);
                            map.put("password", password);
                            return map;
                        }
                    };

                    //设置Tag标签
                    request.setTag(tag);

                    //将请求添加到队列中
                    requestQueue.add(request);
                        }

                    }

        });

    }

    public void register(View view) {
        startActivity(new Intent(MainActivity_login.this,MainActivity_register.class));
    }
}