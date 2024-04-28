package pregproject.pregproject.publicc.daily;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.pregproject.R;
import pregproject.pregproject.other.MainActivity;
import pregproject.pregproject.other.MainActivity_login;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;


public class BlankFragment_new_feeling extends Fragment {

    View rootView;
    Integer happiness=1;
    Calendar ca;
    int  mYear,mMonth,mDay,mHour,mMin;

    public BlankFragment_new_feeling() {
        // Required empty public constructor
    }


    public static BlankFragment_new_feeling newInstance() {
        BlankFragment_new_feeling fragment = new BlankFragment_new_feeling();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ca = Calendar.getInstance();
        mYear = ca.get(Calendar.YEAR);
        mMonth = ca.get(Calendar.MONTH);
        mDay = ca.get(Calendar.DAY_OF_MONTH);
        mHour=ca.get(Calendar.HOUR_OF_DAY);
        mMin=ca.get(Calendar.MINUTE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView=inflater.inflate(R.layout.fragment_blank_new_feeling, container, false);
        ImageView iv_flower_1 = rootView.findViewById(R.id.iv_flower_1);
        ImageView iv_flower_2 = rootView.findViewById(R.id.iv_flower_2);
        ImageView iv_flower_3 = rootView.findViewById(R.id.iv_flower_3);
        ImageView iv_flower_4 = rootView.findViewById(R.id.iv_flower_4);
        ImageView iv_flower_5 = rootView.findViewById(R.id.iv_flower_5);
        EditText tv_new_f_content=rootView.findViewById(R.id.tv_new_f_content);
        Button btn_n_f_submit=rootView.findViewById(R.id.btn_n_f_submit);




        iv_flower_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                iv_flower_1.setSelected(true);
                iv_flower_2.setSelected(false);
                iv_flower_3.setSelected(false);
                iv_flower_4.setSelected(false);
                iv_flower_5.setSelected(false);
                happiness=1;

            }
        });

        iv_flower_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                iv_flower_1.setSelected(true);
                iv_flower_2.setSelected(true);
                iv_flower_3.setSelected(false);
                iv_flower_4.setSelected(false);
                iv_flower_5.setSelected(false);
                happiness=2;

            }
        });

        iv_flower_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                iv_flower_1.setSelected(true);
                iv_flower_2.setSelected(true);
                iv_flower_3.setSelected(true);
                iv_flower_4.setSelected(false);
                iv_flower_5.setSelected(false);
                happiness=3;

            }
        });

        iv_flower_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                iv_flower_1.setSelected(true);
                iv_flower_2.setSelected(true);
                iv_flower_3.setSelected(true);
                iv_flower_4.setSelected(true);
                iv_flower_5.setSelected(false);
                happiness=4;

            }
        });

        iv_flower_5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                iv_flower_1.setSelected(true);
                iv_flower_2.setSelected(true);
                iv_flower_3.setSelected(true);
                iv_flower_4.setSelected(true);
                iv_flower_5.setSelected(true);
                happiness=5;

            }
        });

        btn_n_f_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InputMethodManager imm = ( InputMethodManager ) view.getContext( ).getSystemService( Context.INPUT_METHOD_SERVICE );
                if ( imm.isActive( ) ) {
                    imm.hideSoftInputFromWindow(view.getApplicationWindowToken(), 0);
                }
                String data=tv_new_f_content.getText().toString();
                String create=String.valueOf(mYear)+String.valueOf(mMonth)+String.valueOf(mDay)+String.valueOf(mHour)+String.valueOf(mMin);
                String title="今天的心情是"+String.valueOf(happiness)+"分";
                //图片变量不用上传，已经设置了 展示的时候调用本地数据库的图片
                String userid= MainActivity_login.user;
                Integer type=2;
                /**
                 * 上传以上变量的内容到数据库Lifepost表-------------------------------------------------------------------
                 */  //请求地址
                String url = "http://114.132.251.166:2233/women/LifepostServlet";    //注①
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
                        map.put("picurl", "-1");
                        map.put("title", title);
                        map.put("create",create);
                        map.put("userid",String.valueOf(MainActivity_login.nowuser.user_id));
                        map.put("type",String.valueOf(type));
                        map.put("happiness",String.valueOf(happiness));
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