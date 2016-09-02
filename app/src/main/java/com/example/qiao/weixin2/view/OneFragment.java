package com.example.qiao.weixin2.view;

import android.animation.FloatEvaluator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.AnimationSet;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.qiao.weixin2.Chart.chart_info;
import com.example.qiao.weixin2.R;
import com.example.qiao.weixin2.Round.MyRoundProcess;
import com.example.qiao.weixin2.Round.steps;

import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.gesture.ContainerScrollType;
import lecho.lib.hellocharts.gesture.ZoomType;
import lecho.lib.hellocharts.listener.LineChartOnValueSelectListener;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.ValueShape;
import lecho.lib.hellocharts.model.Viewport;
import lecho.lib.hellocharts.view.LineChartView;

/**
 * Created by 一 on 2016/7/17.
 */
public class OneFragment extends Fragment {
    String[] date = {"10-22", "11-22", "12-22", "1-22", "6-22", "5-23", "5-22", "6-22", "5-23",
            "5-22"};//X轴的标注
    int[] score = {50, 42, 0, 33, 10, 74, 22, 18, 79, 20};//图表的数据点
    int[] score2 = {2, 33, 0, 22, 20, 24, 52, 28, 49, 30};//图表的数据点
    LineChartData data = new LineChartData();
    int steps = (int) (Math.random() * 100);
    int testvalue1, testvalue = 200;
    AnimationSet animationSet = new AnimationSet(true);
    private MyRoundProcess mRoundProcess;
    private Button btntest,btn2;
    private LineChartView lineChart;
    private List<PointValue> mPointValues = new ArrayList<PointValue>();
    private List<AxisValue> mAxisXValues = new ArrayList<AxisValue>();
    private List<PointValue> mPointValues2 = new ArrayList<PointValue>();
    private List<AxisValue> mAxisXValues2 = new ArrayList<AxisValue>();
    private TextView tvshow;
    private float x1, x2, y1, y2;

    private LinearLayout chart_layout, round_layout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
    Bundle savedInstanceState) {
        View viewroot = inflater.inflate(R.layout.fragment_one, container, false);

        chart_layout = (LinearLayout) viewroot.findViewById(R.id.chart_layout);
        tvshow = (TextView) viewroot.findViewById(R.id.tvshow);
        lineChart = (LineChartView) viewroot.findViewById(R.id.chart);
        round_layout = (LinearLayout) viewroot.findViewById(R.id.round_layout);
        btn2=(Button)viewroot.findViewById(R.id.btn_one1);

        mRoundProcess = (MyRoundProcess) viewroot.findViewById(R.id.my_round_process);
        viewroot.findViewById(R.id.btn_one).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ValueAnimator valueAnimator = ValueAnimator.ofObject(new FloatEvaluator(), 0,
                        testvalue);
                valueAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
                valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        float value = (float) animation.getAnimatedValue();
                        tvshow.setText(String.valueOf(value));
//                setProgress(value);
                    }
                });

                valueAnimator.setDuration((long) (testvalue * 30));
                valueAnimator.start();
//                mRoundProcess.startAnimation(animationSet);
            }
        });
        return viewroot;
    }

    public void setProgress(int progress) {
        this.testvalue1 = progress;

        AlphaAnimation alphaAnimation = new AlphaAnimation(0, 1);
        alphaAnimation.setDuration(testvalue1);
        alphaAnimation.setStartOffset(testvalue1);
        animationSet.addAnimation(alphaAnimation);
        animationSet.setFillBefore(false);
        animationSet.setFillAfter(true);
        mRoundProcess.startAnimation(animationSet);
        // 使用 postInvalidate 比 postInvalidat() 好，线程安全
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        initview();
        mRoundProcess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), steps.class);
                startActivity(intent);
            }
        });
        chart_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), chart_info.class);
                startActivity(intent);
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent it=new Intent(getActivity(),wave.class);
//                startActivity(it);
                Intent it=new Intent(getActivity(),HorizontalNtbActivity.class);
                startActivity(it);
            }
        });
