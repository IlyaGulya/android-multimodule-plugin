package ru.hh.plugins.geminio.actions

import com.intellij.icons.AllIcons
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.psi.PsiDirectory
import ru.hh.plugins.extensions.getSelectedPsiElement
import ru.hh.plugins.geminio.ActionsHelper
import ru.hh.plugins.logger.HHLogger
import ru.hh.plugins.logger.HHNotifications

class RescanTemplatesAction : AnAction() {

    init {
        with(templatePresentation) {
            text = "Rescan Templates"
            icon = AllIcons.Actions.Refresh
            description = "Rescan folder with templates"
        }
    }

    override fun update(e: AnActionEvent) {
        super.update(e)

        val selectedPsiElement = e.getSelectedPsiElement()
        e.presentation.isEnabledAndVisible =
            (e.project == null || selectedPsiElement == null || selectedPsiElement !is PsiDirectory).not()
    }

    override fun actionPerformed(e: AnActionEvent) {
        HHLogger.d("Start executing rescan templates")
        e.project?.also(ActionsHelper()::createGeminioActions)
        HHNotifications.info("Templates rescanned")
    }
}
