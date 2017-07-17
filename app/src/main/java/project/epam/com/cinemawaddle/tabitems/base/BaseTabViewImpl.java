package project.epam.com.cinemawaddle.tabitems.base;


import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import project.epam.com.cinemawaddle.R;
import project.epam.com.cinemawaddle.tabitems.CinemaRecyclerAdapter;
import project.epam.com.cinemawaddle.util.Constants;
import project.epam.com.cinemawaddle.util.service.BaseServiceArrayItem;


public abstract class BaseTabViewImpl<T extends BaseServiceArrayItem, S extends BaseTabPresenter<T>>
        extends Fragment implements BaseTabView<T>, CinemaRecyclerAdapter.OnMovieClickListener<T> {

    @BindView(R.id.text_message) TextView messageView;
    @BindView(R.id.progressBar) ProgressBar progressBarView;
    @BindView(R.id.view_movies) protected RecyclerView recyclerView;
    @BindView(R.id.layout_swipe_refresh) protected SwipeRefreshLayout swipeRefreshLayout;

    private Unbinder unbinder;

    protected S presenter;
    protected int page;
    protected int totalItemCount;
    protected int pastVisibleItems;
    protected int visibleItemCount;
    protected boolean loading;
    protected List<T> items;
    protected Spinner spinnerView;
    protected LinearLayoutManager layoutManager;


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        unbinder = ButterKnife.bind(this, view);

        page = Constants.DEFAULT_PAGE;

        loading = true;

        setHasOptionsMenu(true);

        spinnerView = ButterKnife.findById(getActivity(), R.id.spinner);
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
    public void showMoviesSet(List<T> items) {
        this.items.clear();
        this.items.addAll(items);

        layoutManager.scrollToPositionWithOffset(0, 0);

        recyclerView.getAdapter().notifyDataSetChanged();

        swipeRefreshLayout.setRefreshing(false);
    }


    @Override
    public void showMoviesAfterUpdate(List<T> items, int totalPages, int totalResults) {
        this.items.addAll(items);

        if (recyclerView != null) {
            recyclerView.getAdapter().notifyDataSetChanged();
        }

        if (totalPages > page) {
            loading = true;
            page++;
        } else if (totalResults == items.size()) {
            loading = false;
        }
    }

    @Override
    public void showProgressOnScroll() {
        progressBarView.setVisibility(View.VISIBLE);
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
            swipeRefreshLayout.setRefreshing(false);
            progressBarView.setVisibility(View.INVISIBLE);
            View view = getView();
            if (view != null) {
                Snackbar.make(view, message, Snackbar.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void showTextViewMessage(String message) {
        swipeRefreshLayout.setRefreshing(false);
        progressBarView.setVisibility(View.INVISIBLE);
        messageView.setVisibility(View.VISIBLE);
        messageView.setText(message);
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

        recyclerView.setAdapter(new CinemaRecyclerAdapter<>(getContext(), this, items));
    }

    protected void initSpinner(int textArrayResId) {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                textArrayResId, R.layout.item_spinner);
        adapter.setDropDownViewResource(R.layout.item_spinner_dropdown);
        spinnerView.setAdapter(adapter);
    }
}
