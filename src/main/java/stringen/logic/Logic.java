package stringen.logic;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.PriorityQueue;

import stringen.logic.prerequisites.Prerequisite;

public class Logic {
    public static final String OPERATOR_AND = " & ";
    public static final String OPERATOR_OR = " | ";

    public static String generateString(ArrayList<Cohort> cohorts) {
        PriorityQueue<Pair<Cohort, Cohort>> sortedCohortsWithMostSimilarities = compareCohorts(cohorts);
        ArrayList<Cohort> cohortsNotProcessed = new ArrayList<Cohort>(cohorts);
        StringBuilder string = new StringBuilder();
        while (sortedCohortsWithMostSimilarities.size() > 0) {
            if (string.length() != 0) {
                string.append(OPERATOR_OR);
            }
            Pair<Cohort, Cohort> mostSimilarCohort = sortedCohortsWithMostSimilarities.poll();
            String stringForCommonPair = generateStringForCommonPair(mostSimilarCohort);
            removeProcessedCohorts(sortedCohortsWithMostSimilarities, mostSimilarCohort);
            cohortsNotProcessed.remove(mostSimilarCohort.getFirstElement());
            cohortsNotProcessed.remove(mostSimilarCohort.getSecondElement());
            string.append(stringForCommonPair);
        }
        if (cohortsNotProcessed.size() != 0) {
            for (int i = 0; i < cohortsNotProcessed.size(); i++) {
                string.append(string.length() == 0 ? "" : OPERATOR_OR)
                        .append(cohortsNotProcessed.get(i).generateString());
            }
        }
        return string.toString();
    }

    private static PriorityQueue<Pair<Cohort, Cohort>> compareCohorts(ArrayList<Cohort> cohorts) {
        ArrayList<Pair<Cohort, Cohort>> distinctPairs = getAllDistinctPairs(cohorts);
        PriorityQueue<Pair<Cohort, Cohort>> sortedQueue = new PriorityQueue<>(new CohortPairComparator());
        for (int i = 0; i < distinctPairs.size(); i++) {
            Pair<Cohort, Cohort> pair = distinctPairs.get(i);
            compare(pair);
            sortedQueue.add(pair);
        }
        return sortedQueue;
    }

    private static ArrayList<Pair<Cohort, Cohort>> getAllDistinctPairs(ArrayList<Cohort> cohorts) {
        HashSet<Pair<Cohort, Cohort>> cohortPairs = new HashSet<>();
        for (int i = 0; i < cohorts.size(); i++) {
            for (int j = i + 1; j < cohorts.size(); j++) {
                cohortPairs.add(new Pair<>(cohorts.get(i), cohorts.get(j)));
            }
        }
        return new ArrayList<>(cohortPairs);
    }

    private static void compare(Pair<Cohort, Cohort> cohortPair) {
        Cohort firstCohort = cohortPair.getFirstElement();
        Cohort secondCohort = cohortPair.getSecondElement();
        ArrayList<Group> similarities = firstCohort.getSimilarities(secondCohort);
        cohortPair.setNumberOfSimilarities(similarities.size());
        cohortPair.setSimilarities(similarities);
    }

    private static String generateStringForCommonPair(Pair<Cohort, Cohort> cohortPair) {
        extractCommonForPair(cohortPair);
        Cohort firstCohort = cohortPair.getFirstElement();
        Cohort secondCohort = cohortPair.getSecondElement();
        StringBuilder string = new StringBuilder();
        string.append(cohortPair.generateString())
                .append(OPERATOR_AND)
                .append(StringGenerator.combine(firstCohort, secondCohort));
        return string.toString();
    }

    /**
     * Extracts the common prerequisites from both cohortPairs.
     * @param cohortPair Pair that has prerequisites in common.
     */
    private static void extractCommonForPair(Pair<Cohort, Cohort> cohortPair) {
        Cohort firstCohort = cohortPair.getFirstElement();
        Cohort secondCohort = cohortPair.getSecondElement();
        ArrayList<Group> similarities = cohortPair.getSimilarities();
        extractCommonForCohort(firstCohort, similarities);
        extractCommonForCohort(secondCohort, similarities);
    }

    private static void extractCommonForCohort(Cohort cohort, ArrayList<Group> toExtract) {
        for (int i = 0; i < toExtract.size(); i++) {
            cohort.extract(toExtract.get(i));
        }
    }

    private static void removeProcessedCohorts(PriorityQueue<Pair<Cohort, Cohort>> cohortPairs,
                                               Pair<Cohort, Cohort> removedCohorts) {
        PriorityQueue<Pair<Cohort, Cohort>> newQueue = new PriorityQueue<>(new CohortPairComparator());
        for (int i = 0; i < cohortPairs.size(); i++) {
            Pair<Cohort, Cohort> cohortInQueue = cohortPairs.poll();
            boolean doesNotContain = !removedCohorts.contains(cohortInQueue);
            if (doesNotContain) {
                newQueue.add(cohortInQueue);
            }
        }
    }

}
