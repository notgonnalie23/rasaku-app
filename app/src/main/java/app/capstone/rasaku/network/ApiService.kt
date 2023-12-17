package app.capstone.rasaku.network

import app.capstone.rasaku.model.FoodResponse
import app.capstone.rasaku.model.FoodsResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("foods")
    suspend fun getFoods(): FoodsResponse

    @GET("foods/{id}")
    suspend fun getFoodsById(
        @Path("id") id: Int
    ): FoodResponse
}