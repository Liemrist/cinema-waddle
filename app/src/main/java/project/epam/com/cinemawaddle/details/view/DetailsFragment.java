package project.epam.com.cinemawaddle.details.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import project.epam.com.cinemawaddle.R;
import project.epam.com.cinemawaddle.details.model.DetailsModel;
import project.epam.com.cinemawaddle.details.presenter.DetailsPresenter;
import project.epam.com.cinemawaddle.util.Constants;
import project.epam.com.cinemawaddle.util.movies.Details;

public class DetailsFragment extends Fragment implements IMovieDetails {

    @BindView(R.id.text_overview) TextView textView;
    @BindView(R.id.button_fab) FloatingActionButton fabView;

    private DetailsPresenter presenter;
    private Unbinder unbinder;


    public DetailsFragment() {
        // Required empty public constructor
    }

    public static DetailsFragment newInstance(int index) {
        DetailsFragment fragment = new DetailsFragment();
        Bundle args = new Bundle();
        args.putInt(Constants.ARGUMENT_MOVIE_ID, index);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_details, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        final int movieId = getArguments().getInt(Constants.ARGUMENT_MOVIE_ID);

        fabView.setOnClickListener(view1 -> presenter.onMarkAsFavouriteClick(
                Constants.MEDIA_TYPE_MOVIE,
                movieId,
                true));

        presenter = new DetailsPresenter(this, new DetailsModel());
        presenter.loadDetails(movieId);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        presenter.detach();
    }

    @Override
    public void showProgress() {
        // todo: fill emptiness in their souls.
    }

    @Override
    public void hideProgress() {
        // todo: fill emptiness in their souls.
    }

    @Override
    public void showDetails(Details movie) {
        textView.setText(movie.getOverview());
    }

    @Override
    public void showMessage(String message) {
        View view = getView();
        if (view != null) {
            Snackbar.make(view, message, Snackbar.LENGTH_LONG)
                    .show();
        }
    }
}
