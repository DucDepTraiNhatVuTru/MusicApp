package com.example.dell.music.Fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.dell.music.Fragment.FragmentPlaylist;
import com.example.dell.music.Fragment.HomeFragment;

public class MainPagerAdapter extends FragmentStatePagerAdapter {
    public MainPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment= null;
        switch (position){
            case 0:
                fragment = new HomeFragment();
                break;
            case 1:
                fragment = new FragmentPlaylist();
                break;
            case 2:
                fragment = new AccountFragment();
                break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 3;
    }

}
