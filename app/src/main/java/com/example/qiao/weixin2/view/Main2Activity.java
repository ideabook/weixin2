package com.example.qiao.weixin2.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.qiao.weixin2.Chart.chart_info;
import com.example.qiao.weixin2.ConnBluetooth.ConnectBluetooth;
import com.example.qiao.weixin2.R;
import com.example.qiao.weixin2.Round.steps;
import com.gigamole.navigationtabbar.ntb.NavigationTabBar;
import com.nightonke.boommenu.BoomMenuButton;
import com.nightonke.boommenu.Types.BoomType;
import com.nightonke.boommenu.Types.ButtonType;
import com.nightonke.boommenu.Types.PlaceType;
import com.nightonke.boommenu.Util;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.SimpleViewPagerDelegate;
import net.lucode.hackware.magicindicator.buildins.UIUtil;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.BezierPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.CommonPagerTitleView;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Main2Activity extends AppCompatActivity {
    private static String[] Colors = {
            "#F44336", "#E91E63", "#9C27B0", "#2196F3", "#03A9F4", "#00BCD4", "#009688", "#4CAF50",
            "#8BC34A", "#CDDC39", "#FFEB3B", "#FFC107", "#FF9800", "#FF5722", "#795548",
            "#9E9E9E", "#607D8B"};
    String[] channels = new String[]{"主页", "运动", "发现", "更多"};
    private List<String> mDataList = Arrays.asList(channels);
    private ViewPager mViewPager;
    private BoomMenuButton boomMenuButton;
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
        int p = random.nextInt(Colors.length);
        return Color.parseColor(Colors[p]);
    }

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
                        Intent intent2 = new Intent(Main2Activity.this, steps.class);
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
//        final String[] circleSubButtonTexts = new String[]{
//                "No.1 ",
//                "No.2 ",
//                "No.3 "};

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
                .subButtons(circleSubButtonDrawables, subButtonColors, subButtonTexts)
                .button(ButtonType.CIRCLE)
                .boom(BoomType.PARABOLA_2)
                .place(PlaceType.CIRCLE_3_1)
                .subButtonTextColor(ContextCompat.getColor(this, R.color.black))
                .subButtonsShadow(Util.getInstance().dp2px(6), Util.getInstance().dp2px(6))
                .init(boomMenuButton);
    }

//    public static int px2dip(float pxValue) {
//        return (int) (pxValue / scale + 0.5f);
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                startActivity(new Intent(Main2Activity.this, splash.class));
//            }
//        }, 3000);

//         navigationTabBar = (NavigationTabBar) findViewById(R.id.ntb);
//        final ArrayList<NavigationTabBar.Model> models = new ArrayList<>();
//        int[] colors={Color.parseColor("#66CD00"), Color.parseColor("#CD5C5C"), Color
//                .parseColor("#FFD700")};
//        models.add(
//                new NavigationTabBar.Model.Builder(
//                        getResources().getDrawable(R.drawable.walk2),
//                       colors[0]
//                ).title("Heart")
//                        .badgeTitle("NTB")
//                        .build()
//        );
//        models.add(
//                new NavigationTabBar.Model.Builder(
//                        getResources().getDrawable(R.drawable.home_line),
//                       colors[1]
//                ).title("Cup")
//                        .badgeTitle("with")
//                        .build()
//        );
//        models.add(
//                new NavigationTabBar.Model.Builder(
//                        getResources().getDrawable(R.drawable.setting_1),
//                       colors[2]
//                ).title("Diploma")
//                        .badgeTitle("state")
//                        .build()
//        );
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        boomMenuButton = (BoomMenuButton) findViewById(R.id.boom);
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(boomMenuButton
                .getLayoutParams());

        DisplayMetrics dm = getResources().getDisplayMetrics();
        int w_screen = dm.widthPixels;
        int h_screen = dm.heightPixels;
        float scale = dm.density;
//        int wreal=(int)(w_screen/scale+0.5f);
//        int hreal=(int)(h_screen/scale+0.5f);
//        System.out.println("width:="+w_screen+"-----height:="+h_screen+"/n--wreal:="+wreal
//                +"--hreal:="+hreal);
        lp.setMargins((w_screen / 2 - 100), (h_screen - 455 + 100), 0, 0);// 计算居中
        boomMenuButton.setLayoutParams(lp);
        setSupportActionBar(toolbar);
        toolbar.setTitle("MyApp");
        toolbar.setTitleTextColor(Color.WHITE);
        mViewPager = (ViewPager) findViewById(R.id.view_pager);
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
//        navigationTabBar.setModels(models);
//        navigationTabBar.setViewPager(mViewPager, 2);
//        navigationTabBar.setTitleMode(NavigationTabBar.TitleMode.ACTIVE);
//        navigationTabBar.setBadgeGravity(NavigationTabBar.BadgeGravity.BOTTOM);
//        navigationTabBar.setBadgePosition(NavigationTabBar.BadgePosition.CENTER);
//        navigationTabBar.setTypeface("fonts/custom_font.ttf");
//        navigationTabBar.setIsBadged(true);
//        navigationTabBar.setIsTitled(true);
//        navigationTabBar.setIsTinted(true);
//        navigationTabBar.setIsBadgeUseTypeface(true);
//        navigationTabBar.setBadgeBgColor(Color.RED);
//        navigationTabBar.setBadgeTitleColor(Color.WHITE);
//        navigationTabBar.setIsSwiped(true);
//        navigationTabBar.setBgColor(Color.BLACK);
//        navigationTabBar.setBadgeSize(10);
//        navigationTabBar.setTitleSize(10);
//        navigationTabBar.setIconSizeFraction(0.5f);


        final MagicIndicator magicIndicator8 = (MagicIndicator) findViewById(R.id
 .magic_indicator8);
        final CommonNavigator commonNavigator8 = new CommonNavigator(this);
        commonNavigator8.setEnablePivotScroll(true);
        commonNavigator8.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return mDataList == null ? 0 : mDataList.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                ScaleTransitionPagerTitleView colorTransitionPagerTitleView = new
 ScaleTransitionPagerTitleView(context);
                colorTransitionPagerTitleView.setText(mDataList.get(index));
                colorTransitionPagerTitleView.setTextSize(18);
                colorTransitionPagerTitleView.setNormalColor(Color.GRAY);
                colorTransitionPagerTitleView.setBackground(getResources().getDrawable(R.drawable
                        .home_line));
                colorTransitionPagerTitleView.setSelectedColor(Color.BLACK);
                colorTransitionPagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mViewPager.setCurrentItem(index);
                    }
                });
                return colorTransitionPagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                BezierPagerIndicator indicator = new BezierPagerIndicator(context);
                indicator.setColors(Color.parseColor("#ff4a42"), Color.parseColor("#fcde64"),
 Color.parseColor("#73e8f4"), Color.parseColor("#76b0ff"), Color.parseColor("#c683fe"));
                return indicator;
            }
        });
        magicIndicator8.setNavigator(commonNavigator8);
        SimpleViewPagerDelegate.with(magicIndicator8, mViewPager).delegate();


