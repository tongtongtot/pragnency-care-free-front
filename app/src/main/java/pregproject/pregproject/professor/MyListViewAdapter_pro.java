package pregproject.pregproject.professor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.pregproject.R;

import java.util.ArrayList;

public class MyListViewAdapter_pro extends BaseAdapter {

    private ArrayList<Expertpost> items=new ArrayList<>();
    private Context context;

    public MyListViewAdapter_pro(ArrayList<Expertpost> items, Context context) {
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
            view = LayoutInflater.from(context).inflate(R.layout.item_profess, viewGroup, false);
        }
        TextView tv_p_title = view.findViewById(R.id.tv_p_title);
        TextView tv_p_name = view.findViewById(R.id.tv_p_name);
        TextView tv_p_data = view.findViewById(R.id.tv_p_data);

        tv_p_title.setText(items.get(i).getTitle());
       tv_p_name.setText(items.get(i).getUsername());
        tv_p_data.setText(items.get(i).getData().length()>20?items.get(i).getData().substring(0,19)+"...":items.get(i).getData());


        return view;
    }
}
