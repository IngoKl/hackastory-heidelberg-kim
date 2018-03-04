package com.example.leonerath.kim.models

/**
 * Created by leonerath on 04.03.18.
 */



data class Article(
		val abstract: String,
		val background_information_id: Int,
		val category: String,
		val content: List<List<String>>,
		val headline: String,
		val id: Int,
		val lng: Double,
		val lat: Double,
		val quiz_id: Int,
		val timestamp: Any
)
