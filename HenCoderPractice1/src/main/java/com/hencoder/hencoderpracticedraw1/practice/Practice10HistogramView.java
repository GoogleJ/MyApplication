package com.hencoder.hencoderpracticedraw1.practice;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class Practice10HistogramView extends View {

    public Practice10HistogramView(Context context) {
        super(context);
    }

    public Practice10HistogramView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public Practice10HistogramView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

//        综合练习
//        练习内容：使用各种 Canvas.drawXXX() 方法画直方图

        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(2);

        Path path = new Path();
        path.moveTo(250, 150);
        path.lineTo(250, 700);
        path.rLineTo(930, 0);

        canvas.drawPath(path, paint);

        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(0);
        paint.setTextSize(55);
        canvas.drawText("直方图", 600, 850, paint);

        paint.setTextSize(35);
        canvas.drawText("Foyo", 285, 740, paint);
        canvas.drawText("GB", 425, 740, paint);
        canvas.drawText("ICS", 545, 740, paint);
        canvas.drawText("JB", 680, 740, paint);
        canvas.drawText("KitKat", 785, 740, paint);
        canvas.drawText("L", 950, 740, paint);
        canvas.drawText("M", 1070, 740, paint);

        paint.setColor(Color.GREEN);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawRect(270, 690, 380, 699, paint);
        canvas.drawRect(400, 680, 490, 699, paint);
        canvas.drawRect(510, 680, 620, 699, paint);
        canvas.drawRect(640, 590, 750, 699, paint);
        canvas.drawRect(770, 500, 880, 699, paint);
        canvas.drawRect(900, 400, 1010, 699, paint);
        canvas.drawRect(1030, 520, 1140, 699, paint);

    }
}
