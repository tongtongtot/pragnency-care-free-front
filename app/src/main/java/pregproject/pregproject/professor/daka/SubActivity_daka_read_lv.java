package pregproject.pregproject.professor.daka;

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
import pregproject.pregproject.professor.Expertpost;
import pregproject.pregproject.professor.MyListViewAdapter_pro;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SubActivity_daka_read_lv extends AppCompatActivity {

    ListView lv_read;
    ArrayList<Expertpost> items=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_daka_read_lv);

        /**
         * 隐藏导航栏等，做到全屏
         */
        hide_bar hide_bar = new hide_bar(SubActivity_daka_read_lv.this);
        hide_bar.hide();

        lv_read=findViewById(R.id.lv_read);


        /**
         * 把查出来的孕妇阅读打卡的数据（type==2的）放进数组items里面。---------------------------------------------------------------------
         */
        String url = "http://119.91.21.231:8080/women/ExpertpostServlet";    //注①
        String tag = "expertpost";    //注②

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
                        JSONObject jsonObject = (JSONObject) new JSONObject(response).get("params");
                        JSONArray comments = jsonObject.getJSONArray("posts");
                        try {
                            for (int i = 0; i < comments.length(); i++) {
                                JSONObject jsonObject1 = comments.getJSONObject(i);    //注②
                                Expertpost post=new Expertpost();
                                post.setPostid(jsonObject1.getInt("postid"));
                                post.setData(jsonObject1.getString("data"));
                                post.setPic_url(jsonObject1.getString("pic_url"));
                                post.setCreate(jsonObject1.getString("create"));

                                post.setPic_cnt(jsonObject1.getInt("pic_cnt"));
                                post.setCommentnum(jsonObject1.getInt("commentnum"));
                                post.setUserid(jsonObject1.getInt("userid"));
                                post.setType(jsonObject1.getInt("type"));

                                post.setCommentlist(jsonObject1.getString("commentlist"));
                                post.setTitle(jsonObject1.getString("title"));
                                post.setUsername(jsonObject1.getString("username"));

                                items.add(post);
                                //  bookList.add(book);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }




                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    MyListViewAdapter_pro myAdapter = new MyListViewAdapter_pro(items, SubActivity_daka_read_lv.this);
                    lv_read.setAdapter(myAdapter);

                    lv_read.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                            Bundle bundle=new Bundle();
                            bundle.putInt("postid",items.get(i).getPostid());
                            bundle.putString("data",items.get(i).getData());
                            bundle.putString("pic_url",items.get(i).getPic_url());
                            bundle.putString("create",items.get(i).getCreate());
                            bundle.putString("commentlist",items.get(i).getCommentlist());
                            bundle.putString("title",items.get(i).getTitle());
                            bundle.putString("username",items.get(i).getUsername());
                            bundle.putInt("pic_cnt",items.get(i).getPic_cnt());
                            bundle.putInt("commentnum",items.get(i).getCommentnum());
                            bundle.putInt("userid",items.get(i).getUserid());
                            bundle.putInt("type",items.get(i).getType());
                            Intent intent = new Intent(SubActivity_daka_read_lv.this, SubActivity_daka_read_detail.class);
                            intent.putExtras(bundle);
                            startActivity(intent);	//一定要跳转

                        }
                    });

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
                map.put("request_type",String.valueOf(6));


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