package project.epam.com.cinemawaddle.details;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import project.epam.com.cinemawaddle.R;
import project.epam.com.cinemawaddle.details.model.DetailsModel;
import project.epam.com.cinemawaddle.details.presenter.DetailsPresenter;
import project.epam.com.cinemawaddle.details.view.IMovieDetails;
import project.epam.com.cinemawaddle.util.Constants;


public abstract class BaseDetailsFragment<T> extends Fragment implements IMovieDetails<T> {

    @BindView(R.id.text_overview) protected TextView textView;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.fab1)
    FloatingActionButton fab1;
    @BindView(R.id.fab2)
    FloatingActionButton fab2;

    private Boolean isFabOpen = false;
    private Animation fab_open;
    private Animation fab_close;
    private Animation rotate_forward;
    private Animation rotate_backward;

    protected int movieId;

    protected DetailsPresenter presenter;
    private Unbinder unbinder;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        unbinder = ButterKnife.bind(this, view);

        movieId = getArguments().getInt(Constants.ARGUMENT_MOVIE_ID);
//
//        fabView.setOnClickListener(view1 -> presenter.onMarkAsFavouriteClick(
//                Constants.MEDIA_TYPE_MOVIE,
//                movieId,
//                true));

        fab_open = AnimationUtils.loadAnimation(getContext(), R.anim.fab_open);
        fab_close = AnimationUtils.loadAnimation(getContext(), R.anim.fab_close);
        rotate_forward = AnimationUtils.loadAnimation(getContext(), R.anim.rotate_forward);
        rotate_backward = AnimationUtils.loadAnimation(getContext(), R.anim.rotate_backward);

        fab.setOnClickListener(v -> animateFab());
        fab1.setOnClickListener(v -> Log.d("Raj", "Fab 1"));
        fab2.setOnClickListener(v -> Log.d("Raj", "Fab 2"));

        presenter = new DetailsPresenter(this, new DetailsModel(getContext()));
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
    public void showMessage(String message) {
        View view = getView();
        if (view != null) {
            Snackbar.make(view, message, Snackbar.LENGTH_LONG).show();
        }
    }

    private void animateFab() {
        if (isFabOpen) { // close action
            fab.startAnimation(rotate_backward);
            fab1.startAnimation(fab_close);
            fab2.startAnimation(fab_close);
        } else { // open action
            fab.startAnimation(rotate_forward);
            fab1.startAnimation(fab_open);
            fab2.startAnimation(fab_open);
        }
        fab1.setClickable(!isFabOpen);
        fab2.setClickable(!isFabOpen);
        isFabOpen = !isFabOpen;
    }
}
