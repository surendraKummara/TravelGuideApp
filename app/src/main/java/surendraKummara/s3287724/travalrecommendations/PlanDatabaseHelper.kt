package surendraKummara.s3287724.travalrecommendations

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class PlanDatabaseHelper(context: Context) : SQLiteOpenHelper(
    context, DATABASE_NAME, null, DATABASE_VERSION
) {

    companion object {
        private const val DATABASE_NAME = "plans.db"
        private const val DATABASE_VERSION = 1
        private const val TABLE_NAME = "saved_plans"

        private const val COLUMN_ID = "id"
        private const val COLUMN_CATEGORY = "category"
        private const val COLUMN_DESTINATION = "destination"
        private const val COLUMN_START_DATE = "startDate"
        private const val COLUMN_END_DATE = "endDate"
        private const val COLUMN_ACCOMMODATION_TYPE = "accommodationType"
        private const val COLUMN_PLACE_NAME = "placeName"
        private const val COLUMN_LATITUDE = "latitude"
        private const val COLUMN_LONGITUDE = "longitude"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTableQuery = """
            CREATE TABLE $TABLE_NAME (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_CATEGORY TEXT,
                $COLUMN_DESTINATION TEXT,
                $COLUMN_START_DATE TEXT,
                $COLUMN_END_DATE TEXT,
                $COLUMN_ACCOMMODATION_TYPE TEXT,
                $COLUMN_PLACE_NAME TEXT,
                $COLUMN_LATITUDE REAL,
                $COLUMN_LONGITUDE REAL
            )
        """.trimIndent()

        db.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun insertPlan(plan: SavePlan): Boolean {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_CATEGORY, plan.category)
            put(COLUMN_DESTINATION, plan.destination)
            put(COLUMN_START_DATE, plan.startDate)
            put(COLUMN_END_DATE, plan.endDate)
            put(COLUMN_ACCOMMODATION_TYPE, plan.accomodationType)
            put(COLUMN_PLACE_NAME, plan.placeDetails.placeName)
            put(COLUMN_LATITUDE, plan.placeDetails.latitude)
            put(COLUMN_LONGITUDE, plan.placeDetails.longitude)
        }

        val result = db.insert(TABLE_NAME, null, values)
        db.close()
        return result != -1L
    }

    fun getAllPlans(): List<SavePlan> {
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_NAME", null)
        val plans = mutableListOf<SavePlan>()

        if (cursor.moveToFirst()) {
            do {
                val planId = cursor.getInt(cursor.getColumnIndex(COLUMN_ID))
                val category = cursor.getString(cursor.getColumnIndex(COLUMN_CATEGORY))
                val destination = cursor.getString(cursor.getColumnIndex(COLUMN_DESTINATION))
                val startDate = cursor.getString(cursor.getColumnIndex(COLUMN_START_DATE))
                val endDate = cursor.getString(cursor.getColumnIndex(COLUMN_END_DATE))
                val accommodationType = cursor.getString(cursor.getColumnIndex(COLUMN_ACCOMMODATION_TYPE))
                val placeName = cursor.getString(cursor.getColumnIndex(COLUMN_PLACE_NAME))
                val latitude = cursor.getDouble(cursor.getColumnIndex(COLUMN_LATITUDE))
                val longitude = cursor.getDouble(cursor.getColumnIndex(COLUMN_LONGITUDE))

                val plan = SavePlan(
                    category, destination, startDate, endDate, accommodationType,
                    PlaceDetails(placeName, latitude = latitude, longitude = longitude),
                    planId
                )
                plans.add(plan)
            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()
        return plans
    }

    fun deletePlan(planId: Int): Boolean {
        val db = this.writableDatabase
        val result = db.delete(TABLE_NAME, "$COLUMN_ID = ?", arrayOf(planId.toString()))
        db.close()
        return result > 0
    }

}
