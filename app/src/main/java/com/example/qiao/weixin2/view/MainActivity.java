package com.example.qiao.weixin2.view;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.qiao.weixin2.Chart.chart_info;
import com.example.qiao.weixin2.ConnBluetooth.ConnectBluetooth;
import com.example.qiao.weixin2.R;
import com.example.qiao.weixin2.Round.steps;
import com.nightonke.boommenu.BoomMenuButton;
import com.nightonke.boommenu.Types.BoomType;
import com.nightonke.boommenu.Types.ButtonType;
import com.nightonke.boommenu.Types.PlaceType;
import com.nightonke.boommenu.Util;

import java.lang.reflect.Method;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private static String[] Colors = {
            "#F44336", "#E91E63", "#9C27B0", "#2196F3", "#03A9F4", "#00BCD4", "#009688", "#4CAF50",
            "#8BC34A", "#CDDC39", "#FFEB3B", "#FFC107", "#FF9800", "#FF5722", "#795548",
            "#9E9E9E", "#607D8B"};
    private TabLayout mTablayout;
    private ViewPager mViewPager;
    private TabLayout.Tab one;
    private TabLayout.Tab two;
    private TabLayout.Tab three;
    private TabLayout.Tab four;
    private TabLayout.Tab centertab;
    private boolean init = true;
    private BoomMenuButton boomMenuButton;
    private String[] mTitles = new String[]{"主页", "运动", "发现", "更多"};
    private int[] tabIcons = {
            R.drawable.home_line,
            R.drawable.walk,
            R.drawable.discover_line,
            R.drawable.setting_1
    };
    private int[] tabIconsPressed = {
            R.drawable.home_shape,
            R.drawable.walk2,
            R.drawable.discover_shape,
            R.drawable.setting_2
    };
//    @Override
//    public boolean onPrepareOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        count++;
//        System.out.println("count="+count);
//        return super.onPrepareOptionsMenu(menu);
//    }

    public static int GetRandomColor() {
        Random random = new Random();
        int p = random.nextInt(Colors.length);
        return Color.parseColor(Colors[p]);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        boomMenuButton = (BoomMenuButton) findViewById(R.id.boom);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("MyApp");
        toolbar.setTitleTextColor(Color.WHITE);
        initViews();
        initEvents();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    private void initEvents() {

        mTablayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                View view = tab.getCustomView();
                TextView tv = (TextView) view.findViewById(R.id.tv_tab);
                ImageView im = (ImageView) view.findViewById(R.id.img_tab);
                tv.setTextColor(getResources().getColor(R.color.colortext));
                if (tab == mTablayout.getTabAt(0)) {
                    im.setImageResource(tabIconsPressed[0]);

//                    one.setIcon(getResources().getDrawable(R.drawable.home_shape));
                    mViewPager.setCurrentItem(0, false);
                } else if (tab == mTablayout.getTabAt(1)) {
                    im.setImageResource(tabIconsPressed[1]);
//                    two.setIcon(getResources().getDrawable(R.drawable.walk2));
                    mViewPager.setCurrentItem(1, false);
                } else if (tab == mTablayout.getTabAt(2)) {
                    im.setImageResource(tabIconsPressed[2]);
//                    three.setIcon(getResources().getDrawable(R.drawable.discover_shape));
                    mViewPager.setCurrentItem(2, false);
                } else if (tab == mTablayout.getTabAt(3)) {
                    im.setImageResource(tabIconsPressed[3]);
//                    four.setIcon(getResources().getDrawable(R.drawable.setting_2));
                    mViewPager.setCurrentItem(3, false);
                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                View view = tab.getCustomView();
                TextView tv = (TextView) view.findViewById(R.id.tv_tab);
                tv.setTextColor(getResources().getColor(R.color.colorgray));
                ImageView im = (ImageView) view.findViewById(R.id.img_tab);
                if (tab == mTablayout.getTabAt(0)) {
                    im.setImageResource(tabIcons[0]);
//                    one.setIcon(getResources().getDrawable(R.drawable.home_line));
                } else if (tab == mTablayout.getTabAt(1)) {
                    im.setImageResource(tabIcons[1]);
//                    two.setIcon(getResources().getDrawable(R.drawable.walk));
                } else if (tab == mTablayout.getTabAt(2)) {
                    im.setImageResource(tabIcons[2]);
//                    three.setIcon(getResources().getDrawable(R.drawable.discover_line));
                } else if (tab == mTablayout.getTabAt(3)) {
                    im.setImageResource(tabIcons[3]);
//                    four.setIcon(getResources().getDrawable(R.drawable.setting_1));
                }
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }

    private void initViews() {
        mTablayout = (TabLayout) findViewById(R.id.tabLayout);
        mViewPager = (ViewPager) findViewById(R.id.viewPager);
        mViewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            private String[] mTitles = new String[]{"主页", "运动", "发现", "更多"};

            @Override
            public Fragment getItem(int position) {
                if (position == 1) {
                    return new TwoFragment();
                } else if (position == 2) {
                    return new ThreeFragment();
                } else if (position == 3) {
                    return new FourFragment();
                }
                return new OneFragment();
            }

            @Override
            public int getCount() {
                return mTitles.length;
            }
        });
        mTablayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.colorgray));
        mTablayout.setupWithViewPager(mViewPager);
        one = mTablayout.getTabAt(0).setCustomView(getTabView(0));
        two = mTablayout.getTabAt(1).setCustomView(getTabView(1));
        three = mTablayout.getTabAt(2).setCustomView(getTabView(2));
        four = mTablayout.getTabAt(3).setCustomView(getTabView(3));

