package project.epam.com.cinemawaddle.tabitem.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import project.epam.com.cinemawaddle.R;
import project.epam.com.cinemawaddle.util.Constants;
import project.epam.com.cinemawaddle.util.movies.Movie;
import project.epam.com.cinemawaddle.tabitem.model.TabModel;
import project.epam.com.cinemawaddle.tabitem.presenter.MovieListPresenter;

public class MovieListFragment extends Fragment implements ITabView,
        MoviesAdapter.OnMovieClickListener {

    @BindView(R.id.progressBar) ProgressBar progressBarView;
    @BindView(R.id.view_movies) RecyclerView moviesView;
    @BindView(R.id.text_message) TextView messageView;

    private MovieListPresenter presenter;
    private Unbinder unbinder;


    public MovieListFragment() {
    }

    public static MovieListFragment newInstance(int index) {
        MovieListFragment fragment = new MovieListFragment();
        Bundle args = new Bundle();
        args.putInt(Constants.ARGUMENT_POSITION_INDEX, index);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        int position = getArguments().getInt(Constants.ARGUMENT_POSITION_INDEX);
        presenter = new MovieListPresenter(this, new TabModel(getContext(), position));
        presenter.loadMovies();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        if (presenter != null) presenter.detach();
    }

    @Override
    public void showProgress() {
        progressBarView.setVisibility(View.VISIBLE);
        moviesView.setVisibility(View.INVISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBarView.setVisibility(View.INVISIBLE);
        moviesView.setVisibility(View.VISIBLE);
    }

    @Override
    public void showMovies(List<Movie> items) {
        moviesView.setAdapter(new MoviesAdapter(getContext(), this, items));
        // todo: use GridLayoutManager with landscape orientation.
        moviesView.setLayoutManager(new LinearLayoutManager(getContext()));
        moviesView.setHasFixedSize(true);
    }

    @Override
    public void showMessage(String message) {
        progressBarView.setVisibility(View.INVISIBLE);
        messageView.setVisibility(View.VISIBLE);
        messageView.setText(message);
    }

    @Override
    public void onMovieClick(Movie movie) {
        presenter.onMovieClicked(movie);
    }
}
