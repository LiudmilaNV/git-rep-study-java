/**
 * Java 1. HomeWork6
 *
 * @author Liudmila Volkova
 * @version dated Dec.11, 2017
 * @link https://github.com/LiudmilaNV/git-rep-study-java.git
 */
import java.util.*;

class HomeWork6_TicTacToe {

    public static void main(String[] args) {
        //TicTacToe ticTacToe = new TicTacToe();
        TicTacToe ticTacToe = new TicTacToe(5, 4, 'x', 'o', '.');
        ticTacToe.play();
    }
}
interface ITicTacToe{
    final int SIZE = 3;
    final char DOT_X = 'x';
    final char DOT_O = 'o';
    final int WIN_SIZE = 3;
    final char DOT_EMPTY = '.';

    final String HUMAN_WON = "YOU WON!";
    final String AI_WON = "AI WON!";
    final String DRAW_MSG = "Sorry DRAW...";
    final String GAME_OVER_MSG = "GAME OVER.";

    void play();
    boolean checkWin(char dot);
}
class TicTacToe implements ITicTacToe{

    private int size;
    private char dot_x;
    private char dot_o;
    int win_size;
    private char dot_empty;

    private Scanner sc = new Scanner(System.in);

    private Map mapObject;
    //private Human human;
    private AI human;
    private AI ai;

    TicTacToe() {
        size = SIZE;
        dot_x = DOT_X;
        dot_o = DOT_O;
        win_size = WIN_SIZE;
        dot_empty = DOT_EMPTY;

        initToe();
    }
    TicTacToe(int size, int win_size, char dot_x, char dot_o, char dot_empty) {
        this.size = size;
        this.dot_x = dot_x;
        this.dot_o = dot_o;
        this.win_size = win_size;
        this.dot_empty = dot_empty;

        initToe();
    }
    private void initToe() {
        mapObject = new Map(size, dot_empty);
        mapObject.initMap();

        //human = new Human(dot_x);
        human = new AI(dot_x);
        ai = new AI(dot_o);
    }

    public void play() {
        while (true) {
            System.out.print(mapObject);
            System.out.println("");

            human.turn(mapObject);
            ai.turn(mapObject);
            if (checkWin(dot_x)) {
                System.out.println(HUMAN_WON);
                break;
            }
            if (mapObject.isMapFull()) {
                System.out.println(DRAW_MSG);
                break;
            }
            ai.turn(mapObject, this, dot_x);
            if (checkWin(dot_o)) {
                System.out.println(AI_WON);
                break;
            }
            if (mapObject.isMapFull()) {
                System.out.println(DRAW_MSG);
                break;
            }
        }
        System.out.println(GAME_OVER_MSG);
        System.out.print(mapObject);
        System.out.println("");
    }

    public boolean checkWin(char dot) {
        int[] numOfRep_diagonal = new int[4];
        int numOfRep_h;
        int numOfRep_v;
        boolean result = false;
        int max_shift = size - win_size;

        for (int i = 0, shift = 0; !result && i < size; i++, shift++){
            numOfRep_h = 0;
            numOfRep_v = 0;
            numOfRep_diagonal[0] = 0;
            numOfRep_diagonal[1] = 0;
            numOfRep_diagonal[2] = 0;
            numOfRep_diagonal[3] = 0;

            for (int j = 0; !result && j < size; j++){
                if (shift <= max_shift && j < size - shift){
                    if (mapObject.getDot(j, j+shift) == dot) numOfRep_diagonal[0]++; //diagonal left up
                        else numOfRep_diagonal[0] = 0;
                    if (mapObject.getDot(j, size - 1 - j - shift) == dot) numOfRep_diagonal[1]++; //diagonal right up
                        else numOfRep_diagonal[1] = 0;
                    if (shift>0){
                        if (mapObject.getDot(j+shift, j) == dot) numOfRep_diagonal[2]++; //diagonal left douwn
                            else numOfRep_diagonal[2] = 0;
                        if (mapObject.getDot(j+shift, size - 1 - j) == dot) numOfRep_diagonal[3]++; //diagonal right douwn
                            else numOfRep_diagonal[3] = 0;
                    }
                }
                if (mapObject.getDot(i, j) == dot) numOfRep_h++; //horizontal
                    else numOfRep_h = 0;
                if (mapObject.getDot(j, i) == dot) numOfRep_v++; //vertical
                    else numOfRep_v = 0;

                if (numOfRep_diagonal[0] == win_size || numOfRep_diagonal[1] == win_size || numOfRep_diagonal[2] == win_size || numOfRep_diagonal[3] == win_size || numOfRep_h == win_size || numOfRep_v == win_size)
                    result = true;
            }
        }
        return (result);
    }
}
abstract class Player{
    protected char dot;

