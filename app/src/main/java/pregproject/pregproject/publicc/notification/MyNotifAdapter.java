package pregproject.pregproject.publicc.notification;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.pregproject.R;

import java.util.List;

public class MyNotifAdapter extends BaseAdapter {

   List<Applymsg> items;
    Context context;

    public MyNotifAdapter(List<Applymsg> items, Context context) {
        this.items = items;
        this.context = context;
    }


    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        view= LayoutInflater.from(context).inflate(R.layout.item_notif,viewGroup,false);
        TextView tv_notif_kind = view.findViewById(R.id.tv_notif_kind);

        if(items.get(i).getApplyType()==1){
            tv_notif_kind.setText("您有新的普通好友请求");
        }else if (items.get(i).getApplyType()==2){
            tv_notif_kind.setText("您有新的亲密好友请求");
        }else if (items.get(i).getApplyType()==3){
            tv_notif_kind.setText("您的亲密好友发布了新的日常");
        }

        return view;
    }
}
