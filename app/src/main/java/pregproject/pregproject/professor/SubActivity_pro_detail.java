package pregproject.pregproject.professor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.pregproject.R;

import pregproject.pregproject.other.hide_bar;
import pregproject.pregproject.publicc.daily.MyListViewAdapter;
import pregproject.pregproject.publicc.daily.comments;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static pregproject.pregproject.other.MainActivity_login.nowuser;

public class SubActivity_pro_detail extends AppCompatActivity {


    Expertpost expertpost;
    TextView tv_pro_title;
    TextView tv_pro_name;
    TextView tv_pro_data;
    TextView tv_pro_commentCount;
    EditText et_pro_comment;
    Button btn_pro_send;
    ListView lv_pro_d;
    Integer postid;
    Integer type;
    ImageView image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_pro_detail);
        hide_bar hide_bar = new hide_bar(SubActivity_pro_detail.this);
        hide_bar.hide();
        Intent intent = getIntent();
        postid = intent.getIntExtra("postid",0);
        type=intent.getIntExtra("type",0);
        image=findViewById(R.id.pic_pro_detail);
        tv_pro_title=findViewById(R.id.tv_pro_title);
        tv_pro_name=findViewById(R.id.tv_pro_name);
        tv_pro_data=findViewById(R.id.tv_pro_data);
        tv_pro_commentCount=findViewById(R.id.tv_pro_commentCount);
        et_pro_comment=findViewById(R.id.et_pro_comment);
        btn_pro_send=findViewById(R.id.btn_pro_send);
        lv_pro_d=findViewById(R.id.lv_pro_d);
        expertpost=new Expertpost();
        reload();
    }
    void reload(){
        if(type==1){
            /**
             * 查出postid对应在Expertpost表的数据存到item变量里。--专家界面
             */
            String url = "http://114.132.251.166:2233/women/detailServlet";    //注①
            String tag = "expertpost";    //注②

            //取得请求队列
            RequestQueue requestQueue = Volley.newRequestQueue(this);

            //防止重复请求，所以先取消tag标识的请求队列
            requestQueue.cancelAll(tag);

            //创建StringRequest，定义字符串请求的请求方式为POST(省略第一个参数会默认为GET方式)

            final StringRequest request = new StringRequest(Request.Method.POST, url,  new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    if(!response.equals("#")){
                        try {
                                JSONObject jsonObject =  new JSONObject(response);
                                //JSONObject jsonObject = (JSONObject) new JSONObject(response).get("params");
                                expertpost.setPostid(postid);
                                //expertpost.setCommentnum(jsonObject.getInt("commentnum"));
                                expertpost.setCreate(jsonObject.getString("create"));
                                expertpost.setData(jsonObject.getString("data"));
                                expertpost.setPic_cnt(jsonObject.getInt("pic_cnt"));
                                expertpost.setPic_url(jsonObject.getString("pic_url"));
                                expertpost.setUserid(jsonObject.getInt("userid"));
                                expertpost.setType(jsonObject.getInt("type"));
                                expertpost.setUsername(jsonObject.getString("username"));
                                expertpost.setTitle(jsonObject.getString("title"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        tv_pro_title.setText(expertpost.getTitle());
                        tv_pro_name.setText(expertpost.getUsername());
                        tv_pro_data.setText(expertpost.getData());
                        tv_pro_commentCount.setText(String.valueOf(expertpost.getCommentnum()));

                        //请求地址
                        String url1 = expertpost.getPic_url();    //注①
                        String tag1 = "picdownload";    //注②

                        //取得请求队列
                        RequestQueue requestQueue1 = Volley.newRequestQueue(getBaseContext());

                        //防止重复请求，所以先取消tag标识的请求队列
                        requestQueue1.cancelAll(tag1);

                        //创建StringRequest，定义字符串请求的请求方式为POST(省略第一个参数会默认为GET方式)
                        ImageRequest request1 = new ImageRequest(url1, new Response.Listener<Bitmap>() {
                            @Override
                            public void onResponse(Bitmap response) {
                                image.setImageBitmap(response);
                                image.setVisibility(View.VISIBLE);
                            }
                        }, 0,0, Bitmap.Config.RGB_565,new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                //做自己的响应错误操作，如Toast提示（“请稍后重试”等）
                                Log.e("TAG", error.getMessage(), error);
                            }
                        }) ;

                        //设置Tag标签
                        request1.setTag(tag);

                        //将请求添加到队列中
                        requestQueue1.add(request1);



                        /**
                         * 查询评论表，也要判断type值，如果值是2，那么就是论坛版块的评论，如果是1那就是专家版块的评论，再找到属于postid变量的所有评论，放在comment_items里面-------------------------------------------
                         */

                        String url = "http://114.132.251.166:2233/women/topicServlet";    //注①
                        String tag = "comments";    //注②

                        //取得请求队列
                        RequestQueue requestQueue = Volley.newRequestQueue(getBaseContext());

                        //防止重复请求，所以先取消tag标识的请求队列
                        requestQueue.cancelAll(tag);

                        //创建StringRequest，定义字符串请求的请求方式为POST(省略第一个参数会默认为GET方式)

                        final StringRequest request = new StringRequest(Request.Method.POST, url,  new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                if(!response.equals("#")){
                                    System.out.println("专家话题详情页！");
                                    ArrayList<comments> comment_items=new ArrayList<>();
                                    try {
                                        //JSONObject jsonObject = (JSONObject) new JSONObject(response).get("params");
                                        JSONArray jsonArray = new JSONArray(response);
                                        for (int j = jsonArray.length()-1; j >=0 ; j--) {
                                            JSONObject jsonObject = jsonArray.getJSONObject(j);
                                            JSONArray comments = jsonObject.getJSONArray("comments");
                                            try {
                                                for (int i = 0; i < comments.length(); i++) {
                                                    JSONObject jsonObject1 = comments.getJSONObject(i);    //注②
                                                    comments comment = new comments();
                                                    comment.setData(jsonObject1.getString("data"));
                                                    comment.setUserid(jsonObject1.getInt("userid"));
                                                    comment.setUsername(jsonObject1.getString("username"));
                                                    comment.setComment_id(jsonObject1.getInt("commentid"));
                                                    comment_items.add(comment);
                                                    //  bookList.add(book);
                                                }
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                            MyListViewAdapter myAdapter_comments = new MyListViewAdapter(comment_items, SubActivity_pro_detail.this);
                                            lv_pro_d.setAdapter(myAdapter_comments);
                                        }

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }



                                    btn_pro_send.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            if(et_pro_comment.getText().toString().isEmpty()){
                                                Toast.makeText(SubActivity_pro_detail.this, "评论不能为空哦", Toast.LENGTH_SHORT).show();
                                                return;
                                            }
                                            InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                                            if(et_pro_comment.isFocused())
                                                im.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                                            String myComment = et_pro_comment.getText().toString();
                                            /**
                                             * 把myComment和其他需要的数据（id是这个变量：post_id）存到评论数据库。
                                             */
                                            String url = "http://114.132.251.166:2233/women/topicServlet";    //注①
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
                                                        Toast.makeText(SubActivity_pro_detail.this, "评论发布成功", Toast.LENGTH_SHORT).show();
                                                        et_pro_comment.setText("");
                                                        reload();
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
                                                    map.put("request_type",String.valueOf(2));
                                                    map.put("postid",String.valueOf(postid));
                                                    map.put("userid",String.valueOf(nowuser.user_id));
                                                    map.put("data", myComment);
                                                    return map;
                                                }
                                            };

                                            //设置Tag标签
                                            request.setTag(tag);

                                            //将请求添加到队列中
                                            requestQueue.add(request);
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
                                map.put("request_type",String.valueOf(3));
                                map.put("postid",String.valueOf(postid));
                                return map;
                            }
                        };

                        //设置Tag标签
                        request.setTag(tag);

                        //将请求添加到队列中
                        requestQueue.add(request);
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
                    map.put("postid",String.valueOf(postid));
                    map.put("request_type",String.valueOf(4));
                    return map;
                }
            };

            //设置Tag标签
            request.setTag(tag);

            //将请求添加到队列中
            requestQueue.add(request);


        }else if(type==2){

            /**
             * 查出postid对应在post表的数据存到item变量里。
             */
            String url = "http://114.132.251.166:2233/women/detail01Servlet";    //注①
            String tag = "post";    //注②

            //取得请求队列
            RequestQueue requestQueue = Volley.newRequestQueue(this);

            //防止重复请求，所以先取消tag标识的请求队列
            requestQueue.cancelAll(tag);

            //创建StringRequest，定义字符串请求的请求方式为POST(省略第一个参数会默认为GET方式)

            final StringRequest request = new StringRequest(Request.Method.POST, url,  new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    if(!response.equals("#")){
                        try {
                            JSONObject jsonObject = (JSONObject) new JSONObject(response);
                            expertpost.setPostid(postid);
                            //expertpost.setCommentnum(jsonObject.getInt("commentnum"));
                            expertpost.setCreate(jsonObject.getString("create"));
                            expertpost.setData(jsonObject.getString("data"));
                            expertpost.setPic_cnt(jsonObject.getInt("pic_cnt"));
                            expertpost.setPic_url(jsonObject.getString("pic_url"));
                            expertpost.setUserid(jsonObject.getInt("userid"));
                            expertpost.setUsername(jsonObject.getString("username"));
                            expertpost.setTitle(jsonObject.getString("title"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                        tv_pro_title.setText(expertpost.getTitle());
                        System.out.println("用户论坛里的话题是："+expertpost.getTitle());
                        tv_pro_name.setText(expertpost.getUsername());
                        tv_pro_data.setText(expertpost.getData());

                        tv_pro_commentCount.setText(String.valueOf(expertpost.getCommentnum()));

                        //请求地址
                        String url1 = expertpost.getPic_url();    //注①
                        String tag1 = "picdownload";    //注②

                        //取得请求队列
                        RequestQueue requestQueue1 = Volley.newRequestQueue(getBaseContext());

                        //防止重复请求，所以先取消tag标识的请求队列
                        requestQueue1.cancelAll(tag1);

                        //创建StringRequest，定义字符串请求的请求方式为POST(省略第一个参数会默认为GET方式)
                        ImageRequest request1 = new ImageRequest(url1, new Response.Listener<Bitmap>() {
                            @Override
                            public void onResponse(Bitmap response) {
                                image.setImageBitmap(response);
                                image.setVisibility(View.VISIBLE);
                            }
                        }, 0,0, Bitmap.Config.RGB_565,new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                //做自己的响应错误操作，如Toast提示（“请稍后重试”等）
                                Log.e("TAG", error.getMessage(), error);
                            }
                        }) ;

                        //设置Tag标签
                        request1.setTag(tag);

                        //将请求添加到队列中
                        requestQueue1.add(request1);

                        /**
                         * 查询评论表，也要判断type值，如果值是2，那么就是论坛版块的评论，如果是1那就是专家版块的评论，再找到属于postid变量的所有评论，放在comment_items里面-------------------------------------------
                         */

                        String url = "http://114.132.251.166:2233/women/postServlet";    //注①
                        String tag = "comments";    //注②

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
                                        JSONArray comments = jsonObject.getJSONArray("comments");
                                        ArrayList<comments> comment_items=new ArrayList<>();
                                        try {
                                            for (int i = 0; i < comments.length(); i++) {
                                                JSONObject jsonObject1 = comments.getJSONObject(i);    //注②
                                                comments comment=new comments();
                                                comment.setData(jsonObject1.getString("data"));
                                                comment.setUserid(jsonObject1.getInt("userid"));
                                                comment.setUsername(jsonObject1.getString("username"));
                                                comment.setComment_id(jsonObject1.getInt("commentid"));
                                                comment_items.add(comment);
                                                //  bookList.add(book);
                                            }
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                        MyListViewAdapter myAdapter_comments = new MyListViewAdapter(comment_items, SubActivity_pro_detail.this);
                                        lv_pro_d.setAdapter(myAdapter_comments);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    btn_pro_send.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            if(et_pro_comment.getText().toString().isEmpty()){
                                                Toast.makeText(SubActivity_pro_detail.this, "评论不能为空哦", Toast.LENGTH_SHORT).show();
                                                return;
                                            }
                                            String myComment = et_pro_comment.getText().toString();
                                            InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                                            if(et_pro_comment.isFocused())
                                                im.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

                                            /**
                                             * 把myComment和其他需要的数据（id是这个变量：post_id）存到评论数据库。
                                             */
                                            String url = "http://114.132.251.166:2233/women/postServlet";    //注①
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
                                                        Toast.makeText(SubActivity_pro_detail.this, "评论发布成功", Toast.LENGTH_SHORT).show();
                                                        et_pro_comment.setText("");
                                                        reload();
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
                                                    map.put("request_type",String.valueOf(2));
                                                    map.put("postid",String.valueOf(postid));
                                                    map.put("userid",String.valueOf(nowuser.user_id));
                                                    map.put("data", myComment);
                                                    return map;
                                                }
                                            };
                                            //设置Tag标签
                                            request.setTag(tag);
                                            //将请求添加到队列中
                                            requestQueue.add(request);
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
                                map.put("request_type",String.valueOf(3));
                                map.put("postid",String.valueOf(postid));
                                return map;
                            }
                        };

                        //设置Tag标签
                        request.setTag(tag);

                        //将请求添加到队列中
                        requestQueue.add(request);
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
                    map.put("postid",String.valueOf(postid));
                    map.put("request_type",String.valueOf(4));
                    return map;
                }
            };
            //设置Tag标签
            request.setTag(tag);
            //将请求添加到队列中
            requestQueue.add(request);
        }
    }
    public void back(View view) {
        finish();
    }
}