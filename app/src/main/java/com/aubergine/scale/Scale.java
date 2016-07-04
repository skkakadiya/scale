package com.aubergine.scale;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

import java.text.DecimalFormat;

/**
 * Created by aubergine on 30/6/16.
 */
public class Scale extends View {


    private String TAG = "Scale";



    private int pxPerMM = 20;
    int width, height;
    float startingPoint = 0;

    private Paint rulerPaint, textPaint;
    private int endPoint;
    private int scaleLineSmall;
    private int scaleLineMedium;
    private int scaleLineLarge;
    private int textStartPoint;

    private Rect rect;
    private OnScaleUpdate scaleUpdate;

    private int startCm = 0;
    private int maxCm = 100;
    private String unit = " cm";

    public Scale(Context context) {
        super(context);
        if (!isInEditMode()) {
            init();
        }
    }

    public Scale(Context context, AttributeSet foo) {
        super(context, foo);
        if (!isInEditMode()) {
            init();
        }
    }

    private void init() {
        rect = new Rect();

        rulerPaint = new Paint();
        rulerPaint.setStyle(Paint.Style.STROKE);
        rulerPaint.setStrokeWidth(0);
        rulerPaint.setAntiAlias(false);
        rulerPaint.setColor(Color.WHITE);

        textPaint = new TextPaint();
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setStrokeWidth(0);
        textPaint.setAntiAlias(true);
        textPaint.setTextSize(getResources().getDimension(R.dimen.txt_size));
        textPaint.setColor(Color.WHITE);

        scaleLineSmall = (int) getResources().getDimension(R.dimen.scale_line_small);
        scaleLineMedium = (int) getResources().getDimension(R.dimen.scale_line_medium);
        scaleLineLarge = (int) getResources().getDimension(R.dimen.scale_line_large);
        textStartPoint = (int) getResources().getDimension(R.dimen.text_start_point);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(widthMeasureSpec, (int) ((maxCm - startCm) * 10 * pxPerMM));

    }

    @Override
    public void onSizeChanged(int w, int h, int oldW, int oldH) {
        width = w;
        height = h;
        endPoint = width;

    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        startingPoint = 0;

        for (int i = (startCm *10)+1; i < (maxCm * 10); ++i) {
            startingPoint = startingPoint + pxPerMM;
            int size = (i % 10 == 0) ? scaleLineLarge : (i % 5 == 0) ? scaleLineMedium : scaleLineSmall;
            canvas.drawLine(endPoint - size, startingPoint, endPoint, startingPoint, rulerPaint);
            if (i % 10 == 0) {
                canvas.drawText((i / 10) + unit, endPoint - textStartPoint, startingPoint + 8, textPaint);
            }
        }

        canvas.getClipBounds(rect);
        float h = ((rect.bottom - rect.top) / 2);
        float selected = (rect.top + h) / (pxPerMM * 10) + startCm;
        selected = Float.parseFloat(new DecimalFormat("#####.#").format(selected));

        if (scaleUpdate != null) {
            scaleUpdate.onScaleSelected(selected);
        }
    }


    public void setScaleUpdate(OnScaleUpdate l) {
        scaleUpdate = l;
    }

    public void setMaxCm(int maxCm) {
        this.maxCm = maxCm;
    }

    public void setStartCm(int startCm){
        this.startCm = startCm;
    }

    public void setUnit(String unit){
        this.unit = unit;
    }

    public void setPxPerMM(int pxPerMM) {
        this.pxPerMM = pxPerMM;
    }

}
