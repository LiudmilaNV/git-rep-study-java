/**
 * Java 1. HomeWork8
 *
 * @author Liudmila Volkova
 * @version dated Dec.19, 2017
 * @link https://github.com/LiudmilaNV/git-rep-study-java.git
 */

import java.util.Random;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.border.*;

class HomeWork8  extends JFrame {
    final String TITLE_OF_PROGRAM = "Guess the colors";
    final String TITLE_OF_BORDER = "Color for choice";
    final int WINDOW_SIZE = 630;
    final int WINDOW_DX = 7;
    final int WINDOW_DY = -85;
    final int FIELD_SIZE = 3;
    final int SHIFT_FIELD_X = 100;
    final int CELL_SIZE = (WINDOW_SIZE - SHIFT_FIELD_X*2) / FIELD_SIZE;
    final String BTN_INIT = "1. New game";
    final String BTN_READY = "2. READY TO PLAY";
    final String BTN_GIVE_UP = "3. GIVE UP";
    final String BTN_WIN = "3. Check";
    final String BTN_EXIT = "4. Exit";
    
    private int status = 0;    
    private Field field = new Field(FIELD_SIZE, CELL_SIZE);    
    
    public static void main(String[] args) {
        new HomeWork8();
    }
    HomeWork8() {
        setTitle(TITLE_OF_PROGRAM);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(WINDOW_SIZE + WINDOW_DX, WINDOW_SIZE + WINDOW_DY);
        setLocationRelativeTo(null); // to the center
        setResizable(false);
        
        int MAX_COLOR = Field.getMAX_COLOR();
        Color[] arrColors = Field.getArrColors();    
    
        JToggleButton[] btn_color = new JToggleButton[MAX_COLOR];
        Human human = new Human();
        Panel panel = new Panel();    
        ButtonGroup bg = new ButtonGroup();    
        JPanel boxToggleBtn = new JPanel();
        boxToggleBtn.setLayout(new FlowLayout());

        for (int i=0; i< MAX_COLOR; i++){
            btn_color[i] = new JToggleButton();
            btn_color[i].setText("Color");
            btn_color[i].setForeground(arrColors[i]);
            bg.add(btn_color[i]);
            boxToggleBtn.add(btn_color[i]);
            btn_color[i].setBackground(arrColors[i]);
        }
        boxToggleBtn.setBorder(new TitledBorder(TITLE_OF_BORDER));

        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                if (field.getGameOver()) JOptionPane.showMessageDialog(HomeWork8.this, "Game over");
                    else{ 
                        if (status == 2){
                            for (int i=0; i< MAX_COLOR; i++){
                                if (btn_color[i].isSelected() && e.getX()> SHIFT_FIELD_X && e.getX()< (CELL_SIZE * FIELD_SIZE + SHIFT_FIELD_X)
                                    && e.getY()< (CELL_SIZE * FIELD_SIZE)) {
                                    human.turn((e.getX() - SHIFT_FIELD_X)/CELL_SIZE, e.getY()/CELL_SIZE, field, i);
                                    panel.repaint();
                                    break;
                                }
                            }
                        } 
                    }
            }
        });
        JButton init = new JButton(BTN_INIT);
        init.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                status = 1;                
                field.init();
                field.setPaintMap(field.getModelMap());
                panel.repaint();
            }
        });
        JButton ready = new JButton(BTN_READY);
        ready.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (status == 1){
                    status = 2;                    
                    field.setPaintMap(field.getMainMap());
                    panel.repaint();
                }    
            }
        });
        JButton win = new JButton(BTN_WIN);
        win.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (status == 2){                                        
                    JOptionPane.showMessageDialog(HomeWork8.this, (field.checkWin() ? field.getMSG_WON(): field.getMSG_LOSS()));                    
                }
            }
        });
        JButton exit = new JButton(BTN_EXIT);
        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        JPanel panelBtn = new JPanel();
        panelBtn.setLayout(new GridLayout()); // for panel of buttons
        panelBtn.add(init);
        panelBtn.add(ready);
        panelBtn.add(win);
        panelBtn.add(exit);

        setLayout(new BorderLayout()); // for main window
        add(boxToggleBtn, BorderLayout.NORTH);
        add(panel, BorderLayout.CENTER);
        add(panelBtn, BorderLayout.SOUTH);
        //pack();        
        setVisible(true);
    }    

    class Panel extends JPanel { // for painting
        @Override
        public void paint(Graphics g) {
            super.paint(g);
            field.paint(g, SHIFT_FIELD_X);
        }
    }
}
class Field {
    private final int FIELD_SIZE;
    private final int CELL_SIZE;
    private final int EMPTY_DOT = -1;
    private final String MSG_WON = "CORRECT!";
    private final String MSG_LOSS = "WRONG! Try again.";
    private int[][] map;
    private Random random = new Random();
    private static int MAX_COLOR = 5;
    private static Color[] arrColors = new Color[]{Color.red, Color.blue, Color.green, Color.yellow, Color.white};
    private int[][] mapModel;
    private int[][] mapPaint;
    private Boolean statusGameOver = false;    
    
