package com.azurehorsecreations.sensorapp;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.wearable.view.WatchViewStub;
import android.support.wearable.view.WearableListView;
import android.util.Log;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class SensorWearActivity extends Activity implements SensorEventListener {
    private static final String TAG = "SensorWearActivity";
    private SensorManager mSensorManager;
    private WearableListView mWearableListView;
    private List<AHCSensor> deviceSensorsList;
    private SensorAdapter mSensorAdapter;
    private TextView mTextViewAccelerometer;
    private TextView mTextViewMagneticField;
    private TextView mTextViewGyroscope;
    private TextView mTextViewPressure;
    private TextView mTextViewHumidity;
    private TextView mTextViewTemperature;
    private TextView mTextViewGravity;
    private TextView mTextViewLinearAcceleration;
    private TextView mTextViewRotationVector;
    private TextView mTextViewSignificantMotion;
    private TextView mTextViewStepCounter;
    private TextView mTextViewStepDetect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor_wear);
        mSensorManager = ((SensorManager)getSystemService(SENSOR_SERVICE));
        deviceSensorsList = getDeviceSensors();
        registerForSensorEvents(deviceSensorsList);
        mSensorAdapter = new SensorAdapter(getApplicationContext(), deviceSensorsList);
        final WatchViewStub stub = (WatchViewStub) findViewById(R.id.watch_view_stub);
        stub.setOnLayoutInflatedListener(new WatchViewStub.OnLayoutInflatedListener() {
            @Override
            public void onLayoutInflated(WatchViewStub stub) {
                mWearableListView = (WearableListView) stub.findViewById(R.id.list);
                mWearableListView.setAdapter(mSensorAdapter);
                mWearableListView.setClickListener(new WearableListView.ClickListener() {
                    @Override
                    public void onClick(WearableListView.ViewHolder viewHolder) {
                        int position = viewHolder.getPosition();
                    }

                    @Override
                    public void onTopEmptyRegionClick() {
                    }
                });
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mSensorManager != null) {
            mSensorManager.unregisterListener(this);
        }
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        Log.v(TAG, "Got " + sensorEvent.sensor.getName());
        updateSensorInList(sensorEvent.sensor, sensorEvent.values);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    private void registerForSensorEvents(List<AHCSensor> sensorList) {
        if (mSensorManager != null) {
            for (AHCSensor ahcSensor : sensorList) {
                Log.v(TAG, "registered for " + ahcSensor.getSensor().getType());
                mSensorManager.registerListener(this, ahcSensor.getSensor(), SensorManager.SENSOR_DELAY_NORMAL);
            }
        }
    }

    private String currentTimeStr() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
        return df.format(c.getTime());
    }

    private void updateSensorInList(Sensor sensor, float[] values) {
        for (AHCSensor ahcSensor : deviceSensorsList) {
            if (ahcSensor.getSensor().getType() == sensor.getType()) {
                ahcSensor.setValues(values);
                deviceSensorsList.set(deviceSensorsList.indexOf(ahcSensor), ahcSensor);
                mSensorAdapter.notifyDataSetChanged();
            }
        }
    }

    private List<AHCSensor> getDeviceSensors() {
        deviceSensorsList = new ArrayList<>();
        List<Sensor> sensorList = new ArrayList<>();
        if (mSensorManager != null) {
            sensorList.addAll(mSensorManager.getSensorList(Sensor.TYPE_ALL));
            for (Sensor sensor : sensorList) {
                deviceSensorsList.add(new AHCSensor(sensor.getName(), sensor));
            }
        } else {
            Log.e(TAG, "Sensor manager has not been initialized");
        }
        return deviceSensorsList;
    }
}
