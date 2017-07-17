package project.epam.com.cinemawaddle.tabitems.tv.view;


import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

import project.epam.com.cinemawaddle.R;
import project.epam.com.cinemawaddle.tabitems.BaseTabViewImpl;
import project.epam.com.cinemawaddle.tabitems.adapters.TvShowsRecyclerAdapter;
import project.epam.com.cinemawaddle.tabitems.tv.model.TvShowsModel;
import project.epam.com.cinemawaddle.tabitems.tv.presenter.TvShowPresenter;
import project.epam.com.cinemawaddle.util.Constants;
import project.epam.com.cinemawaddle.util.service.tvshows.TvShow;


public class TvShowsFragment extends BaseTabViewImpl<TvShow, TvShowPresenter>
        implements TvShowsRecyclerAdapter.OnMovieClickListener {

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

        initRecycler();

        initSpinner(R.array.tv_show_spinner_items);

        presenter = new TvShowPresenter(this, new TvShowsModel(getContext()));
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


    @Override
    public void onMovieClick(TvShow movie) {
        presenter.onMovieClicked(getContext(), movie);
    }
}
