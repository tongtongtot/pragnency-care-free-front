package pregproject.pregproject.other;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;

public class MyViewPagerAdapter extends FragmentStateAdapter {

    private ArrayList<Fragment> FragmentList=new ArrayList<>();

    public MyViewPagerAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle,ArrayList<Fragment> FragmentList_g) {
        super(fragmentManager, lifecycle);
        FragmentList=FragmentList_g;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return FragmentList.get(position);
    }

    @Override
    public int getItemCount() {
        return FragmentList.size();
    }
}
