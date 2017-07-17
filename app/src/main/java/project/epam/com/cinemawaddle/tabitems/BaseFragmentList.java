package project.epam.com.cinemawaddle.tabitems;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import project.epam.com.cinemawaddle.R;
import project.epam.com.cinemawaddle.util.Constants;
import project.epam.com.cinemawaddle.util.base.BasePresenter;
import project.epam.com.cinemawaddle.util.base.BaseView;


public abstract class BaseFragmentList extends Fragment implements BaseView {

    @BindView(R.id.text_message) TextView messageView;
    @BindView(R.id.progressBar) ProgressBar progressBarView;
    @BindView(R.id.view_movies) protected RecyclerView recyclerView;
    @BindView(R.id.swiperefresh_titles) protected SwipeRefreshLayout swipeRefreshLayout;

    private Unbinder unbinder;

    protected int page;
    protected int totalItemCount;
    protected int pastVisibleItems;
    protected int visibleItemCount;
    protected boolean loading;

    protected BasePresenter presenter;
    protected LinearLayoutManager layoutManager;


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        unbinder = ButterKnife.bind(this, view);

        page = Constants.DEFAULT_PAGE;

        loading = true;

        setHasOptionsMenu(true);
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

    protected void initRecycler() {
        // todo: use GridLayoutManager with landscape orientation.
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        RecyclerView.ItemDecoration itemDecoration =
                new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(itemDecoration);

        recyclerView.setHasFixedSize(true);
    }
}
