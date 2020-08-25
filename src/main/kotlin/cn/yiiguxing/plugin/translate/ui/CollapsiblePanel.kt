package cn.yiiguxing.plugin.translate.ui

import cn.yiiguxing.plugin.translate.ui.UI.fillX
import cn.yiiguxing.plugin.translate.ui.UI.migLayout
import com.intellij.icons.AllIcons
import com.intellij.ui.components.labels.LinkLabel
import net.miginfocom.layout.CC
import java.awt.Dimension
import javax.swing.JComponent
import javax.swing.JPanel
import javax.swing.SwingConstants.LEADING

class CollapsiblePanel(content: JComponent, expandTitle: String) {

    private val collapseButton = LinkLabel<Void>("", AllIcons.General.CollapseComponent)
    private val expandButton = LinkLabel<Void>(expandTitle, AllIcons.General.ExpandComponent).apply {
        horizontalTextPosition = LEADING
    }

    private val expandedPanel = JPanel(migLayout()).apply {
        add(content, fillX())
        val collapseButtonPanel = JPanel(migLayout()).apply {
            add(collapseButton, CC().dockNorth())
        }
        add(collapseButtonPanel, CC().dockEast())
        isVisible = !isCollapsed
    }
    private val collapsedPanel = JPanel(migLayout()).apply {
        add(expandButton, CC().dockEast())
        isVisible = isCollapsed
    }

    private var listener: (() -> Unit)? = null

    val panel = JPanel(migLayout()).apply {
        add(expandedPanel, fillX())
        add(collapsedPanel, fillX())
    }

    var isCollapsed: Boolean = true
        set(value) {
            if (value != field) {
                field = value
                onExpandOrCollapse()
            }
        }

    init {
        collapseButton.setListener({_, _ ->  isCollapsed = !isCollapsed}, null)
        expandButton.setListener({_, _ ->  isCollapsed = !isCollapsed}, null)
    }

    private fun onExpandOrCollapse() {
        collapsedPanel.isVisible = !collapsedPanel.isVisible
        expandedPanel.isVisible = !expandedPanel.isVisible
        listener?.invoke()
    }

    fun setExpandCollapseListener(listener: () -> Unit) {
        this.listener = listener
    }

}