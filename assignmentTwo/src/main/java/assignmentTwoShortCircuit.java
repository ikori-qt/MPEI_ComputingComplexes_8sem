import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class assignmentTwoShortCircuit {
    Random random = new Random();
    private Logger logger = Logger.getLogger("anataNoOkaasan");


    private int decideShortCircuit() {
        int decision;
        decision = random.nextInt(3);
        return decision;
    }

    private int decideVoltageClass() {
        return random.nextInt(2);
    }

    private int selectRandomFromList(int size) {
        return random.nextInt(size);
    }

    private Double assignCurrentValue(int type, int voltage) {
        Double value = null;
        if (voltage == 220) { // =220
            if (type == 0) { value = random.nextDouble(900, 1200); }//1phase
            else if (type == 1) { value = random.nextDouble(1400, 1600);} //2phase
            else { value = random.nextDouble(1900, 2000); } //3phase
        } else if (voltage == 10) { // =10
            if (type == 0) { value = random.nextDouble(250,300); }//1phase
            else if (type == 1) { value = random.nextDouble(300,400);} //2phase
            else { value = random.nextDouble(450, 550); } //3phase
        }
        return value;
    }

    private void willItBeGoneOnItsOwn (String equipmentID) {
        if (random.nextDouble(10) < 3) { logger.log(Level.INFO, String.format("A Short Circuit in %s has gone on it's own", equipmentID)); }
        else {logger.log(Level.SEVERE, String.format("It seems the defences failed. %s is gone for good. Bad(", equipmentID)); }
        //logger thing and a stop
    }

    private boolean handleWorkings(assignmentTwoRelays theActualObject, Double tempCurrent) {
        if (theActualObject instanceof assignmentTwoProtectionCutoff) {
            assignmentTwoProtectionCutoff overCurrentRelay = (assignmentTwoProtectionCutoff) theActualObject;
            if (overCurrentRelay.worksInTheFirstPlace() && overCurrentRelay.canCutCurrent(tempCurrent)) {
                logger.log(Level.INFO, String.format("The relay protection %s situated on %s worked on %s. The Short Circuit has stopped", overCurrentRelay.getUniqueID(), overCurrentRelay.workedOn(), overCurrentRelay.protects()));
                return true;
            }
            else {logger.log(Level.WARNING, String.format(" The relay protection %s \"working\" setting wasn't enough on %s. Rerouting and trying to find a way....", overCurrentRelay.getUniqueID(), overCurrentRelay.protects())); return false;}
        }
        else if (theActualObject instanceof assignmentTwoProtectionMTZ) {
            assignmentTwoProtectionMTZ MTZRelay = (assignmentTwoProtectionMTZ) theActualObject;
            if (MTZRelay.worksInTheFirstPlace() && MTZRelay.canCutCurrent(tempCurrent)) {
                logger.log(Level.INFO, String.format("The relay protection %s situated on %s worked on %s. The Short Circuit has stopped", MTZRelay.getUniqueID(), MTZRelay.workedOn(), MTZRelay.protects()));
                return true;
            }
            else {logger.log(Level.WARNING, String.format(" The relay protection %s \"working\" setting wasn't enough on %s. Rerouting and trying to find a way....", MTZRelay.getUniqueID(), MTZRelay.protects())); return false;}
        }
        else {
            assignmentTwoProtectionGas GasThing = (assignmentTwoProtectionGas) theActualObject;
            if (GasThing.worksInTheFirstPlace() && GasThing.canCutCurrent(tempCurrent)) {
                logger.log(Level.INFO, String.format("The relay protection %s situated on %s worked on %s. The Short Circuit has stopped", GasThing.getUniqueID(), GasThing.workedOn(), GasThing.protects()));
                return true;
            }
            else {logger.log(Level.WARNING, String.format(" The relay protection %s \"working\" setting wasn't enough on %s. Rerouting and trying to find a way....", GasThing.getUniqueID(), GasThing.protects())); return false;}
        }
    }

    public void testEquipment (ArrayList<assignmentTwoLine> lines, ArrayList<assignmentTwoBus> buses, ArrayList<assignmentTwoTransformer> transformers, ArrayList<? extends assignmentTwoRelays> relays) {
        logger.log(Level.INFO, "||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||");
        int temp = decideVoltageClass();
        int voltage;
        if (temp == 1) { voltage = 10;}
        else { voltage = 220;}
        List<ArrayList<? extends assignmentTwoParentEquipment>> everythingExceptBreakers = Arrays.asList(lines, buses, transformers);
        int eqType = selectRandomFromList(everythingExceptBreakers.size());
        int eqName = selectRandomFromList(everythingExceptBreakers.get(eqType).size());
        Double tempCurrent = assignCurrentValue(decideShortCircuit(), voltage);
        boolean theChecker = false;
        logger.log(Level.INFO, String.format("The chosen equipment IS \"%s\"", everythingExceptBreakers.get(eqType).get(eqName).getUniqueID()));
        logger.log(Level.INFO, String.format("Current amount is %.1f", tempCurrent));

        if (everythingExceptBreakers.get(eqType).get(eqName) instanceof assignmentTwoBus) {
            logger.log(Level.INFO, "BUS CIRCUIT LOGIC IS UNIMPLEMENTED REASON GIVEN COMPLEXITY");
            /*
             this requires knowledge or where the power comes from, every breaker reliable for it
             and this requires the logic to be changed into a MUCH better one, but the one that requires a rewrite +
             more data that will make this an entire diploma project.

             The real way to implement this IS using a short circuit calculator. I've worked on it. I can see how this "bruteforce" method is better.
             */
        }
        else {
            String tempSelectedRelay = everythingExceptBreakers.get(eqType).get(eqName).getFirstResponseRelay(); //cannot call a method from children the moment their type is ambiguous
            assignmentTwoRelays theActualObject = relays.stream().filter(relay -> (relay.getUniqueID().equals(tempSelectedRelay))).findAny().get(); //magic to get it into a single variable
            theChecker = handleWorkings(theActualObject, tempCurrent);
            if (!theChecker) {
                logger.log(Level.INFO, "The reserve has kicked in!");
                ArrayList<String> tempHolder = everythingExceptBreakers.get(eqType).get(eqName).getSecondResponse();
                temp = 0;
                while (!theChecker && temp < tempHolder.size()) {
                    int requiredForSomeReasonCopy = temp;
                    theActualObject = relays.stream().filter(relay -> (relay.getUniqueID().equals(tempHolder.get(requiredForSomeReasonCopy)))).findAny().get();
                    theChecker = handleWorkings(theActualObject, tempCurrent);
                    temp++;
                }
            }

            if (!theChecker) {
                willItBeGoneOnItsOwn(everythingExceptBreakers.get(eqType).get(eqName).getUniqueID());
            }
        }
    }
}
