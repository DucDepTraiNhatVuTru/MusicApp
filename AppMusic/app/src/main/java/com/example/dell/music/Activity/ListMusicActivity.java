package com.example.dell.music.Activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.dell.music.DoApi.DoGetSongs;
import com.example.dell.music.Fragment.FragmentPlaylist;
import com.example.dell.music.Fragment.HomeFragment;
import com.example.dell.music.Fragment.MainPagerAdapter;
import com.example.dell.music.Host;
import com.example.dell.music.R;

public class ListMusicActivity extends AppCompatActivity {

    private EditText edtSearch;
    private Button btnSearch;
    private ListView lvSongs;
    private ViewPager viewPager;
    private BottomNavigationView bottomNavigationView;
    private MenuItem prevMenuItem = null;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_main);
        anhxa();

        final FragmentManager fragmentManager = getSupportFragmentManager();
        final MainPagerAdapter pagerAdapter = new MainPagerAdapter(fragmentManager);

        viewPager.setAdapter(pagerAdapter);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @SuppressLint("ResourceType")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.actionHome:
                        viewPager.setCurrentItem(0,true);
                        break;
                    case R.id.actionMyMusic:
                        viewPager.setCurrentItem(1,true);
                        break;
                    case R.id.actionAccount:
                        viewPager.setCurrentItem(2,true);
                }
                return false;
            }
        });

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if(prevMenuItem!=null){
                    prevMenuItem.setChecked(false);
                }else {
                    bottomNavigationView.getMenu().getItem(0).setChecked(false);
                }
                bottomNavigationView.getMenu().getItem(position).setChecked(true);
                prevMenuItem = bottomNavigationView.getMenu().getItem(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void anhxa(){
        viewPager = findViewById(R.id.viewPager);
        bottomNavigationView = findViewById(R.id.bottom_navigation);
    }

}
