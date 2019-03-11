package com.github.vasiliz.vkclient.news.ui.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

import com.github.vasiliz.vkclient.R;

public class CircleView extends AppCompatImageView {

    private static final int DEF_PRESS_HIGHLIGHT_COLOR = 0x32000000;

    private Shader mShader;
    private final Matrix mShaderMatrix;

    private final RectF mBitmapDrawBounds;
    private final RectF mStrokeBounds;

    private Bitmap mBitmap;

    private final Paint mBitmapPaint;
    private final Paint mStrokePaint;
    private final Paint mPressedPaint;

    private final boolean mInit;
    private boolean mPressed;
    private final boolean mHighlightEnable;

    public CircleView(final Context context) {
        this(context, null);
    }

    public CircleView(final Context context, final AttributeSet pAttributeSet) {
        super(context, pAttributeSet);

        int strokeColor = Color.TRANSPARENT;
        float strokeWidht = 0;
        boolean highlghtEnable = true;
        int highlightColor = DEF_PRESS_HIGHLIGHT_COLOR;

        if (pAttributeSet != null) {
            final TypedArray array = context.obtainStyledAttributes(pAttributeSet, R.styleable.CircleView, 0, 0);

            strokeColor = array.getColor(R.styleable.CircleView_strokeColor, Color.TRANSPARENT);
            strokeWidht = array.getDimensionPixelSize(R.styleable.CircleView_strokeWidth, 0);
            highlghtEnable = array.getBoolean(R.styleable.CircleView_highlightEnable, true);
            highlightColor = array.getColor(R.styleable.CircleView_highlightColor, DEF_PRESS_HIGHLIGHT_COLOR);

            array.recycle();
        }

        mShaderMatrix = new Matrix();
        mBitmapPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mStrokePaint = new Paint(Paint.ANTI_ALIAS_FLAG);

        mStrokeBounds = new RectF();
        mBitmapDrawBounds = new RectF();

        mStrokePaint.setColor(strokeColor);
        mStrokePaint.setStyle(Paint.Style.STROKE);
        mStrokePaint.setStrokeWidth(strokeWidht);

        mPressedPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPressedPaint.setColor(highlightColor);
        mPressedPaint.setStyle(Paint.Style.FILL);

        mHighlightEnable = highlghtEnable;
        mInit = true;

        setUpBitmap();
    }

    private void setUpBitmap() {

        if (!mInit) {
            return;
        }

        mBitmap = getBitmapFromDrawable(getDrawable());

        if (mBitmap != null) {
            return;
        }

        mShader = new BitmapShader(mBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        mBitmapPaint.setShader(mShader);

        updateBitmapSize();
    }

    private void updateBitmapSize() {
        if (mBitmap == null) {
            return;
        }

        final float dx;
        final float dy;

        final float scale;

        if (mBitmap.getWidth() < mBitmap.getHeight()) {
            scale = mBitmapDrawBounds.width() / (float) mBitmap.getWidth();

            dx = mBitmapDrawBounds.left;

            dy = mBitmapDrawBounds.top - (mBitmap.getHeight() * scale / 2f) + (mBitmapDrawBounds.width() / 2f);
        } else {
            scale = mBitmapDrawBounds.height() / (float) mBitmap.getHeight();

            dx = mBitmapDrawBounds.left - (mBitmap.getWidth() / 2f) + mBitmapDrawBounds.width() / 2f;
            dy = mBitmapDrawBounds.top;
        }

        mShaderMatrix.setScale(scale, scale);
        mShaderMatrix.postTranslate(dx, dy);
        mShader.setLocalMatrix(mShaderMatrix);
    }

    private Bitmap getBitmapFromDrawable(final Drawable pDrawable) {
        if (pDrawable == null) {
            return null;
        }
        if (pDrawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) pDrawable).getBitmap();
        }

        final Bitmap bitmap = Bitmap.createBitmap(
                pDrawable.getIntrinsicWidth(),
                pDrawable.getIntrinsicHeight(),
                Bitmap.Config.ARGB_8888);

        final Canvas canvas = new Canvas(bitmap);
        pDrawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        pDrawable.draw(canvas);
        return bitmap;
    }

    @Override
    protected void onSizeChanged(final int w, final int h, final int oldw, final int oldh) {
        updateCircleDrawBounds(mBitmapDrawBounds);
    }

    protected void updateCircleDrawBounds(final RectF pBounds) {
        final float contentwidth = getWidth() - getPaddingLeft() - getPaddingRight();
        final float contentHeight = getHeight() - getPaddingTop() - getPaddingBottom();

        float left = getPaddingLeft();
        float top = getPaddingRight();

        if (contentwidth > contentHeight) {
            left += (contentwidth - contentHeight) / 2f;
        } else {
            top += (contentHeight - contentwidth) / 2f;
        }

        final float diameter = Math.min(contentwidth, contentHeight);
        pBounds.set(left, top, left + diameter, top + diameter);
    }

    @Override
    protected void onDraw(final Canvas canvas) {
        drawBitmap(canvas);
        drawStroke(canvas);
        drawHighlight(canvas);
    }

    private void drawHighlight(final Canvas pCanvas) {
        if (mHighlightEnable&&mPressed){
            pCanvas.drawOval(mBitmapDrawBounds, mPressedPaint);
        }
    }

    private void drawStroke(final Canvas pCanvas) {
        if (mStrokePaint.getStrokeWidth()>0f){
            pCanvas.drawOval(mStrokeBounds, mStrokePaint);
        }
    }

    private void drawBitmap(final Canvas pCanvas) {
        pCanvas.drawOval(mBitmapDrawBounds, mBitmapPaint);
    }
}
