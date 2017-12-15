/**
 * Java 1. HomeWork7
 *
 * @author Liudmila Volkova
 * @version dated Dec.14, 2017
 * @link https://github.com/LiudmilaNV/git-rep-study-java.git
 */

import java.util.Random;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

class HomeWork7_MyWindow extends JFrame implements ActionListener{
    protected final String DEFAULT = "default";
    protected final String FEED = "feed";
    protected final String ADD_FOOD = "add_food";
    protected final int MAX_CATS = 5;
    protected final int MAX_APPETITE = 10;
    protected final int MAX_FOOD = 20;
    protected final int PORTION = 5;

    private Random random = new Random();
    private Cat[] cats = new Cat[MAX_CATS];
    private Plate plate = new Plate(MAX_FOOD);
    private JLabel jlbl_food;
    private JLabel[] jlbl_cats = new JLabel[MAX_CATS];

    public static void main(String[] args) {

        HomeWork7_MyWindow myWindow = new HomeWork7_MyWindow();
        myWindow.setLocationRelativeTo(null); //center
        myWindow.setVisible(true);
    }

    HomeWork7_MyWindow (){
        setTitle("Hungry cats");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        //setBounds(300, 300, 400, 400);
        JPanel panButton = new JPanel();
        JButton[] jbs = new JButton[3];

        jbs[0] = new JButton("Default");
        panButton.add(jbs[0]);
        jbs[0].setActionCommand(DEFAULT);
        jbs[0].addActionListener(this);

        jbs[1] = new JButton("Feed");
        panButton.add(jbs[1]);
        jbs[1].setActionCommand(FEED);
        jbs[1].addActionListener(this);

        jbs[2] = new JButton("Add Food");
        panButton.add(jbs[2]);
        jbs[2].setActionCommand(ADD_FOOD);
        jbs[2].addActionListener(this);

        add(panButton, BorderLayout.PAGE_START);

        // JPanel pan = new JPanel();
        // pan.setLayout(new BoxLayout(pan, BoxLayout.Y_AXIS));
        Box box = Box.createVerticalBox();

        jlbl_food = new JLabel();
        box.add(jlbl_food);
        box.add(Box.createVerticalStrut(10));

        for (int i = 0; i < cats.length; i++){
            cats[i] = new Cat("Cat " + i, random.nextInt(MAX_APPETITE+1));

            jlbl_cats[i] = new JLabel();
            box.add(jlbl_cats[i]);
            box.add(Box.createVerticalStrut(10));
        }
        add(box);
        updateMyWindow();
        pack();
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        if (DEFAULT.equals(command)) {
            defaultState();
        } else if (FEED.equals(command)) {
            feedCats();
        } else if (ADD_FOOD.equals(command)) {
            addFood();
        }
        updateMyWindow();
    }
    private void updateMyWindow(){
        jlbl_food.setText(plate.toString());

        for (int i = 0; i < cats.length; i++){
            jlbl_cats[i].setText(cats[i].toString());
        }
    }
    protected void feedCats(){
        for (int i = 0; i < cats.length; i++){
            cats[i].eat(plate);
        }
    }
    protected void addFood(){
        plate.addFood(PORTION);
    }
    protected void defaultState(){
        for (int i = 0; i < cats.length; i++){
            cats[i].setAppetite(random.nextInt(MAX_APPETITE+1));
        }
        plate.setFeed(MAX_FOOD);
    }
}
class Cat {
    private String name;
    private int appetite;
    private boolean fullness;

    Cat(String name, int appetite) {
        this.name = name;
        this.appetite = appetite;
        fullness = false;
    }
    void setAppetite(int appetite) {
        this.appetite = appetite;
        fullness = false;
    }
    void eat(Plate plate) {
        if (!fullness) fullness = plate.decreaseFood(appetite);
    }
    @Override
    public String toString(){
        return "Cat: " + name + "; appetite: " + appetite + "; fullness: " + fullness;
    }
}
class Plate {
    private int food;

    Plate(int food) {
        this.food = food;
    }
    int getFood(){
        return food;
    }
    void setFeed(int food) {
        this.food = food;
    }
    boolean decreaseFood(int food) {
        if (this.food - food >= 0){
            this.food -= food;
            return true;
        } else return false;
    }
    void addFood(int food){
        this.food += food;
    }
    @Override
    public String toString() {
        return "Plate: " + food;
    }
}
