package com.example.bitfit_pt1

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.TypeConverter
import java.time.Instant
import java.util.*

class Converters {
    @RequiresApi(Build.VERSION_CODES.O)
    @TypeConverter
    public fun instantFromLong(value: Long): Instant? {
        return if(value == null) {
            null
        } else {
            Instant.ofEpochMilli(value)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @TypeConverter
    public fun longFromInstant(date: Instant): Long? {
        return if (date == null) {
            null
        } else {
            date.toEpochMilli()
        }
    }
}