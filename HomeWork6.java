/**
 * Java 1. HomeWork6
 *
 * @author Liudmila Volkova
 * @version dated Dec.10, 2017
 * @link https://github.com/LiudmilaNV/git-rep-study-java.git
 */
public class HomeWork6{
    public static void main(String[] args){
        Cat cat = new Cat();
        Dog dog = new Dog();
        
        cat.run(150);
        cat.swim(10);
        cat.jump(1);
        
        dog.run(150);
        dog.swim(10);
        dog.jump(1);
    }
}
interface IAnimal{    
    void run(int length);
    void swim(int length);
    void jump(int height);    
}

abstract class Animal implements IAnimal{
    protected int limitRun;
    protected int limitSwim;
    protected float limitJump;
    
    public void run(int length) {
        info("run: " + (length <= limitRun && length > 0));
    }
    public void swim(int length) {
        info("swim: " + (length <= limitSwim && length > 0));
    }
    public void jump(int height) {
        info("jump: " + (height <= limitJump && height > 0));
    }
    abstract void info(String text);
}
class Cat extends Animal {
    Cat(){
        limitRun = 200;
        limitSwim = 0;
        limitJump = 2;
    }
    @Override
    void info(String text){
        System.out.println("cat " + text);
    }
}
class Dog extends Animal {
    Dog(){
        limitRun = 500;
        limitSwim = 10;
        limitJump = (float) 0.5;
    }
    @Override
    void info(String text){
        System.out.println("dog " + text);
    }
}