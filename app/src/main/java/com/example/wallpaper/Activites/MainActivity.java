package com.example.wallpaper.Activites;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.wallpaper.Fragments.CollectionsFragmen;
import com.example.wallpaper.Fragments.FavoriteFragment;
import com.example.wallpaper.Fragments.PhotesFragment;
import com.example.wallpaper.R;
import com.example.wallpaper.Utils.Functions;
public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
            Functions.changeMainFragment(this,new PhotesFragment());
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_Photos) {
            PhotesFragment photesFragment=new PhotesFragment();
            Functions.changeMainFragment(MainActivity.this,photesFragment);
        } else if (id == R.id.nav_Collections) {
            CollectionsFragmen collectionsFragmen=new CollectionsFragmen();
            Functions.changeMainFragment(MainActivity.this,collectionsFragmen);
        } else if (id == R.id.nav_Favorite) {
            FavoriteFragment favoriteFragment=new FavoriteFragment();
            Functions.changeMainFragment(MainActivity.this,favoriteFragment);
        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
