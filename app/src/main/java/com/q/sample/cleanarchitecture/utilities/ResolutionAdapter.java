package com.q.sample.cleanarchitecture.utilities;

import android.content.res.Resources;

public final class ResolutionAdapter
{
	private static final float DENSITY = Resources.getSystem().getDisplayMetrics().density;
	private static final float SCALE_DENSITY = Resources.getSystem().getDisplayMetrics().scaledDensity;
	private ResolutionAdapter() {}

	public static float convertPxToDp(float pixel) {
		return pixel / DENSITY;
	}

	public static float convertDpToPx(float dp) {
		return dp * DENSITY;
	}

	public static float convertSpToPx(float sp) {
		return sp * DENSITY;
	}
}
