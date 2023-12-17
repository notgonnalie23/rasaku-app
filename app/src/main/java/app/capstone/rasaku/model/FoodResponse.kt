package app.capstone.rasaku.model

import com.google.gson.annotations.SerializedName

data class FoodResponse(

	@field:SerializedName("data")
	val data: Data? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class Data(

	@field:SerializedName("data")
	val data: List<FoodsItem?>? = null,

	@field:SerializedName("id_food")
	val idFood: String? = null
)
