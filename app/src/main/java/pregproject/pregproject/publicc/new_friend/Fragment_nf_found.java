package pregproject.pregproject.publicc.new_friend;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import pregproject.pregproject.other.MainActivity_login;
import com.example.pregproject.R;

import java.util.HashMap;
import java.util.Map;

public class Fragment_nf_found extends Fragment {

    private String phone,name,data;
    int recid;
    private View rootView;
    private TextView tv_c_f_name;
    private EditText et_c_f_msg;
    private Button btn_c_apply_n,btn_c_apply_c;

    public Fragment_nf_found() {
        // Required empty public constructor
    }


    public static Fragment_nf_found newInstance() {
        Fragment_nf_found fragment = new Fragment_nf_found();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        name = this.getArguments().getString("username");
        recid=this.getArguments().getInt("userid");


        rootView=inflater.inflate(R.layout.fragment_nf_found, container, false);

        tv_c_f_name=rootView.findViewById(R.id.tv_c_f_name);
        et_c_f_msg=rootView.findViewById(R.id.et_c_f_msg);
        btn_c_apply_n=rootView.findViewById(R.id.btn_c_apply_n);
        btn_c_apply_c=rootView.findViewById(R.id.btn_c_apply_c);

        /**
         * 查出给个phone对应用户的用户名，填进下行代码----------------------------------------------------------------------------------------
         */
        tv_c_f_name.setText(name);


        /**
         * 申请为普通好友
         */
        btn_c_apply_n.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InputMethodManager imm = ( InputMethodManager ) view.getContext( ).getSystemService( Context.INPUT_METHOD_SERVICE );
                if ( imm.isActive( ) ) {
                    imm.hideSoftInputFromWindow(view.getApplicationWindowToken(), 0);
                }
                String msg=et_c_f_msg.getText().toString().trim();
                if(MainActivity_login.nowuser.friendlist!=null&& MainActivity_login.nowuser.friendlist.contains("#"+recid+"#")){
                    Toast.makeText(getActivity(), "对方已经是您的好友", Toast.LENGTH_SHORT).show();
                }else{

                    //请求地址
                    String url = "http://119.91.21.231:8080/women/ApplyServlet";    //注①
                    String tag = "friend";    //注②

                    //取得请求队列
                    RequestQueue requestQueue = Volley.newRequestQueue(getContext());

                    //防止重复请求，所以先取消tag标识的请求队列
                    requestQueue.cancelAll(tag);

                    //创建StringRequest，定义字符串请求的请求方式为POST(省略第一个参数会默认为GET方式)

                    final StringRequest request = new StringRequest(Request.Method.POST, url,  new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            if(!response.equals("-1")){
                                Toast.makeText(getActivity(), "普通好友申请已发出", Toast.LENGTH_SHORT).show();//给出提示消息



                            }else{
                                Toast.makeText(getActivity(), "普通好友申请已发出", Toast.LENGTH_SHORT).show();//给出提示消息

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
                            map.put("request_type", "1");
                            map.put("sendid", String.valueOf(MainActivity_login.nowuser.user_id));
                            map.put("applytype", String.valueOf(1));
                            map.put("postid", String.valueOf(MainActivity_login.nowuser.user_id));
                            map.put("data", msg);
                            map.put("recid", String.valueOf(recid));
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


        btn_c_apply_c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String msg=et_c_f_msg.getText().toString().trim();
                if(MainActivity_login.nowuser.love_id!=-1){
                    Toast.makeText(getActivity(), "您已经拥有亲密好友", Toast.LENGTH_SHORT).show();
                }else{

                    //请求地址
                    String url = "http://119.91.21.231:8080/women/ApplyServlet";    //注①
                    String tag = "love";    //注②

                    //取得请求队列
                    RequestQueue requestQueue = Volley.newRequestQueue(getContext());

                    //防止重复请求，所以先取消tag标识的请求队列
                    requestQueue.cancelAll(tag);

                    //创建StringRequest，定义字符串请求的请求方式为POST(省略第一个参数会默认为GET方式)

                    final StringRequest request = new StringRequest(Request.Method.POST, url,  new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            if(!response.equals("-1")){
                                Toast.makeText(getActivity(), "亲密好友申请已发出", Toast.LENGTH_SHORT).show();//给出提示消息



                            }else{
                                Toast.makeText(getActivity(), "亲密好友申请已发出", Toast.LENGTH_SHORT).show();//给出提示消息

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
                            map.put("request_type", "1");
                            map.put("sendid", String.valueOf(MainActivity_login.nowuser.user_id));
                            map.put("applytype", String.valueOf(2));
                            map.put("postid", String.valueOf(MainActivity_login.nowuser.user_id));
                            map.put("data", msg);
                            map.put("recid", String.valueOf(recid));
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

        return rootView;
    }

}