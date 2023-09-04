package pregproject.pregproject.publicc.notification;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.pregproject.R;

import pregproject.pregproject.other.hide_bar;
import pregproject.pregproject.publicc.daily.SubActivity_public_detail;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static pregproject.pregproject.other.MainActivity_login.nowuser;

public class SubActivity_notification extends AppCompatActivity {
    ListView listView;

    List<Applymsg> items=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_notification);

        /**
         * 隐藏导航栏等，做到全屏
         */
        hide_bar hide_bar = new hide_bar(SubActivity_notification.this);
        hide_bar.hide();

        listView = findViewById(R.id.lv);







    }

    @Override
    protected void onStart() {
        super.onStart();
        items.clear();
        /**
         * -------------------------------------------------------------------------------------------------------
         * 数据库查Applymsg类，存到items数组里面。
         */
        //请求地址
        String url = "http://119.91.21.231:8080/women/ApplyServlet";    //注①
        String tag = "apply";    //注②

        //取得请求队列
        RequestQueue requestQueue = Volley.newRequestQueue(getBaseContext());

        //防止重复请求，所以先取消tag标识的请求队列
        requestQueue.cancelAll(tag);

        //创建StringRequest，定义字符串请求的请求方式为POST(省略第一个参数会默认为GET方式)

        final StringRequest request = new StringRequest(Request.Method.POST, url,  new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (!response.equals("#")) {

                    try {
                        JSONObject jsonObject = (JSONObject) new JSONObject(response).get("params");
                        JSONArray comments = jsonObject.getJSONArray("Applymsg");
                        try {

                            for (int i = comments.length()-1; i >=0 ; i--) {
                                JSONObject jsonObject1 = comments.getJSONObject(i);    //注②
                                Applymsg apply = new Applymsg();
                                apply.setData(jsonObject1.getString("data"));
                                apply.setApplyId(jsonObject1.getInt("apply_id"));
                                apply.setApplyType(jsonObject1.getInt("apply_type"));
                                apply.setSendId(jsonObject1.getInt("send_id"));
                                apply.setPostId(jsonObject1.getInt("post_id"));
                                items.add(apply);
                                //  bookList.add(book);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        listView.setAdapter(new MyNotifAdapter(items, SubActivity_notification.this));
                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                                Bundle bundle = new Bundle();

                                if (items.get(i).getApplyType() == 1 || items.get(i).getApplyType() == 2) {
                                    bundle.putInt("apply_type", items.get(i).getApplyType());
                                    bundle.putInt("send_id", items.get(i).getSendId());
                                    bundle.putString("data", items.get(i).getData());
                                    bundle.putInt("apply_id", items.get(i).getApplyId());

                                    Intent intent = new Intent(SubActivity_notification.this, SubActivity_notif_detail_friendApply.class);
                                    intent.putExtras(bundle);
                                    startActivity(intent);    //一定要跳转

                                } else if (items.get(i).getApplyType() == 3) {
                                    String url = "http://119.91.21.231:8080/women/ApplyServlet";    //注①
                                    String tag = "friend";    //注②

                                    //取得请求队列
                                    RequestQueue requestQueue = Volley.newRequestQueue(SubActivity_notification.this);

                                    //防止重复请求，所以先取消tag标识的请求队列
                                    requestQueue.cancelAll(tag);

                                    //创建StringRequest，定义字符串请求的请求方式为POST(省略第一个参数会默认为GET方式)

                                    final StringRequest request = new StringRequest(Request.Method.POST, url,  new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {

                                            bundle.putInt("post_id", items.get(i).getPostId());
                                            Intent intent = new Intent(SubActivity_notification.this, SubActivity_public_detail.class);
                                            intent.putExtras(bundle);
                                            startActivity(intent);    //一定要跳转
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
                                            map.put("applyid", String.valueOf(items.get(i).getApplyId()));

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

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
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
                map.put("request_type",String.valueOf(4) );
                map.put("recid",String.valueOf(nowuser.user_id));


                return map;
            }
        };

        //设置Tag标签
        request.setTag(tag);

        //将请求添加到队列中
        requestQueue.add(request);


    }

    public void back(View view) {
        finish();
    }


}