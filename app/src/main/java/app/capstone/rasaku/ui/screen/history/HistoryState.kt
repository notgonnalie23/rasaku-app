package app.capstone.rasaku.ui.screen.history

import app.capstone.rasaku.model.History

data class HistoryState(
    val histories: List<History> = emptyList()
)