package app.capstone.rasaku.ui.screen.home

import app.capstone.rasaku.model.FoodsItem

data class HomeState(
    val foods : List<FoodsItem?> = emptyList(),
    val carousel: List<FoodsItem> = emptyList(),
)
