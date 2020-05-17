package dk.bluebox.demo.githubviewer.common.ui.compose

import androidx.ui.graphics.Color
import androidx.ui.material.lightColorPalette
import dk.bluebox.demo.githubviewer.R
import dk.bluebox.demo.githubviewer.ViewerApplication

val appColorPalette = lightColorPalette(
    primary = colorFromResources(R.color.colorPrimary),
    primaryVariant = colorFromResources(R.color.colorPrimaryDark),
    secondary = colorFromResources(R.color.colorPrimaryDark),
    background = colorFromResources(R.color.gray)
)

private fun colorFromResources(resId: Int): Color {
    return Color(ViewerApplication.appContext.getColor(resId))
}
