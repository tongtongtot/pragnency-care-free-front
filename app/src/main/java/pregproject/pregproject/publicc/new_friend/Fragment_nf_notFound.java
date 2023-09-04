package pregproject.pregproject.publicc.new_friend;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.pregproject.R;


public class Fragment_nf_notFound extends Fragment {


    public Fragment_nf_notFound() {
        // Required empty public constructor
    }


    public static Fragment_nf_notFound newInstance() {
        Fragment_nf_notFound fragment = new Fragment_nf_notFound();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_nf_not_found, container, false);
    }
}