//
        getAxisXLables();//获取x轴的标注
        getAxisPoints();//获取坐标点
        initLineChart();//初始化
        resetViewport();
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }

    public void initview() {
        mRoundProcess.setGoals(100);
        mRoundProcess.runAnimate(steps);
        mRoundProcess.setText2("今日步数");
//        if (!ACTIVITY_ON) {
//            mRoundProcess.runAnimate(steps);
//            mRoundProcess.setText2("今日步数");
//            tvshow.setText("第一次打开");
//            ACTIVITY_ON = true;
//        } else {
//            mRoundProcess.runnoAnimate(steps);
//            tvshow.setText("第二次打开");
//        }
    }


    private void getAxisXLables() {
        for (int i = 0; i < date.length; i++) {
            mAxisXValues.add(new AxisValue(i).setLabel(date[i]));
        }
    }

    /**
     * 图表的每个点的显示
     */
    private void getAxisPoints() {
        for (int i = 0; i < score.length; i++) {
            mPointValues.add(new PointValue(i, score[i]));
        }
    }


    private void initLineChart() {
        Line line = new Line(mPointValues).setColor(Color.parseColor("#5CACEE"));
//        Line line2 = new Line(mPointValues2).setColor(Color.parseColor("#9F79EE"));//折线的颜色（橙色）
        final List<Line> lines = new ArrayList<Line>();
        line.setShape(ValueShape.CIRCLE);//折线图上每个数据点的形状  这里是圆形 （有三种 ：ValueShape.SQUARE  ValueShape
        // .CIRCLE  ValueShape.DIAMOND）
        line.setCubic(true);//曲线是否平滑，即是曲线还是折线
        line.setFilled(false);//是否填充曲线的面积
//        line.setPointColor(Color.parseColor("#1874CD"));
//        line.setHasLabels(true);//曲线的数据坐标是否加上备注
        line.setHasLabelsOnlyForSelected(true);//点击数据坐标提示数据（设置了这个line.setHasLabels(true);就无效）
        line.setHasLines(true);//是否用线显示。如果为false 则没有曲线只有点显示
        line.setHasPoints(true);//是否显示圆点 如果为false 则没有原点只有点显示（每个数据点都是个大的圆点）
        line.setPointRadius(3);
        line.setStrokeWidth(2);
        //line2
//        line2.setShape(ValueShape.CIRCLE);//折线图上每个数据点的形状  这里是圆形 （有三种 ：ValueShape.SQUARE
// ValueShape
//        // .CIRCLE  ValueShape.DIAMOND）
//        line2.setCubic(true);//曲线是否平滑，即是曲线还是折线
//        line2.setFilled(false);//是否填充曲线的面积
//        line2.setHasLabelsOnlyForSelected(true);//点击数据坐标提示数据（设置了这个line.setHasLabels(true);就无效）
////        line2.setHasLabels(true);//曲线的数据坐标是否加上备注
//        line2.setHasLines(true);//是否用线显示。如果为false 则没有曲线只有点显示
//        line2.setHasPoints(true);//是否显示圆点 如果为false 则没有原点只有点显示（每个数据点都是个大的圆点）
//        line2.setPointRadius(3);
//        line2.setStrokeWidth(1);
        lines.add(line);
//        lines.add(line2);
        data.setLines(lines);
        //坐标轴
        final Axis axisX = new Axis(); //X轴
        axisX.setHasTiltedLabels(false);  //X坐标轴字体是斜的显示还是直的，true是斜的显示
        axisX.setTextColor(Color.WHITE);  //设置字体颜色
//        axisX.setName("表格");  //表格名称
        axisX.setTextSize(10);//设置字体大小
        axisX.setTextColor(Color.parseColor("#B2DFEE"));
        axisX.setMaxLabelChars(11); //最多几个X轴坐标，意思就是你的缩放让X轴上数据的个数7<=x<=mAxisXValues.length
        axisX.setValues(mAxisXValues);  //填充X轴的坐标名称
        data.setAxisXBottom(axisX); //x 轴在底部
//        data.setAxisXTop(axisX);  //x 轴在顶部
        axisX.setHasLines(true); //x 轴分割线

        // Y轴是根据数据的大小自动设置Y轴上限(在下面我会给出固定Y轴数据个数的解决方案)
//        Axis axisY = new Axis().setHasLines(true);  //Y轴
////        axisY.setName("Y轴");//y轴标注
////        axisY.setTextSize(10);//设置字体大小
//        List<AxisValue> values = new ArrayList<>();
//        for (int i = 0; i < 200; i += 10) {
//            AxisValue value = new AxisValue(i);
////            String label = String.valueOf(i);
////            value.setLabel(label);
//            values.add(value);
//        }
//        axisY.setValues(values);
//        data.setAxisYLeft(axisY);
        //Y轴设置在左边
        //data.setAxisYRight(axisY);  //y轴设置在右边


        //设置行为属性，支持缩放、滑动以及平移
//        lineChart.setBackgroundColor(Color.parseColor("#EEE0E5"));
        lineChart.setInteractive(true);
        lineChart.setZoomType(ZoomType.HORIZONTAL);
        lineChart.setMaxZoom((float) 2);//最大方法比例
        lineChart.setContainerScrollEnabled(true, ContainerScrollType.HORIZONTAL);
        lineChart.setLineChartData(data);
        for (Line line3 : data.getLines()) {
            for (PointValue value : line.getValues()) {
                // 这里我只修改目标X Y值,但可以修改目标。
                value.setTarget(value.getX(), (float) Math.random() * 100);
            }
        }

        lineChart.setVisibility(View.VISIBLE);
        lineChart.setOnValueTouchListener(new LineChartOnValueSelectListener() {
            @Override
            public void onValueSelected(int lineIndex, int pointIndex, PointValue value) {
                tvshow.setText("pointIndex:" + pointIndex + '\n' + "-getY():" + value.getY
                        () + '\n' + "-getX():" + String.valueOf(axisX.getValues().get(pointIndex)
                        .getLabel()));
            }

            @Override
            public void onValueDeselected() {

            }
        });
        /**注：下面的7，10只是代表一个数字去类比而已
         * 当时是为了解决X轴固定数据个数。见（http://forum.xda-developers
         * .com/tools/programming/library-hellocharts-charting-library-t2904456/page2）;
         */
//        ChartDataAnimator chartDataAnimator=new ChartDataAnimatorV8(lineChart);
//        chartDataAnimator.startAnimation(400);
    }

    private void resetViewport() {
        // Reset viewport height range to (0,100)
        final Viewport v = new Viewport(lineChart.getMaximumViewport());
        v.bottom = 0;
        v.left = 0;
        lineChart.setMaximumViewport(v);
        lineChart.setCurrentViewport(v);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }
}


