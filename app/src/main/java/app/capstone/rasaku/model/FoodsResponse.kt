package app.capstone.rasaku.model

import com.google.gson.annotations.SerializedName

data class FoodsResponse(

	@field:SerializedName("data")
	val data: List<FoodsItem?>? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class FoodsItem(

	@field:SerializedName("food_recipe")
	val foodRecipe: String? = null,

	@field:SerializedName("food_name")
	val foodName: String? = null,

	@field:SerializedName("description_food")
	val descriptionFood: String? = null,

	@field:SerializedName("city")
	val city: String? = null,

	@field:SerializedName("images_url")
	val imagesUrl: String? = null,

	@field:SerializedName("id_food")
	val idFood: Int? = null
)
