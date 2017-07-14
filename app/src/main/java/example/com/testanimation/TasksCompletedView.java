package example.com.testanimation;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by admin on 2017/5/24.
 */
public class TasksCompletedView extends View {

    // 画实心圆的画笔
    private Paint mCirclePaint;
    // 画圆环的画笔
    private Paint mRingPaint;
    // 画字体的画笔
    private Paint mTextPaint;
    // 圆形颜色
    private int mCircleColor;
    // 圆环颜色
    private int mRingColor;
    // 半径
    private float mRadius;
    // 圆环半径
    private float mRingRadius;
    // 圆环宽度
    private float mStrokeWidth;
    // 圆心x坐标
    private int mXCenter;
    // 圆心y坐标
    private int mYCenter;
    // 字的长度
    private float mTxtWidth;
    // 字的高度
    private float mTxtHeight;
    // 总进度
    private int mTotalProgress = 100;
    // 当前进度
    private int mProgress;


    private Paint mRingPaintwhite;//白色圆环画笔
    private float mStrokeWidthwhite;//白色圆环宽度
    private int mRingColorwhite;// 白色圆环颜色

    private float mRadiussmall;// 小圆半径


    public TasksCompletedView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // 获取自定义的属性
        initAttrs(context, attrs);
        initVariable();
    }

    private void initAttrs(Context context, AttributeSet attrs) {
        TypedArray typeArray = context.getTheme().obtainStyledAttributes(attrs,
                R.styleable.TasksCompletedView, 0, 0);
        mRadius = typeArray.getDimension(R.styleable.TasksCompletedView_radius, 80);
        mStrokeWidth = typeArray.getDimension(R.styleable.TasksCompletedView_strokeWidth, 10);
        mCircleColor = typeArray.getColor(R.styleable.TasksCompletedView_circleColor, 0xFFFFFFFF);
        mRingColor = typeArray.getColor(R.styleable.TasksCompletedView_ringColor, 0xFFFFFFFF);

        mRingRadius = mRadius + mStrokeWidth / 2;

        mStrokeWidthwhite = typeArray.getDimension(R.styleable.TasksCompletedView_strokeWidthwhite, 20);
        mRingColorwhite = typeArray.getColor(R.styleable.TasksCompletedView_ringColorwhite, getResources().getColor(R.color.white));

        mRadiussmall = typeArray.getDimension(R.styleable.TasksCompletedView_radiussmall, 80);

    }

    private void initVariable() {
        mCirclePaint = new Paint();
        mCirclePaint.setAntiAlias(true);
        mCirclePaint.setColor(mCircleColor);
        mCirclePaint.setStyle(Paint.Style.FILL);

        mRingPaint = new Paint();
        mRingPaint.setAntiAlias(true);
        mRingPaint.setColor(mRingColor);
        mRingPaint.setStyle(Paint.Style.STROKE);
        mRingPaint.setStrokeWidth(mStrokeWidth);

//        mTextPaint = new Paint();
//        mTextPaint.setAntiAlias(true);
//        mTextPaint.setStyle(Paint.Style.FILL);
//        mTextPaint.setARGB(255, 255, 255, 255);
//        mTextPaint.setTextSize(mRadius / 2);
//
//        Paint.FontMetrics fm = mTextPaint.getFontMetrics();
//        mTxtHeight = (int) Math.ceil(fm.descent - fm.ascent);


        mRingPaintwhite = new Paint();
        mRingPaintwhite.setAntiAlias(true);
        mRingPaintwhite.setColor(mRingColorwhite);
        mRingPaintwhite.setStyle(Paint.Style.STROKE);
        mRingPaintwhite.setStrokeWidth(mStrokeWidthwhite);

    }


    @Override
    protected void onDraw(Canvas canvas) {
        mXCenter = getWidth() / 2;
        mYCenter = getHeight() / 2;


        canvas.drawCircle(mXCenter, mYCenter, mRingRadius, mRingPaintwhite);

        if (mProgress > 0) {
            RectF oval = new RectF();
            oval.left = (mXCenter - mRingRadius);
            oval.top = (mYCenter - mRingRadius);
            oval.right = mRingRadius * 2 + (mXCenter - mRingRadius);
            oval.bottom = mRingRadius * 2 + (mYCenter - mRingRadius);
            canvas.drawArc(oval, 180, ((float) mProgress / mTotalProgress) * 360, false, mRingPaint);

            float angle = ((float) mProgress / mTotalProgress) * 360 + 180;
            float x1 = (float) (mXCenter + mRingRadius * Math.cos(angle * Math.PI / 180));
            float y1 = (float) (mYCenter + mRingRadius * Math.sin(angle * Math.PI / 180));
            canvas.drawCircle(x1, y1, mRadiussmall, mCirclePaint);
            Log.i("11111", "angle----->" + angle);

//            String txt = mProgress + "%";
//            mTxtWidth = mTextPaint.measureText(txt, 0, txt.length());
//            canvas.drawText(txt, mXCenter - mTxtWidth / 2, mYCenter + mTxtHeight / 4, mTextPaint);
        }

    }

    public void setProgress(int progress) {
        mProgress = progress;
//		invalidate();
        postInvalidate();
    }
}
