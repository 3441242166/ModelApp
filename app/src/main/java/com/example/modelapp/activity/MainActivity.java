package com.example.modelapp.activity;

import android.arch.lifecycle.ViewModelProviders;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.example.modelapp.R;
import com.example.modelapp.databinding.ActivityMainBinding;
import com.example.modelapp.model.MainActivityModel;
import com.example.mvvm.base.BaseActivity;

public class MainActivity extends BaseActivity<ActivityMainBinding, MainActivityModel> {


    @Override
    protected MainActivityModel getViewModel() {
        return ViewModelProviders.of(this).get(MainActivityModel.class);
    }

    @Override
    protected void initView() {
        FragmentPagerAdapter adapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int i) {
                return viewModel.getFragments().get(i);
            }

            @Override
            public int getCount() {
                return viewModel.getFragments().size();
            }
        };
        binding.viewpager.setAdapter(adapter);
        binding.viewpager.setOffscreenPageLimit(3);
    }

    @Override
    protected void initEvent() {
        binding.bottom.setOnNavigationItemSelectedListener(item -> {
            binding.group.openBottomLayout();
            return false;
        });
        binding.bottom.setOnNavigationItemSelectedListener(item -> {
            binding.group.openBottomLayout();
            switch (item.getItemId()) {
                case R.id.main_menu_home:
                    binding.viewpager.setCurrentItem(0);
                    return true;
                case R.id.main_menu_date:
                    binding.viewpager.setCurrentItem(1);
                    return true;
                case R.id.main_menu_message:
                    binding.viewpager.setCurrentItem(2);
                    return true;
                case R.id.main_menu_my:
                    binding.viewpager.setCurrentItem(3);
                    return true;
            }
            return false;
        });

        binding.viewpager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        binding.bottom.setSelectedItemId(R.id.main_menu_home);
                        break;
                    case 1:
                        binding.bottom.setSelectedItemId(R.id.main_menu_date);
                        break;
                    case 2:
                        binding.bottom.setSelectedItemId(R.id.main_menu_message);
                        break;
                    case 3:
                        binding.bottom.setSelectedItemId(R.id.main_menu_my);
                        break;
                }
            }
        });
    }

    @Override
    public int getContentView() {
        return R.layout.activity_main;
    }
}
