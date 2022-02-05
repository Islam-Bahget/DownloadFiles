package com.example.downloadfiles.model

import com.google.gson.annotations.SerializedName

data class MovieResponse(

	@field:SerializedName("MovieResponse")
	val movieResponse: List<MovieResponseItem?>? = null
)

data class MovieResponseItem(

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("type")
	val type: String? = null,

	@field:SerializedName("url")
	val url: String? = null
)
