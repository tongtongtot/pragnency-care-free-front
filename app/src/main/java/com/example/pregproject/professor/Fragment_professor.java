package com.example.pregproject.professor;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

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
import com.example.pregproject.professor.daka.SubActivity_daka_read_a;
import com.example.pregproject.publicc.StaggerAdapter;
import com.example.pregproject.publicc.daily.Lifepost;
import com.example.pregproject.publicc.daily.SubActivity_public_detail;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class Fragment_professor extends Fragment {

    private View root;
    private ArrayList<Expertpost> items=new ArrayList<>();
    ListView lv_pro ;
    ImageView iv_daka;
    public Fragment_professor() {
        // Required empty public constructor
    }


    public static Fragment_professor newInstance() {
        Fragment_professor fragment = new Fragment_professor();
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
            root=inflater.inflate(R.layout.fragment_professor, container, false);
        }

        lv_pro = root.findViewById(R.id.lv_pro);
        iv_daka=root.findViewById(R.id.iv_daka);

        iv_daka.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), SubActivity_daka_read_a.class));
            }
        });


        return root;
    }

    @Override
    public void onStart() {
        super.onStart();
items.clear();
        /**
         * 查询数据库的Expertpost表，放到items数组里。
         */
        String url = "http://119.91.21.231:8080/women/topicProServlet";    //注①
        String tag = "expertpost";    //注②

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
                            System.out.println("进来啦"+jsonArray.length());
                            for (int i = jsonArray.length()-1; i >=0 ; i--) {
                                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
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
                    MyListViewAdapter_pro myAdapter = new MyListViewAdapter_pro(items, getActivity());
                    lv_pro.setAdapter(myAdapter);
                    lv_pro.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            System.out.println("执行了3");
                            Bundle bundle=new Bundle();
                            bundle.putInt("postid",items.get(i).getPostid());
                            bundle.putInt("type",1);
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