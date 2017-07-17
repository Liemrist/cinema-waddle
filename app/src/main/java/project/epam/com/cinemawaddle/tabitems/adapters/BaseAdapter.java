package project.epam.com.cinemawaddle.tabitems.adapters;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import project.epam.com.cinemawaddle.R;


abstract class BaseAdapter<T> extends RecyclerView.Adapter<BaseAdapter.ViewHolder> {

    List<T> objects;


    BaseAdapter(List<T> objects) {
        this.objects = objects;
    }

    @Override
    public BaseAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new BaseAdapter.ViewHolder(inflater.inflate(R.layout.item_movie, parent, false));
    }

    @Override
    public int getItemCount() {
        return objects.size();
    }

    protected abstract String getVote(T object);

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.text_title) TextView titleView;
        @BindView(R.id.text_date) TextView genreView;
        @BindView(R.id.text_rating) TextView ratingView;
        @BindView(R.id.image_poster) ImageView posterView;

        T object;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
