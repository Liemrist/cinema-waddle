package project.epam.com.cinemawaddle.tabitems.tv.view;


import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import project.epam.com.cinemawaddle.R;
import project.epam.com.cinemawaddle.tabitems.tv.model.TvShowsModel;
import project.epam.com.cinemawaddle.tabitems.tv.presenter.TvShowPresenter;
import project.epam.com.cinemawaddle.tabitems.BaseFragmentList;
import project.epam.com.cinemawaddle.tabitems.adapters.TvShowsRecyclerAdapter;
import project.epam.com.cinemawaddle.util.Constants;
import project.epam.com.cinemawaddle.util.tvshows.TvListResult;


public class TvShowsFragment extends BaseFragmentList implements ITvShowView,
        TvShowsRecyclerAdapter.OnMovieClickListener{

    private List<TvListResult> items;
    private TvShowPresenter presenter;
    protected Spinner spinnerView;

    public TvShowsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tab, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        swipeRefreshLayout.setOnRefreshListener(() -> presenter
                .onSwipeRefreshInteraction(spinnerView.getSelectedItemPosition()));

        spinnerView = ButterKnife.findById(getActivity(), R.id.spinner);

        initRecycler();

        initSpinner(R.array.tv_show_spinner_items);

        presenter = new TvShowPresenter(this, new TvShowsModel(getContext()));
    }

    @Override
    public void showMoviesAfterUpdate(List<TvListResult> items, int totalPages) {
        this.items.addAll(items);

        recyclerView.getAdapter().notifyDataSetChanged();

        if (totalPages > page) {
            loading = true;
            page++;
        }
    }

    @Override
    public void showMoviesSet(List<TvListResult> items) {
        this.items.clear();
        this.items.addAll(items);

        layoutManager.scrollToPositionWithOffset(0, 0);

        recyclerView.getAdapter().notifyDataSetChanged();

        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onMovieClick(TvListResult movie) {
        presenter.onMovieClicked(getContext(), movie);
    }

    @Override
    protected void initRecycler() {
        super.initRecycler();

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
                            presenter.loadMovies(spinnerView.getSelectedItemPosition(), page);
                        }
                    }
                }
            }
        });
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

    protected void initSpinner(int textArrayResId) {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                textArrayResId, R.layout.item_spinner);
        adapter.setDropDownViewResource(R.layout.item_spinner_dropdown);
        spinnerView.setAdapter(adapter);

        spinnerView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                items.clear();
                page = Constants.DEFAULT_PAGE;
                presenter.loadMovies(position, page);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }
}
