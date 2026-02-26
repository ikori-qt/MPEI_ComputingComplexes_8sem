import java.util.ArrayList;

public class Main{
    public static void main(){
        assignmentOne variable = new assignmentOne();
        ArrayList<Integer> tempArray = new ArrayList();
        ArrayList<Integer> tempArraySequel = new ArrayList();
        ArrayList<Integer> rectangleDimensionOne = new ArrayList<>();
        ArrayList<Integer> rectangleDimensionTwo = new ArrayList<>();
        String message = "I like pickles";

        variable.subTaskOne(tempArray);
        variable.subTaskTwo(tempArray, tempArraySequel);
        variable.subTaskThree(tempArray);
        variable.subTaskFour(tempArray);

        final int size = 15;
        variable.randomValueAdder(variable.random, rectangleDimensionOne, "Integer", size, 101);
        variable.randomValueAdder(variable.random, rectangleDimensionTwo, "Integer", size, 51);

        variable.subTaskFive(rectangleDimensionOne, rectangleDimensionTwo);
        tempArray = variable.subTaskSix(0,70, 3);
        System.out.println(tempArray);

        String redacted = variable.subTaskSeven(message, "pickles", "*bleep*");
        System.out.println(redacted);
    }
}
