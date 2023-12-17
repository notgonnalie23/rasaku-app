package app.capstone.rasaku.ui.screen.searchinput

import app.capstone.rasaku.model.History

data class SearchInputState(
    val histories : List<History> = emptyList()
)
