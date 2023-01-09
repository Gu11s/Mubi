package com.gdevs.mubi.presentation.components

enum class Category(val value: String) {
    POPULAR("popular"),
    RATED("top_rated"),
    ONTV("on_the_air"),
    AIRING("airing_today")
}

fun getAllCategories(): List<Category>{
    return listOf(Category.POPULAR, Category.RATED, Category.ONTV, Category.AIRING)
}

fun getCategory(value: String): Category?{
    val map = Category.values().associateBy(Category::value)
    return map[value]
}