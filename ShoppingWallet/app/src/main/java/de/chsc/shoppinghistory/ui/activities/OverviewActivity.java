package de.chsc.shoppinghistory.ui.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import de.chsc.shoppinghistory.R;
import de.chsc.shoppinghistory.ui.adapter.OverviewViewPager2FragmentAdapter;
import de.chsc.shoppinghistory.util.GlobalConstants;

public class OverviewActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;
    private ViewPager2 viewPager2Overview;
    private OverviewViewPager2FragmentAdapter viewPager2FragmentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overview);
        this.initViews();
        this.initViewPager();
        this.initBottomNavigationView();
        this.initPageChangeListener();
    }

    private void initViews(){
        this.bottomNavigationView = findViewById(R.id.overview_bottom_navigation);
        this.viewPager2Overview = findViewById(R.id.view_pager_overview);
    }

    private void initViewPager(){
        this.viewPager2FragmentAdapter
                = new OverviewViewPager2FragmentAdapter(getSupportFragmentManager(), getLifecycle());
        this.viewPager2Overview.setAdapter(this.viewPager2FragmentAdapter);
    }

    private void initBottomNavigationView(){
        this.bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.menu_overview_id:
                        viewPager2Overview.setCurrentItem(0);
//                        getSupportFragmentManager().beginTransaction().replace(R.id.fcv_fragment_overview, new OverviewFragment()).commit();
                        break;
                    case R.id.menu_trash_id:
                        viewPager2Overview.setCurrentItem(1);
//                        getSupportFragmentManager().beginTransaction().replace(R.id.fcv_fragment_overview, new TrashFragment()).commit();
                        break;
                }
                return true;
            }
        });
    }

    private void initPageChangeListener(){
        this.viewPager2Overview.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                bottomNavigationView.getMenu().getItem(position).setChecked(true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
            }
        });
    }

    private void goToSettingsActivity(){
        Intent settingsIntent = new Intent(OverviewActivity.this, SettingsActivity.class);
        startActivityForResult(settingsIntent, GlobalConstants.REQUEST_CODE_SETTINGS);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.settings_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_settings:
                goToSettingsActivity();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }
}