package com.example.qiao.weixin2.Round;

import android.animation.ArgbEvaluator;
import android.animation.FloatEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

import com.example.qiao.weixin2.R;

/**
 * @author: SvenHe(heshiweij@gmail.com)
 * @Date: 2016-04-25
 * @Time: 12:02
 * @des 圆形转圈的 View
 */
public class MyRoundProcess extends View {

    /**
     * 自定义属性：
     * <p/>
     * 1. 外层圆的颜色 roundColor
     * <p/>
     * 2. 弧形进度圈的颜色 rouncProgressColor
     * <p/>
     * 3. 中间百分比文字的颜色 textColor
     * <p/>
     * 4. 中间百分比文字的大小 textSize
     * <p/>
     * 5. 圆环的宽度（以及作为弧形进度的圆环宽度）
     * <p/>
     * 6. 圆环的风格（Paint.Style.FILL  Paint.Syle.Stroke）
     */


    private static final String TAG = "MyRoundProcess";
    private final float maxProgress = 100f; // 不可以修改的最大值
    //region 自定义属性的值
    int roundColor;
    int roundProgressColor;
    int textColor, textColor2;
    float textSize, textSize2;
    private int mWidth;
    private int mHeight;
    private Paint mPaint, mlinePaint;
    private Paint mTextPaint, mTextPaint2;
    private float progress = 0f;
    private int steps = 0;
    private String text2 = "";
    private int goals = 1000;
    private int degree = 0;
    private ArgbEvaluator argbEvaluator = new ArgbEvaluator();
    //endregion
    // 画笔的粗细（默认为40f, 在 onLayout 已修改）
    private float mStrokeWidth = 40f;
    private ValueAnimator mAnimator;
    private float mLastProgress = -1;
    private int roundalpha = 33; //透明度

    public MyRoundProcess(Context context) {
        this(context, null);
    }

    public MyRoundProcess(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyRoundProcess(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        // 初始化属性
        initAttrs(context, attrs, defStyleAttr);

        // 初始化点击事件
//        initClickListener();
    }

    public String getText2() {
        return text2;
    }

    public void setText2(String text2) {
        this.text2 = text2;
    }

    public int getGoals() {
        return goals;
    }

    public void setGoals(int goals) {
        this.goals = goals;
    }

    /**
     * 初始化属性
     *
     * @param context
     * @param attrs
     * @param defStyleAttr
     */
    private void initAttrs(Context context, AttributeSet attrs, int defStyleAttr) {
        TypedArray a = null;
        try {
            a = context.obtainStyledAttributes(attrs, R.styleable.MyRoundProcess);

            roundColor = a.getColor(R.styleable.MyRoundProcess_roundColor, getResources()
                    .getColor(android.R.color.darker_gray));
            roundProgressColor = a.getColor(R.styleable.MyRoundProcess_roundProgressColor,
                    getResources().getColor(android.R.color.holo_red_dark));
            textColor = a.getColor(R.styleable.MyRoundProcess_textColor, getResources().getColor
                    (android.R.color.holo_blue_dark));
            textSize = a.getDimension(R.styleable.MyRoundProcess_textSize, 22f);
            textColor2 = a.getColor(R.styleable.MyRoundProcess_textColor2, getResources().getColor(
                    (android.R.color.holo_blue_dark)));
            textSize2 = a.getDimension(R.styleable.MyRoundProcess_textSize2, 22f);

        } finally {
            // 注意，别忘了 recycle
            a.recycle();
        }
    }

    /**
     * 初始化点击事件
     */
//    private void initClickListener() {
//        setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                // 重新开启动画
//                restartAnimate();
//            }
//        });
//    }

    /**
     * 当开始布局时候调用
     */
    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        // 获取总的宽高
        mWidth = getMeasuredWidth();
        mHeight = getMeasuredHeight();

        // 初始化各种值
        initValue();

        // 设置圆环画笔
        setupPaint();
        // 设置文字画笔
        setupTextPaint();
        setupTextPaint2();
    }

    /**
     * 初始化各种值
     */
    private void initValue() {
        // 画笔的粗细为总宽度的 1 / 15
        mStrokeWidth = mWidth / 9f;
    }

    /**
     * 设置圆环画笔
     */
    private void setupPaint() {
        // 创建圆环画笔
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(roundColor);
        mPaint.setStyle(Paint.Style.STROKE); // 边框风格
        mPaint.setStrokeWidth(mStrokeWidth);
    }


