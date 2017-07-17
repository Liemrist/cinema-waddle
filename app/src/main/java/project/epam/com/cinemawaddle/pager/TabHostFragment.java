package project.epam.com.cinemawaddle.pager;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;

import butterknife.BindArray;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import project.epam.com.cinemawaddle.R;
import project.epam.com.cinemawaddle.pager.adapters.MoviePagerAdapter;
import project.epam.com.cinemawaddle.pager.adapters.TvShowsPagerAdapter;
import project.epam.com.cinemawaddle.util.Constants;


public class TabHostFragment extends Fragment {

    @BindView(R.id.pager_movies) ViewPager viewPager;
    @BindView(R.id.layout_tabs) TabLayout tabLayout;
    @BindArray(R.array.movie_tab_titles) String[] movieTabTitles;
    @BindArray(R.array.tv_show_tab_titles) String[] tvShowTabTitles;

    private Unbinder unbinder;
    private ActionBar actionBar;
    private Spinner spinnerView;


    public static TabHostFragment newInstance(int index) {
        TabHostFragment fragment = new TabHostFragment();
        Bundle args = new Bundle();
        args.putInt(Constants.ARGUMENT_TYPE_INDEX, index);
        fragment.setArguments(args);
        return fragment;
    }

    public TabHostFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tab_host, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        unbinder = ButterKnife.bind(this, view);

        if (getArguments() != null) {
            FragmentPagerAdapter pagerAdapter = null;

            int type = getArguments().getInt(Constants.ARGUMENT_TYPE_INDEX);
            if (type == Constants.MOVIES) {
                pagerAdapter = new MoviePagerAdapter(getChildFragmentManager(), movieTabTitles);
            } else if (type == Constants.TV_SHOWS) {
                pagerAdapter = new TvShowsPagerAdapter(getChildFragmentManager(), tvShowTabTitles);
            }

            initPager(pagerAdapter);

            tabLayout.setupWithViewPager(viewPager);
        }

        spinnerView = ButterKnife.findById(getActivity(), R.id.spinner);
        spinnerView.setVisibility(View.VISIBLE);

        actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (actionBar != null) actionBar.setDisplayShowTitleEnabled(false);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private void initPager(FragmentPagerAdapter pagerAdapter) {
        viewPager.setAdapter(pagerAdapter);
        viewPager.setOffscreenPageLimit(3);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                if (position > 0) {
                    if (actionBar != null) actionBar.setDisplayShowTitleEnabled(true);
                    spinnerView.setVisibility(View.GONE);
                } else {
                    if (actionBar != null) actionBar.setDisplayShowTitleEnabled(false);
                    spinnerView.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }
}
