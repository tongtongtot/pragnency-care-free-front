package pregproject.pregproject.publicc.daily;

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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import pregproject.pregproject.other.MainActivity_login;

public class SubActivity_public_detail extends AppCompatActivity {

    ArrayList<comments> comment_items=new ArrayList<>();
    Lifepost lifepost=new Lifepost();
    TextView tv_pub_detail_flowerCount,tv_pub_detail_commentCount,tv_d_title,tv_d_content;
    ImageView iv_d_pic1;
    Integer post_id;
    ListView lv_d;
    ImageView iv_pub_detail_flower;
    Button btn_p_d_send;
    EditText et_p_d_comment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_public_detail);
        hide_bar hide_bar = new hide_bar(SubActivity_public_detail.this);
        hide_bar.hide();
        Intent intent = getIntent();
        post_id= intent.getIntExtra("post_id",0);

        tv_d_title = findViewById(R.id.tv_d_title);
        tv_d_content=findViewById(R.id.tv_d_content);
        iv_d_pic1=findViewById(R.id.iv_d_pic1);
        lv_d=findViewById(R.id.lv_d);

        iv_pub_detail_flower=findViewById(R.id.iv_pub_detail_flower);
        tv_pub_detail_flowerCount=findViewById(R.id.tv_pub_detail_flowerCount);
        tv_pub_detail_commentCount=findViewById(R.id.tv_pub_detail_commentCount);

        btn_p_d_send=findViewById(R.id.btn_p_d_send);
        et_p_d_comment=findViewById(R.id.et_p_d_comment);

        reload();





    }

    public void reload(){
        /**
         * 查询出post_id在Lifepost表里的内容，存到lifepost变量里----------------------------------------------------------
         */
        String url = "http://114.132.251.166:2233/women/LifepostServlet";    //注①
        String tag = "lifepost";    //注②
        comment_items.clear();
        //取得请求队列
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        //防止重复请求，所以先取消tag标识的请求队列
        requestQueue.cancelAll(tag);

        //创建StringRequest，定义字符串请求的请求方式为POST(省略第一个参数会默认为GET方式)

        final StringRequest request = new StringRequest(Request.Method.POST, url,  new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String tempres=response;
                if(!response.equals("#")){

                    try {

                        JSONObject jsonObject = (JSONObject) new JSONObject(response);
                        lifepost.setid(post_id);
                        lifepost.setCommentnum(jsonObject.getInt("commentnum"));
                        lifepost.setCreate(jsonObject.getString("create"));
                        lifepost.setData(jsonObject.getString("data"));
                        lifepost.setPic_cnt(jsonObject.getInt("pic_cnt"));
                        lifepost.setPic_url(jsonObject.getString("pic_url"));
                        lifepost.setUserid(jsonObject.getInt("userid"));
                        lifepost.setType(jsonObject.getInt("type"));
                        lifepost.setHappiness(jsonObject.getInt("happiness"));
                        lifepost.setTitle(jsonObject.getString("title"));
                        lifepost.setLike(jsonObject.getInt("like"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    if(lifepost.getType()==1){
                        tv_d_title.setText(lifepost.getTitle());
                        if(!lifepost.getPic_url().equals("-1")) {
                            //请求地址
                            String url = lifepost.getPic_url();    //注①
                            String tag = "picdownload";    //注②

                            //取得请求队列
                            RequestQueue requestQueue = Volley.newRequestQueue(getBaseContext());

                            //防止重复请求，所以先取消tag标识的请求队列
                            requestQueue.cancelAll(tag);

                            //创建StringRequest，定义字符串请求的请求方式为POST(省略第一个参数会默认为GET方式)
                            ImageRequest request = new ImageRequest(url, new Response.Listener<Bitmap>() {
                                @Override
                                public void onResponse(Bitmap response) {
                                    iv_d_pic1.setImageBitmap(response);
                                    iv_d_pic1.setVisibility(View.VISIBLE);
                                }
                            }, 0, 0, Bitmap.Config.RGB_565, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    //做自己的响应错误操作，如Toast提示（“请稍后重试”等）
                                    Log.e("TAG", error.getMessage(), error);
                                }
                            });

                            //设置Tag标签
                            request.setTag(tag);

                            //将请求添加到队列中
                            requestQueue.add(request);

                        }
                        //iv_d_pic1.setImageURI();
                    }else if(lifepost.getType()==2){
                        tv_d_title.setText("今天的心情是"+String.valueOf(lifepost.getHappiness())+"分");
                        switch (lifepost.getHappiness()){
                            case 1:
                                iv_d_pic1.setImageResource(R.drawable.star1);
                                iv_d_pic1.setVisibility(View.VISIBLE);
                                break;
                            case 2:
                                iv_d_pic1.setImageResource(R.drawable.star2);
                                iv_d_pic1.setVisibility(View.VISIBLE);
                                break;
                            case 3:
                                iv_d_pic1.setImageResource(R.drawable.star3);
                                iv_d_pic1.setVisibility(View.VISIBLE);
                                break;
                            case 4:
                                iv_d_pic1.setImageResource(R.drawable.star4);
                                iv_d_pic1.setVisibility(View.VISIBLE);
                                break;
                            case 5:
                                iv_d_pic1.setImageResource(R.drawable.star5);
                                iv_d_pic1.setVisibility(View.VISIBLE);
                                break;
                        }
                    }

                    tv_d_content.setText(lifepost.getData());

                    tv_pub_detail_flowerCount.setText(String.valueOf(lifepost.getLike()));

                    tv_pub_detail_commentCount.setText(String.valueOf(lifepost.getCommentnum()));

                    String url = "http://114.132.251.166:2233/women/LifepostServlet";    //注①
                    String tag = "lifepost";    //注②

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
                                    MyListViewAdapter myAdapter_comments = new MyListViewAdapter(comment_items, SubActivity_public_detail.this);
                                    lv_d.setAdapter(myAdapter_comments);



                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }



                                iv_pub_detail_flower.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {


                                        /**
                                         * 让数据库里面送花数量加一
                                         */
                                        if(iv_pub_detail_flower.isSelected()){
                                            Toast.makeText(SubActivity_public_detail.this, "送花频繁，稍后再试哦~", Toast.LENGTH_SHORT).show();
                                            return;
                                        }
                                        iv_pub_detail_flower.setSelected(true);
                                        String url = "http://114.132.251.166:2233/women/LifepostServlet";    //注①
                                        String tag = "like";    //注②

                                        //取得请求队列
                                        RequestQueue requestQueue = Volley.newRequestQueue(getBaseContext());

                                        //防止重复请求，所以先取消tag标识的请求队列
                                        requestQueue.cancelAll(tag);

                                        //创建StringRequest，定义字符串请求的请求方式为POST(省略第一个参数会默认为GET方式)

                                        final StringRequest request = new StringRequest(Request.Method.POST, url,  new Response.Listener<String>() {
                                            @Override
                                            public void onResponse(String response) {
                                                if(!response.equals("#")){
                                                    Toast.makeText(SubActivity_public_detail.this, "送花成功~", Toast.LENGTH_SHORT).show();
                                                    reload();
                                                /*
                                                try {

                                                    JSONObject jsonObject = (JSONObject) new JSONObject(response);




                                                } catch (JSONException e) {
                                                    e.printStackTrace();
                                                }
*/
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
                                                map.put("postid",String.valueOf(lifepost.getid()));
                                                return map;
                                            }
                                        };

                                        //设置Tag标签
                                        request.setTag(tag);

                                        //将请求添加到队列中
                                        requestQueue.add(request);
                                        tv_pub_detail_flowerCount.setText(String.valueOf(lifepost.getLike()+1));
                                    }
                                });

                                btn_p_d_send.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {

                                        if(MainActivity_login.nowuser.user_id!=lifepost.getUserid()&&!MainActivity_login.nowuser.friendlist.contains("#"+String.valueOf(lifepost.getUserid())+"#")&& MainActivity_login.nowuser.love_id!=lifepost.getUserid()) {

                                            Toast.makeText(SubActivity_public_detail.this, "你还不是TA的好友呢~不能评论哦", Toast.LENGTH_SHORT).show();
                                            return;}
                                        if(et_p_d_comment.getText().toString().isEmpty()){
                                            Toast.makeText(SubActivity_public_detail.this, "评论不能为空哦", Toast.LENGTH_SHORT).show();
                                            return;
                                        }
                                        InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                                        if(et_p_d_comment.isFocused())
                                            im.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                                        String myComment = et_p_d_comment.getText().toString();

                                        /**
                                         * 把myComment和其他需要的数据（id是这个变量：post_id）存到评论数据库。
                                         */
                                        String url = "http://114.132.251.166:2233/women/LifepostServlet";    //注①
                                        String tag = "lifepost";    //注②

                                        //取得请求队列
                                        RequestQueue requestQueue = Volley.newRequestQueue(getBaseContext());

                                        //防止重复请求，所以先取消tag标识的请求队列
                                        requestQueue.cancelAll(tag);

                                        //创建StringRequest，定义字符串请求的请求方式为POST(省略第一个参数会默认为GET方式)

                                        final StringRequest request = new StringRequest(Request.Method.POST, url,  new Response.Listener<String>() {
                                            @Override
                                            public void onResponse(String response) {
                                                if(!response.equals("#")){
                                                    Toast.makeText(SubActivity_public_detail.this, "评论发布成功", Toast.LENGTH_SHORT).show();

                                                    et_p_d_comment.setText("");
                                                    reload();
                                                 /*
                                                try {

                                                    JSONObject jsonObject = (JSONObject) new JSONObject(response);




                                                } catch (JSONException e) {
                                                    e.printStackTrace();
                                                }
*/
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
                                                map.put("postid",String.valueOf(lifepost.getid()));
                                                map.put("userid",String.valueOf(MainActivity_login.nowuser.user_id));
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
                            map.put("request_type",String.valueOf(4));
                            map.put("postid",String.valueOf(lifepost.getid()));

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
                map.put("postid",String.valueOf(post_id));
                map.put("request_type",String.valueOf(5));
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