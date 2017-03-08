package com.azurehorsecreations.sensorapp;

import android.hardware.Sensor;

/**
 * Created by pattycase on 8/27/16.
 */
public class AHCSensor implements Comparable<AHCSensor> {
    private String name;
    private Sensor sensor;
    private long timestamp;
    private float[] values;

    public AHCSensor (String name, Sensor sensor) {
        this.name = name;
        this.sensor = sensor;
        this.values = new float[0];
    }

    public String getName() {
        return name;
    }

    public Sensor getSensor() {
        return sensor;
    }

    public void setSensor(Sensor sensor) {
        this.sensor = sensor;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public float[] getValues() {
        return values;
    }

    public void setValues(float[] values) {
        this.values = values;
    }

    @Override
    public int compareTo(AHCSensor AHCSensorCompareTo) {
        return this.name.compareTo(AHCSensorCompareTo.getName());
    }
}