    /**
     * 设置文字画笔
     */
    private void setupTextPaint() {
        mTextPaint = new Paint();
        mTextPaint.setAntiAlias(true);
        mTextPaint.setColor(textColor);
        mTextPaint.setTextSize(textSize);
    }

    //画笔2
    private void setupTextPaint2() {
        mTextPaint2 = new Paint();
        mTextPaint2.setAntiAlias(true);
        mTextPaint2.setColor(textColor2);
        mTextPaint2.setTextSize(textSize2);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // 第一步：绘制一个圆环
        mPaint.setStrokeWidth(mStrokeWidth);
        mPaint.setColor(roundColor);
        float cx = mWidth / 2.0f;
        float cy = mHeight / 2.0f;
        float radius = mWidth / 2.0f - mStrokeWidth / 2.0f;
        //透明度

        mPaint.setAlpha(roundalpha);
        canvas.drawCircle(cx, cy, radius, mPaint);

        // 第二步：绘制文字
        String text = ((int) (progress / maxProgress * 100)) + "步";
        Rect bounds = new Rect();
        mTextPaint.getTextBounds(text, 0, text.length(), bounds);
        canvas.drawText(text, mWidth / 2 - bounds.width() / 2, mHeight / 2 - bounds.height() / 2,
                mTextPaint);
        //-----------绘制文字2
        Rect bounds2 = new Rect();
        mTextPaint2.getTextBounds(text2, 0, text2.length(), bounds2);
        canvas.drawText(text2, mWidth / 2 - bounds2.width() / 2, mHeight / 2 + bounds2.height(),
                mTextPaint2);
        // 第三步：绘制动态进度圆环
        mPaint.setDither(true);
        mPaint.setStrokeJoin(Paint.Join.BEVEL);
//        mPaint.setStrokeCap(Paint.Cap.ROUND); //  设置笔触为圆形
        mPaint.setStrokeWidth(mStrokeWidth);
        RectF oval = new RectF(0 + mStrokeWidth / 2, 0 + mStrokeWidth / 2,
                mWidth - mStrokeWidth / 2, mHeight - mStrokeWidth / 2);
//        canvas.drawArc(oval, 0, progress / maxProgress * 360, false, mPaint);
        int degree = (int) (progress / maxProgress * 360);
        mPaint.setColor(Color.parseColor
                ("#2292DD"));
        for (int i = 0; i < degree; i++) {

            Integer color = (Integer) argbEvaluator.evaluate(i / 360f, Color.parseColor
                    ("#2292DD"), Color.parseColor("#E6E61A"));
            if (i % 3 == 0) {
                canvas.drawArc(oval, i, 1.35f, false, mPaint);
            }
            mPaint.setColor(color);
        }

    }


    /**
     * 设置当前显示的进度条
     *
     * @param progress
     */
    public void setProgress(float progress) {
        this.progress = progress;

        // 使用 postInvalidate 比 postInvalidat() 好，线程安全
        postInvalidate();
    }

    /**
     * 重新开启动画
     */
    private void restartAnimate() {
        if (mLastProgress > 0) {
            // 取消动画
            cancelAnimate();
            // 重置进度
            setProgress(0f);
            // 重新开启动画
            runAnimate(mLastProgress);
        }
    }

    /**
     * 开始执行动画
     *
     * @param targetProgress 最终到达的进度
     */
    public void runAnimate(float targetProgress) {
        // 运行之前，先取消上一次动画
        cancelAnimate();
        mLastProgress = (targetProgress / (float) goals) * 100;
        mAnimator = ValueAnimator.ofObject(new FloatEvaluator(), 0, mLastProgress);
        // 设置差值器
        mAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                setProgress(value);
            }
        });

        mAnimator.setDuration((long) (mLastProgress * 33));
        mAnimator.start();
    }

    public void runnoAnimate(float targetProgress) {
        // 运行之前，先取消上一次动画
        cancelAnimate();
        mLastProgress = (targetProgress / (float) goals) * 100;
        mAnimator = ValueAnimator.ofObject(new FloatEvaluator(), 0, mLastProgress);
        mAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        setProgress(mLastProgress);
//        mAnimator.setDuration((long) (mLastProgress * 33));
        mAnimator.start();
    }

    /**
     * 取消动画
     */
    public void cancelAnimate() {
        if (mAnimator != null && mAnimator.isRunning()) {
            mAnimator.cancel();
        }
    }
}
