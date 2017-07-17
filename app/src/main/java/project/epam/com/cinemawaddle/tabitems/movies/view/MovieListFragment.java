package project.epam.com.cinemawaddle.tabitems.movies.view;


import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import project.epam.com.cinemawaddle.tabitems.movies.model.TabModel;
import project.epam.com.cinemawaddle.tabitems.movies.presenter.MovieListPresenter;
import project.epam.com.cinemawaddle.tabitems.BaseFragmentList;
import project.epam.com.cinemawaddle.tabitems.adapters.MoviesRecyclerAdapter;
import project.epam.com.cinemawaddle.util.Constants;
import project.epam.com.cinemawaddle.util.movies.Movie;


public class MovieListFragment extends BaseFragmentList implements ITabView,
        MoviesRecyclerAdapter.OnMovieClickListener {

    private List<Movie> items;
    private MovieListPresenter presenter;
    protected Spinner spinnerView;


    public MovieListFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tab, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        spinnerView = ButterKnife.findById(getActivity(), R.id.spinner);

        swipeRefreshLayout.setOnRefreshListener(() -> presenter
                .onSwipeRefreshInteraction(spinnerView.getSelectedItemPosition()));

        initRecycler();

        initSpinner(R.array.spinner_items);

        presenter = new MovieListPresenter(this, new TabModel(getContext()));
    }

    @Override
    public void showMoviesAfterUpdate(List<Movie> items, int totalPages, int totalResults) {
        this.items.addAll(items);

        if (recyclerView != null) {
            recyclerView.getAdapter().notifyDataSetChanged();
        }

        if (totalPages > page) {
            loading = true;
            page++;
        }
    }

    @Override
    public void showMoviesSet(List<Movie> items) {
        this.items.clear();
        this.items.addAll(items);

        layoutManager.scrollToPositionWithOffset(0, 0);

        recyclerView.getAdapter().notifyDataSetChanged();

        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onMovieClick(Movie movie) {
        presenter.onMovieClicked(getContext(), movie);
    }

    @Override
    protected void initRecycler() {
        super.initRecycler();

        items = new ArrayList<>();

        recyclerView.setAdapter(new MoviesRecyclerAdapter(getContext(), this, items));
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