//        one.setIcon(getResources().getDrawable(R.drawable.home_shape));
//        two.setIcon(getResources().getDrawable(R.drawable.walk));
//        three.setIcon(getResources().getDrawable(R.drawable.discover_line));
//        four.setIcon(getResources().getDrawable(R.drawable.setting_1));


    }

    //boommenu

    //回调显示图标
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (menu != null) {
            if (menu.getClass().getSimpleName().equals("MenuBuilder")) {
                try {
                    Method m = menu.getClass().getDeclaredMethod("setOptionalIconsVisible",
                            Boolean.TYPE);
                    m.setAccessible(true);
                    m.invoke(menu, true);
                } catch (Exception e) {
                    Log.e(getClass().getSimpleName(), "onMenuOpened...unable to set icons for " +
                            "overflow menu", e);
                }
            }
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_connect:
                Intent intent = new Intent(MainActivity.this, ConnectBluetooth.class);
                startActivity(intent);
                System.out.println("打开蓝牙连接界面");
                break;
//            case R.id.action_add:
//                System.out.println("add");
//                break;
//            case R.id.action_back:
//                System.out.println("back");
//                break;
            default:
                System.out.println("---===---");
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    //setup

    public View getTabView(int position) {
        View view = LayoutInflater.from(this).inflate(R.layout.tabitem, null);
        TextView tv = (TextView) view.findViewById(R.id.tv_tab);
        ImageView im = (ImageView) view.findViewById(R.id.img_tab);
        tv.setTextColor(getResources().getColor(R.color.colorgray));
        if (position == 0) {
            tv.setText(mTitles[position]);
            tv.setTextColor(getResources().getColor(R.color.colortext));
            im.setImageResource(tabIconsPressed[position]);

        } else {
            tv.setText(mTitles[position]);
            im.setImageResource(tabIcons[position]);
            tv.setTextColor(getResources().getColor(R.color.colorgray));
        }
        return view;
    }

    //boommenu
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        initboombtn();
        boomMenuButton.setOnSubButtonClickListener(new BoomMenuButton.OnSubButtonClickListener() {
            @Override
            public void onClick(int buttonIndex) {
                Intent intent = null;
                switch (buttonIndex) {
                    case 0:
                        intent = new Intent(MainActivity.this, chart_info.class);
                        Handler handler = new Handler();
                        final Intent finalIntent = intent;
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                startActivity(finalIntent);
                            }
                        }, 600);

                        break;
                    case 1:
                        Intent intent2 = new Intent(MainActivity.this, steps.class);
                        startActivity(intent2);
                        break;
                    case 3:

                        break;
                    default:
                        break;
                }
            }
        });
    }

    private void initboombtn() {
        Drawable[] subButtonDrawables = new Drawable[3];
        int[] drawablesResource = new int[]{
                R.drawable.java,
                R.drawable.boom,
                R.drawable.github
        };
        for (int i = 0; i < 3; i++)
            subButtonDrawables[i] = ContextCompat.getDrawable(this, drawablesResource[i]);

        String[] subButtonTexts = new String[]{"乔磊", "闫美玲", "qiaoqiao"};
        final Drawable[] circleSubButtonDrawables = new Drawable[3];
        for (int i = 0; i < 3; i++)
            circleSubButtonDrawables[i]
                    = ContextCompat.getDrawable(this, drawablesResource[i]);
        int[][] subButtonColors = new int[3][2];
        for (int i = 0; i < 3; i++) {
            subButtonColors[i][1] = GetRandomColor();
            subButtonColors[i][0] = Util.getInstance().getPressedColor(subButtonColors[i][1]);
        }
        final String[] circleSubButtonTexts = new String[]{
                "No.1 ",
                "No.2 ",
                "No.3 "};

        // Now with Builder, you can init BMB more convenient
//        new BoomMenuButton.Builder()
//                .addSubButton(ContextCompat.getDrawable(this, R.drawable.boom),
//                        subButtonColors[0], subButtonTexts[0])
//                .addSubButton(ContextCompat.getDrawable(this, R.drawable.java),
//                        subButtonColors[0], subButtonTexts[1])
//                .addSubButton(ContextCompat.getDrawable(this, R.drawable.github),
//                        subButtonColors[0], subButtonTexts[2])
//                .button(ButtonType.HAM)
//                .boom(BoomType.LINE)
//                .place(PlaceType.HAM_3_1)
//                .subButtonTextColor(ContextCompat.getColor(this, R.color.black))
//                .subButtonsShadow(Util.getInstance().dp2px(2), Util.getInstance().dp2px(2))
//                .init(boomMenuButton);
        new BoomMenuButton.Builder()
                .subButtons(circleSubButtonDrawables, subButtonColors, circleSubButtonTexts)
                .button(ButtonType.CIRCLE)
                .boom(BoomType.PARABOLA)
                .place(PlaceType.CIRCLE_3_1)
                .subButtonTextColor(ContextCompat.getColor(this, R.color.black))
                .subButtonsShadow(Util.getInstance().dp2px(2), Util.getInstance().dp2px(2))
                .init(boomMenuButton);
    }
}
