package com.github.vasiliz.vkclient.news.ui.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

import com.github.vasiliz.vkclient.R;

import static android.graphics.Color.BLACK;

public class CircleImage extends AppCompatImageView {

    private static final float STROKE_WIDTH = 5f;
    private int mBorderColor;
    private int mImageResourse;
    private int mImageSise;
    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint mBorderPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private RectF mRectF = new RectF();
    private Path mPath = new Path();

    public CircleImage(Context context) {
        super(context);
        init();
    }

    public CircleImage(Context context, AttributeSet attrs) {
        super(context, attrs);
        getAttributes(attrs);
        init();
    }

    public CircleImage(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        getAttributes(attrs);
        init();
    }

    private void init() {
        setBorderColor(mBorderColor);
    }

    private void setBorderColor(int pBorderColor) {
        mBorderPaint.setColor(pBorderColor);
        mBorderPaint.setStyle(Paint.Style.STROKE);
        mBorderPaint.setAntiAlias(true);
        mBorderPaint.setStrokeWidth(STROKE_WIDTH);
        invalidate();
    }

    private void getAttributes(AttributeSet pAttributeSet) {

        TypedArray array = getContext().getTheme().obtainStyledAttributes(pAttributeSet, R.styleable.circle_image, 0, 0);
        try {
            mBorderColor = array.getColor(R.styleable.circle_image_border_color, getResources().getColor(R.color.colorPrimary));
            mImageResourse = array.getInteger(R.styleable.circle_image_src, R.drawable.template);
        } finally {
            array.recycle();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int screenWidth = MeasureSpec.getSize(widthMeasureSpec);
        int screenHeight = MeasureSpec.getSize(heightMeasureSpec);
        mRectF.set(0, 0, screenWidth, screenHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        mPaint.setColor(BLACK);
        float borderWidth = 1f;
        canvas.drawCircle(mRectF.centerX(), mRectF.centerY(), (mRectF.height() / 2) - borderWidth, mPaint);
        canvas.drawCircle(mRectF.centerX(), mRectF.centerY(), (mRectF.height() / 2) - borderWidth, mBorderPaint);
        mPath.addCircle(mRectF.centerX(), mRectF.centerY(), (mRectF.height() / 2), Path.Direction.CW);
        canvas.clipPath(mPath);
        super.onDraw(canvas);
    }

}
