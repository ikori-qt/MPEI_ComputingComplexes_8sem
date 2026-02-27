import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class assignmentOne {
    Random random = new Random();

    /**
     * Adds defined amount of random numbers (int/double) in range of 0 to a bound (excluding bound).
     *
     * @param randomSeedObject - expects a Random object, defining the seed
     * @param workingHorse - ArrayList of Integer/Double that is being modified
     * @param size - amount of random numbers added
     * @param bound - upper limit of random generation
     */
    public void randomValueAdder(Random randomSeedObject,ArrayList workingHorse, String type, int size, int bound){
        if (type.equals("Integer")) {
            for (int i = 0; i < size; i++) {
                workingHorse.add(randomSeedObject.nextInt(bound));
            }
        } else if (type.equals("Double")) {
            for (int i = 0; i < size; i++) {
                workingHorse.add(randomSeedObject.nextDouble(bound));
            }
        }
    }

    /**
     *Adds fixed amount of Integer from 1 to fixed size to an existing (declared) array of Integer.
     *
     * @param tempArray - declared array variable
     */
    public void subTaskOne(ArrayList tempArray) {
        int i = 0;
        while (i < 16) {
            tempArray.add(i);
            i++;
        }
        System.out.println(tempArray);
    }

    /**
     * Based on the tempArray creates a second array counting from zero up to tempArray size or ten, if the size
     * exceeds ten.
     *
     * @param tempArray - blueprint Array upon which the second is created
     */
    public ArrayList<Integer> subTaskTwo(ArrayList tempArray){
        ArrayList<Integer> tempArraySequel = new ArrayList<>();
        int i = 0;
        while (i < tempArray.size()) {
            if (i > 10) {
                break;
            }
            tempArraySequel.add(i);
            i++;
        }
        System.out.println(tempArraySequel);
        return tempArraySequel;
    }

    /**
     * Removes all elements of tempArray lower than 11.
     *
     * @param tempArray - interacted array
     */
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

    /**
     * Creates a list (tupleReplacement) sized the same as tempArray of random int values. Based on generated
     * values (elements) performs a math transformation on each of them and puts those into the same tempArray's
     * element's index. Adds list's elements in the back in reverse.
     *
     * @param tempArray - interacting array
     */
    public void subTaskFour(ArrayList tempArray){
        List<Integer> tupleReplacement = new ArrayList<>(); //the same for given task and much easier
        for(int i = 0;i<tempArray.size();i++){
            tupleReplacement.add(random.nextInt(101));
        }
        System.out.println(tupleReplacement);
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

    /**
     * Requires two arrays that are the same size. Based on two arrays being length and width of a rectangle calculates
     * the diagonal to find the area of a circumcircle. Calculates the average of given areas. Creates a new array
     * that filters areas based on being lower than 1.1 of the average. Multiplies left areas and divides
     * the answer by their amount.
     *
     * @param dimensionOne - array of width/length
     * @param dimensionTwo - array of length/width
     */
    public void subTaskFive (ArrayList<Integer> dimensionOne, ArrayList<Integer> dimensionTwo) {
        double diagonal;
        ArrayList<Double> circumcircleArea = new ArrayList<>();
        for (int i = 0; i<dimensionOne.size(); i++) { // arrays must be the same size
            diagonal = Math.pow((Math.pow(dimensionOne.get(i),2)+Math.pow(dimensionTwo.get(i),2)),0.5);
            circumcircleArea.add(Math.PI*Math.pow(diagonal,2)/4);
        }
        double averageTenPercentIncreased;
        double tempDouble = 0;
        for (Double each : circumcircleArea){
            tempDouble += each;
        }
        averageTenPercentIncreased = 1.1*tempDouble/circumcircleArea.size();
        ArrayList<Double> filteredCircumcircleArea = circumcircleArea.stream().filter(n -> n<averageTenPercentIncreased).collect(Collectors.toCollection(ArrayList::new));
        tempDouble=1;
        for (Double each : filteredCircumcircleArea){
            tempDouble *= each;
        }
        double answer = tempDouble/dimensionOne.size();
        System.out.println(answer);
    }

    /**
     * Creates a new array of values in range of lower and upperLimit with a given step. Filters every number
     * divisible by seven into a new array.
     * If the amount of sevens is lower than the median between given limits, reverses the array.
     * Else adds this amount to the beginning of a new array containing this number and all generated numbers.
     *
     * @param lowerLimit - range beginning
     * @param upperLimit range end
     * @param step - step
     * @return - an ArrayList of either reversed numbers or amount of divisible by 7 elements + generated elements
     */
    public ArrayList subTaskSix (int lowerLimit, int upperLimit, int step) {
        ArrayList<Integer> numbersBetween = new ArrayList<>();
        numbersBetween.add(lowerLimit);
        int i = lowerLimit+step;
        while (i<upperLimit){
            numbersBetween.add(i);
            i += step;
        }
        ArrayList<Integer> everyNumberDivisibleBySeven = numbersBetween.stream().filter(n -> n % 7 ==0).collect(Collectors.toCollection(ArrayList::new));
        int checkResult = everyNumberDivisibleBySeven.size() - (upperLimit-lowerLimit);
        if (checkResult < 0) {
            Collections.reverse(numbersBetween);
            return numbersBetween;
        }
        else {
            ArrayList<Integer> newNumbersBetween = new ArrayList<>();
            newNumbersBetween.add(checkResult);
            for (Integer each : numbersBetween){
                newNumbersBetween.add(each);
            }
            return newNumbersBetween;
        }
    }

    /**
     * Replaces a chosen word with a censor using .substring method.
     *
     * @param originalMessage - an original String message
     * @param redactionTarget - a target String word
     * @param censor - a replacement String word
     * @return - redacted String
     */
    public String subTaskSeven (String originalMessage, String redactionTarget, String censor) {
        StringBuilder redacted = new StringBuilder(); //creates a class re-write a string
        int i = 0;
        while (i<(originalMessage.length() - redactionTarget.length())+1) {
            String lensCheck = originalMessage.substring(i, i+redactionTarget.length());
            if  (lensCheck.equals(redactionTarget)) {
                redacted.append(censor);
                i +=redactionTarget.length();
            }
            else {
                redacted.append(originalMessage.charAt(i));
                i++;
            }
        }
        return redacted.toString();
    }
}
