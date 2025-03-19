package com.example.productexplorerapp.model

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {
    @TypeConverter
    fun fromRatingDto(rating: RatingDto): String {
        return Gson().toJson(rating)
    }

    @TypeConverter
    fun toRatingDto(ratingString: String): RatingDto {
        val type = object : TypeToken<RatingDto>() {}.type
        return Gson().fromJson(ratingString, type)
    }
}