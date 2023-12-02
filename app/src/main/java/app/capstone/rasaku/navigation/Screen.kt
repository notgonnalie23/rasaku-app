package app.capstone.rasaku.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Search : Screen("search")
    object Camera : Screen("camera")
    object Favorite : Screen("favorite")
    object History : Screen("history")
    object SearchInput : Screen("search/query")

}
