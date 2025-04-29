package surendraKummara.s3287724.travalrecommendations

import android.content.Context



object TravalRecommendationData {

    fun recordTravelStatus(context: Context, value: Boolean) {
        val userLogin = context.getSharedPreferences("TRAVELER_DETAILS", Context.MODE_PRIVATE)
        val editor = userLogin.edit()
        editor.putBoolean("TRAVELER_STATUS", value).apply()
    }

    fun receiveTravelStatus(context: Context): Boolean {
        val userLogin = context.getSharedPreferences("TRAVELER_DETAILS", Context.MODE_PRIVATE)
        return userLogin.getBoolean("TRAVELER_STATUS", false)
    }

    fun recordTravelName(context: Context, value: String) {
        val userLogin = context.getSharedPreferences("TRAVELER_DETAILS", Context.MODE_PRIVATE)
        val editor = userLogin.edit()
        editor.putString("TRAVELER_NAME", value).apply()
    }

    fun receiveTravelName(context: Context): String {
        val userLogin = context.getSharedPreferences("TRAVELER_DETAILS", Context.MODE_PRIVATE)
        return userLogin.getString("TRAVELER_NAME", "")!!
    }

    fun recordTravelMailMail(context: Context, value: String) {
        val userLogin = context.getSharedPreferences("TRAVELER_DETAILS", Context.MODE_PRIVATE)
        val editor = userLogin.edit()
        editor.putString("TRAVELER_MAIL", value).apply()
    }

    fun receiveTravelMail(context: Context): String {
        val userLogin = context.getSharedPreferences("TRAVELER_DETAILS", Context.MODE_PRIVATE)
        return userLogin.getString("TRAVELER_MAIL", "")!!
    }


}