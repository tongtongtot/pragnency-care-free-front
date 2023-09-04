package pregproject.pregproject.publicc.daily;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.pregproject.R;

import java.util.ArrayList;

public class MyListViewAdapter extends BaseAdapter {

    private ArrayList<comments> items;
    private Context context;

    public MyListViewAdapter(ArrayList<comments> items, Context context) {
        this.items = items;
        this.context = context;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int i) {
        return items.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view==null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_comment, viewGroup, false);
        }

        TextView tv_comment_name = view.findViewById(R.id.tv_comment_name);
        TextView tv_comment_content = view.findViewById(R.id.tv_comment_content);




        tv_comment_name.setText(items.get(i).getUsername());

        tv_comment_content.setText(items.get(i).getData());

        return view;
    }
}
