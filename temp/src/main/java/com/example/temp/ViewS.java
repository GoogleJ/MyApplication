package com.example.temp;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.drawable.shapes.RoundRectShape;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import static android.content.ContentValues.TAG;

/**
 * Created by Administrator on 2018/3/24.
 */

public class ViewS extends View {
    public ViewS(Context context) {
        super(context);
    }

    public ViewS(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ViewS(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

        Path path = new Path();

        path.moveTo(40, 200);
        path.lineTo(100, 0);
        path.lineTo(160, 200);
        path.lineTo(0, 80);
        path.lineTo(200, 80);

        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.RED);

        canvas.drawPath(path, paint);

        Path path1 = new Path();
        path1.moveTo(50,50);

        int widthPixels = getContext().getResources().getDisplayMetrics().widthPixels;

        path1.arcTo(50, 50, widthPixels, 500, -90, 90, false);

        paint.setColor(ContextCompat.getColor(getContext(), R.color.colorAccent));
        paint.setStyle(Paint.Style.FILL);
        canvas.drawPath(path1, paint);

    }
}
