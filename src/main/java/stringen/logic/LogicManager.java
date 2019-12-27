package stringen.logic;

import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.stream.Collectors;

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
            String cohortString = cohort.generateString();
            string = new StringBuilder(StringGenerator.or(string.toString(), cohortString));
            cohortsYetToBeProcessed.remove(cohort);
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
        ArrayList<OrGroup> identicalRequirements = pair.getSimilarities();
        Cohort firstCohort = (Cohort) pair.getFirstElement();
        Cohort secondCohort = (Cohort) pair.getSecondElement();
        firstCohort.removeOrGroups(identicalRequirements);
        secondCohort.removeOrGroups(identicalRequirements);

        ArrayList<OrGroup> firstCohortOrGroups = firstCohort.getOrGroups();
        ArrayList<OrGroup> secondCohortOrGroups = secondCohort.getOrGroups();
        ArrayList<OrGroupPair> pairsWithSimilarities = getSimilarities(getDistinctGroupPairs(firstCohortOrGroups, secondCohortOrGroups));;
        Cohort dummyCohort = new Cohort();
        boolean isFirstRound = true;
        while (pairsWithSimilarities.size() > 0) {
            if (isFirstRound) {
                dummyCohort = createNewCohort(firstCohort, secondCohort, pairsWithSimilarities);

                ArrayList<OrGroup> firstElements = (ArrayList<OrGroup>) pairsWithSimilarities.stream().map(
                        orGroupPair -> (OrGroup) orGroupPair.getFirstElement()).collect(Collectors.toList());
                ArrayList<OrGroup> secondElements = (ArrayList<OrGroup>) pairsWithSimilarities.stream().map(
                        orGroupPair -> (OrGroup) orGroupPair.getSecondElement()).collect(Collectors.toList());
                dummyCohort.addOrGroup(getRemainingOrGroups(firstCohort, firstElements));
                dummyCohort.addOrGroup(getRemainingOrGroups(secondCohort, secondElements));
            } else {
                dummyCohort = createNewCohort(pairsWithSimilarities);
                dummyCohort.addOrGroups(getRemainingOrGroups(pairsWithSimilarities, dummyCohort));
            }
            pairsWithSimilarities = getSimilarities(getDistinctGroupPairs(dummyCohort.getOrGroups()));
            isFirstRound = false;
        }
        return StringGenerator.and(StringGenerator.appendOrGroups(identicalRequirements),
                dummyCohort.generateString());
    }

    public static OrGroup getRemainingOrGroups(Cohort cohort, ArrayList<OrGroup> orGroups) {
        ArrayList<OrGroup> orGroupsFromCohort = cohort.getOrGroups();
        for (int i = 0; i < orGroups.size(); i++) {
            OrGroup orGroup = orGroups.get(i);
            orGroupsFromCohort.remove(orGroup);
        }
        ArrayList<AndGroup> andGroupsForOrGroup = new ArrayList<>();
        if (orGroupsFromCohort.size() == 1) {
            return new OrGroup();
        } else {
            andGroupsForOrGroup.add(new AndGroup(orGroupsFromCohort));
            return new OrGroup(andGroupsForOrGroup);
        }
    }

    public static ArrayList<OrGroup> getRemainingOrGroups(ArrayList<OrGroupPair> pairsWithSimilarities, Cohort cohort) {
        ArrayList<OrGroup> orGroups = cohort.getOrGroups();
        for (int i = 0; i < pairsWithSimilarities.size(); i++) {
            OrGroupPair pair = pairsWithSimilarities.get(i);
            OrGroup firstOrGroup = (OrGroup) pair.getFirstElement();
            OrGroup secondOrGroup = (OrGroup) pair.getSecondElement();
            orGroups.remove(firstOrGroup);
            orGroups.remove(secondOrGroup);
        }
        return orGroups;
    }


    private static ArrayList<OrGroupPair> getSimilarities(ArrayList<OrGroupPair> distinctOrGroupPairs) {
        return findEmbeddedAndGroups(distinctOrGroupPairs);
    }

    private static Cohort createNewCohort(Cohort firstCohort, Cohort secondCohort,
                                            ArrayList<OrGroupPair> pairsWithSimilarities) {
        Cohort newDummyCohort = new Cohort();
        ArrayList<OrGroup> orGroupsForNewCohort = new ArrayList<>();
        for (int i = 0; i < pairsWithSimilarities.size(); i++) {
            OrGroupPair orGroupPair = pairsWithSimilarities.get(i);
            orGroupsForNewCohort.add(createNewOrGroup(orGroupPair, firstCohort, secondCohort));
        }
        newDummyCohort.addOrGroups(orGroupsForNewCohort);
        return newDummyCohort;
    }

    private static Cohort createNewCohort(ArrayList<OrGroupPair> pairsWithSimilarities) {
        Cohort newDummyCohort = new Cohort();
        ArrayList<OrGroup> orGroupsForNewCohort = new ArrayList<>();
        for (int i = 0; i < pairsWithSimilarities.size(); i++) {
            OrGroupPair orGroupPair = pairsWithSimilarities.get(i);
            orGroupsForNewCohort.add(createNewOrGroup(orGroupPair));
        }
        newDummyCohort.addOrGroups(orGroupsForNewCohort);
        return newDummyCohort;
    }

    private static OrGroup createNewOrGroup(OrGroupPair orGroupPair) {
        OrGroup firstGroup = (OrGroup) orGroupPair.getFirstElement();
        OrGroup secondGroup = (OrGroup) orGroupPair.getSecondElement();
        AndGroup similarity = orGroupPair.getSimilarity();
        boolean wholeAndGroupWasExtracted = firstGroup.getAndGroups().size() > 1;
        if (wholeAndGroupWasExtracted) {
            return createOrGroupForFullyExtractedGroup(firstGroup, secondGroup, similarity);
        } else {
            return createOrGroupForPartiallyExtractedGroup(firstGroup, secondGroup, similarity);
        }
    }

    private static OrGroup createNewOrGroup(OrGroupPair orGroupPair, Cohort firstCohort, Cohort secondCohort) {
        OrGroup firstGroup = (OrGroup) orGroupPair.getFirstElement();
        OrGroup secondGroup = (OrGroup) orGroupPair.getSecondElement();
        AndGroup similarity = orGroupPair.getSimilarity();
        boolean wholeAndGroupWasExtracted = firstGroup.getAndGroups().size() > 1;
        if (wholeAndGroupWasExtracted) {
            return createOrGroupForFullyExtractedGroup(firstGroup, secondGroup, similarity, firstCohort, secondCohort);
        } else {
            return createOrGroupForPartiallyExtractedGroup(firstGroup, secondGroup, similarity, firstCohort, secondCohort);
        }
    }

    private static OrGroup createOrGroupForFullyExtractedGroup(OrGroup firstGroup, OrGroup secondGroup,
                                                             AndGroup similarity, Cohort firstCohort, Cohort secondCohort) {
        ArrayList<AndGroup> andGroupsForOrGroup = new ArrayList<>();
        andGroupsForOrGroup.add(similarity);
        andGroupsForOrGroup.add(firstGroup.getRemainingAndGroup(similarity, firstCohort));
        andGroupsForOrGroup.add(secondGroup.getRemainingAndGroup(similarity, secondCohort));
        return new OrGroup(andGroupsForOrGroup);
    }

    private static OrGroup createOrGroupForFullyExtractedGroup(OrGroup firstGroup, OrGroup secondGroup,
                                                              AndGroup similarity) {
        ArrayList<AndGroup> andGroupsForOrGroup = new ArrayList<>();
        andGroupsForOrGroup.add(similarity);
        andGroupsForOrGroup.add(firstGroup.getRemainingAndGroup(similarity));
        andGroupsForOrGroup.add(secondGroup.getRemainingAndGroup(similarity));
        return new OrGroup(andGroupsForOrGroup);
    }

    private static OrGroup createOrGroupForPartiallyExtractedGroup(OrGroup firstGroup, OrGroup secondGroup,
                                                                   AndGroup similarity, Cohort firstCohort, Cohort secondCohort) {
        ArrayList<AndGroup> andGroupsForOrGroup = new ArrayList<>();

        ArrayList<OrGroup> orGroupsForNewAndGroup = new ArrayList<>();
        orGroupsForNewAndGroup.addAll(similarity.getOrGroups());

        ArrayList<AndGroup> remainingAndGroups = new ArrayList<>();
        remainingAndGroups.add(firstGroup.getRemainingAndGroup(similarity, firstCohort));
        remainingAndGroups.add(secondGroup.getRemainingAndGroup(similarity, secondCohort));
        orGroupsForNewAndGroup.add(new OrGroup(remainingAndGroups));

        andGroupsForOrGroup.add(new AndGroup(orGroupsForNewAndGroup));
        return new OrGroup(andGroupsForOrGroup);
    }

    private static OrGroup createOrGroupForPartiallyExtractedGroup(OrGroup firstGroup, OrGroup secondGroup,
                                                                  AndGroup similarity) {
        ArrayList<AndGroup> andGroupsForOrGroup = new ArrayList<>();

        ArrayList<OrGroup> orGroupsForNewAndGroup = new ArrayList<>();
        orGroupsForNewAndGroup.addAll(similarity.getOrGroups());

        ArrayList<AndGroup> remainingAndGroups = new ArrayList<>();
        remainingAndGroups.add(firstGroup.getRemainingAndGroup(similarity));
        remainingAndGroups.add(secondGroup.getRemainingAndGroup(similarity));
        orGroupsForNewAndGroup.add(new OrGroup(remainingAndGroups));

        andGroupsForOrGroup.add(new AndGroup(orGroupsForNewAndGroup));
        return new OrGroup(andGroupsForOrGroup);
    }

    public static String concatenate(ArrayList<OrGroup> similarOrGroups, OrGroup similarAndGroupsCombined,
                                     Cohort firstCohort, Cohort secondCohort) {
        String similarOrGroupsString = StringGenerator.appendOrGroups(similarOrGroups);
        String similarAndGroupsCombinedString = similarAndGroupsCombined.generateString();
        return StringGenerator.and(StringGenerator.or(similarOrGroupsString, similarAndGroupsCombinedString),
                StringGenerator.or(firstCohort.generateString(), secondCohort.generateString()));
    }

    private static ArrayList<OrGroupPair> findEmbeddedAndGroups(ArrayList<OrGroupPair> distinctOrGroupPairs) {
        PriorityQueue<OrGroupPair> sortedGroupsInDecreasingSimilarity =
                sortGroupPairs(distinctOrGroupPairs);

        OrGroupPair orGroupPair = sortedGroupsInDecreasingSimilarity.poll();
        ArrayList<OrGroupPair> pairsWithSimilarities = new ArrayList<>();
        while (orGroupPair != null) {
            if (orGroupPair.getSimilarity().size() >= 1) {
                pairsWithSimilarities.add(orGroupPair);
            }
            orGroupPair = sortedGroupsInDecreasingSimilarity.poll();
        }
        return pairsWithSimilarities;
    }

    private static ArrayList<OrGroupPair> getDistinctGroupPairs(ArrayList<OrGroup> firstCohortOrGroups,
                                              ArrayList<OrGroup> secondCohortOrGroups) {
        ArrayList<OrGroupPair> distinctOrGroupPairs = new ArrayList<>();
        for (int i = 0; i < firstCohortOrGroups.size(); i++) {
            OrGroup firstCohortOrGroup = firstCohortOrGroups.get(i);
            for (int j = 0; j < secondCohortOrGroups.size(); j++) {
                OrGroup secondCohortOrGroup = secondCohortOrGroups.get(j);
                if (firstCohortOrGroup.containsYearRequirement() || secondCohortOrGroup.containsYearRequirement()) {
                    continue;
                }
                distinctOrGroupPairs.add(new OrGroupPair(firstCohortOrGroup, secondCohortOrGroup));
            }
        }
        return distinctOrGroupPairs;
    }

    private static ArrayList<OrGroupPair> getDistinctGroupPairs(ArrayList<OrGroup> orGroups) {
        ArrayList<OrGroupPair> distinctOrGroupPairs = new ArrayList<>();
        for (int i = 0; i < orGroups.size(); i++) {
            OrGroup firstOrGroup = orGroups.get(i);
            for (int j = 0; j < orGroups.size(); j++) {
                OrGroup secondOrGroup = orGroups.get(j);
                OrGroupPair pair = new OrGroupPair(firstOrGroup, secondOrGroup);
                if (!firstOrGroup.equals(secondOrGroup) && !distinctOrGroupPairs.contains(pair)) {
                    distinctOrGroupPairs.add(pair);
                }
            }
        }
        return distinctOrGroupPairs;
    }

    private static PriorityQueue<OrGroupPair> sortGroupPairs(ArrayList<OrGroupPair> pairs) {
        PriorityQueue<OrGroupPair> sortedGroups = new PriorityQueue<>(new OrPairComparator());
        for (int i = 0; i < pairs.size(); i++) {
            OrGroupPair pair = pairs.get(i);
            OrGroup firstGroup = (OrGroup) pair.getFirstElement();
            OrGroup secondGroup = (OrGroup) pair.getSecondElement();
            pair.setSimilarity(getBiggestEmbeddedAndGroup(firstGroup, secondGroup));
            sortedGroups.add(pair);
        }
        return sortedGroups;
    }

    private static AndGroup getBiggestEmbeddedAndGroup(OrGroup firstOrGroup, OrGroup secondOrGroup) {
        return firstOrGroup.getBiggestEmbeddedAndGroups(secondOrGroup);
    }
}
