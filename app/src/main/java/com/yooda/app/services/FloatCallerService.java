package com.yooda.app.services;

import android.app.Service;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.yooda.app.R;

public class FloatCallerService extends Service {
    public FloatCallerService() {
    }


    @Override
    public void onCreate() {
        super.onCreate();

        WindowManager windowManager;
        LinearLayout ll;
        WindowManager.LayoutParams params;

        //инициализируем его
        windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);

        //создаем нашу кнопку что бы отобразить
        RelativeLayout rootView = (RelativeLayout) LayoutInflater.from(this).inflate(R.layout.relative_phone_layout, null);

        //задаем параметры для картинки, что бы была
        //своего размера, что бы можно было перемещать по экрану
        //что бы была прозрачной, и устанавливается ее стартовое полодение
        //на экране при создании
        params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT);

        params.gravity = Gravity.TOP | Gravity.LEFT;
        params.x = 0;
        params.y = 100;

        //кол перемещения тоста по экрану при помощи touch
        rootView.setOnTouchListener(new View.OnTouchListener() {
            private int initialX;
            private int initialY;
            private float initialTouchX;
            private float initialTouchY;
            private boolean shouldClick;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        shouldClick = true;
                        initialX = params.x;
                        initialY = params.y;
                        initialTouchX = event.getRawX();
                        initialTouchY = event.getRawY();
                        return true;
                    case MotionEvent.ACTION_UP:
                        if(shouldClick)
                            Toast.makeText(getApplicationContext(), "Клик по тосту случился!", Toast.LENGTH_LONG).show();
                        return true;
                    case MotionEvent.ACTION_MOVE:
                        shouldClick = false;
                        params.x = initialX
                                + (int) (event.getRawX() - initialTouchX);
                        params.y = initialY
                                + (int) (event.getRawY() - initialTouchY);
                        windowManager.updateViewLayout(rootView, params);
                        return true;
                }
                return false;
            }
        });
        windowManager.addView(rootView, params);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}