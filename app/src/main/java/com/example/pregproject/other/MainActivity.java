package com.example.pregproject.other;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.pregproject.R;
import com.example.pregproject.forum.Fragment_forum;
import com.example.pregproject.professor.Fragment_professor;
import com.example.pregproject.publicc.Fragment_public;
import com.example.pregproject.publicc.notification.Applymsg;
import com.example.pregproject.publicc.notification.SubActivity_notification;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.example.pregproject.other.MainActivity_login.nowuser;

public class MainActivity extends AppCompatActivity {

    ViewPager2 ViewPager;
    LinearLayout ll_forum,ll_professor,ll_public;
    ArrayList<Fragment> FragmentList=new ArrayList<>();
    ImageView iv_public,iv_professor,iv_forum,ivnow;
    private MediaPlayer bgmediaPlayer;
    private void getNotification(){
        if(!NotificationsUtils.isNotificationEnabled(this))
        {

            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this)
                    .setCancelable(true)
                    .setTitle("检测到通知权限未开启!")
                    .setMessage("如果不开启权限会收不到推送通知哦~")
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    })
                    .setPositiveButton("去开启", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            dialog.cancel();
                            Intent intent = new Intent();
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                intent.setAction("android.settings.APP_NOTIFICATION_SETTINGS");
                                intent.putExtra("android.provider.extra.APP_PACKAGE", MainActivity.this.getPackageName());
                            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) { //5.0
                                intent.setAction("android.settings.APP_NOTIFICATION_SETTINGS");
                                intent.putExtra("app_package", MainActivity.this.getPackageName());
                                intent.putExtra("app_uid", MainActivity.this.getApplicationInfo().uid);
                                startActivity(intent);
                            } else if (Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT) { //4.4
                                intent.setAction("Settings.ACTION_APPLICATION_DETAILS_SETTINGS");
                                intent.addCategory(Intent.CATEGORY_DEFAULT);
                                intent.setData(Uri.parse("package:" + MainActivity.this.getPackageName()));
                            } else if (Build.VERSION.SDK_INT >= 15) {
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                intent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
                                intent.setData(Uri.fromParts("package", MainActivity.this.getPackageName(), null));
                            }
                            startActivity(intent);
                        }
                    });
            builder.create().show();
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /**
         * 隐藏导航栏等，做到全屏
         */
        hide_bar hide_bar = new hide_bar(MainActivity.this);
        hide_bar.hide();

        /**
         * 注册
         */
         ViewPager = findViewById(R.id.ViewPager);

         ll_forum=findViewById(R.id.ll_forum);
         ll_professor=findViewById(R.id.ll_professor);
         ll_public=findViewById(R.id.ll_public);

        iv_public=findViewById(R.id.iv_public);
        iv_professor=findViewById(R.id.iv_professor);
        iv_forum=findViewById(R.id.iv_forum);


        /**
         * 初始化
         */

        FragmentList.add(Fragment_public.newInstance());
         FragmentList.add(Fragment_professor.newInstance());
         FragmentList.add(Fragment_forum.newInstance());

         ivnow=iv_public;
         ivnow.setSelected(true);


         ViewPager.setAdapter(new MyViewPagerAdapter(getSupportFragmentManager(),getLifecycle(),FragmentList));
        getNotification();

         if(nowuser.love_id>-1){
             new Thread() {

                 @Override

                 public void run() {

                     super.run();
                     if (bgmediaPlayer == null) {

                         bgmediaPlayer = MediaPlayer.create(getBaseContext(), R.raw.demo);
                         bgmediaPlayer.setLooping(true);
                         bgmediaPlayer.setVolume(0,0);
                         bgmediaPlayer.start();

                     }
                     while (true) {
                         //System.out.println("once:");
                         // NotificationManager manager=(NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                         //  if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
                         //      NotificationChannel channel=new NotificationChannel("ShadyPi","ShadyPi的博客针不戳",
                         //              NotificationManager.IMPORTANCE_HIGH);
                         //       manager.createNotificationChannel(channel);
                         //   }

                         //  Notification note = new NotificationCompat.Builder(getBaseContext(), "ShadyPi")
                         //          .setContentTitle("帅气的宝藏博主")
                         //          .setContentText("ShadyPi")
                         //         .setSmallIcon(R.drawable.flower)
                         //         .build();
                         // manager.notify(1,note);
//你必要实行的使命

                         try {

                             //请求地址
                             String url = "http://119.91.21.231:8080/women/ApplyServlet";    //注①
                             String tag = "loveapply";    //注②

                             //取得请求队列
                             RequestQueue requestQueue = Volley.newRequestQueue(getBaseContext());

                             //防止重复请求，所以先取消tag标识的请求队列
                             requestQueue.cancelAll(tag);

                             //创建StringRequest，定义字符串请求的请求方式为POST(省略第一个参数会默认为GET方式)

                             final StringRequest request = new StringRequest(Request.Method.POST, url,  new Response.Listener<String>() {
                                 @Override
                                 public void onResponse(String response) {

                                     if (!response.equals("#")) {
                                         try {

                                             JSONObject jsonObject = (JSONObject) new JSONObject(response).get("params");
                                             JSONArray applys = jsonObject.getJSONArray("Applymsg");

                                             for (int i = 0; i < applys.length(); i++) {

                                                 JSONObject jsonObject1 = applys.getJSONObject(i);    //注②
                                                 Applymsg apply = new Applymsg();
                                                 apply.setData(jsonObject1.getString("data"));
                                                 System.out.println("data:"+apply.getData());
                                                 apply.setApplyId(jsonObject1.getInt("apply_id"));
                                                 apply.setApplyType(jsonObject1.getInt("apply_type"));
                                                 apply.setSendId(jsonObject1.getInt("send_id"));
                                                 apply.setPostId(jsonObject1.getInt("post_id"));

                                             }
                                         } catch (JSONException e) {
                                             e.printStackTrace();
                                         }

                                             //注②


                                             NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

                                             if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
                                                 NotificationChannel channel=new NotificationChannel("Hi","你的亲密用户更新啦~",
                                                         NotificationManager.IMPORTANCE_HIGH);
                                                 manager.createNotificationChannel(channel);
                                             }
                                         Intent intent=new Intent(getBaseContext(), SubActivity_notification.class);
                                         PendingIntent pending=PendingIntent.getActivity(getBaseContext(),0,intent, PendingIntent.FLAG_IMMUTABLE);

                                         Notification note=new NotificationCompat.Builder(getBaseContext(),"Hi")
                                                 .setContentTitle("亲密用户更新啦")
                                                 .setContentText("来看看呀!记得送花花~")
                                                 .setSmallIcon(R.drawable.flower)
                                                 .setLargeIcon(BitmapFactory.decodeResource(getResources(),R.drawable.change_flower))
                                                 .setColor(Color.parseColor("#ff0000"))//设置小图标颜色
                                                 .setContentIntent(pending)//设置点击动作
                                                 .setAutoCancel(true)
                                                 .build();
                                             manager.notify(2,note);
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
                                     map.put("request_type",String.valueOf(5) );
                                     map.put("recid",String.valueOf(nowuser.user_id));
                                     return map;
                                 }
                             };

                             //设置Tag标签
                             request.setTag(tag);

                             //将请求添加到队列中
                             requestQueue.add(request);
                             Thread.sleep(10000);

                         } catch (InterruptedException es) {
                             es.printStackTrace();

                         }
                     }
                     //举行本人的操作
                 }

             }.start();
         }
        /**
         * 响应逻辑
         */

        ViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                changeTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
            }
        });

        ll_public.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeTab(0);
            }
        });

        ll_professor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeTab(1);
            }
        });

        ll_forum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeTab(2);
            }
        });
    }

    private void changeTab(int position) {
        ivnow.setSelected(false);//用来使没有选中的不亮起来
        switch (position){
            case 2:
                ViewPager.setCurrentItem(2);//实现我点击那个，就显示某个fragment。
                iv_forum.setSelected(true);//实现点击效果
                ivnow=iv_forum;
                break;
            case 1:
                ViewPager.setCurrentItem(1);
                iv_professor.setSelected(true);
                ivnow=iv_professor;
                break;
            case 0:
                ViewPager.setCurrentItem(0);
                iv_public.setSelected(true);
                ivnow=iv_public;
                break;
        }
    }
}