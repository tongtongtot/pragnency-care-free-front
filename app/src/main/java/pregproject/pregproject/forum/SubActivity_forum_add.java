package pregproject.pregproject.forum;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
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
import pregproject.pregproject.identity_Pro.MainActivity_pro;
import pregproject.pregproject.other.MainActivity_login;
import pregproject.pregproject.other.hide_bar;
import pregproject.pregproject.professor.Expertpost;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import static pregproject.pregproject.other.MainActivity_login.nowuser;
import static pregproject.pregproject.publicc.daily.BlankFragment_new_daily.compress;
import static pregproject.pregproject.publicc.daily.BlankFragment_new_daily.picupload;

public class SubActivity_forum_add extends AppCompatActivity {


    private ImageView iv_h_pic1;
    private Button btn_h_submit;
    private TextView tv_new_title,tv_new_content;
    Calendar ca;
    int  mYear,mMonth,mDay,mHour,mMin,msec;
    Bitmap bit1;
    String pic_url="-1";
    Expertpost expertpost=new Expertpost();
    String create="";
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(data==null){
            pic_url="-1";
            return;
        }
        Uri uri = data.getData();
        ContentResolver cr = SubActivity_forum_add.this.getContentResolver();
        try {

            if (requestCode == 0x1) {
                if (data != null) {
                    bit1 = BitmapFactory.decodeStream(cr.openInputStream(uri));//uri转bitmap
                    iv_h_pic1.setImageBitmap(bit1);//显示出选择的照片
                    pic_url="http://114.132.251.166:2233/women_pic/"+nowuser.user_id+create+".jpeg";
                }
            } else  if (requestCode == 0x2) {
                if (data != null) {

                }
            }else  if (requestCode == 0x3) {
                if (data != null) {

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_forum_add);
        hide_bar hide_bar = new hide_bar(SubActivity_forum_add.this);
        hide_bar.hide();

        Intent intent = getIntent();
        Integer comeFrom = intent.getIntExtra("comeFrom",0);

        iv_h_pic1=findViewById(R.id.iv_h_pic1);
        btn_h_submit=findViewById(R.id.btn_h_submit);

        tv_new_title=findViewById(R.id.tv_new_title);
        tv_new_content=findViewById(R.id.tv_new_content);

        ca = Calendar.getInstance();
        mYear = ca.get(Calendar.YEAR);
        mMonth = ca.get(Calendar.MONTH);
        mDay = ca.get(Calendar.DAY_OF_MONTH);
        mHour=ca.get(Calendar.HOUR_OF_DAY);
        mMin=ca.get(Calendar.MINUTE);
        msec=ca.get(Calendar.SECOND);
        create=String.valueOf(mYear)+String.valueOf(mMonth)+String.valueOf(mDay)+String.valueOf(mHour)+String.valueOf(mMin)+String.valueOf(msec);
        iv_h_pic1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Intent.ACTION_PICK, null);

                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");

                startActivityForResult(intent, 0x1);//---------------------------------------------------这里看startActivityForResult这个函数

            }
        });

        btn_h_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                im.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

                String title=tv_new_title.getText().toString();
                String data=tv_new_content.getText().toString();
                //图片变量？？？-----i
                if(!pic_url.equals("-1")){

                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    bit1.compress(Bitmap.CompressFormat.JPEG, 50, baos);
                    byte[] imageBytes = baos.toByteArray();

                    String temp = Base64.encodeToString(imageBytes, Base64.DEFAULT);
                    try {
                        String imageString = compress(temp);
                        picupload(temp,  nowuser.user_id+create, SubActivity_forum_add.this);


                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }



                //数据类型借用专家版块的数据类型吧（只相差一个type而已，不填就是了）-------------------------------------------------------
                expertpost.setCreate(create);
                expertpost.setTitle(title);
                expertpost.setData(data);
                expertpost.setUserid(MainActivity_login.userId);

                if(comeFrom==1){
                    /**
                     * 把以上变量存到论坛数据库------------------------------------------------------------------------------------
                     */
                    //请求地址
                    String url = "http://114.132.251.166:2233/women/postServlet";    //注①
                    String tag = "post";    //注②

                    //取得请求队列
                    RequestQueue requestQueue = Volley.newRequestQueue(SubActivity_forum_add.this);

                    //防止重复请求，所以先取消tag标识的请求队列
                    requestQueue.cancelAll(tag);

                    //创建StringRequest，定义字符串请求的请求方式为POST(省略第一个参数会默认为GET方式)

                    final StringRequest request = new StringRequest(Request.Method.POST, url,  new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            if(!response.equals("#")){
                                Toast.makeText(SubActivity_forum_add.this, "发送成功~", Toast.LENGTH_SHORT).show();
                               finish();
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
                            map.put("request_type",String.valueOf(1));
                            map.put("piccnt",String.valueOf(1));
                            map.put("data", data);
                            map.put("picurl", pic_url);
                            map.put("title", title);
                            map.put("create",create);
                            map.put("userid",String.valueOf(nowuser.user_id));
                            map.put("user_type",String.valueOf(nowuser.user_type));
                            map.put("user_name",String.valueOf(nowuser.user_name));
                            System.out.println("现在的用户是："+nowuser.user_name);
                            return map;
                        }
                    };

                    //设置Tag标签
                    request.setTag(tag);

                    //将请求添加到队列中
                    requestQueue.add(request);
                }
                else if(comeFrom==2){
                    /**
                     * 把以上变量存到专家数据库------------------------------------------------------------------------------------（也就是说，该条是专家发的）
                     */
                    System.out.println("进到专家数据库！！！！");
                    //请求地址
                    //String url = "http://114.132.251.166:2233/women/ExpertpostServlet";    //注①
                    String url = "http://114.132.251.166:2233/women/postProServlet";
                    String tag = "lifepost";    //注②

                    //取得请求队列
                    RequestQueue requestQueue = Volley.newRequestQueue(SubActivity_forum_add.this);

                    //防止重复请求，所以先取消tag标识的请求队列
                    requestQueue.cancelAll(tag);

                    //创建StringRequest，定义字符串请求的请求方式为POST(省略第一个参数会默认为GET方式)

                    final StringRequest request = new StringRequest(Request.Method.POST, url,  new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            System.out.println("得到的响应是:"+response);
                            if(!response.equals("#")){
                                Toast.makeText(SubActivity_forum_add.this, "发送成功~", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(SubActivity_forum_add.this, MainActivity_pro.class));
                                finish();
                                /*
                                try {
                                    JSONObject jsonObject = (JSONObject) new JSONObject(response);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
*/
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
                            map.put("request_type",String.valueOf(1));
                            map.put("piccnt",String.valueOf(1));
                            map.put("data", data);
                            map.put("picurl", pic_url);
                            map.put("title", title);
                            map.put("create",create);
                            map.put("userid",String.valueOf(nowuser.user_id));
                            map.put("user_name",String.valueOf(nowuser.user_name));
                            map.put("user_type",String.valueOf(nowuser.user_type));
                            map.put("type",String.valueOf(1));
                            System.out.println("现在的专家是："+nowuser.user_name);
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

    public void back(View view) {
        finish();
    }
}