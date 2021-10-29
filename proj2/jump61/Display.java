package jump61;

import ucb.gui2.TopLevel;
import ucb.gui2.LayoutSpec;

import java.util.concurrent.ArrayBlockingQueue;

import static jump61.Side.*;

/** The GUI controller for jump61.  To require minimal change to textual
 *  interface, we adopt the strategy of converting GUI input (mouse clicks)
 *  into textual commands that are sent to the Game object through a
 *  a Writer.  The Game object need never know where its input is coming from.
 *  A Display is an Observer of Games and Boards so that it is notified when
 *  either changes.
 *  @author Yu
 */
class Display extends TopLevel implements View, CommandSource, Reporter {

    /** A new window with given TITLE displaying GAME, and using COMMANDWRITER
     *  to send commands to the current game. */
    Display(String title) {
        super(title, true);

        addMenuButton("Game->Quit", this::quit);
        addMenuButton("Game->New Game", this::newGame);

        _boardWidget = new BoardWidget(_commandQueue);
        add(_boardWidget, new LayoutSpec("y", 1, "width", 2));
        display(true);

        addMenuButton("Player->Auto Red", this::autoRed);
        addMenuButton("Player->Auto Blue", this::autoBlue);
        addMenuButton("Player->Manual Red", this::manualRed);
        addMenuButton("Player->Manual Blue", this::manualBlue);
        addMenuButton("Size->Set Size", this::setSize);
    }

    /** Response to "Quit" button click. */
    void quit(String dummy) {
        System.exit(0);
    }

    /** Response to "New Game" button click. */
    void newGame(String dummy) {
        _commandQueue.offer("new");
    }

    /** Response to "Size" button click. */
    void setSize(String dummy) {
        String n = getTextInput("SET SIZE", "SIZE", "", "");
        Board board = new Board(Integer.parseInt(n));
        this.update(board);
    }

    /** Response to "Auto Red" button click. */
    void autoRed(String dummy) {
        _commandQueue.offer("auto red");
    }
    /** Response to "Auto Blue" button click. */
    void autoBlue(String dummy) {
        _commandQueue.offer("auto blue");
    }

    /** Response to "manual red" button click. */
    void manualRed(String dummy) {
        _commandQueue.offer("manual red");
    }

    /** Response to "manual blue" button click. */
    void manualBlue(String dummy) {
        _commandQueue.offer("manual blue");
    }

    @Override
    public void update(Board board) {
        _boardWidget.update(board);
        pack();
        _boardWidget.repaint();
    }

    @Override
    public String getCommand(String ignored) {
        try {
            return _commandQueue.take();
        } catch (InterruptedException excp) {
            throw new Error("unexpected interrupt");
        }
    }

    @Override
    public void announceWin(Side side) {
        showMessage(String.format("%s wins!", side.toCapitalizedString()),
                    "Game Over", "information");
    }

    @Override
    public void announceMove(int row, int col) {
    }

    @Override
    public void msg(String format, Object... args) {
        showMessage(String.format(format, args), "", "information");
    }

    @Override
    public void err(String format, Object... args) {
        showMessage(String.format(format, args), "Error", "error");
    }

    /** Time interval in msec to wait after a board update. */
    static final long BOARD_UPDATE_INTERVAL = 50;

    /** The widget that displays the actual playing board. */
    private BoardWidget _boardWidget;

    /** Queue for commands going to the controlling Game. */
    private final ArrayBlockingQueue<String> _commandQueue =
        new ArrayBlockingQueue<>(5);

}
