package project.epam.com.cinemawaddle.main.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import project.epam.com.cinemawaddle.R;
import project.epam.com.cinemawaddle.main.model.MainModel;
import project.epam.com.cinemawaddle.main.presenter.MainPresenter;
import project.epam.com.cinemawaddle.pager.TabHostFragment;
import project.epam.com.cinemawaddle.util.Constants;
import project.epam.com.cinemawaddle.util.authentication.Account;

// Modifier 'final' before variables. Also 'static final'.
// tabLayout.setTabGravity(TabLayout.GRAVITY_FILL) is default setting, but doesn't work (in
// landscape orientation). Why?

public class MainActivity extends AppCompatActivity implements IMainView {

    @BindView(R.id.layout_drawer) DrawerLayout drawerLayout;
    @BindView(R.id.view_navigation) NavigationView navigationView;
    @BindView(R.id.toolbar) Toolbar toolbarView;

    private TextView nameView;
    private TextView usernameView;
    private Unbinder unbinder;
    private MainPresenter presenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        unbinder = ButterKnife.bind(this);
        nameView = ButterKnife.findById(navigationView.getHeaderView(0), R.id.text_name);
        usernameView = ButterKnife.findById(navigationView.getHeaderView(0), R.id.text_username);

        setSupportActionBar(toolbarView);
        setupDrawerLayout();

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.layout_container_main, new TabHostFragment())
                .commit();

        presenter = new MainPresenter(this, new MainModel(getApplicationContext()));
        presenter.loadAccountDetails();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        presenter.detach();
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
        if (requestCode == Constants.ACTIVITY_REQUEST_CODE) {
            presenter.createSession();
        }
    }

    @Override
    public void showAccountDetails(Account account) {
        nameView.setText(account.getName());
        usernameView.setText(account.getUsername());
    }

    private void setupDrawerLayout() {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,
                drawerLayout,
                toolbarView,
                R.string.openDrawer,
                R.string.closeDrawer);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(menuItem -> {
            int id = menuItem.getItemId();

            if (id == R.id.nav_item1) {
                presenter.onAuthenticateClick();
            }

            menuItem.setChecked(false);
            drawerLayout.closeDrawers();
            return true;
        });
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
        View view = getCurrentFocus();
        if (view != null) {
            Snackbar.make(view, message, Snackbar.LENGTH_LONG)
                    .show();
        }
    }
}
