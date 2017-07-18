package project.epam.com.cinemawaddle.main.view;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import project.epam.com.cinemawaddle.R;
import project.epam.com.cinemawaddle.main.model.MainModel;
import project.epam.com.cinemawaddle.main.presenter.MainPresenter;
import project.epam.com.cinemawaddle.pager.TabHostFragment;
import project.epam.com.cinemawaddle.settings.SettingsActivity;
import project.epam.com.cinemawaddle.util.Constants;
import project.epam.com.cinemawaddle.util.service.authentication.Account;


public class MainActivity extends AppCompatActivity implements IMainView {

    @BindView(R.id.layout_drawer) DrawerLayout drawerLayout;
    @BindView(R.id.view_navigation) NavigationView navigationView;
    @BindView(R.id.toolbar) Toolbar toolbarView;
    @BindString(R.string.disconnected) String disconnected;

    private TextView nameView;
    private TextView usernameView;
    private ImageView imageView;
    private Unbinder unbinder;
    private MainPresenter presenter;
    private int currentCheckPosition;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        unbinder = ButterKnife.bind(this);

        presenter = new MainPresenter(this, new MainModel(getApplicationContext()));

        setSupportActionBar(toolbarView);

        if (savedInstanceState != null) {
            currentCheckPosition = savedInstanceState
                    .getInt(Constants.BUNDLE_CURRENT_POSITION, Constants.MOVIES);
        } else {
            currentCheckPosition = Constants.MOVIES;
        }

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.layout_container, TabHostFragment.newInstance(currentCheckPosition))
                .commit();
    }

    @Override
    protected void onStart() {
        super.onStart();

        nameView = ButterKnife.findById(navigationView.getHeaderView(0), R.id.text_name);
        imageView = ButterKnife.findById(navigationView.getHeaderView(0), R.id.image_avatar);
        usernameView = ButterKnife.findById(navigationView.getHeaderView(0), R.id.text_username);

        initDrawer();

        presenter.loadAccountDetails();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        presenter.detach();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // This adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.toolbar_main, menu);
        return true;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(Constants.BUNDLE_CURRENT_POSITION, currentCheckPosition);
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // This calls after 3rd party authentication request has been handled by user.
        if (requestCode == Constants.ACTIVITY_REQUEST_CODE) {
            presenter.createSession();
        }
    }


    @Override
    public void launchBrowser(Uri uri) {
        // Launches browser to allow 3rd party authentication.
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivityForResult(intent, Constants.ACTIVITY_REQUEST_CODE);
    }

    @Override
    public void showAccountDetails(Account account) {
        Glide.with(this)
                .load(Constants.URL_GRAVATAR + account.getGravatarHash() + Constants.URL_DEFAULT_IMAGE)
                .centerCrop()
                .placeholder(R.drawable.clapperboard)
                .fallback(R.drawable.clapperboard)
                .into(imageView);
        nameView.setText(account.getName());
        usernameView.setText(account.getUsername());
    }

    @Override
    public void hideAccountDetails() {
        Glide.with(this)
                .load(R.drawable.popcorn)
                .into(imageView);
        nameView.setText(R.string.you_are);
        usernameView.setText(R.string.not_authorized);

        showMessage(disconnected);
    }

    @Override
    public void showMessage(String message) {
        View view = getCurrentFocus();
        if (view != null) {
            Snackbar.make(view, message, Snackbar.LENGTH_LONG).show();
        }
    }

    private void initDrawer() {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,
                drawerLayout, toolbarView,
                R.string.openDrawer, R.string.closeDrawer);

        drawerLayout.addDrawerListener(toggle);

        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(menuItem -> {
            int id = menuItem.getItemId();

            switch (id) {
                case R.id.nav_connect:
                    presenter.connectToTmdb();
                    break;
                case R.id.nav_disconnect:
                    presenter.disconnectFromTmdb();
                    break;
                case R.id.nav_movies:
                    currentCheckPosition = Constants.MOVIES;
                    launchFragmentOfType(Constants.MOVIES);
                    break;
                case R.id.nav_tv_shows:
                    currentCheckPosition = Constants.TV_SHOWS;
                    launchFragmentOfType(Constants.TV_SHOWS);
                    break;
                case R.id.nav_settings:
                    Intent intent = new Intent(this, SettingsActivity.class);
                    startActivity(intent);
                    break;
            }

            drawerLayout.closeDrawers();
            return true;
        });
    }

    private void launchFragmentOfType(int initialType) {
        TabHostFragment fragment = (TabHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.layout_container);
        if (fragment != null && fragment.getArguments() != null) {
            int type = fragment.getArguments().getInt(Constants.ARGUMENT_TYPE);
            if (type != initialType) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.layout_container, TabHostFragment.newInstance(initialType))
                        .commit();
            }
        }
    }
}
