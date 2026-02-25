import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class assignmentOne {
    Random random = new Random();
    ArrayList<Integer> tempArray = new ArrayList();
    ArrayList<Integer> tempArraySequel = new ArrayList();

    /**
     *Adds fixed amount of Integer from 1 to fixed size to an existing (declared) array of Integer.
     *
     * @param tempArray - declared array variable.
     */
    public void subTaskOne(ArrayList tempArray) {
        int i = 0;
        while (i < 15) {
            tempArray.add(i);
            i++;
        }
        System.out.println(tempArray);
    }

    /**
     *
     *
     * @param tempArray
     * @param tempArraySequel
     */
    public void subTaskTwo(ArrayList tempArray, ArrayList tempArraySequel){
        int i = 0;
        while (i < tempArray.size()) {
            if (i > 10) {
                break;
            }
            tempArraySequel.add(i);
            i++;
        }
        System.out.println(tempArraySequel);
    }

    public void subTaskThree(ArrayList<Integer> tempArray){
        int i = 0;
        while (i < tempArray.size()) {
            if (tempArray.get(i) < 11) {
                tempArray.remove(i);
                i--;
            }
            i++;
        }
        System.out.println(tempArray);
    }

    public void subTaskFour(ArrayList tempArray){
        List<Integer> tupleReplacement = new ArrayList<>(); //the same for given task and much easier
        for(int i = 0;i<tempArray.size();i++){
            tupleReplacement.add(random.nextInt(101));
        }
        for (int i = 0;i<tempArray.size();i++){
            int tempPlaceholder;
            if (tupleReplacement.get(i) >=50){
                tempPlaceholder = tupleReplacement.get(i) + 39;
            }
            else {
                tempPlaceholder = tupleReplacement.get(i) * 3;
            }
            tempArray.set(i,tempPlaceholder);
        }
        System.out.println(tempArray);

        for (int i = 0;i<tupleReplacement.size();i++){
            tempArray.add(tupleReplacement.get(tupleReplacement.size()-1-i));
        }
        System.out.println(tempArray);
    }

    public void subTaskFive () {

    }

    public void subTaskSix () {

    }

    public void subTaskSeven () {

    }
}
