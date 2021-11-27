package com.jiajia.mypractisedemos.module.demo;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.jiajia.mypractisedemos.R;

import java.util.ArrayList;
import java.util.List;

public class Demo2Activity extends AppCompatActivity {

    private static final String TAG = "Demo2Activity";

    private TextView mTvAge;
    private EditText editText;

    TabLayout mTabLayout;
    ViewPager2 mViewPager;
    ViewPagerAdapter mAdapter;

    BlankFragment blankFragment1;
    BlankFragment blankFragment2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo2);
        initView();
    }

    private void initView() {
        mTvAge = findViewById(R.id.tv_age);
        editText = findViewById(R.id.edit_text);
        mTabLayout = findViewById(R.id.smart_record_tab);
        mViewPager = findViewById(R.id.smart_record_viewpager);

        blankFragment1 = new BlankFragment();
        blankFragment2 = new BlankFragment();
        List<Fragment> list = new ArrayList<>();
        list.add(blankFragment1);
        list.add(blankFragment2);
        mAdapter = new ViewPagerAdapter(this, list);
        mViewPager.setAdapter(mAdapter);

        new TabLayoutMediator(mTabLayout, mViewPager, (tab, position) -> {
            if (position == 0) {
                tab.setText("完整记录");
            } else if (position == 1) {
                tab.setText("标记信息");
            }
        }).attach();

        mTvAge.setOnClickListener(v -> {
            Intent intent = new Intent(this, Demo2Activity.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.d(TAG, "onNewIntent: ");
    }

    public String getInputContents() {
        String text = this.editText.getText().toString();
        this.editText.setText("");
        return text;
    }

    static class ViewPagerAdapter extends FragmentStateAdapter {

        private final List<Fragment> fragmentList;

        public ViewPagerAdapter(FragmentActivity activity, List<Fragment> fragmentList) {
            super(activity);
            this.fragmentList = fragmentList;
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getItemCount() {
            return  fragmentList.size();
        }

    }
}