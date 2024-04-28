package pregproject.pregproject.publicc.new_friend;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
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

import pregproject.pregproject.other.hide_bar;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SubActivity_newFriend extends AppCompatActivity {

    private EditText et_c_searchText;
    private ImageView btn_c_searchBtn,iv_default;
    private String search_name="",phone_input="";
    int search_id=0;
    private FrameLayout fl_chat_finded;
    private Fragment_nf_found fragment_found;
    private Fragment_nf_notFound fragment_not_found;
    private boolean found;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_new_friend);

        /**
         * 隐藏导航栏等，做到全屏
         */
        hide_bar hide_bar = new hide_bar(SubActivity_newFriend.this);
        hide_bar.hide();

        ImageView iv_public_add_friend_back = findViewById(R.id.iv_public_add_friend_back);
        et_c_searchText=findViewById(R.id.et_c_searchText);
        btn_c_searchBtn=findViewById(R.id.btn_c_searchBtn);
        iv_default=findViewById(R.id.iv_default);

        found=false;
        phone_input ="";
        fragment_found=Fragment_nf_found.newInstance();
        fragment_not_found=Fragment_nf_notFound.newInstance();


        /**
         * 查找
         */
        btn_c_searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {




                iv_default.setVisibility(View.GONE);


                phone_input =et_c_searchText.getText().toString().trim();

                /**
                 * 搜索用户手机号码，要用用户名变量phone_input去数据库查找User表---------------------------------------------------------------------------------------
                 * 如果找到，设置found变量=true,不然设为false。
                 */
                //请求地址
                String url = "http://114.132.251.166:2233/women/FinduserServlet";    //注①
                String tag = "regist";    //注②

                //取得请求队列
                RequestQueue requestQueue = Volley.newRequestQueue(getBaseContext());

                //防止重复请求，所以先取消tag标识的请求队列
                requestQueue.cancelAll(tag);

                //创建StringRequest，定义字符串请求的请求方式为POST(省略第一个参数会默认为GET方式)

                final StringRequest request = new StringRequest(Request.Method.POST, url,  new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(!response.equals("#")){
                            found=true;
                            try {

                                JSONObject jsonObject = (JSONObject) new JSONObject(response);

                                search_id=jsonObject.getInt("user_id");

                               search_name=jsonObject.getString("user_name");
                               if(jsonObject.getInt("user_type")==2){
                                   Toast.makeText( getBaseContext(),"该用户是医生用户，不可加好友噢~", Toast.LENGTH_SHORT).show();
                                   found=false;
                               }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                             }else{
                            found=false;
                        }
                        /**
                         * 装填Fragment
                         */

                        FragmentManager FragmentManager = getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = FragmentManager.beginTransaction();

                        if(found){

                            Bundle args = new Bundle();//用于Fragment和Activity之间的信息传递
                            args.putString("phone", phone_input);
                            args.putInt("userid",search_id);
                            args.putString("username",search_name);
                            fragment_found.setArguments(args);
                            fragmentTransaction.replace(R.id.fl_chat_finded,fragment_found);
                            fragmentTransaction.commit();
                        }
                        else {
                            Toast.makeText( getBaseContext(),"未找到该用户", Toast.LENGTH_SHORT).show();

                            fragmentTransaction.replace(R.id.fl_chat_finded,fragment_not_found);
                            fragmentTransaction.commit();
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
                        map.put("user_phone", phone_input);
                        map.put("findtype",String.valueOf(2));


                        return map;
                    }
                };

                //设置Tag标签
                request.setTag(tag);

                //将请求添加到队列中
                requestQueue.add(request);



            }
        });



        iv_public_add_friend_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });





    }
}