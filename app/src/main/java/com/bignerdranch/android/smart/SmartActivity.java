package com.bignerdranch.android.smart;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.UUID;

public class SmartActivity extends SingleFragmentActivity {

    private static final String EXTRA_SMART_ID =
            "com.bignerdranch.android.smart.smart_id";

    public static Intent newIntent (Context packageContext, UUID smartId){
        Intent intent = new Intent(packageContext, SmartActivity.class);
        intent.putExtra(EXTRA_SMART_ID, smartId);
        return intent;
    }

    @Override
    protected Fragment createFragment(){
        UUID smartId = (UUID) getIntent()
                .getSerializableExtra(EXTRA_SMART_ID);
        return SmartFragment.newInstance(smartId);
    }
}
