package com.bignerdranch.android.smart;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import java.util.List;
import java.util.UUID;

public class SmartPagerActivity extends AppCompatActivity {

    private static final String EXTRA_SMART_ID =
            "com.bignerdranch.android.smart.smart_id";

    private ViewPager mViewPager;
    private List<Smart> mSmart;

    public static Intent newIntent (Context packageContext, UUID smartId){
        Intent intent = new Intent(packageContext, SmartPagerActivity.class);
        intent.putExtra(EXTRA_SMART_ID, smartId);
        return intent;
    }

    @Override
    protected void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smart_pager);

        UUID smartId = (UUID) getIntent()
                .getSerializableExtra(EXTRA_SMART_ID);

        mViewPager = (ViewPager) findViewById(R.id.smart_view_pager);

        mSmart = SmartLab.get(this).getSmart();
        FragmentManager fragmentManager = getSupportFragmentManager();
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {

            @Override
                    public Fragment getItem(int position){
                Smart smart = mSmart.get(position);
                return SmartFragment.newInstance(smart.getId());
            }

            @Override
                    public int getCount(){
                return mSmart.size();
            }
        });

        for (int i = 0; i < mSmart.size(); i++){
            if (mSmart.get(i).getId().equals(smartId)){
                mViewPager.setCurrentItem(i);
                break;
            }
        }
    }
}
