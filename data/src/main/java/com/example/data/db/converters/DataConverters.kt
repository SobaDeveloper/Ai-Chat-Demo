package com.example.data.db.converters

import androidx.room.TypeConverter
import com.example.data.service.gemini.dto.Role

class DataConverters {
    @TypeConverter
    fun fromRole(role: Role): String = role.name

    @TypeConverter
    fun toRole(role: String): Role = Role.valueOf(role)
}