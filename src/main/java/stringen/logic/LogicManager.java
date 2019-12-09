package stringen.logic;

import java.util.ArrayList;
import java.util.PriorityQueue;

public class LogicManager {

    public static final String OPERATOR_AND = " & ";
    public static final String OPERATOR_OR = " | ";

    public static String generateString(ArrayList<Cohort> cohorts) {
        ArrayList<CohortPair> distinctPairs = getDistinctPairs(cohorts);
        PriorityQueue<CohortPair> cohortsInDecreasingSimilarities = sortCohortPairs(distinctPairs);
        ArrayList<Cohort> cohortsYetToBeProcessed = cohorts;

        StringBuilder string = new StringBuilder();

        while (cohortsInDecreasingSimilarities.size() > 0) {
            CohortPair cohortPairWithMostSimilarities = cohortsInDecreasingSimilarities.poll();
            Cohort firstCohort = (Cohort) cohortPairWithMostSimilarities.getFirstElement();
            Cohort secondCohort = (Cohort) cohortPairWithMostSimilarities.getSecondElement();
            if (!cohortsYetToBeProcessed.contains(firstCohort) || !cohortsYetToBeProcessed.contains(secondCohort)) {
                continue;
            }
            string.append(process(cohortPairWithMostSimilarities));
            cohortsYetToBeProcessed.remove(firstCohort);
            cohortsYetToBeProcessed.remove(secondCohort);
        }

        while (cohortsYetToBeProcessed.size() > 0) {
            Cohort cohort = cohortsYetToBeProcessed.get(0);
            String cohortString = StringGenerator.appendOrGroups(cohort.getOrGroups());
            string = new StringBuilder(StringGenerator.or(string.toString(), cohortString));
        }

        return string.toString();
    }

    private static ArrayList<CohortPair> getDistinctPairs(ArrayList<Cohort> cohorts) {
        ArrayList<CohortPair> distinctPairs = new ArrayList<>();
        for (int i = 0; i < cohorts.size(); i++) {
            Cohort firstCohort = cohorts.get(i);
            for (int j = i + 1; j < cohorts.size(); j++) {
                Cohort secondCohort = cohorts.get(j);
                distinctPairs.add(new CohortPair(firstCohort, secondCohort));
            }
        }
        return distinctPairs;
    }

    private static PriorityQueue<CohortPair> sortCohortPairs(ArrayList<CohortPair> pairs) {
        PriorityQueue<CohortPair> sortedPairs = new PriorityQueue<>(
                new PairComparator());
        for (int i = 0; i < pairs.size(); i++) {
            CohortPair pair = pairs.get(i);
            pair.setSimilarities(getDirectSimilarities(pair));
            sortedPairs.add(pair);
        }
        return sortedPairs;
    }

    public static ArrayList<OrGroup> getDirectSimilarities(CohortPair pair) {
        Cohort firstCohort = (Cohort) pair.getFirstElement();
        Cohort secondCohort = (Cohort) pair.getSecondElement();
        ArrayList<OrGroup> firstCohortOrGroups = firstCohort.getOrGroups();
        ArrayList<OrGroup> similarities = new ArrayList<>();
        for (int i = 0; i < firstCohortOrGroups.size(); i++) {
            OrGroup orGroup = firstCohortOrGroups.get(i);
            if (secondCohort.getOrGroups().contains(orGroup)) {
                similarities.add(orGroup);
            }
        }
        return similarities;
    }

