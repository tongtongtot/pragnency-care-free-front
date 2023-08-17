package com.example.pregproject.publicc;

import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;
import com.example.pregproject.R;
import com.example.pregproject.publicc.daily.Lifepost;

import java.util.ArrayList;

public class StaggerAdapter extends RecyclerView.Adapter<StaggerAdapter.InnerHolder> {

    private  ArrayList<Lifepost> DataList=new ArrayList<>();
    private OnItemClickListener OnItemClickListener;

    public StaggerAdapter(ArrayList<Lifepost> DataList){
        this.DataList=DataList;
    }

    @NonNull
    @Override
    public InnerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=View.inflate(parent.getContext(), R.layout.item_public,null);
        return new InnerHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InnerHolder holder, int position) {
        holder.setData(DataList.get(position),position);
    }

    @Override
    public int getItemCount() {
        if(DataList!=null){
            return DataList.size();
        }
        return 0;
    }



    public void setOnItemClickListener(OnItemClickListener listener) {
        this.OnItemClickListener=listener;
        //设置一个监听（回调接口）
    }

    public interface OnItemClickListener{
        void OnItemClick(Integer position);
    }



    public class InnerHolder extends RecyclerView.ViewHolder {

        private ImageView iv_item_pub_pic,iv_item_pub_head,iv_item_pub_flower;
        private TextView tv_item_pub_title,tv_item_pub_user_name,tv_item_pub_count_flower;
        private Integer position;

        public InnerHolder(@NonNull View itemView) {
            super(itemView);
            iv_item_pub_pic = itemView.findViewById(R.id.iv_item_pub_pic);
            tv_item_pub_title = itemView.findViewById(R.id.tv_item_pub_title);
            iv_item_pub_head=itemView.findViewById(R.id.iv_item_pub_head);
            tv_item_pub_user_name=itemView.findViewById(R.id.tv_item_pub_user_name);
            iv_item_pub_flower=itemView.findViewById(R.id.iv_item_pub_flower);
            tv_item_pub_count_flower=itemView.findViewById(R.id.tv_item_pub_count_flower);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(OnItemClickListener!=null){
                        OnItemClickListener.OnItemClick(position);
                    }
                }
            });

        }


        public void setData(Lifepost bean_public,Integer position) {

            this.position=position;

            if(bean_public.getType()==2){
                switch (bean_public.getHappiness()){
                    case 1:
                        iv_item_pub_pic.setImageResource(R.drawable.star1);
                        break;
                    case 2:
                        iv_item_pub_pic.setImageResource(R.drawable.star2);
                        break;
                    case 3:
                        iv_item_pub_pic.setImageResource(R.drawable.star3);
                        break;
                    case 4:
                        iv_item_pub_pic.setImageResource(R.drawable.star4);
                        break;
                    case 5:
                        iv_item_pub_pic.setImageResource(R.drawable.star5);
                        break;
                    default:break;
                }
            }
            else if(!bean_public.getPic_url().equals("-1")){
                //请求地址
                String url = bean_public.getPic_url();    //注①
                String tag = "itempic";    //注②

                //取得请求队列
                RequestQueue requestQueue = Volley.newRequestQueue(itemView.getContext());

                //防止重复请求，所以先取消tag标识的请求队列
                requestQueue.cancelAll(tag);

                //创建StringRequest，定义字符串请求的请求方式为POST(省略第一个参数会默认为GET方式)
                ImageRequest request = new ImageRequest(url, new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap response) {
                        iv_item_pub_pic.setImageBitmap(response);
                    }
                }, 0,0, Bitmap.Config.RGB_565,new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //做自己的响应错误操作，如Toast提示（“请稍后重试”等）
                        Log.e("TAG", error.getMessage(), error);
                    }
                }) ;

                //设置Tag标签
                request.setTag(tag);

                //将请求添加到队列中
                requestQueue.add(request);
            }else{
                iv_item_pub_pic.setImageResource(R.drawable.defaultpic);
            }
            tv_item_pub_title.setText(bean_public.getTitle());
            iv_item_pub_head.setImageResource(R.drawable.head_w);
            tv_item_pub_user_name.setText(bean_public.getUsername());
            tv_item_pub_count_flower.setText(String.valueOf(bean_public.getLike()));
        }
    }
}
