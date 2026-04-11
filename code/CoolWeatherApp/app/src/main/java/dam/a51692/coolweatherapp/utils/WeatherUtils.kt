package dam.a51692.coolweatherapp.utils

import android.content.Context
import dam.a51692.coolweatherapp.R

object WeatherUtils {

    private fun getIndex(context: Context, code: Int): Int {
        val codes = context.resources.getStringArray(R.array.weather_codes)
        return codes.indexOf(code.toString())
    }

    fun getWeatherIcon(context: Context, code: Int): Int {
        val index = getIndex(context, code)
        if (index == -1) return R.drawable.overcast_icon

        val ta = context.resources.obtainTypedArray(R.array.weather_icons)
        val resId = ta.getResourceId(index, R.drawable.overcast_icon)
        ta.recycle()

        return resId
    }

    fun getWeatherBackground(context: Context, code: Int): Int {
        val index = getIndex(context, code)
        if (index == -1) return R.drawable.overcast_sky_day

        val ta = context.resources.obtainTypedArray(R.array.weather_backgrounds)
        val resId = ta.getResourceId(index, R.drawable.overcast_sky_day)
        ta.recycle()

        return resId
    }

    fun getWeatherDescription(context: Context, code: Int): String {
        val index = getIndex(context, code)
        val desc = context.resources.getStringArray(R.array.weather_descriptions)

        return if (index != -1) desc[index] else "Unknown"
    }
}