    Player(char dot){
        this.dot = dot;
    }
    char getDot() {
        return dot;
    }
    abstract void turn(Map map);
}
class Human extends Player{
    private Scanner sc = new Scanner(System.in);

    Human(char dot){
        super(dot);
    }
    @Override
    void turn(Map map) {
        int x, y;
        do {
            System.out.println("Enter X and Y (1.." + map.getSize() + "):");
            x = sc.nextInt() - 1;
            y = sc.nextInt() - 1;
        } while (!map.isCellValid(x, y));
        map.setDot(x, y, dot);
    }
}
class AI extends Player{

    private Scanner sc = new Scanner(System.in);
    private Random rand = new Random();

    AI(char dot){
        super(dot);
    }
    @Override
    void turn(Map map) {
        int x, y;
        do {
            x = rand.nextInt(map.getSize());
            y = rand.nextInt(map.getSize());
        } while (!map.isCellValid(x, y));
        map.setDot(x, y, dot);
    }
    void turn(Map map, TicTacToe toe, char enemyDot) {
        boolean block = false;

        for (int i = 0; !block && i < map.getSize(); i++){
            for (int j = 0; !block && j < map.getSize(); j++){
                if (map.isCellValid(i, j)) {
                    map.setDot(i, j, enemyDot);

                    if (toe.checkWin(enemyDot)) {
                        map.setDot(i, j, dot);
                        block = true;
                    }
                    else
                        map.setDot(i, j, map.getEmptyDot());
                }
            }
        }
        if (block) return;

        int x, y;
        do {
            x = rand.nextInt(map.getSize());
            y = rand.nextInt(map.getSize());
        } while (!map.isCellValid(x, y));
        map.setDot(x, y, dot);
    }
}

class Map{
    private final int SIZE = 5;
    private final char DOT_EMPTY = '.';

    private int size;
    private char dot_empty;
    private char[][] map;

    Map(){
        size = SIZE;
        dot_empty = DOT_EMPTY;
        map = new char[size][size];
        initMap();
    }
    Map(int size, char dot_empty){
        this.size = size;
        this.dot_empty = dot_empty;
        map = new char[size][size];
        initMap();
    }
    char[][] map(){
        return map;
    }
    void initMap() {
        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++)
                map[i][j] = dot_empty;
    }
    int getSize() {
        return size;
    }

    void setDot(int x, int y, char dot) {
        map[x][y] = dot;
    }

    char getDot(int x, int y) {
        return map[x][y];
    }
    char getEmptyDot() {
        return dot_empty;
    }    
    @Override
    public String toString() {
        String result = "";
        for (int y = 0; y < size; y++) {
            for (int x = 0; x < size; x++)
                result += map[x][y] + " ";
            result += "\n";
        }
        return result;
    }
    boolean isMapFull() {
        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++)
                if (map[i][j] == dot_empty)
                    return false;
        return true;
    }
    boolean isCellValid(int x, int y) {
        if (x < 0 || y < 0 || x >= size || y >= size)
            return false;
        if (map[x][y] == dot_empty)
            return true;
        return false;
    }
}