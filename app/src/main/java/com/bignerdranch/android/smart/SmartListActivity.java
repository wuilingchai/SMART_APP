package com.bignerdranch.android.smart;

import android.support.v4.app.Fragment;

public class SmartListActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment(){
        return new SmartListFragment();
    }
}
