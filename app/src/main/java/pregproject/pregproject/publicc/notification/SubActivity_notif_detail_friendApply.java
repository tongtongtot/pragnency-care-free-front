package pregproject.pregproject.publicc.notification;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.pregproject.R;
import pregproject.pregproject.other.MainActivity_login;
import pregproject.pregproject.other.hide_bar;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SubActivity_notif_detail_friendApply extends AppCompatActivity {

    TextView tv_sub_notif_friend_kind,msg;
    int apply_id;
    TextView nametext;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_notif_detail_friend_apply);

        /**
         * 隐藏导航栏等，做到全屏
         */
        hide_bar hide_bar = new hide_bar(SubActivity_notif_detail_friendApply.this);
        hide_bar.hide();

        //接消息
       intent = getIntent();
        Integer send_id = intent.getIntExtra("send_id",0);

        Integer apply_type = intent.getIntExtra("apply_type",0);
        String data = intent.getStringExtra("data");
       apply_id=intent.getIntExtra("apply_id",0);
       nametext=findViewById(R.id.tv_c_f_name);
        tv_sub_notif_friend_kind = findViewById(R.id.tv_sub_notif_friend_kind);
        msg=findViewById(R.id.et_notif_friend_msg);
        msg.setText(data);
        Button btn_notif_friend_accept=findViewById(R.id.btn_notif_friend_accept);
        Button btn_notif_friend_refuse=findViewById(R.id.btn_notif_friend_refuse);
        TextView et_notif_friend_msg=findViewById(R.id.et_notif_friend_msg);


        if(apply_type==1){
            tv_sub_notif_friend_kind.setText("普通好友");

        }else if (apply_type==2){
            tv_sub_notif_friend_kind.setText("亲密好友");

        }
        String url = "http://119.91.21.231:8080/women/FinduserServlet";    //注①
        String tag = "finduser";    //注②

        //取得请求队列
        RequestQueue requestQueue = Volley.newRequestQueue(getBaseContext());

        //防止重复请求，所以先取消tag标识的请求队列
        requestQueue.cancelAll(tag);

        //创建StringRequest，定义字符串请求的请求方式为POST(省略第一个参数会默认为GET方式)

        final StringRequest request = new StringRequest(Request.Method.POST, url,  new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if(!response.equals("#")){

                    try {

                        JSONObject jsonObject = (JSONObject) new JSONObject(response);



                        String sendname=jsonObject.getString("user_name");

                        nametext.setText(sendname);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }else{

                }
                /**
                 * 装填Fragment
                 */



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
                map.put("user_id", String.valueOf(send_id));
                map.put("findtype",String.valueOf(1));


                return map;
            }
        };
        //设置Tag标签
        request.setTag(tag);

        //将请求添加到队列中
        requestQueue.add(request);
        et_notif_friend_msg.setText(data);

        /**
         * 同意
         */
        btn_notif_friend_accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //判断是亲密还是普通好友
                int flag=1;
                if(apply_type==1){
                    /**
                     * 普通好友，写进数据库
                     */


                    Toast.makeText(SubActivity_notif_detail_friendApply.this, "您已成功添加对方为普通好友", Toast.LENGTH_SHORT).show();
                    MainActivity_login.nowuser.friendlist+=send_id+"#";

                }else if (MainActivity_login.nowuser.love_id==-1){

                    /**
                     * 亲密好友，写进数据库
                     * 判断是否已经有亲密好友了：
                     */
                    //没有：

                    Toast.makeText(SubActivity_notif_detail_friendApply.this, "您已成功添加对方为亲密好友", Toast.LENGTH_SHORT).show();
                    MainActivity_login.nowuser.love_id=send_id;

                }else{
                    flag=0;
                    Toast.makeText(SubActivity_notif_detail_friendApply.this, "添加失败，您已拥有亲密好友", Toast.LENGTH_SHORT).show();
                }
                if(flag==1){
                    //请求地址
                    String url = "http://119.91.21.231:8080/women/ApplyServlet";    //注①
                    String tag = "friend";    //注②

                    //取得请求队列
                    RequestQueue requestQueue = Volley.newRequestQueue(SubActivity_notif_detail_friendApply.this);

                    //防止重复请求，所以先取消tag标识的请求队列
                    requestQueue.cancelAll(tag);

                    //创建StringRequest，定义字符串请求的请求方式为POST(省略第一个参数会默认为GET方式)

                    final StringRequest request = new StringRequest(Request.Method.POST, url,  new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            finish();
                            if(!response.equals("#")){




                            }else{


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
                            map.put("request_type", "2");
                            map.put("applyid", String.valueOf(apply_id));

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

        /**
         * 拒绝
         */
        btn_notif_friend_refuse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "http://119.91.21.231:8080/women/ApplyServlet";    //注①
                String tag = "friend";    //注②

                //取得请求队列
                RequestQueue requestQueue = Volley.newRequestQueue(SubActivity_notif_detail_friendApply.this);

                //防止重复请求，所以先取消tag标识的请求队列
                requestQueue.cancelAll(tag);

                //创建StringRequest，定义字符串请求的请求方式为POST(省略第一个参数会默认为GET方式)

                final StringRequest request = new StringRequest(Request.Method.POST, url,  new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Toast.makeText(SubActivity_notif_detail_friendApply.this, "已拒绝", Toast.LENGTH_SHORT).show();
                        finish();


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
                        map.put("request_type", "3");
                        map.put("applyid", String.valueOf(apply_id));

                        return map;
                    }
                };

                //设置Tag标签
                request.setTag(tag);

                //将请求添加到队列中
                requestQueue.add(request);


            }
        });

    }

}