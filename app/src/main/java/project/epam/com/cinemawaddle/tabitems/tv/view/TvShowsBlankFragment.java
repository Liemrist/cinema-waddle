package project.epam.com.cinemawaddle.tabitems.tv.view;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import project.epam.com.cinemawaddle.R;
import project.epam.com.cinemawaddle.tabitems.BaseTabView;
import project.epam.com.cinemawaddle.tabitems.adapters.TvShowsRecyclerAdapter;
import project.epam.com.cinemawaddle.tabitems.tv.model.TvShowsModel;
import project.epam.com.cinemawaddle.tabitems.tv.presenter.TvShowPresenter;
import project.epam.com.cinemawaddle.util.Constants;
import project.epam.com.cinemawaddle.util.service.tvshows.TvShow;


public class TvShowsBlankFragment extends Fragment implements BaseTabView<TvShow>,
        TvShowsRecyclerAdapter.OnMovieClickListener {


    @BindView(R.id.text_message)
    TextView messageView;
    @BindView(R.id.progressBar)
    ProgressBar progressBarView;
    @BindView(R.id.view_movies)
    protected RecyclerView recyclerView;
    @BindView(R.id.layout_swipe_refresh)
    protected SwipeRefreshLayout swipeRefreshLayout;

    private List<TvShow> items;
    private TvShowPresenter presenter;
    private int position;
    private Unbinder unbinder;

    protected int page;
    protected int totalItemCount;
    protected int pastVisibleItems;
    protected int visibleItemCount;
    protected boolean loading;

    protected LinearLayoutManager layoutManager;

    public TvShowsBlankFragment() {
    }

    public static TvShowsBlankFragment newInstance(int index) {
        TvShowsBlankFragment fragment = new TvShowsBlankFragment();
        Bundle args = new Bundle();
        args.putInt(Constants.ARGUMENT_POSITION_INDEX, index);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tab, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        unbinder = ButterKnife.bind(this, view);

        page = Constants.DEFAULT_PAGE;

        loading = true;

        presenter = new TvShowPresenter(this, new TvShowsModel(getContext()));

        setHasOptionsMenu(true);

        swipeRefreshLayout.setOnRefreshListener(() -> presenter
                .onSwipeRefreshInteractionBlank(position));

        initRecycler();


        if (getArguments() != null) {
            position = getArguments().getInt(Constants.ARGUMENT_POSITION_INDEX);
            if (position == Constants.WATCHLIST) presenter.loadWatchlist(page);
            else if (position == Constants.FAVORITES) presenter.loadFavorites(page);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        if (presenter != null) presenter.detach();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_connect:
                presenter.onSortByNameClick(items);
                return true;
            case R.id.nav_disconnect:
                presenter.onSortByRatingClick(items);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void showProgress() {
        progressBarView.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.INVISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBarView.setVisibility(View.INVISIBLE);
        recyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void showMessage(String message) {
        if (getView() != null) {
            progressBarView.setVisibility(View.INVISIBLE);
            messageView.setVisibility(View.VISIBLE);
            messageView.setText(message);
        }
    }

    @Override
    public void showMoviesAfterUpdate(List<TvShow> items, int totalPages, int totalResults) {
        this.items.addAll(items);

        recyclerView.getAdapter().notifyDataSetChanged();

        if (totalPages > page) {
            loading = true;
            page++;
        } else if (totalResults == items.size()) {
            loading = false;
        }
    }

    @Override
    public void showMoviesSet(List<TvShow> items) {
        this.items.clear();
        this.items.addAll(items);

        layoutManager.scrollToPositionWithOffset(0, 0);

        recyclerView.getAdapter().notifyDataSetChanged();

        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onMovieClick(TvShow movie) {
        presenter.onMovieClicked(getContext(), movie);
    }


    protected void initRecycler() {
        // todo: use GridLayoutManager with landscape orientation.
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        RecyclerView.ItemDecoration itemDecoration =
                new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(itemDecoration);

        recyclerView.setHasFixedSize(true);

        items = new ArrayList<>();

        recyclerView.setAdapter(new TvShowsRecyclerAdapter(getContext(), this, items));
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0) //check for scroll down
                {
                    visibleItemCount = layoutManager.getChildCount();
                    totalItemCount = layoutManager.getItemCount();
                    pastVisibleItems = layoutManager.findFirstVisibleItemPosition();

                    if (loading) {
                        if ((visibleItemCount + pastVisibleItems) >= totalItemCount) {
                            loading = false;
                            Log.v("...", "Last Item Wow !");
                            if (position == Constants.WATCHLIST) presenter.loadWatchlist(page);
                            else if (position == Constants.FAVORITES) presenter.loadFavorites(page);
                        }
                    }
                }
            }
        });
    }
}
