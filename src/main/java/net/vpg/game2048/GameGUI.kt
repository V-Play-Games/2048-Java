package net.vpg.game2048

import java.awt.*
import java.awt.event.ActionEvent
import javax.swing.*
import kotlin.system.exitProcess

lateinit var board: Board
lateinit var tiles: Array<Array<JLabel>>
var scoreLabel = JLabel()
var resultLabel = JLabel()

fun main() {
    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName())
    val frame = JFrame("2048 Game")

    val gridPanel = JPanel().apply { layout = GridLayout(4, 4) }
    tiles = Array(4) {
        Array(4) {
            JLabel().apply {
                horizontalAlignment = SwingConstants.CENTER
                isOpaque = true
                background = Color.LIGHT_GRAY
                font = Font("Arial", Font.BOLD, 24)
                border = BorderFactory.createLineBorder(Color.DARK_GRAY, 2)
                gridPanel.add(this)
            }
        }
    }

    val panel = JPanel().apply { layout = GridBagLayout() }
    val gbc = GridBagConstraints().apply {
        fill = GridBagConstraints.BOTH
        insets = Insets(5, 5, 5, 5)
    }
    panel.add(scoreLabel, gbc.apply { gridx = 0; gridy = 0 })
    panel.add(move(Move.UP, 'w', frame), gbc.apply { gridx = 1; gridy = 0 })
    panel.add(resultLabel, gbc.apply { gridx = 2; gridy = 0 })
    panel.add(move(Move.LEFT, 'a', frame), gbc.apply { gridx = 0; gridy = 1 })
    panel.add(move(Move.DOWN, 's', frame), gbc.apply { gridx = 1; gridy = 1 })
    panel.add(move(Move.RIGHT, 'd', frame), gbc.apply { gridx = 2; gridy = 1 })
    panel.add(JButton("Reset (r)").apply {
        addActionListener {
            newBoard()
        }
    }, gbc.apply { gridx = 3; gridy = 0 })
    panel.add(JButton("Exit").apply {
        addActionListener {
            exitProcess(0)
        }
    }, gbc.apply { gridx = 3; gridy = 1 })

    newBoard()
    frame.apply {
        defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        setSize(400, 400)
        layout = BorderLayout()
        isResizable = false
        add(gridPanel, BorderLayout.CENTER)
        add(panel, BorderLayout.SOUTH)
        isVisible = true
        rootPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke('r'), 'r')
        rootPane.actionMap.put('r', object : AbstractAction() {
            override fun actionPerformed(e: ActionEvent?) {
                newBoard()
            }
        })
    }
}

fun move(move: Move, key: Char, frame: JFrame) = JButton("$move ($key)").apply {
    addActionListener {
        move(move)
    }
    frame.rootPane.apply {
        getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(key), key)
        actionMap.put(key, object : AbstractAction() {
            override fun actionPerformed(e: ActionEvent?) {
                move(move)
            }
        })
    }
}

fun move(move: Move) {
    if (!board.win && !board.lose) {
        board.move(move)
        update()
    }
}

fun update() {
    tiles.forEachIndexed({ i, row ->
        row.forEachIndexed { j, label ->
            label.text = board.cells[i][j].formatted
        }
    })
    scoreLabel.text = "Score: ${board.score}"
    resultLabel.text = when {
        board.win -> "YOU WON!"
        board.lose -> "YOU LOST!"
        else -> ""
    }
}

fun newBoard() {
    board = Board(4)
    board.spawn()
    board.spawn()
    update()
}
