package de.htwberlin.f4.ai.ma.measurement.modules.d;

import android.content.Context;

import de.htwberlin.f4.ai.ma.measurement.modules.c.DistanceModuleC;

/**
 * DistanceModuleD Class which implements the DistanceModule Interface
 *
 * Simply calculate distance by using the previously calibrated
 * step length. Change distance if stair toggle is active
 *
 * Author: Benjamin Kneer
 */

public class DistanceModuleD extends DistanceModuleC {

    public DistanceModuleD(Context context, float stepLength) {
        super(context, stepLength);
    }
}
