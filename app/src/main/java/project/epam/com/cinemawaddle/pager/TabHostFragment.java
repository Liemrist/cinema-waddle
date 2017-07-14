package project.epam.com.cinemawaddle.pager;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import project.epam.com.cinemawaddle.R;

public class TabHostFragment extends Fragment {
    @BindView(R.id.pager_movies) ViewPager viewPager;
    @BindView(R.id.layout_tabs) TabLayout tabLayout;


    public TabHostFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab_host, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        viewPager.setAdapter(new PagerAdapter(getContext(), getChildFragmentManager()));
        tabLayout.setupWithViewPager(viewPager);
        // This to set only in landscape for I don't know why reason.
//        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
    }
}
