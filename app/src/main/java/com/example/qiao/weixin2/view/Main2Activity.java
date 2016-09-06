package com.example.qiao.weixin2.view;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.qiao.weixin2.Chart.chart_info;
import com.example.qiao.weixin2.ConnBluetooth.ConnectBluetooth;
import com.example.qiao.weixin2.R;
import com.gigamole.navigationtabbar.ntb.NavigationTabBar;
import com.nightonke.boommenu.BoomMenuButton;
import com.nightonke.boommenu.Types.BoomType;
import com.nightonke.boommenu.Types.ButtonType;
import com.nightonke.boommenu.Types.PlaceType;
import com.nightonke.boommenu.Util;
import com.readystatesoftware.systembartint.SystemBarTintManager;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * @author qiao
 *         created at 2016/9/2 16:12
 */
public class Main2Activity extends AppCompatActivity {
    private static String[] colorsets = {
            "#F44336", "#E91E63", "#9C27B0", "#2196F3", "#03A9F4", "#00BCD4", "#009688", "#4CAF50",
            "#8BC34A", "#CDDC39", "#FFEB3B", "#FFC107", "#FF9800", "#FF5722", "#795548",
            "#9E9E9E", "#607D8B"};
    String[] channels = new String[]{"主页", "运动", "发现", "更多"};
    private List<String> mDataList = Arrays.asList(channels);
    private ViewPager mViewPager;
    private BoomMenuButton boomMenuButton;
    private Toolbar toolbar;
    //     NavigationTabBar navigationTabBar;
    private int[] tabIconsPressed = {
            R.drawable.home_shape,
            R.drawable.walk2,
            R.drawable.discover_shape,
            R.drawable.setting_2
    };
    private int[] tabIcons = {
            R.drawable.home_line,
            R.drawable.walk,
            R.drawable.discover_line,
            R.drawable.setting_1
    };
    private PagerAdapter mAdapter = new PagerAdapter() {

        @Override
        public int getCount() {
            return mDataList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            TextView textView = new TextView(container.getContext());
            textView.setText(mDataList.get(position));
            textView.setGravity(Gravity.CENTER);
            container.addView(textView);
            return textView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public int getItemPosition(Object object) {
            TextView textView = (TextView) object;
            String text = textView.getText().toString();
            int index = mDataList.indexOf(text);
            if (index >= 0) {
                return index;
            }
            return POSITION_NONE;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mDataList.get(position);
        }
    };

    public static int GetRandomColor() {
        Random random = new Random();
        int p = random.nextInt(colorsets.length);
        return Color.parseColor(colorsets[p]);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        initboombtn();

    }

    private void initboombtn() {
        Drawable[] subButtonDrawables = new Drawable[3];
        int[] drawablesResource = new int[]{
                R.drawable.run,
                R.drawable.um,
                R.drawable.light
        };
        int[] boomsubbd = {Color.parseColor("#66CD00"), Color.parseColor("#CD5C5C"), Color
                .parseColor("#FFD700")};
        for (int i = 0; i < 3; i++)
            subButtonDrawables[i] = ContextCompat.getDrawable(this, drawablesResource[i]);

        String[] subButtonTexts = new String[]{"", "", ""};
        final Drawable[] circleSubButtonDrawables = new Drawable[3];
        int[][] subButtonColors = new int[3][2];
        for (int i = 0; i < 3; i++) {
//            subButtonColors[i][1] = GetRandomColor();
            circleSubButtonDrawables[i]
                    = ContextCompat.getDrawable(this, drawablesResource[i]);
            subButtonColors[i][1] = boomsubbd[i];
            subButtonColors[i][0] = Util.getInstance().getPressedColor(subButtonColors[i][1]);
        }

        final String[] circleSubButtonTexts = new String[]{
                "No.1 ",
                "No.2 ",
                "No.3 "};

        /**  new BoomMenuButton.Builder()
         .addSubButton(ContextCompat.getDrawable(this, R.drawable.boom),
         subButtonColors[0], subButtonTexts[0])
         .addSubButton(ContextCompat.getDrawable(this, R.drawable.java),
         subButtonColors[0], subButtonTexts[1])
         .addSubButton(ContextCompat.getDrawable(this, R.drawable.github),
         subButtonColors[0], subButtonTexts[2])
         .button(ButtonType.HAM)
         .boom(BoomType.LINE)
         .place(PlaceType.HAM_3_1)
         .subButtonTextColor(ContextCompat.getColor(this, R.color.black))
         .subButtonsShadow(Util.getInstance().dp2px(2), Util.getInstance().dp2px(2))
         .init(boomMenuButton);*/


        new BoomMenuButton.Builder()
                .subButtons(circleSubButtonDrawables, subButtonColors, subButtonTexts)
                .button(ButtonType.CIRCLE)
                .boom(BoomType.PARABOLA_2)
                .place(PlaceType.CIRCLE_3_1)
                .subButtonTextColor(ContextCompat.getColor(this, R.color.black))
                .subButtonsShadow(Util.getInstance().dp2px(6), Util.getInstance().dp2px(6))
                .init(boomMenuButton);
        boomMenuButton.setOnSubButtonClickListener(new BoomMenuButton.OnSubButtonClickListener() {
            @Override
            public void onClick(int buttonIndex) {
                Intent intent = null;
                switch (buttonIndex) {
                    case 0:
                        intent = new Intent(Main2Activity.this, chart_info.class);
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
//                        Intent intent2 = new Intent(Main2Activity.this, steps.class);
//                        startActivity(intent2);

//                        SystemBarTintManager tintManager = new SystemBarTintManager(Main2Activity
//                                .this);
//                        tintManager.setStatusBarTintEnabled(true);
//                        tintManager.setNavigationBarTintEnabled(true);
//                        tintManager.setTintColor(getResources().getColor(R.color.colortext));
////                        tintManager.setNavigationBarTintResource(R.color.black);
//                        toolbar.setBackgroundColor(getResources().getColor(R.color.colortext));
//                        setSupportActionBar(toolbar);
                        break;
                    case 3:

                        break;
                    default:
                        break;
                }
            }
        });
    }


    /**
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        initToolbar();
        initview();

        initUI();
    }

    //初始化toolbar
    public void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("运动");
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
    }

    //初始化 控件
    public void initview() {
        mViewPager = (ViewPager) findViewById(R.id.view_pager);
        boomMenuButton = (BoomMenuButton) findViewById(R.id.boom);
//        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(boomMenuButton
//                .getLayoutParams());
//
//        DisplayMetrics dm = getResources().getDisplayMetrics();
//        int w_screen = dm.widthPixels;
//        int h_screen = dm.heightPixels;
//        float scale = dm.density;
//        lp.setMargins((w_screen / 2 - 100), (h_screen - 455 + 100), 0, 0);// 计算居中
//        boomMenuButton.setLayoutParams(lp);
    }

    //初始化tab
    private void initUI() {
        mViewPager.setOffscreenPageLimit(4);
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
            public void destroyItem(ViewGroup container, int position, Object object) {
//                super.destroyItem(container, position, object);
            }

            @Override
            public int getCount() {
                return mTitles.length;
            }
        });

        final String[] colors = getResources().getStringArray(R.array.default_preview);

        final NavigationTabBar navigationTabBar = (NavigationTabBar) findViewById(R.id
                .ntb_horizontal);
        final ArrayList<NavigationTabBar.Model> models = new ArrayList<>();
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.ic_first),
                        Color.parseColor(colors[0]))
                        .title("Heart")
                        .build()
        );
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.ic_second),
                        Color.parseColor(colors[1]))
                        .title("Cup")
                        .build()
        );
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.ic_third),
                        Color.parseColor(colors[2]))
                        .title("Diploma")
                        .build()
        );
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.ic_fourth),
                        Color.parseColor(colors[3]))
                        .title("Flag")
                        .build()
        );

        navigationTabBar.setModels(models);
        navigationTabBar.setViewPager(mViewPager, 0);
        navigationTabBar.setBehaviorEnabled(true);
//        navigationTabBar.setIsBadged(false);
        navigationTabBar.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(final int position, final float positionOffset, final int
                    positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(final int position) {
                mViewPager.setCurrentItem(position);
            }

            @Override
            public void onPageScrollStateChanged(final int state) {

            }

        });

        navigationTabBar.postDelayed(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < navigationTabBar.getModels().size(); i++) {
                    final NavigationTabBar.Model model = navigationTabBar.getModels().get(i);
                    navigationTabBar.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            model.showBadge();
                        }
                    }, i * 100);
                }
            }
        }, 500);
    }

    private void initUI2() {
        mViewPager.setOffscreenPageLimit(4);
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
        final String[] colors = getResources().getStringArray(R.array.default_preview);

        final NavigationTabBar navigationTabBar = (NavigationTabBar) findViewById(R.id
                .ntb_horizontal);
        final ArrayList<NavigationTabBar.Model> models = new ArrayList<>();
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.ic_first),
                        Color.parseColor(colors[0]))
                        .selectedIcon(getResources().getDrawable(R.drawable.ic_sixth))
                        .title("Heart")
                        .badgeTitle("NTB")
                        .build()
        );
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.ic_second),
                        Color.parseColor(colors[1]))
//                        .selectedIcon(getResources().getDrawable(R.drawable.ic_eighth))
                        .title("Cup")
                        .badgeTitle("with")
                        .build()
        );
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.ic_third),
                        Color.parseColor(colors[2]))
                        .title("Diploma")
                        .badgeTitle("state")
                        .build()
        );
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.ic_fourth),
                        Color.parseColor(colors[3]))
//                        .selectedIcon(getResources().getDrawable(R.drawable.ic_eighth))
                        .title("Flag")
                        .badgeTitle("icon")
                        .build()
        );
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.ic_fifth),
                        Color.parseColor(colors[4]))
                        .title("Medal")
                        .badgeTitle("777")
                        .build()
        );

        navigationTabBar.setModels(models);
        navigationTabBar.setViewPager(mViewPager, 0);
        navigationTabBar.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(final int position, final float positionOffset, final int
                    positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(final int position) {
                navigationTabBar.getModels().get(position).hideBadge();
            }

            @Override
            public void onPageScrollStateChanged(final int state) {

            }
        });

        navigationTabBar.postDelayed(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < navigationTabBar.getModels().size(); i++) {
                    final NavigationTabBar.Model model = navigationTabBar.getModels().get(i);
                    navigationTabBar.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            model.showBadge();
                        }
                    }, i * 100);
                }
            }
        }, 500);
    }

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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_connect:
                Intent intent = new Intent(Main2Activity.this, ConnectBluetooth.class);
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
}
