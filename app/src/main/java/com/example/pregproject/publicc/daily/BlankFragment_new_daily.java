package com.example.pregproject.publicc.daily;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.example.pregproject.other.MainActivity;
import com.example.pregproject.other.MainActivity_login;
import com.example.pregproject.publicc.notification.SubActivity_notif_detail_friendApply;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.GZIPOutputStream;

import static com.example.pregproject.other.MainActivity_login.nowuser;


public class BlankFragment_new_daily extends Fragment {

    private View rootView;
    private ImageView iv_h_pic1;
    private Button btn_h_submit;
    private TextView tv_new_title,tv_new_content;
    Calendar ca;
    int  mYear,mMonth,mDay,mHour,mMin,mSec;
    Bitmap bit1;
    Uri uri;
    String pic_url="-1";
    String create;
    public BlankFragment_new_daily() {
        // Required empty public constructor
    }


    public static BlankFragment_new_daily newInstance() {
        BlankFragment_new_daily fragment = new BlankFragment_new_daily();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }
    public static void picupload(String image1, String url, Activity nowactivity) {
        //请求地址
        String url1 = "http://119.91.21.231:8080/women/PicServlet";    //注①
        String tag = "picupload";    //注②

        //取得请求队列
        RequestQueue requestQueue = Volley.newRequestQueue(nowactivity);

        //防止重复请求，所以先取消tag标识的请求队列
        requestQueue.cancelAll(tag);

        //创建StringRequest，定义字符串请求的请求方式为POST(省略第一个参数会默认为GET方式)

        final StringRequest request = new StringRequest(Request.Method.POST, url1,  new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    // JSONObject jsonObject = (JSONObject) new JSONObject(response).get("params");
                    // books = jsonObject.getJSONArray("Books");


                } catch (Exception e) {
                    //做自己的请求异常操作，如Toast提示（“无网络连接”等）
                    Log.e("TAG", e.getMessage(), e);
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
                map.put("url", url);
                map.put("image", image1);
                return map;
            }
        };

        //设置Tag标签
        request.setTag(tag);

        //将请求添加到队列中
        requestQueue.add(request);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(data==null){
            pic_url="-1";
            return;
        }
        uri=data.getData();
        if(uri.getPath()==null){
            pic_url="-1";
            return;
        }
        ContentResolver cr = getActivity().getContentResolver();
        try {

            if (requestCode == 0x1) {

                bit1 = BitmapFactory.decodeStream(cr.openInputStream(uri));//uri转bitmap

                iv_h_pic1.setImageBitmap(bit1);//显示出选择的照片

                pic_url="http://119.91.21.231:8080/women_pic/"+nowuser.user_id+create+".jpeg";



            } else  if (requestCode == 0x2) {

            }else  if (requestCode == 0x3) {

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static String compress(String str) throws IOException {
        long time1  = System.currentTimeMillis();
        if (null == str || str.length() <= 0) {
            return str;
        }
        // 创建一个新的输出流
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        // 使用默认缓冲区大小创建新的输出流
        GZIPOutputStream gzip = new GZIPOutputStream(out);
        // 将字节写入此输出流
        gzip.write(str.getBytes("utf-8")); // 因为后台默认字符集有可能是GBK字符集，所以此处需指定一个字符集
        gzip.close();
        long time2 = System.currentTimeMillis();
        // System.out.println("compress time:"+(time2 - time1)/1000.0);
        // 使用指定的 charsetName，通过解码字节将缓冲区内容转换为字符串
        return out.toString("ISO-8859-1");
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView=inflater.inflate(R.layout.fragment_blank_new_daily, container, false);
        iv_h_pic1=rootView.findViewById(R.id.iv_h_pic1);
        btn_h_submit=rootView.findViewById(R.id.btn_h_submit);

        tv_new_title=rootView.findViewById(R.id.tv_new_title);
        tv_new_content=rootView.findViewById(R.id.tv_new_content);

        ca = Calendar.getInstance();
        mYear = ca.get(Calendar.YEAR);
        mMonth = ca.get(Calendar.MONTH);
        mDay = ca.get(Calendar.DAY_OF_MONTH);
        mHour=ca.get(Calendar.HOUR_OF_DAY);
        mMin=ca.get(Calendar.MINUTE);
        mSec=ca.get(Calendar.SECOND);
        create=String.valueOf(mYear)+String.valueOf(mMonth)+String.valueOf(mDay)+String.valueOf(mHour)+String.valueOf(mMin)+String.valueOf(mSec);

        iv_h_pic1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /**
                 *
                 * 访问相册选择图片
                 */
                Intent intent = new Intent(Intent.ACTION_PICK, null);

                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");

                startActivityForResult(intent, 0x1);//---------------------------------------------------这里看startActivityForResult这个函数


            }
        });

        btn_h_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InputMethodManager imm = ( InputMethodManager ) view.getContext( ).getSystemService( Context.INPUT_METHOD_SERVICE );
                if ( imm.isActive( ) ) {
                    imm.hideSoftInputFromWindow(view.getApplicationWindowToken(), 0);
                }

                String title=tv_new_title.getText().toString();
                String data=tv_new_content.getText().toString();
                //图片变量？？？
                String userid=MainActivity_login.user;
                Integer type=1;
                /**
                 * 把以上变量存到数据库------------------------------------------------------------------------------------
                 */

                if(pic_url!="-1") {

                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    bit1.compress(Bitmap.CompressFormat.JPEG, 50, baos);
                    byte[] imageBytes = baos.toByteArray();

                    String temp = Base64.encodeToString(imageBytes, Base64.DEFAULT);
                    try {
                        String imageString = compress(temp);
                        picupload(temp
                                ,  nowuser.user_id+create, getActivity());


                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
                //请求地址
                String url = "http://119.91.21.231:8080/women/LifepostServlet";    //注①
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
                            Toast.makeText(getContext(), "发送成功~", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getActivity(), MainActivity.class));
                            getActivity().finish();
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
                        map.put("piccnt",pic_url=="-1"?String.valueOf(0):String.valueOf(1));
                        map.put("data", data);
                        map.put("picurl", pic_url);
                        map.put("title", title);
                        map.put("create",create);
                        map.put("userid",String.valueOf(nowuser.user_id));
                        map.put("type",String.valueOf(type));
                        map.put("happiness",String.valueOf(-1));
                        return map;
                    }
                };

                //设置Tag标签
                request.setTag(tag);

                //将请求添加到队列中
                requestQueue.add(request);






            }
        });

        return rootView;
    }


}

