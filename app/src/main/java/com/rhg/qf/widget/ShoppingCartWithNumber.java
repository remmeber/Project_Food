package com.rhg.qf.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.rhg.qf.R;
import com.rhg.qf.utils.SizeUtil;

/*
 *desc 带有数字显示的购物车
 *author rhg
 *time 2016/6/20 16:06
 *email 1013773046@qq.com
 */
public class ShoppingCartWithNumber extends FrameLayout {
    private static final int DEFAULT_BACK_COLOR = Color.GREEN;
    Context context;
    ImageView iv_goodsCart;
    boolean isVisible;
    String number = "2";
    float r = SizeUtil.dip2px(15);
    float text_size = 10;
    int width;
    int height;
    Paint circlePaint;
    Paint textPaint;
    private int backColor;

    public ShoppingCartWithNumber(Context context) {
        this(context, null);
    }

    public ShoppingCartWithNumber(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ShoppingCartWithNumber(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        final TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ShoppingCartWithNumber);
        if (a.hasValue(R.styleable.ShoppingCartWithNumber_tint_color)) {
            backColor = a.getColor(R.styleable.ShoppingCartWithNumber_tint_color, DEFAULT_BACK_COLOR);
        }
        if (a.hasValue(R.styleable.ShoppingCartWithNumber_text_size)) {
            text_size = a.getDimensionPixelSize(R.styleable.ShoppingCartWithNumber_text_size, SizeUtil.sp2px(text_size));
        }
        a.recycle();
        initView(context);
        circlePaint = getCirclePaint();
        textPaint = getTextPaint();
//        initView(context);
    }

    private Paint getTextPaint() {
        Paint _paint = new Paint();
        _paint.setColor(Color.BLACK);
        _paint.setAntiAlias(true);
        _paint.setTextSize(SizeUtil.sp2px(10));
        return _paint;
    }


    private Paint getCirclePaint() {
        Paint _paint = new Paint();
        _paint.setColor(backColor);
        _paint.setStyle(Paint.Style.FILL);
        _paint.setAntiAlias(true);
        return _paint;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void initView(Context context) {
        setWillNotDraw(false);
        setBackground(context.getResources().getDrawable(R.drawable.ic_shopping_cart_green));
        /*iv_goodsCart = new ImageView(context);
        iv_goodsCart.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_shopping_cart_green));
        addView(iv_goodsCart);*/
//        ImageUtils.TintFill(iv_goodsCart, iv_goodsCart.getDrawable(), backColor);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(SizeUtil.dip2px(36), SizeUtil.dip2px(36));
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = w;
        height = h;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint.FontMetrics fm = textPaint.getFontMetrics();
        if (!" ".equals(number) && !"0".equals(number)) {
            canvas.drawCircle(width - r / 2, r / 2, r / 2, circlePaint);
            canvas.drawText(number, width - r / 2 - textPaint.measureText(number) / 2,
                    r / 2 - (fm.descent + fm.ascent) / 2, textPaint);
        } else {
            canvas.drawCircle(width - r, r, 0, circlePaint);
            canvas.drawText("", width - r, r - text_size / 2, textPaint);
        }
    }

    public int getNum() {
        return Integer.valueOf(number);
    }

    public void setNum(String num) {
        number = num;
        invalidate();
    }

   /* public void setDrawable(int resID) {
//        iv_goodsCart.setImageDrawable(getResources().getDrawable(resID));
        ImageUtils.TintFill(iv_goodsCart, getResources().getDrawable(resID), backColor);
    }*/

    /*private void setNumVisible(boolean visible) {
        if (tv_goodsNum != null) {
            if (visible)
                tv_goodsNum.setVisibility(VISIBLE);
            else tv_goodsNum.setVisibility(GONE);
        }
    }*/

}
