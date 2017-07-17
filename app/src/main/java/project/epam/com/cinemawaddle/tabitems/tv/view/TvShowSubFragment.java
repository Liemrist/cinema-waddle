package project.epam.com.cinemawaddle.tabitems.tv.view;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import project.epam.com.cinemawaddle.R;
import project.epam.com.cinemawaddle.tabitems.base.BaseTabViewImpl;
import project.epam.com.cinemawaddle.tabitems.tv.model.TvShowsModel;
import project.epam.com.cinemawaddle.tabitems.tv.presenter.TvShowPresenter;
import project.epam.com.cinemawaddle.util.Constants;
import project.epam.com.cinemawaddle.util.service.tvshows.TvShow;


public class TvShowSubFragment extends BaseTabViewImpl<TvShow, TvShowPresenter> {
    private int position;


    public TvShowSubFragment() {}

    public static TvShowSubFragment newInstance(int index) {
        TvShowSubFragment fragment = new TvShowSubFragment();
        Bundle args = new Bundle();
        args.putInt(Constants.ARGUMENT_POSITION_INDEX, index);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tab, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        presenter = new TvShowPresenter(this, new TvShowsModel(getContext()));

        swipeRefreshLayout.setOnRefreshListener(() -> presenter.onSubSwipeRefresh(position));

        initRecycler();

        if (getArguments() != null) {
            position = getArguments().getInt(Constants.ARGUMENT_POSITION_INDEX);
            if (position == Constants.WATCHLIST) presenter.loadWatchlist(page);
            else if (position == Constants.FAVORITES) presenter.loadFavorites(page);
        }
    }

    @Override
    protected void initRecycler() {
        super.initRecycler();
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0)
                {
                    totalItemCount = layoutManager.getItemCount();
                    visibleItemCount = layoutManager.getChildCount();
                    pastVisibleItems = layoutManager.findFirstVisibleItemPosition();

                    if (loading) {
                        if ((visibleItemCount + pastVisibleItems) >= totalItemCount) {
                            loading = false;
                            if (position == Constants.WATCHLIST) presenter.loadWatchlist(page);
                            else if (position == Constants.FAVORITES) presenter.loadFavorites(page);
                        }
                    }
                }
            }
        });
    }

    @Override
    public void onMovieClick(TvShow movie) {
        presenter.onMovieClicked(getContext(), movie);
    }
}
