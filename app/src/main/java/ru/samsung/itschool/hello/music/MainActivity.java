package ru.samsung.itschool.hello.music;

import android.app.Activity;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;

public class MainActivity extends Activity implements SensorEventListener {
    private SensorManager mSensorManager;
    private Sensor mLight;
    private int variableLight=500;
    private int tVariableLight=500;
    private Date date;
    private long stTime;
    private long pstTime;
    private long time = 0;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView =findViewById(R.id.textView2);
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mLight = mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        if (mLight!=null){
            mSensorManager.registerListener(this,mLight,SensorManager.SENSOR_DELAY_NORMAL);
        }
        else{
            Toast.makeText(this, "Sensor wasn't defined", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Intent intent = new Intent(this, MusicService.class);
        stopService(intent);
        mSensorManager.unregisterListener(this);
    }

    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }

    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        textView.setText("Датчик освещенности: " + event.values[0]);
        loadSensorData(event); // получаем данные с датчика
        if (variableLight<20 && tVariableLight>20){ // на практике должно быть актуально, тк значения сенсоров будут всегда обновляться
            date = new Date();
            stTime = date.getTime();
            Intent intent = new Intent(this, MusicService.class);
            intent.putExtra("time",time);
            startService(intent);
        }
        else if (variableLight>20 && tVariableLight <20){
            date = new Date();
            pstTime = date.getTime();
            time = time + pstTime - stTime;
            Intent intent = new Intent(this, MusicService.class);
            stopService(intent);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    private void loadSensorData(SensorEvent event) {
        tVariableLight = variableLight;
        variableLight = (int) event.values[0];
    }
}