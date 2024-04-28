package pregproject.pregproject.publicc;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.pregproject.R;

import pregproject.pregproject.publicc.daily.Lifepost;
import pregproject.pregproject.publicc.daily.SubActivity_new_daily;
import pregproject.pregproject.publicc.daily.SubActivity_public_detail;
import pregproject.pregproject.publicc.new_friend.SubActivity_newFriend;
import pregproject.pregproject.publicc.notification.SubActivity_notification;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class Fragment_public extends Fragment {


    private View root;
    private RecyclerView recyclerView;
    ArrayList<Lifepost> DataList=new ArrayList<>();
    private ImageView iv_public_add_friend,iv_public_new,iv_public_message;
    StaggerAdapter staggerAdapter;
    public Fragment_public() {
        // Required empty public constructor
    }

    public static Fragment_public newInstance() {
        Fragment_public fragment = new Fragment_public();
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
            root=inflater.inflate(R.layout.fragment_public, container, false);
        }

        /**
         * 控件注册
         */

        recyclerView = root.findViewById(R.id.recycleView);
        iv_public_add_friend = root.findViewById(R.id.iv_public_add_friend);
        iv_public_new = root.findViewById(R.id.iv_public_new);
        iv_public_message = root.findViewById(R.id.iv_public_message);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);

        staggerAdapter =new StaggerAdapter(DataList);
        recyclerView.setAdapter(staggerAdapter);
        staggerAdapter.setOnItemClickListener(new StaggerAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(Integer position) {

                /**
                 * 写监听事件
                 */
                //  Toast.makeText(getContext(), "点击了"+position, Toast.LENGTH_SHORT).show();

                Bundle bundle=new Bundle();
                bundle.putInt("post_id",DataList.get(position).getid());
                Intent intent = new Intent(getActivity(), SubActivity_public_detail.class);
                intent.putExtras(bundle);
                startActivity(intent); //一定要跳转
                //   reload();
            }
        });





        return root;
    }

    @Override
    public void onStart() {
        super.onStart();
        DataList.clear();
        /**
         * 查出Lifepost的所有数据放在DataList数组里。-------------------------------------------------------------
         */
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

                    try {
                        JSONObject jsonObject = (JSONObject) new JSONObject(response).get("params");
                        JSONArray lifeposts = jsonObject.getJSONArray("posts");
                        try {
                            for (int i = lifeposts.length()-1; i >=0 ; i--) {
                                JSONObject jsonObject1 = lifeposts.getJSONObject(i);    //注②
                                Lifepost lifepost=new Lifepost();
                                lifepost.setid(jsonObject1.getInt("post_id"));
                                lifepost.setData(jsonObject1.getString("data"));
                                lifepost.setPic_url(jsonObject1.getString("pic_url"));
                                lifepost.setCreate(jsonObject1.getString("create"));
                                lifepost.setLike(jsonObject1.getInt("like"));
                                lifepost.setPic_cnt(jsonObject1.getInt("pic_cnt"));
                                lifepost.setCommentnum(jsonObject1.getInt("commentnum"));
                                lifepost.setUserid(jsonObject1.getInt("userid"));
                                lifepost.setType(jsonObject1.getInt("type"));
                                lifepost.setHappiness(jsonObject1.getInt("happiness"));
                                lifepost.setCommentlist(jsonObject1.getString("commentlist"));
                                lifepost.setTitle(jsonObject1.getString("title"));
                                lifepost.setUsername(jsonObject1.getString("username"));

                                DataList.add(lifepost);
                                //  bookList.add(book);
                            }
                            staggerAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }




                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    //设置布局管理器

                    //设置适配器




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
                map.put("request_type",String.valueOf(6));


                return map;
            }
        };

        //设置Tag标签
        request.setTag(tag);

        //将请求添加到队列中
        requestQueue.add(request);
        /**
         * 写控件逻辑
         */


        iv_public_add_friend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(getActivity(), SubActivity_newFriend.class));

            }
        });

        iv_public_new.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(getActivity(), SubActivity_new_daily.class));

            }
        });

        iv_public_message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(getActivity(), SubActivity_notification.class));

            }
        });

    }

}