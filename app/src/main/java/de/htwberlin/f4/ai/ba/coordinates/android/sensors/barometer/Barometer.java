package de.htwberlin.f4.ai.ba.coordinates.android.sensors.barometer;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import java.sql.Timestamp;

import de.htwberlin.f4.ai.ba.coordinates.android.sensors.SensorData;
import de.htwberlin.f4.ai.ba.coordinates.android.sensors.SensorListener;
import de.htwberlin.f4.ai.ba.coordinates.android.sensors.SensorType;

/**
 * Created by benni on 19.07.2017.
 */

public class Barometer implements SensorEventListener, de.htwberlin.f4.ai.ba.coordinates.android.sensors.Sensor {

    private static final SensorType SENSORTYPE = SensorType.BAROMETER;

    private SensorManager sensorManager;
    private SensorListener listener;

    private SensorData sensorData;


    public Barometer(Context context) {
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        sensorData = new SensorData();
        sensorData.setSensorType(SENSORTYPE);
    }

    @Override
    public void start() {
        Sensor barometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE);
        if (barometerSensor != null) {
            sensorManager.registerListener(this, barometerSensor, SensorManager.SENSOR_DELAY_UI);
        }
    }

    @Override
    public void stop() {
        if (sensorManager != null) {
            sensorManager.unregisterListener(this);
        }
    }

    @Override
    public SensorData getValues() {
        return sensorData;
    }

    @Override
    public boolean isSensorAvailable() {
        if (sensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE) == null) {
            return false;
        }

        return true;
    }

    @Override
    public void setListener(SensorListener listener) {
        this.listener = listener;
    }

    @Override
    public SensorType getSensorType() {
        return SENSORTYPE;
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if (sensorEvent.sensor.getType() == Sensor.TYPE_PRESSURE) {
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            long realTimestamp = timestamp.getTime();
            float[] values = new float[sensorEvent.values.length];
            System.arraycopy(sensorEvent.values, 0, values, 0, sensorEvent.values.length);

            sensorData = new SensorData();
            sensorData.setSensorType(SENSORTYPE);
            sensorData.setTimestamp(realTimestamp);
            sensorData.setValues(values);

            if (listener != null) {
                listener.valueChanged(sensorData);
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
