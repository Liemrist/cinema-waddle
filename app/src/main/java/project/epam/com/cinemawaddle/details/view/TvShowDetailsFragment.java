package project.epam.com.cinemawaddle.details.view;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import project.epam.com.cinemawaddle.R;
import project.epam.com.cinemawaddle.details.BaseDetailsFragment;
import project.epam.com.cinemawaddle.util.Constants;
import project.epam.com.cinemawaddle.util.service.BaseDetailsItem;
import project.epam.com.cinemawaddle.util.service.movies.Details;
import project.epam.com.cinemawaddle.util.service.tvshows.TvShowDetails;

/**
 * A simple {@link Fragment} subclass.
 */
public class TvShowDetailsFragment extends BaseDetailsFragment<TvShowDetails> {


    public TvShowDetailsFragment() {
        // Required empty public constructor
    }

    public static TvShowDetailsFragment newInstance(int index) {
        TvShowDetailsFragment fragment = new TvShowDetailsFragment();
        Bundle args = new Bundle();
        args.putInt(Constants.ARGUMENT_MOVIE_ID, index);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tv_show_details, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        presenter.loadTvShowDetails(movieId);
    }

    @Override
    public void showDetails(TvShowDetails movie) {
        textView.setText(movie.getOverview());
    }
}
