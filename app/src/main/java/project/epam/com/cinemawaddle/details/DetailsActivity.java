package project.epam.com.cinemawaddle.details;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import project.epam.com.cinemawaddle.R;
import project.epam.com.cinemawaddle.details.view.DetailsFragment;
import project.epam.com.cinemawaddle.util.Constants;

public class DetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        int movieId = getIntent().getIntExtra(Constants.EXTRA_MOVIE_ID, Constants.DEFAULT_MOVIE_ID);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.layout_container_details, DetailsFragment.newInstance(movieId))
                .commit();
    }
}