//        final MagicIndicator magicIndicator12 = (MagicIndicator) findViewById(R.id
//                .magic_indicator8);
//        CommonNavigator commonNavigator12 = new CommonNavigator(this);
//        commonNavigator12.setAdjustMode(true);  // 自适应模式
//        commonNavigator12.setSkimOver(true);
//        commonNavigator12.setAdapter(new CommonNavigatorAdapter() {
//
//            @Override
//            public int getCount() {
//                return mDataList == null ? 0 : mDataList.size();
//            }
//
//            @Override
//            public IPagerTitleView getTitleView(Context context, final int index) {
//                CommonPagerTitleView commonPagerTitleView = new CommonPagerTitleView
//                        (Main2Activity.this);
//                commonPagerTitleView.setContentView(R.layout.simple_pager_title_layout);
//
//                // 初始化
//                final ImageView titleImg = (ImageView) commonPagerTitleView.findViewById(R.id
//                        .title_img);
//                titleImg.setImageResource(R.mipmap.ic_launcher);
//                final TextView titleText = (TextView) commonPagerTitleView.findViewById(R.id
//                        .title_text);
//                titleText.setText(mDataList.get(index));
//
//                commonPagerTitleView.setOnPagerTitleChangeListener(new CommonPagerTitleView
//                        .OnPagerTitleChangeListener() {
//
//                    @Override
//                    public void onSelected(int index, int totalCount) {
//                        titleText.setTextColor(getResources().getColor(R.color.colortext));
//                    }
//
//                    @Override
//                    public void onDeselected(int index, int totalCount) {
//                        titleText.setTextColor(getResources().getColor(R.color.colorgray));
//                    }
//
//                    @Override
//                    public void onLeave(int index, int totalCount, float leavePercent, boolean
//                            leftToRight) {
//                        titleImg.setImageResource(tabIcons[index]);
////                        titleImg.setScaleX(1.3f + (0.8f - 1.3f) * leavePercent);
////                        titleImg.setScaleY(1.3f + (0.8f - 1.3f) * leavePercent);
//                    }
//
//                    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
//                    @Override
//                    public void onEnter(int index, int totalCount, float enterPercent, boolean
//                            leftToRight) {
//
//             titleImg.setImageResource(tabIconsPressed[index]);
//                    }
//                });
//
//                commonPagerTitleView.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        mViewPager.setCurrentItem(index);
//                    }
//                });
//
//                return commonPagerTitleView;
//            }
//
//            @Override
//            public IPagerIndicator getIndicator(Context context) {
//                LinePagerIndicator indicator = new LinePagerIndicator(context);
//                indicator.setMode(LinePagerIndicator.MODE_EXACTLY);
//                indicator.setLineHeight(UIUtil.dip2px(context, 5));
//                indicator.setLineWidth(UIUtil.dip2px(context, 30));
//                indicator.setRoundRadius(UIUtil.dip2px(context, 3));
//                indicator.setStartInterpolator(new AccelerateInterpolator());
//                indicator.setEndInterpolator(new DecelerateInterpolator(2.0f));
//                indicator.setColors(Color.parseColor("#76b0ff"), Color
//                                .parseColor("#c683fe"), Color.parseColor("#ff4a42"), Color
//                                .parseColor("#fcde64"),
//                        Color.parseColor("#73e8f4"));
//                // 贝赛尔曲线
//                BezierPagerIndicator indicator2 = new BezierPagerIndicator(context);
//                indicator2.setMaxCircleRadius(15);
//                indicator2.setMinCircleRadius(5);
//                indicator2.setColors(Color.parseColor("#76b0ff"), Color.parseColor("#fcde64"),
//                        Color.parseColor("#73e8f4"), Color
//                                .parseColor("#c683fe"));
//                indicator2.setScrollBarSize(4);
//                return indicator;
//            }
//        });
//        magicIndicator12.setNavigator(commonNavigator12);
//        SimpleViewPagerDelegate.with(magicIndicator12, mViewPager).delegate();
//
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
