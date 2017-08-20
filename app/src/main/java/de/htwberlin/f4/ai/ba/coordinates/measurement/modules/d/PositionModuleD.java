package de.htwberlin.f4.ai.ba.coordinates.measurement.modules.d;

import android.content.Context;

import de.htwberlin.f4.ai.ba.coordinates.android.sensors.SensorFactory;
import de.htwberlin.f4.ai.ba.coordinates.android.measure.CalibrationData;
import de.htwberlin.f4.ai.ba.coordinates.measurement.modules.a.PositionModuleA;

/**
 * Orientation: CompassSimple
 * Altitude: Barometer
 * Distance: Steplength
 *
 * Lowpass filter
 */

public class PositionModuleD extends PositionModuleA{

    public PositionModuleD(Context context, CalibrationData calibrationData) {
        super(context, calibrationData);

        altitudeModule = new AltitudeModuleD(context, calibrationData.getAirPressure(), calibrationData.getLowpassFilterValue());
        distanceModule = new DistanceModuleD(context, calibrationData.getStepLength());
        orientationModule = new OrientationModuleD(context, calibrationData.getLowpassFilterValue());
    }
}
