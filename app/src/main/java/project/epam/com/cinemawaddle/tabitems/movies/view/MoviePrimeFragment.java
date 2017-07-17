package project.epam.com.cinemawaddle.tabitems.movies.view;


import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import project.epam.com.cinemawaddle.R;
import project.epam.com.cinemawaddle.tabitems.base.BaseTabViewImpl;
import project.epam.com.cinemawaddle.tabitems.movies.model.TabModel;
import project.epam.com.cinemawaddle.tabitems.movies.presenter.MovieListPresenter;
import project.epam.com.cinemawaddle.util.Constants;
import project.epam.com.cinemawaddle.util.service.movies.Movie;


public class MoviePrimeFragment extends BaseTabViewImpl<Movie, MovieListPresenter> {

    public MoviePrimeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tab, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        presenter = new MovieListPresenter(this, new TabModel(getContext()));

        swipeRefreshLayout.setOnRefreshListener(() -> presenter
                .onPrimeSwipeRefresh(spinnerView.getSelectedItemPosition()));

        initRecycler();
        initSpinner(R.array.spinner_items);
    }


    @Override
    protected void initRecycler() {
        super.initRecycler();

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0) // Check for scroll down.
                {
                    totalItemCount = layoutManager.getItemCount();
                    visibleItemCount = layoutManager.getChildCount();
                    pastVisibleItems = layoutManager.findFirstVisibleItemPosition();

                    if (loading) {
                        if ((visibleItemCount + pastVisibleItems) >= totalItemCount) {
                            loading = false;
                            presenter.loadMoviesOnScroll(spinnerView.getSelectedItemPosition(), page);
                        }
                    }
                }
            }
        });
    }

    @Override
    protected void initSpinner(int textArrayResId) {
        super.initSpinner(textArrayResId);

        spinnerView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                items.clear();
                page = Constants.DEFAULT_PAGE;
                presenter.loadObjects(position, page);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }


    @Override
    public void onMovieClick(Movie movie) {
        presenter.onMovieClicked(getContext(), movie);
    }
}
