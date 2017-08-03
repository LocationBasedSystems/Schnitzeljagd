package de.htwberlin.f4.ai.ba.coordinates.android.calibrate;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.carol.bvg.R;

import de.htwberlin.f4.ai.ba.coordinates.android.sensors.SensorFactory;
import de.htwberlin.f4.ai.ba.coordinates.android.sensors.SensorFactoryImpl;
import de.htwberlin.f4.ai.ba.coordinates.measurement.CalibrationData;
import de.htwberlin.f4.ai.ba.coordinates.measurement.IndoorMeasurementFactory;

/**
 * Save / load calibration from SharedPreferences
 */

public class CalibratePersistanceImpl implements CalibratePersistance {

    private Context context;
    private float stepLength;
    private int stepPeriod; // in ms

    public CalibratePersistanceImpl(Context context) {
        this.context = context;
    }

    @Override
    public CalibrationData load() {
        CalibrationData calibrationData = null;
        SharedPreferences sharedPreferences = context.getSharedPreferences(context.getString(R.string.coordinates_shared_preferences), Context.MODE_PRIVATE);

        // just checking if the keys exist, maybe we should check if the value is != 0 too...
        if (sharedPreferences.contains(context.getString(R.string.coordinates_shared_preferences_steplength)) &&
                sharedPreferences.contains(context.getString(R.string.coordinates_shared_preferences_stepperiod))) {

            calibrationData = new CalibrationData();
            calibrationData.setStepLength(sharedPreferences.getFloat(context.getString(R.string.coordinates_shared_preferences_steplength), 0.0f));
            calibrationData.setStepPeriod(sharedPreferences.getInt(context.getString(R.string.coordinates_shared_preferences_stepperiod), 0));

        }

        return calibrationData;
    }

    @Override
    public void save(float stepLength, int stepPeriod) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(context.getString(R.string.coordinates_shared_preferences), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putFloat(context.getString(R.string.coordinates_shared_preferences_steplength), stepLength);
        editor.putInt(context.getString(R.string.coordinates_shared_preferences_stepperiod), stepPeriod);

        editor.commit();
    }

}
