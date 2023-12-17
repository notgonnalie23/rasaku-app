package app.capstone.rasaku.ui.screen.searchresult

import app.capstone.rasaku.model.FoodsItem

data class SearchResultState(
    val foods : List<FoodsItem> = listOf()
)
