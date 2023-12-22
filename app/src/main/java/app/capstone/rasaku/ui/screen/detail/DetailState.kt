package app.capstone.rasaku.ui.screen.detail

import app.capstone.rasaku.model.Favorite
import app.capstone.rasaku.model.FoodsItem

data class DetailState(
    val food : FoodsItem? = null,
    val recommendations : List<FoodsItem> = emptyList(),
    val favorites : List<Favorite> = listOf()
)
