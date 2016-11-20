package com.rhg.qf.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.rhg.qf.R;

/**
 * desc: 星级评论的进度条
 * author：remember
 * time：2016/7/18 23:26
 * email：1013773046@qq.com
 */
public class MyRatingBar extends View {
    //星星水平排列
    public static final int HORIZONTAL = 0;
    //星星垂直排列
    public static final int VERTICAL = 1;
    Rect rectSrc;
    Rect dstF;
    float finalWidth;
    float finalHeight;
    //实心图片
    private Bitmap mSolidBitmap;
    //空心图片
    private Bitmap mHollowBitmap;
    //最大的数量
    private int starMaxNumber;
    private float starRating;
    private Paint paint;
    private int mSpaceWidth;//星星间隔
    private int mStarWidth;//星星宽度
    private int mStarHeight;//星星高度
    private boolean isIndicator;//是否是一个指示器（用户无法进行更改）
    private int mOrientation;

    public MyRatingBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();
        rectSrc = new Rect();
        dstF = new Rect();
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.MyRatingBar);
        mSpaceWidth = a.getDimensionPixelSize(R.styleable.MyRatingBar_space_width, 0);
        mStarWidth = a.getDimensionPixelSize(R.styleable.MyRatingBar_star_width, 0);
        mStarHeight = a.getDimensionPixelSize(R.styleable.MyRatingBar_star_height, 0);
        starMaxNumber = a.getInt(R.styleable.MyRatingBar_star_max, 0);
        starRating = a.getFloat(R.styleable.MyRatingBar_star_rating, 0);
        isIndicator = a.getBoolean(R.styleable.MyRatingBar_star_isIndicator, true);
        mSolidBitmap = getZoomBitmap(BitmapFactory.decodeResource(context.getResources(),
                R.drawable.star_blue));
        mHollowBitmap = getZoomBitmap(BitmapFactory.decodeResource(context.getResources(),
                R.drawable.star_gray));
        mOrientation = a.getInt(R.styleable.MyRatingBar_star_orientation, HORIZONTAL);
        a.recycle();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (mHollowBitmap == null || mSolidBitmap == null) {
            return;
        }
        //绘制实心进度
        int solidStarNum = (int) starRating;
        //绘制实心的起点位置
        int solidStartPoint = 0;
        paint.setColor(Color.GREEN);
        for (int i = 1; i <= solidStarNum; i++) {
            canvas.drawBitmap(mSolidBitmap, solidStartPoint, 0, paint);
            solidStartPoint += mSpaceWidth + mSolidBitmap.getWidth();
        }
        //虚心开始位置
        int hollowStartPoint = solidStartPoint;
        //多出的实心部分起点
        int extraSolidStarPoint = hollowStartPoint;
        //虚心数量
        int hollowStarNum = starMaxNumber - solidStarNum;
        paint.setColor(Color.WHITE);
        for (int j = 1; j <= hollowStarNum; j++) {
            canvas.drawBitmap(mHollowBitmap, hollowStartPoint, 0, paint);
            hollowStartPoint = hollowStartPoint + mSpaceWidth + mSolidBitmap.getWidth();
        }
        //多出的实心长度
        paint.setColor(Color.GREEN);
        int extraSolidLength = (int) ((starRating - solidStarNum) * mHollowBitmap.getWidth());
        rectSrc.set(0, 0, extraSolidLength, mHollowBitmap.getHeight());
        dstF.set(extraSolidStarPoint, 0, extraSolidStarPoint + extraSolidLength, mHollowBitmap.getHeight());
        canvas.drawBitmap(mSolidBitmap, rectSrc, dstF, paint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (!isIndicator) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    float clickX = event.getX();
                    float starTotalWidth = finalWidth - getPaddingLeft() - getPaddingRight();
                    float newStarRating = 0.0f;
                    if (clickX <= starTotalWidth + getPaddingLeft()
                            && clickX >= getPaddingLeft()) {
                        float ration = clickX / (mStarWidth + mSpaceWidth);
                        if (clickX - (mStarWidth + mSpaceWidth) * (int) ration > mStarWidth)
                            newStarRating = (int) ration + 1;
                        else
                            newStarRating = (clickX - mSpaceWidth * (int) ration) / mStarWidth;
                        setStarRating(newStarRating);
                    }
                    break;
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL:

                    break;
            }
        }
        return super.onTouchEvent(event);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        finalWidth = w;
        finalHeight = h;
    }

    public float getStarRating() {
        return starRating;
    }

    /**
     * 设置星星的进度
     * @param starRating This should be float type number
     *                   for setting rate.
     */
    public void setStarRating(float starRating) {
        this.starRating = starRating;
        invalidate();
    }

    /**
     * 获取缩放图片
     *
     * @param bitmap    The bitmap which should been zoomed
     * @return          The bitmap object
     */
    public Bitmap getZoomBitmap(Bitmap bitmap) {
        if (mStarWidth == 0 || mStarHeight == 0) {
            return bitmap;
        }
        // 获得图片的宽高
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();

        // 设置想要的大小
        int newWidth = mStarWidth;
        int newHeight = mStarHeight;
        // 计算缩放比例
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // 取得想要缩放的matrix参数
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        // 得到新的图片
        return Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix,
                true);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (mOrientation == HORIZONTAL) {
            setMeasuredDimension(measureLong(widthMeasureSpec), measureShort(heightMeasureSpec));
        } else {
            setMeasuredDimension(measureShort(widthMeasureSpec), measureLong(heightMeasureSpec));
        }
    }

    /**
     * Determines the width of this view
     *
     * @param measureSpec   A measureSpec packed into an int
     * @return              The width of the view, honoring constraints from measureSpec
     */
    private int measureLong(int measureSpec) {
        int result;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        if ((specMode == MeasureSpec.EXACTLY)) {
            //We were told how big to be
            result = specSize;
        } else {
            //Calculate the width according the views count
            result = getPaddingLeft() + getPaddingRight()
                    + mSpaceWidth * (starMaxNumber - 1) + mStarWidth * (starMaxNumber);
            //Respect AT_MOST value if that was what is called for by measureSpec
            if (specMode == MeasureSpec.AT_MOST) {
                result = Math.min(result, specSize);
            }
        }
        return result;
    }

    /**
     * Determines the height of this view
     *
     * @param measureSpec A measureSpec packed into an int
     * @return The height of the view, honoring constraints from measureSpec
     */
    private int measureShort(int measureSpec) {
        int result;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        if (specMode == MeasureSpec.EXACTLY) {
            //We were told how big to be
            result = specSize;
        } else {
            //Measure the height
            result = mStarHeight + getPaddingTop() + getPaddingBottom();
            //Respect AT_MOST value if that was what is called for by measureSpec
            if (specMode == MeasureSpec.AT_MOST) {
                result = Math.min(result, specSize);
            }
        }
        return result;
    }

    public int getStarMaxNumber() {
        return starMaxNumber;
    }

    public void setStarMaxNumber(int starMaxNumber) {
        this.starMaxNumber = starMaxNumber;
        invalidate();
    }

    public boolean isIndicator() {
        return isIndicator;
    }

    public void setIsIndicator(boolean isIndicator) {
        this.isIndicator = isIndicator;
    }
}
