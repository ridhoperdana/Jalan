package net.ridhoperdana.jalan;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.FrameLayout;

import static net.ridhoperdana.jalan.R.id.navigationView;

public class BaseActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private NavigationView navigationView;
    private ActionBarDrawerToggle drawerToggle;
    private DrawerLayout fullView;
    private Toolbar toolbar;
    private int selectedNavItemId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_base);
    }

    @Override
    public void setContentView(int layoutResID)
    {
        fullView = (DrawerLayout) getLayoutInflater().inflate(R.layout.activity_base, null);

        FrameLayout activityContainer = (FrameLayout) fullView.findViewById(R.id.activity_content);
        getLayoutInflater().inflate(layoutResID, activityContainer, true);
        super.setContentView(fullView);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        navigationView = (NavigationView) findViewById(R.id.navigationView);
        setSupportActionBar(toolbar);
//        setTitle("Activity Title");
        setUpNavView();
    }


    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        fullView.closeDrawer(GravityCompat.START);
        selectedNavItemId = item.getItemId();

        return onOptionsItemSelected(item);
    }

    protected void setUpNavView()
    {
        navigationView.setNavigationItemSelectedListener(this);

        if( useDrawerToggle()) { // use the hamburger menu
            drawerToggle = new ActionBarDrawerToggle(this, fullView, toolbar,
                    R.string.navigation_drawer_open,
                    R.string.navigation_drawer_close);

            fullView.setDrawerListener(drawerToggle);
            drawerToggle.syncState();
        }
//        else if(useToolbar() && getSupportActionBar() != null) {
//            // Use home/back button instead
//            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//            getSupportActionBar().setHomeAsUpIndicator(getResources()
//                    .getDrawable(R.drawable.abc_ic_ab_back_mtrl_am_alpha));
//        }
    }

    protected boolean useToolbar()
    {
        return true;
    }

    protected boolean useDrawerToggle()
    {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if(id==R.id.tombolRiwayat)
        {
            startActivity(new Intent(this, RiwayatActivity.class));
        }
        if(id==R.id.tombolPengaturan)
        {
            startActivity(new Intent(this, PengaturanActivity.class));
        }
        if(id==R.id.tombolBeranda)
        {
            startActivity(new Intent(this, MainActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }
}
