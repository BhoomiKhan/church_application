package com.cornicosia.android.utils;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import com.cornicosia.android.fragments.FetchArticolePosts;
import com.cornicosia.android.fragments.FetchGaleriePosts;
import com.cornicosia.android.fragments.FetchProgramPosts;
import com.cornicosia.android.fragments.FetchStiriPosts;
import com.cornicosia.android.fragments.PostContentList;


public class TabPageAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public TabPageAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                PostContentList tab1 = new PostContentList();
                return tab1;
            case 1:
                FetchProgramPosts tab2 = new FetchProgramPosts();
                return tab2;
            case 2:
                FetchArticolePosts tab3 = new FetchArticolePosts();
                return tab3;
            case 3:
                FetchStiriPosts tab4 = new FetchStiriPosts();
                return tab4;
            case 4:
                FetchGaleriePosts tab5 = new FetchGaleriePosts();
                return tab5;

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}