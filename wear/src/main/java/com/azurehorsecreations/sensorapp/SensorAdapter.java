package com.azurehorsecreations.sensorapp;

import android.content.Context;
import android.hardware.Sensor;
import android.support.wearable.view.WearableListView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by pattycase on 10/13/16.
 */
public class SensorAdapter extends WearableListView.Adapter {
    private LayoutInflater mInflater;
    private List<AHCSensor> mSensorList;

    /**
     * Constructor
     *
     * @param context
     * @param sensors
     */
    public SensorAdapter(Context context, List<AHCSensor> sensors) {
        mInflater = LayoutInflater.from(context);
        mSensorList = sensors;
    }

    /**
     * create a new view for a row in the list
     *
     * @param viewGroup
     * @param viewType
     * @return
     */
    @Override
    public WearableListView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = mInflater.inflate(R.layout.row_simple_item_layout, viewGroup, false);
        return new WearableListView.ViewHolder(view);
    }

    /**
     * To determines the position (that is index) of every visible
     * row in the list
     *
     * @param viewHolder
     * @param position
     */
    @Override
    public void onBindViewHolder(WearableListView.ViewHolder viewHolder, int position) {
        TextView textView = (TextView) viewHolder.itemView;
        AHCSensor ahcSensor = mSensorList.get(position);
        String value = null;
        if (ahcSensor.getValues().length > 0) {
            value = String.valueOf(ahcSensor.getValues()[0]);
        }
        textView.setText(ahcSensor.getName() + " " + value);
    }

    /**
     * To know how many items are in the list
     * @return
     */
    @Override
    public int getItemCount() {
        return mSensorList.size();
    }

}