    Field(int field_size, int cell_size) {
        FIELD_SIZE = field_size;
        CELL_SIZE = cell_size;
        map = new int[FIELD_SIZE][FIELD_SIZE];
        mapModel = new int[FIELD_SIZE][FIELD_SIZE];
        mapPaint = map;        
        init();
    }
    void init() {
        statusGameOver = false;
        for (int i = 0; i < FIELD_SIZE; i++)
            for (int j = 0; j < FIELD_SIZE; j++){
                map[i][j] = EMPTY_DOT;
                mapModel[i][j] = random.nextInt(MAX_COLOR);
            }
    }
    int[][] getModelMap() {
        return mapModel;
    }
    int[][] getMainMap() {
        return map;
    }
    int[][] getPaintMap() {
        return mapPaint;
    }
    void setPaintMap(int[][] mapPaint) {
        this.mapPaint = mapPaint;
    }
    int getSize() { return FIELD_SIZE; }

    void setDot(int x, int y, int dot) {
        map[x][y] = dot;
    }
    int getDot(int x, int y) {
        return map[x][y];
    }
    int getEmptyDot() {
        return EMPTY_DOT;
    }
    String getMSG_WON() {
        return MSG_WON;
    }
    String getMSG_LOSS() {
        return MSG_LOSS;
    }
    static Color[] getArrColors() {
        return arrColors;
    }
    static int getMAX_COLOR() {
        return MAX_COLOR;
    }
    void setGameOver (boolean status){
        statusGameOver = status;
    }
    boolean getGameOver(){
        return statusGameOver;
    }        
    boolean isMapFull() {
        for (int i = 0; i < FIELD_SIZE; i++)
            for (int j = 0; j < FIELD_SIZE; j++)
                if (map[i][j] == EMPTY_DOT) return false;
        return true;
    }
    boolean checkWin() {        
        for (int i = 0; i < FIELD_SIZE; i++)
            for (int j = 0; j < FIELD_SIZE; j++)
                if (map[i][j] != mapModel[i][j]) return false;
            
        statusGameOver = true;
        return true;
    }
    boolean isCellValid(int x, int y) {
        if (x < 0 || y < 0 || x > FIELD_SIZE - 1 || y > FIELD_SIZE - 1) return false;
        if (map[x][y] == EMPTY_DOT) return true;
        return false;
    }
    public void paint(Graphics g, int shiftX) {
        g.setColor(Color.lightGray);
        for (int i = 0; i < FIELD_SIZE+1; i++) {
            g.drawLine(0 + shiftX, i*CELL_SIZE, FIELD_SIZE*CELL_SIZE + shiftX, i*CELL_SIZE);
            g.drawLine(i*CELL_SIZE + shiftX, 0, i*CELL_SIZE + shiftX, FIELD_SIZE*CELL_SIZE);
        }
        for (int y = 0; y < FIELD_SIZE; y++) {
            for (int x = 0; x < FIELD_SIZE; x++) {
                int dot = mapPaint[x][y];
                if (dot >= 0 && dot < MAX_COLOR) {
                    g.setColor(arrColors[dot]);
                    g.fillRect(x*CELL_SIZE + 1 + shiftX, y*CELL_SIZE + 1, CELL_SIZE - 1, CELL_SIZE - 1);
                }
            }
        }
    }
}
class Human {
    void turn(int x, int y, Field field, int dot) {
        if (field.getDot(x, y) == dot) field.setDot(x, y, field.getEmptyDot());
            else field.setDot(x, y, dot);

    }
}