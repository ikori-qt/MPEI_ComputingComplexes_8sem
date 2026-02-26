import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class assignmentOne {
    Random random = new Random();

    /**
     *
     * @param randomSeedObject
     * @param workingHorse
     * @param size
     * @param bound
     */
    public void randomValueAdder(Random randomSeedObject,ArrayList workingHorse, String type, int size, int bound){
        if (type == "Integer") {
            for (int i = 0; i < size; i++) {
                workingHorse.add(randomSeedObject.nextInt(bound));
            }
        } else if (type == "Double") {
            for (int i = 0; i < size; i++) {
                workingHorse.add(randomSeedObject.nextDouble(bound));
            }
        }
    }

    /**
     *Adds fixed amount of Integer from 1 to fixed size to an existing (declared) array of Integer.
     *
     * @param tempArray - declared array variable.
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

    /**
     *
     *
     * @param tempArray
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
     *
     * @param tempArray
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
     *
     * !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
     * @param dimensionOne
     * @param dimensionTwo
     */
    public void subTaskFive (ArrayList<Integer> dimensionOne, ArrayList<Integer> dimensionTwo) {
        double diagonal;
        ArrayList<Double> circumcircleArea = new ArrayList<>();
        for (int i = 0; i<dimensionOne.size(); i++) { //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
            diagonal = Math.pow((Math.pow(dimensionOne.get(i),2)+Math.pow(dimensionTwo.get(i),2)),0.5);
            circumcircleArea.add(Math.PI*Math.pow(diagonal,2)/4);
        }
        double averageTenPercentIncreased;
        double tempDouble = 0;
        for (Double each : circumcircleArea){
            tempDouble += each;
        }
        averageTenPercentIncreased = 1.1*tempDouble/circumcircleArea.size();
        int i = 0;
        ArrayList<Double> filteredCircumcircleArea = circumcircleArea.stream().filter(n -> n<averageTenPercentIncreased).collect(Collectors.toCollection(ArrayList::new));
        tempDouble=1;
        for (Double each : filteredCircumcircleArea){
            tempDouble *= each;
        }
        double answer = tempDouble/dimensionOne.size();
        System.out.println(answer);
    }

    /**
     *
     * @param lowerLimit
     * @param upperLimit
     * @param step
     * @return
     */
    public ArrayList subTaskSix (int lowerLimit, int upperLimit, int step) {
        ArrayList<Integer> numbersBetween = new ArrayList<>();
        numbersBetween.add(lowerLimit);
        int i = lowerLimit+step;
        while (i<upperLimit){
            numbersBetween.add(i);
            i += step;
        }
        ArrayList<Integer> amountOfSevens = numbersBetween.stream().filter(n -> n % 7 ==0).collect(Collectors.toCollection(ArrayList::new));
        int checkResult = amountOfSevens.size() - (upperLimit-lowerLimit);
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
     *
     * @param originalMessage
     * @param redactionTarget
     * @param censor
     * @return
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
