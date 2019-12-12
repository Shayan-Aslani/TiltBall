package com.example.tiltball;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.view.View;



public class BallView extends View {

    private PointF pos ;
    private final int r;
    private final Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    public void setPos(PointF pos) {
        this.pos = pos;
    }

    public BallView(Context context, PointF pos) {
        super(context);
        mPaint.setColor(Color.RED);
        pos = new PointF(pos.x , pos.y);
        this.r = 20;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(pos.x, pos.y, r, mPaint);
        invalidate();
    }
}



