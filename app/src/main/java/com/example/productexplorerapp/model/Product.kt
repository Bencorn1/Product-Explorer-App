package com.example.productexplorerapp.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
data class Product(
    val id: Int,
    val title: String,
    val price: Double,
    val description: String,
    val category: String,
    val image: String,
    val rating: Rating
) : Parcelable

@Parcelize
data class Rating(
    val rate: Double,
    val count: Int
) : Parcelable

@Parcelize
@Entity(tableName = "products")
data class ProductDto(
    @PrimaryKey val id: Int,
    val title: String,
    val price: Double,
    val description: String,
    val category: String,
    val image: String,
    val rating: RatingDto
) : Parcelable

@Parcelize
data class RatingDto(
    val rate: Double,
    val count: Int
) : Parcelable

fun ProductDto.toProduct(): Product {
    return Product(
        id = this.id,
        title = this.title,
        price = this.price,
        description = this.description,
        category = this.category,
        image = this.image,
        rating = this.rating.toRating() // Ensure conversion from RatingDto to Rating
    )
}

// Function to convert RatingDto to Rating
fun RatingDto.toRating(): Rating {
    return Rating(
        rate = this.rate,
        count = this.count
    )
}

fun Rating.toRatingDto(): RatingDto {
    return RatingDto(
        rate = this.rate,
        count = this.count
    )
}
