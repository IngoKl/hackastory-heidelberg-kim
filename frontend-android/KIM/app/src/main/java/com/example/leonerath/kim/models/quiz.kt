package com.example.leonerath.kim.models

/**
 * Created by leonerath on 04.03.18.
 */

data class Quiz(
		val answers: List<List<String>>,
		val id: Int,
		val question: String,
		val solution: String
)