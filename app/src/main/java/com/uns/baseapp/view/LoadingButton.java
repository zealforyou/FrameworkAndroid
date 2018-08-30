package com.uns.baseapp.view;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;

import com.uns.baseapp.R;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by zhuo.zhang on 2018/2/24.
 */

public class LoadingButton extends AppCompatButton {
    int circleR;
    int circle_r;
    int circleCount = 10;
    Path circlePath;
    PathMeasure pathMeasure;
    Paint paint = new Paint();
    float pos[] = new float[2];
    private long value = 0;
    private boolean drawLoading;

    public LoadingButton(Context context) {
        super(context);
        init();
    }

    private Disposable subscribe;

    public LoadingButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LoadingButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        circlePath = new Path();
        pathMeasure = new PathMeasure(circlePath, true);
        paint.setColor(ActivityCompat.getColor(getContext(), R.color.white));
        paint.setStyle(Paint.Style.FILL);
        paint.setAntiAlias(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (drawLoading) {
            drawCircle(canvas);
        }
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

    @Override
    public boolean performClick() {
//        showLoading();
        return super.performClick();
    }

    public void hideLoading() {
        drawLoading = false;
        stop();
        setClickable(true);
        invalidate();
    }

    public void showLoading() {
        drawLoading = true;
        start();
        setClickable(false);
    }

    private void start() {
        circleR = getHeight() / 2;
        circle_r = (int) (1f / 6 * circleR);
        circlePath.reset();
        circlePath.addCircle(getWidth() / 2, getHeight() / 2, circleR - 2 * circle_r, Path.Direction.CCW);
        pathMeasure.setPath(circlePath, true);
        if (subscribe == null || subscribe.isDisposed()) {
            subscribe = Observable.interval(50, 100, TimeUnit.MILLISECONDS)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<Long>() {
                        @Override
                        public void accept(Long aLong) throws Exception {
                            value = aLong % circleCount;
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
}
