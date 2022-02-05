package com.dc.news;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.dc.base.activity.BaseActivity;
import com.dc.base.viewmodel.BaseViewModel;
import com.dc.news.databinding.ActivityMainBinding;
import com.dc.news.homefragment.HomeFragment;
import com.dc.news.otherfragments.AccountFragment;
import com.dc.news.otherfragments.CategoryFragment;
import com.dc.news.otherfragments.ServiceFragment;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;

import q.rorbin.badgeview.QBadgeView;

public class MainActivity extends BaseActivity<ActivityMainBinding, BaseViewModel> {
    private HomeFragment mHomeFragment = new HomeFragment();
    private CategoryFragment mCategoryFragment = new CategoryFragment();
    private ServiceFragment mServiceFragment = new ServiceFragment();
    private AccountFragment mAccountFragment = new AccountFragment();

    Fragment fromFragment;

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected BaseViewModel getViewModel() {
        return null;
    }

    @Override
    public int getBindingVariable() {
        return 0;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        fromFragment = mHomeFragment;

        setSupportActionBar(viewDataBinding.toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getString(R.string.menu_home));

        viewDataBinding.bottomView.setOnNavigationItemSelectedListener(menuItem -> {
            Fragment fragment = null;
            switch (menuItem.getItemId()) {
                case R.id.menu_home:
                    fragment = mHomeFragment;
                    break;
                case R.id.menu_categories:
                    fragment = mCategoryFragment;
                    break;
                case R.id.menu_services:
                    fragment = mServiceFragment;
                    break;
                case R.id.menu_account:
                    fragment = mAccountFragment;
                    break;
                default:
                    break;
            }
            if(getSupportActionBar() != null) {
                getSupportActionBar().setTitle(menuItem.getTitle());
            }
            switchFragment(fromFragment, fragment);
            fromFragment = fragment;
            return true;
        });
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(viewDataBinding.container.getId(), mHomeFragment);
        transaction.commit();
        showBadgeView(3, 5);
    }

    private void switchFragment(Fragment from, Fragment to) {
        if (from != to) {
            FragmentManager manger = getSupportFragmentManager();
            FragmentTransaction transaction = manger.beginTransaction();
            if (!to.isAdded()) {
                if (from != null) {
                    transaction.hide(from);
                }
                transaction.add(R.id.container, to).commit();

            } else {
                if (from != null) {
                    transaction.hide(from);
                }
                transaction.show(to).commit();

            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: {
                finish();
                break;
            }
            // case blocks for other MenuItems (if any)
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    /**
     * BottomNavigationView显示角标
     *
     * @param viewIndex  tab索引
     * @param showNumber 显示的数字，小于等于0是将不显示
     */
    private void showBadgeView(int viewIndex, int showNumber) {
        // 具体child的查找和view的嵌套结构请在源码中查看
        // 从bottomNavigationView中获得BottomNavigationMenuView
        BottomNavigationMenuView menuView = (BottomNavigationMenuView) viewDataBinding.bottomView.getChildAt(0);
        // 从BottomNavigationMenuView中获得childview, BottomNavigationItemView
        if (viewIndex < menuView.getChildCount()) {
            // 获得viewIndex对应子tab
            View view = menuView.getChildAt(viewIndex);
            // 从子tab中获得其中显示图片的ImageView
            View icon = view.findViewById(com.google.android.material.R.id.icon);
            // 获得图标的宽度
            int iconWidth = icon.getWidth();
            // 获得tab的宽度/2
            int tabWidth = view.getWidth() / 2;
            // 计算badge要距离右边的距离
            int spaceWidth = tabWidth - iconWidth;

            // 显示badegeview
            new QBadgeView(this).bindTarget(view).setGravityOffset(spaceWidth + 50, 13, false).setBadgeNumber(showNumber);
        }
    }
}