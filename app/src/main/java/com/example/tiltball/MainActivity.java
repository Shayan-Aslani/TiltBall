package com.example.tiltball;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Point;
import android.graphics.PointF;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.Display;
import android.widget.FrameLayout;

public class MainActivity extends AppCompatActivity implements SensorEventListener {


    private PointF accel , ballPos , ballSpeed ;
    private float xMax, yMax;
    private SensorManager sensorManager;
    private Sensor accSensor ;
    private BallView ballView;
    private FrameLayout mainView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        mainView = findViewById(R.id.main_view);

        Point size = new Point();
        Display display = getWindowManager().getDefaultDisplay();
        display.getSize(size);
        xMax = (float) size.x;
        yMax = (float) size.y - 150;

        accel = new PointF(0 , 0);
        ballPos = new PointF(xMax/2 , yMax/2);
        ballSpeed = new PointF(0 , 0);

        ballView = new BallView(this, ballPos );
        mainView.addView(ballView);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        accSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(this, accSensor, SensorManager.SENSOR_DELAY_GAME);

    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if (sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            accel.x = sensorEvent.values[0];
            accel.y = -sensorEvent.values[1];
            updateBall();
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }


    public void onStop() {
        super.onStop();
        sensorManager.unregisterListener(this);
    }

    private void updateBall() {
        float frameTime = 0.6f;
        ballSpeed.x += (accel.x * frameTime);
        ballSpeed.y += (accel.y * frameTime);

        float x = (ballSpeed.x / 2) * frameTime;
        float y = (ballSpeed.y / 2) * frameTime;

        ballPos.x -= x;
        ballPos.y -= y;

        if (ballPos.x > xMax) {
            ballPos.x = xMax;
            ballSpeed.x = 0 ;
        } else if (ballPos.x < 10) {
            ballPos.x = 10;
            ballSpeed.x = 0 ;
        }
        if (ballPos.y > yMax) {
            ballPos.y = yMax;
            ballSpeed.y = 0 ;
        } else if (ballPos.y < 10) {
            ballPos.y = 10;
            ballSpeed.y = 0 ;
        }

            ballView.setPos(ballPos);
            ballView.invalidate();
    }
}