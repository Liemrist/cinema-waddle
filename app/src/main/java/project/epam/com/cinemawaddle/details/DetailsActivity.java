package project.epam.com.cinemawaddle.details;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import project.epam.com.cinemawaddle.R;
import project.epam.com.cinemawaddle.details.view.DetailsFragment;
import project.epam.com.cinemawaddle.details.view.TvShowDetailsFragment;
import project.epam.com.cinemawaddle.util.Constants;

public class DetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        int movieId = getIntent().getIntExtra(Constants.EXTRA_MOVIE_ID, Constants.DEFAULT_MOVIE_ID);
        int type = getIntent().getIntExtra(Constants.EXTRA_TYPE, 0);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        }

        Fragment fragment;
        if (type == Constants.TV_SHOWS) {
            fragment = TvShowDetailsFragment.newInstance(movieId);
        } else {
            fragment = DetailsFragment.newInstance(movieId);
        }

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.layout_container_details, fragment)
                .commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return false;

        }
    }
}
