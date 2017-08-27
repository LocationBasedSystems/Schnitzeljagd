package de.htwberlin.f4.ai.ma.android.sensors;

/**
 * SensorFactory interface, so we can hide any android related stuff (Context in this case) from IndoorMeasurement
 * and keep it seperated
 */

public interface SensorFactory {

    Sensor getSensor(SensorType sensorType, int sensorRate);
}