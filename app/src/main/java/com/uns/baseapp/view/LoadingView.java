package com.uns.baseapp.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

import com.uns.baseapp.R;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by zhuo.zhang on 2018/2/23.
 */

public class LoadingView extends View {
    int circleR = 80;
    int circle_r = 20;
    int circleCount = 10;
    int maxSize = 40;
    Path circlePath;
    PathMeasure pathMeasure;
    Paint paint = new Paint();
    TextPaint textPain = new TextPaint();
    float pos[] = new float[2];
    private long value = 0;
    private Disposable subscribe;

    public LoadingView(Context context) {
        super(context);
        init();
    }

    public LoadingView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LoadingView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        circlePath = new Path();
        circlePath.addCircle(circleR + 2 * circle_r, circleR + 2 * circle_r, circleR, Path.Direction.CCW);
        pathMeasure = new PathMeasure(circlePath, true);
        paint.setColor(ActivityCompat.getColor(getContext(), R.color.white));
        paint.setStyle(Paint.Style.FILL);
        paint.setAntiAlias(true);
        textPain.setColor(ActivityCompat.getColor(getContext(), R.color.aquamarine));
        textPain.setTextSize(maxSize);
        textPain.setAntiAlias(true);
        Typeface font = Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD);
        textPain.setTypeface(font);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(2 * circleR + 4 * circle_r, 2 * circleR + 4 * circle_r);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawCircle(canvas);
    }

    protected void drawCircle(Canvas canvas) {
        float cLength = pathMeasure.getLength();
        for (int i = 0; i < circleCount; i++) {
            boolean posTan = pathMeasure.getPosTan(cLength * i / circleCount, pos, null);
            if (posTan) {
                float r = 1f * circle_r * ((i + 1 + value) % circleCount) / circleCount;
                canvas.drawCircle(pos[0], pos[1], r, paint);
            }
        }
    }

    protected void drawMoney(Canvas canvas) {
        float cLength = pathMeasure.getLength();
        for (int i = 0; i < circleCount; i++) {
            boolean posTan = pathMeasure.getPosTan(cLength * i / circleCount, pos, null);
            if (posTan) {
                float r = 1f * maxSize * ((i + 1 + value) % circleCount) / circleCount;
                textPain.setTextSize(r);
                canvas.drawText("￥", pos[0], pos[1], textPain);
            }
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        stop();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        start();
    }

    private void start() {
        if (subscribe == null || subscribe.isDisposed()) {
            subscribe = Observable.interval(50, 100, TimeUnit.MILLISECONDS)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<Long>() {
                        @Override
                        public void accept(Long aLong) throws Exception {
                            value = aLong % circleCount;
//                            paint.setColor(Color.rgb((int) (Math.random() * 256)
//                                    , (int) (Math.random() * 256), (int) (Math.random() * 256)));
                            invalidate();
                        }
                    });
        }
    }

    private void stop() {
        if (subscribe != null && !subscribe.isDisposed()) {
            subscribe.dispose();
        }
        subscribe = null;
    }
}
