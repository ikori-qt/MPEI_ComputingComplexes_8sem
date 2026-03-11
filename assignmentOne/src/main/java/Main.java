import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main{
    public static void main(){
        assignmentOne variable = new assignmentOne();
        ArrayList<Integer> tempArray = new ArrayList();
        ArrayList<Integer> tempArraySequel;
        ArrayList<Integer> rectangleDimensionOne = new ArrayList<>();
        ArrayList<Integer> rectangleDimensionTwo = new ArrayList<>();
        String message = "I like pickles";

        variable.subTaskOne(tempArray);
        tempArraySequel = variable.subTaskTwo(tempArray);
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

        ArrayList<String> emailList = new ArrayList<>(Arrays.asList("kidNamedFinger@cool.com", "dogWithAPillow@mpei.ru", "bestTeacherEver@mpei.ru", "littlesjeff1@gmail.com", "whyamidoingthis@yahoo.com", "mewocatZ@yandex.ru", "randomthrowaway78950@gmail.com"));
        variable.additionalTask(emailList, "@mpei.ru");
    }
}