    private static String process(CohortPair pair) {
        ArrayList<OrGroup> similarOrGroups = pair.getSimilarities();
        Cohort firstCohort = (Cohort) pair.getFirstElement();
        Cohort secondCohort = (Cohort) pair.getSecondElement();
        firstCohort.removeOrGroups(similarOrGroups);
        secondCohort.removeOrGroups(similarOrGroups);

        OrGroupPair pairWithMostSimilarAndGroup =
                findMostSimilarEmbeddedAndGroups(firstCohort, secondCohort);
        ArrayList<AndGroup> similarAndGroups = pairWithMostSimilarAndGroup.getSimilarities();
        firstCohort.removeAndGroupsFromOrGroup((OrGroup) pairWithMostSimilarAndGroup.getFirstElement(),
                similarAndGroups);
        secondCohort.removeAndGroupsFromOrGroup((OrGroup) pairWithMostSimilarAndGroup.getSecondElement(),
                similarAndGroups);

        OrGroup similarAndGroupsCombined = new OrGroup(similarAndGroups);
        ArrayList<OrGroup> similarOrGroupsCombined = new ArrayList<>(similarOrGroups);
        similarOrGroupsCombined.add(similarAndGroupsCombined);

        ArrayList<OrGroup> remainingOrGroupsInFirstCohort = firstCohort.getOrGroups();
        remainingOrGroupsInFirstCohort.addAll(similarOrGroups);
        ArrayList<OrGroup> remainingOrGroupsInSecondCohort = secondCohort.getOrGroups();
        remainingOrGroupsInSecondCohort.addAll(similarOrGroups);

        return StringGenerator.or(StringGenerator.appendOrGroups(similarOrGroupsCombined),
                StringGenerator.appendOrGroups(remainingOrGroupsInFirstCohort),
                StringGenerator.appendOrGroups(remainingOrGroupsInSecondCohort));
    }

    private static OrGroupPair findMostSimilarEmbeddedAndGroups(Cohort firstCohort, Cohort secondCohort) {
        ArrayList<OrGroup> firstCohortOrGroups = firstCohort.getOrGroups();
        ArrayList<OrGroup> secondCohortOrGroups = secondCohort.getOrGroups();

        ArrayList<OrGroupPair> distinctOrGroupPairs =
                getDistinctGroupPairs(firstCohortOrGroups, secondCohortOrGroups);
        PriorityQueue<OrGroupPair> sortedGroupsInDecreasingSimilarity =
                sortGroupPairs(distinctOrGroupPairs);
        return sortedGroupsInDecreasingSimilarity.poll();
    }

    private static ArrayList<OrGroupPair> getDistinctGroupPairs(ArrayList<OrGroup> firstCohortOrGroups,
                                              ArrayList<OrGroup> secondCohortOrGroups) {
        ArrayList<OrGroupPair> distinctOrGroupPairs = new ArrayList<>();
        for (int i = 0; i < firstCohortOrGroups.size(); i++) {
            OrGroup firstCohortOrGroup = firstCohortOrGroups.get(i);
            for (int j = 0; j < secondCohortOrGroups.size(); j++) {
                OrGroup secondCohortOrGroup = secondCohortOrGroups.get(i);
                distinctOrGroupPairs.add(new OrGroupPair(firstCohortOrGroup, secondCohortOrGroup));
            }
        }
        return distinctOrGroupPairs;
    }

    private static PriorityQueue<OrGroupPair> sortGroupPairs(ArrayList<OrGroupPair> pairs) {
        PriorityQueue<OrGroupPair> sortedGroups = new PriorityQueue<>(new PairComparator());
        for (int i = 0; i < pairs.size(); i++) {
            OrGroupPair pair = pairs.get(i);
            OrGroup firstGroup = (OrGroup) pair.getFirstElement();
            OrGroup secondGroup = (OrGroup) pair.getSecondElement();
            pair.setSimilarities(getNumberOfEmbeddedSimilarities(firstGroup, secondGroup));
            sortedGroups.add(pair);
        }
        return sortedGroups;
    }

    private static ArrayList<AndGroup> getNumberOfEmbeddedSimilarities(OrGroup firstOrGroup, OrGroup secondOrGroup) {
        ArrayList<AndGroup> firstOrGroupAndGroups = firstOrGroup.getAndGroups();
        ArrayList<AndGroup> similarities = new ArrayList<>();
        for (int i = 0; i < firstOrGroupAndGroups.size(); i++) {
            AndGroup andGroup = firstOrGroupAndGroups.get(i);
            if (secondOrGroup.getAndGroups().contains(andGroup)) {
                similarities.add(andGroup);
            }
        }
        return similarities;
    }
}
