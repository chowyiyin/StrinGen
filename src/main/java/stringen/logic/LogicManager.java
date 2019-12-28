package stringen.logic;

import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.stream.Collectors;

public class LogicManager {

    public static final String OPERATOR_AND = " & ";
    public static final String OPERATOR_OR = " | ";

    public static String generateString(ArrayList<Cohort> cohorts) {
        ArrayList<CohortPair> distinctPairs = getDistinctCohortPairs(cohorts);
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
            Cohort newCohort = simplifyCohort(cohort);
            String cohortString = newCohort.generateString();
            string = new StringBuilder(StringGenerator.or(string.toString(), cohortString));
            cohortsYetToBeProcessed.remove(cohort);
        }

        return string.toString();
    }

    private static ArrayList<CohortPair> getDistinctCohortPairs(ArrayList<Cohort> cohorts) {
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
        Cohort dummyCohort = createDummyCohort(firstCohort, secondCohort, pairsWithSimilarities);
        return StringGenerator.and(StringGenerator.appendOrGroups(identicalRequirements),
                dummyCohort.generateString());
    }

    private static ArrayList<OrGroupPair> extractPairsWithSimilarities(PriorityQueue<OrGroupPair> sortedPairs) {
        OrGroupPair orGroupPair = sortedPairs.poll();
        ArrayList<OrGroupPair> pairsWithSimilarities = new ArrayList<>();
        while (orGroupPair != null) {
            if (orGroupPair.getSimilarity().size() >= 1) {
                pairsWithSimilarities.add(orGroupPair);
            }
            orGroupPair = sortedPairs.poll();
        }
        return pairsWithSimilarities;
    }

    private static Cohort createDummyCohort(Cohort firstCohort, Cohort secondCohort, ArrayList<OrGroupPair> pairsWithSimilarities) {
        Cohort dummyCohort = new Cohort();
        dummyCohort = createNewCohort(firstCohort, secondCohort, pairsWithSimilarities);

        ArrayList<OrGroup> firstElements = (ArrayList<OrGroup>) pairsWithSimilarities.stream().map(
                orGroupPair -> (OrGroup) orGroupPair.getFirstElement()).collect(Collectors.toList());
        ArrayList<OrGroup> secondElements = (ArrayList<OrGroup>) pairsWithSimilarities.stream().map(
                orGroupPair -> (OrGroup) orGroupPair.getSecondElement()).collect(Collectors.toList());
        dummyCohort.addOrGroup(getRemainingOrGroups(firstCohort, firstElements));
        dummyCohort.addOrGroup(getRemainingOrGroups(secondCohort, secondElements));
        return dummyCohort;
        //return simplifyCohort(dummyCohort);
    }

    public static Cohort simplifyCohort(Cohort cohort) {
        ArrayList<OrGroup> orGroups = cohort.getOrGroups();
        ArrayList<OrGroup> newOrGroups = new ArrayList<>();
        for (int i = 0; i < orGroups.size(); i++) {
            OrGroup orGroup = orGroups.get(i);
            newOrGroups.add(simplifyOrGroup(orGroup));
        }
        Cohort newCohort = new Cohort();
        newCohort.addOrGroups(newOrGroups);
        return newCohort;
    }

    public static OrGroup simplifyOrGroup(OrGroup orGroup) {
        ArrayList<AndGroup> andGroups = new ArrayList<>(orGroup.getAndGroups());
        ArrayList<AndGroupPair> distinctPairs = getDistinctAndGroupPairs(andGroups);
        PriorityQueue<AndGroupPair> sortedAndGroupPairs = new PriorityQueue<>(new AndPairComparator());
        for (int i = 0; i < distinctPairs.size(); i++) {
            AndGroupPair pair = distinctPairs.get(i);
            AndGroup firstAndGroup = (AndGroup) pair.getFirstElement();
            AndGroup secondAndGroup = (AndGroup) pair.getSecondElement();
            pair.setSimilarity(firstAndGroup.getEmbeddedAndGroup(secondAndGroup));
            sortedAndGroupPairs.add(pair);
        }

        ArrayList<AndGroup> andGroupsForNewOrGroup = new ArrayList<>();
        AndGroupPair pairWithMostSimilarities = sortedAndGroupPairs.poll();
        while (pairWithMostSimilarities != null) {
            AndGroup firstAndGroup = (AndGroup) pairWithMostSimilarities.getFirstElement();
            AndGroup secondAndGroup = (AndGroup) pairWithMostSimilarities.getSecondElement();
            if (!andGroups.contains(firstAndGroup) || !andGroups.contains(secondAndGroup)) {
                continue;
            } else {
                andGroups.remove(firstAndGroup);
                andGroups.remove(secondAndGroup);
                ArrayList<OrGroup> orGroupsForNewAndGroupToBeAdded = new ArrayList<>();

                AndGroup similarity = pairWithMostSimilarities.getSimilarity();
                ArrayList<AndGroup> andGroupsForSimilarityOrgroup = new ArrayList<>();
                andGroupsForSimilarityOrgroup.add(similarity);
                OrGroup similarityAsOrGroup = new OrGroup(andGroupsForSimilarityOrgroup);

                ArrayList<AndGroup> andGroupsForRemainingOrGroup = new ArrayList<>();
                andGroupsForRemainingOrGroup.add(firstAndGroup.getRemainingAndGroup(similarity));
                andGroupsForRemainingOrGroup.add(secondAndGroup.getRemainingAndGroup(similarity));
                OrGroup remainingOrGroup = new OrGroup(andGroupsForRemainingOrGroup);

                orGroupsForNewAndGroupToBeAdded.add(similarityAsOrGroup);
                orGroupsForNewAndGroupToBeAdded.add(remainingOrGroup);
                andGroupsForNewOrGroup.add(new AndGroup(orGroupsForNewAndGroupToBeAdded));

                pairWithMostSimilarities = sortedAndGroupPairs.poll();
            }
        }

        andGroupsForNewOrGroup.addAll(andGroups);
        return new OrGroup(andGroupsForNewOrGroup);
    }

    private static ArrayList<AndGroupPair> getDistinctAndGroupPairs(ArrayList<AndGroup> andGroups) {
        ArrayList<AndGroupPair> distinctPairs = new ArrayList<>();
        for (int i = 0; i < andGroups.size(); i++) {
            AndGroup firstGroup = andGroups.get(i);
            for (int j = i + 1; j < andGroups.size(); j++) {
                AndGroup secondGroup = andGroups.get(j);
                distinctPairs.add(new AndGroupPair(firstGroup, secondGroup));
            }
        }
        return distinctPairs;
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

    private static ArrayList<OrGroupPair> getSimilarities(ArrayList<OrGroupPair> distinctOrGroupPairs) {
        return findEmbeddedAndGroups(distinctOrGroupPairs);
    }

    private static Cohort createNewCohort(Cohort firstCohort, Cohort secondCohort,
                                            ArrayList<OrGroupPair> pairsWithSimilarities) {
        Cohort newDummyCohort = new Cohort();
        ArrayList<OrGroup> orGroupsForNewCohort = new ArrayList<>();
        for (int i = 0; i < pairsWithSimilarities.size(); i++) {
            OrGroupPair orGroupPair = pairsWithSimilarities.get(i);
            orGroupsForNewCohort.addAll(createNewOrGroups(orGroupPair, firstCohort, secondCohort));
        }
        newDummyCohort.addOrGroups(orGroupsForNewCohort);
        return newDummyCohort;
    }

    private static ArrayList<OrGroup> createNewOrGroups(OrGroupPair orGroupPair, Cohort firstCohort, Cohort secondCohort) {
        OrGroup firstGroup = (OrGroup) orGroupPair.getFirstElement();
        OrGroup secondGroup = (OrGroup) orGroupPair.getSecondElement();
        AndGroup similarity = orGroupPair.getSimilarity();
        boolean wholeAndGroupWasExtracted = firstGroup.getAndGroups().size() > 1;
        ArrayList<OrGroup> orGroups = new ArrayList<>();
        if (wholeAndGroupWasExtracted) {
            orGroups.add(createOrGroupForFullyExtractedGroup(firstGroup, secondGroup, similarity, firstCohort, secondCohort));
        } else {
            orGroups.addAll(createOrGroupsForPartiallyExtractedGroup(firstGroup, secondGroup, similarity, firstCohort, secondCohort));
        }
        return orGroups;
    }

    private static OrGroup createOrGroupForFullyExtractedGroup(OrGroup firstGroup, OrGroup secondGroup,
                                                                    AndGroup similarity, Cohort firstCohort, Cohort secondCohort) {
        ArrayList<AndGroup> andGroupsForOrGroup = new ArrayList<>();
        andGroupsForOrGroup.add(similarity);
        andGroupsForOrGroup.add(firstGroup.getRemainingAndGroup(similarity, firstCohort));
        andGroupsForOrGroup.add(secondGroup.getRemainingAndGroup(similarity, secondCohort));
        return new OrGroup(andGroupsForOrGroup);
    }

    private static ArrayList<OrGroup> createOrGroupsForPartiallyExtractedGroup(OrGroup firstGroup, OrGroup secondGroup,
                                                                   AndGroup similarity, Cohort firstCohort, Cohort secondCohort) {
        AndGroup remainingAndGroupInFirstGroup = firstGroup.getRemainingAndGroup(similarity, firstCohort);
        AndGroup remainingAndGroupInSecondGroup = secondGroup.getRemainingAndGroup(similarity, secondCohort);
        return createGroups(similarity, remainingAndGroupInFirstGroup, remainingAndGroupInSecondGroup);
    }

    private static ArrayList<OrGroup> createGroups(AndGroup similarity, AndGroup remainingAndGroupInFirstGroup,
                                                   AndGroup remainingAndGroupInSecondGroup) {
        ArrayList<AndGroup> remainingAndGroups = new ArrayList<>();
        remainingAndGroups.add(remainingAndGroupInFirstGroup);
        remainingAndGroups.add(remainingAndGroupInSecondGroup);

        ArrayList<OrGroup> orGroups = new ArrayList<>();

        ArrayList<AndGroup> similarities = new ArrayList<>();
        similarities.add(similarity);
        OrGroup similarityAsOrGroup = new OrGroup(new ArrayList<AndGroup>(similarities));
        OrGroup remainingAndGroupsAsOrGroup = new OrGroup(remainingAndGroups);

        orGroups.add(similarityAsOrGroup);
        orGroups.add(remainingAndGroupsAsOrGroup);
        return orGroups;
    }

    private static ArrayList<OrGroupPair> findEmbeddedAndGroups(ArrayList<OrGroupPair> distinctOrGroupPairs) {
        PriorityQueue<OrGroupPair> sortedGroupsInDecreasingSimilarity =
                sortGroupPairs(distinctOrGroupPairs);

        ArrayList<OrGroupPair> pairsWithSimilarities = extractPairsWithSimilarities(sortedGroupsInDecreasingSimilarity);
        return pairsWithSimilarities;
    }

    private static ArrayList<OrGroupPair> getDistinctGroupPairs(ArrayList<OrGroup> firstCohortOrGroups,
                                              ArrayList<OrGroup> secondCohortOrGroups) {
        ArrayList<OrGroupPair> distinctOrGroupPairs = new ArrayList<>();
        for (int i = 0; i < firstCohortOrGroups.size(); i++) {
            OrGroup firstCohortOrGroup = firstCohortOrGroups.get(i);
            for (int j = 0; j < secondCohortOrGroups.size(); j++) {
                OrGroup secondCohortOrGroup = secondCohortOrGroups.get(j);
                if (firstCohortOrGroup.isYearRequirement()|| secondCohortOrGroup.isYearRequirement()) {
                    continue;
                }
                OrGroupPair pair = new OrGroupPair(firstCohortOrGroup, secondCohortOrGroup);
                if (!distinctOrGroupPairs.contains(pair)) {
                    distinctOrGroupPairs.add(new OrGroupPair(firstCohortOrGroup, secondCohortOrGroup));
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
