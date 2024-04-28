package pregproject.pregproject.identity_Pro;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.pregproject.R;
import pregproject.pregproject.forum.SubActivity_forum_add;
import pregproject.pregproject.other.hide_bar;
import pregproject.pregproject.professor.Expertpost;
import pregproject.pregproject.professor.MyListViewAdapter_pro;
import pregproject.pregproject.professor.SubActivity_pro_detail;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity_pro extends AppCompatActivity {

    private ArrayList<Expertpost> items=new ArrayList<>();
    ListView lv_pro ;
    ImageView iv_add ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_pro);

        /**
         * 隐藏导航栏等，做到全屏
         */
        hide_bar hide_bar = new hide_bar(MainActivity_pro.this);
        hide_bar.hide();

         lv_pro =findViewById(R.id.lv_pro);
         iv_add =findViewById(R.id.iv_add);
         reload();


        iv_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle=new Bundle();
                bundle.putInt("comeFrom",2);
                Intent intent = new Intent(MainActivity_pro.this, SubActivity_forum_add.class);
                intent.putExtras(bundle);
                startActivity(intent);	//一定要跳转
            }
        });

    }
    void reload(){
        items.clear();
        /**
         * 查询数据库的Expertpost表，放到items数组里。
         */
        String url = "http://114.132.251.166:2233/women/topicProServlet";    //注①
        String tag = "expertpost";    //注②

        //取得请求队列
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity_pro.this);

        //防止重复请求，所以先取消tag标识的请求队列
        requestQueue.cancelAll(tag);

        //创建StringRequest，定义字符串请求的请求方式为POST(省略第一个参数会默认为GET方式)

        final StringRequest request = new StringRequest(Request.Method.POST, url,  new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(!response.equals("#")){

                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        try {
                            for (int i = jsonArray.length()-1; i >=0 ; i--) {
                                JSONObject jsonObject1 = jsonArray.getJSONObject(i);    //注②
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
                    MyListViewAdapter_pro myAdapter = new MyListViewAdapter_pro(items, MainActivity_pro.this);
                    //MyListViewAdapter_pro myAdapter = new MyListViewAdapter_pro(items, getActivity());
                    lv_pro.setAdapter(myAdapter);

                    lv_pro.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            Bundle bundle=new Bundle();
                            System.out.println("postid到底是多少？"+items.get(i).getPostid());
                            bundle.putInt("postid",items.get(i).getPostid());
                            bundle.putInt("type",1);
                            Intent intent = new Intent(MainActivity_pro.this, SubActivity_pro_detail.class);
                            intent.putExtras(bundle);
                            startActivity(intent);	//一定要跳转
                            reload();
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
                map.put("request_type",String.valueOf(5));
                return map;
            }
        };

        //设置Tag标签
        request.setTag(tag);

        //将请求添加到队列中
        requestQueue.add(request);
    }
}