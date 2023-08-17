package com.example.pregproject.forum;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.example.pregproject.identity_Pro.MainActivity_pro;
import com.example.pregproject.professor.Expertpost;
import com.example.pregproject.professor.MyListViewAdapter_pro;
import com.example.pregproject.professor.SubActivity_pro_detail;
import com.example.pregproject.publicc.StaggerAdapter;
import com.example.pregproject.publicc.daily.Lifepost;
import com.example.pregproject.publicc.daily.SubActivity_public_detail;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class Fragment_forum extends Fragment {

    private View root;
    private ArrayList<Expertpost> items=new ArrayList<>();
    private ListView lv_for;

    public Fragment_forum() {

    }


    public static Fragment_forum newInstance() {
        Fragment_forum fragment = new Fragment_forum();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if(root==null){
            root=inflater.inflate(R.layout.fragment_forum, container, false);
        }
        lv_for = root.findViewById(R.id.lv_for);
        ImageView iv_forum_new=root.findViewById(R.id.iv_forum_new);

        iv_forum_new.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Bundle bundle=new Bundle();
                bundle.putInt("comeFrom",1);
                Intent intent = new Intent(getActivity(), SubActivity_forum_add.class);
                intent.putExtras(bundle);
                startActivity(intent);	//一定要跳转

            }
        });
        return root;}

    @Override
    public void onStart() {
        super.onStart();
        items.clear();

        /**
         * 查询Post表，找出所有帖子的信息放进items数组里面。（因为post和Expertpost表很像，所以这里items用Expertpost的数据结构。）
         */
        String url = "http://119.91.21.231:8080/women/topicServlet";    //注①
        String tag = "lifepost";    //注②

        //取得请求队列
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());

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
                                Expertpost lifepost = new Expertpost();
                                lifepost.setPostid(jsonObject1.getInt("postid"));
                                lifepost.setData(jsonObject1.getString("data"));
                                lifepost.setPic_url(jsonObject1.getString("pic_url"));
                                lifepost.setCreate(jsonObject1.getString("create"));

                                lifepost.setPic_cnt(jsonObject1.getInt("pic_cnt"));
                                lifepost.setCommentnum(jsonObject1.getInt("commentnum"));
                                lifepost.setUserid(jsonObject1.getInt("userid"));


                                lifepost.setCommentlist(jsonObject1.getString("commentlist"));
                                lifepost.setTitle(jsonObject1.getString("title"));
                                lifepost.setUsername(jsonObject1.getString("username"));
                                items.add(lifepost);
                                //  bookList.add(book);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    //设置布局管理器

                    //设置适配器
                    MyListViewAdapter_pro myAdapter = new MyListViewAdapter_pro(items, getActivity());
                    lv_for.setAdapter(myAdapter);

                    lv_for.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                            Bundle bundle=new Bundle();
                            bundle.putInt("postid",items.get(i).getPostid());
                            bundle.putInt("type",2);
                            Intent intent = new Intent(getActivity(), SubActivity_pro_detail.class);
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