package stringen.logic;

import java.util.ArrayList;
import java.util.PriorityQueue;

import stringen.logic.requirements.YearPrerequisite;

public class LogicManager {

    public static final String OPERATOR_AND = " & ";
    public static final String OPERATOR_OR = " | ";
    public static ArrayList<Cohort> cohorts = new ArrayList<>();

    /**
     * Generates string for all cohorts.
     * @param cohorts Cohorts that were generated by <code>Generator</code>.
     * @return Output string.
     */
    public static String generateString(ArrayList<Cohort> cohorts, int originalNumberOfCohorts) {
        LogicManager.cohorts = cohorts;
        if (cohorts.size() == 1) {
            Cohort finalCohort = cohorts.get(0);
            finalCohort.removePureYearRequirements(originalNumberOfCohorts);
            return finalCohort.generateString();
        }
        ArrayList<CohortPair> distinctPairs = getDistinctCohortPairs(cohorts);
        PriorityQueue<CohortPair> cohortsInDecreasingSimilarities = sortCohortPairs(distinctPairs);
        ArrayList<Cohort> cohortsYetToBeProcessed = new ArrayList<>(cohorts);

        StringBuilder string = new StringBuilder();

        ArrayList<Cohort> newCohorts = new ArrayList<>();
        // process and extract similarities in pairs of cohorts that have similarities
        while (cohortsInDecreasingSimilarities.size() > 0) {
            CohortPair cohortPairWithMostSimilarities = cohortsInDecreasingSimilarities.poll();
            Cohort firstCohort = (Cohort) cohortPairWithMostSimilarities.getFirstElement();
            Cohort secondCohort = (Cohort) cohortPairWithMostSimilarities.getSecondElement();
            if (!cohortsYetToBeProcessed.contains(firstCohort) || !cohortsYetToBeProcessed.contains(secondCohort)) {
                continue;
            }
            Cohort newCohort = process(cohortPairWithMostSimilarities, cohorts);
            newCohorts.add(newCohort);
            string.append(newCohort.generateString());
            cohortsYetToBeProcessed.remove(firstCohort);
            cohortsYetToBeProcessed.remove(secondCohort);
        }

        newCohorts.addAll(cohortsYetToBeProcessed);
        for (Cohort cohort: newCohorts) {
            cohort.simplify(originalNumberOfCohorts);
        }
        return generateString(newCohorts, originalNumberOfCohorts);
    }

    /**
     * Creates all possible distinct pairs of cohorts.
     * @param cohorts All cohorts that were input by user.
     * @return List of pairs of cohorts.
     */
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

    /**
     * Calls #getDirectSimilarities() to obtain the similarities in each pair and sorts them in descending order based
     * on the number of similarities.
     * @param pairs Pairs of cohorts.
     * @return A PriorityQueue of pairs in decreasing order of number of similarities between each cohort in a pair.
     */
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

    /**
     * Gets the <code>OrGroup</code>s that contain whole numbered requirements that are common between the two cohorts in the pair.
     * @param pair A pair of cohorts.
     * @return List of whole <code>OrGroup</code>s that are common between the pair.
     */
    private static ArrayList<OrGroup> getDirectSimilarities(CohortPair pair) {
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

    /**
     * Generates the string for the pair. This is accomplished by creating a new dummy cohort to contain
     * <code>OrGroup</code>s of the two cohorts after extraction of similarities.
     * @param pair Pair of cohorts.
     * @return String for the combined cohorts.
     */
    private static Cohort process(CohortPair pair, ArrayList<Cohort> cohorts) {
        // first, remove the whole OrGroups that were identified previously
        ArrayList<OrGroup> identicalRequirements = pair.getSimilarities();
        Cohort firstCohort = (Cohort) pair.getFirstElement();
        Cohort secondCohort = (Cohort) pair.getSecondElement();
        firstCohort.removeOrGroups(identicalRequirements);
        secondCohort.removeOrGroups(identicalRequirements);
        firstCohort = simplifyCohort(firstCohort);
        secondCohort = simplifyCohort(secondCohort);


        ArrayList<OrGroup> firstCohortOrGroups = firstCohort.getOrGroups();
        ArrayList<OrGroup> secondCohortOrGroups = secondCohort.getOrGroups();
        OrGroupPair pairWithSimilarities = findEmbeddedAndGroup(getDistinctGroupPairs(firstCohortOrGroups,
                secondCohortOrGroups));;
        Cohort dummyCohort = createDummyCohort(firstCohort, secondCohort, pairWithSimilarities);

        if (identicalRequirements.size() != 0) {
            ArrayList<AndGroup> andGroupsForOrGroupToContainYearRequirements = new ArrayList<>();
            andGroupsForOrGroupToContainYearRequirements.add(firstCohort.getYearRequirementAsAndGroup());
            andGroupsForOrGroupToContainYearRequirements.add(secondCohort.getYearRequirementAsAndGroup());
            OrGroup orGroupOfYearRequirements = new OrGroup(andGroupsForOrGroupToContainYearRequirements);
            identicalRequirements.add(orGroupOfYearRequirements);

            AndGroup andGroup = new AndGroup(identicalRequirements);
            ArrayList<AndGroup> andGroupsForOrGroup = new ArrayList<>();
            andGroupsForOrGroup.add(andGroup);
            OrGroup orGroupForIdenticalRequirements = new OrGroup(andGroupsForOrGroup);
            dummyCohort.addOrGroup(orGroupForIdenticalRequirements);
        }
        // And with previously removed OrGroups
        return dummyCohort;
    }

    /**
     * Finds <code>AndGroup</code>s that are common between <code></code>OrGroupPair</code>s.
     * @param distinctOrGroupPairs Pairs of OrGroups
     * @return Biggest <code>OrGroupPair</code>s that have similarities
     */
    private static OrGroupPair findEmbeddedAndGroup(ArrayList<OrGroupPair> distinctOrGroupPairs) {
        PriorityQueue<OrGroupPair> sortedGroupsInDecreasingSimilarity =
                sortGroupPairs(distinctOrGroupPairs);
        return sortedGroupsInDecreasingSimilarity.poll();
    }

    /**
     * Collates <code>OrGroupPair</code>s that have similarities.
     * @param sortedPairs Pairs of <code>OrGroup</code>s in descending number of similarities.
     * @return List of <code>OrGroupPair</code>s that have similarities.
     */
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

    /**
     * First creates a new cohort with the pairs that have similarities and then adds in the remaining <code>OrGroup</code>s
     * that were not part of a pair.
     * @param firstCohort Cohort that contains the first element in each pair.
     * @param secondCohort Cohort that contains the second element in each pair.
     * @param pairWithSimilarities <code>OrGroupPair</code>s that have similarities.
     * @return New dummy cohort.
     */
    private static Cohort createDummyCohort(Cohort firstCohort, Cohort secondCohort, OrGroupPair pairWithSimilarities) {
        Cohort dummyCohort = new Cohort();
        dummyCohort = createNewCohort(firstCohort, secondCohort, pairWithSimilarities);

        OrGroup firstElement = (OrGroup) pairWithSimilarities.getFirstElement();
        OrGroup secondElement = (OrGroup) pairWithSimilarities.getSecondElement();
        ArrayList<AndGroup> andGroupsForRemainingOrGroups = new ArrayList<>();
        andGroupsForRemainingOrGroups.add(getRemainingOrGroups(firstCohort, firstElement));
        andGroupsForRemainingOrGroups.add(getRemainingOrGroups(secondCohort, secondElement));
        dummyCohort.addOrGroup(new OrGroup(andGroupsForRemainingOrGroups));
        return simplifyCohort(dummyCohort);
    }

    /**
     * Creates a new cohort to contain the new <code>OrGroup</code>s after similarities are extracted.
     * @param firstCohort First cohort in pair of cohorts.
     * @param secondCohort Second cohort in pair of cohorts.
     * @param pairWithSimilarities code>OrGroupPair</code>s that have similarities.
     * @return New dummy cohort.
     */
    private static Cohort createNewCohort(Cohort firstCohort, Cohort secondCohort,
                                          OrGroupPair pairWithSimilarities) {
        Cohort newDummyCohort = new Cohort();
        ArrayList<OrGroup> orGroupsForNewCohort = new ArrayList<>();
        orGroupsForNewCohort.addAll(createNewOrGroups(pairWithSimilarities, firstCohort, secondCohort));
        newDummyCohort.addOrGroups(orGroupsForNewCohort);
        return newDummyCohort;
    }

    /**
     * Creates a new <code>OrGroup</code> with extracted similarity and the remaining elements in the
     * <code>OrGroup</code> in each pair.
     * @param orGroupPair Pair of <code>OrGroup</code>s.
     * @param firstCohort Cohort that contains first <code>OrGroup</code> in pair.
     * @param secondCohort Cohort that contains second <code>OrGroup</code> in pair.
     * @return List of OrGroups created for this pair.
     */
    private static ArrayList<OrGroup> createNewOrGroups(OrGroupPair orGroupPair, Cohort firstCohort, Cohort secondCohort) {
        OrGroup firstGroup = (OrGroup) orGroupPair.getFirstElement();
        OrGroup secondGroup = (OrGroup) orGroupPair.getSecondElement();
        AndGroup similarity = orGroupPair.getSimilarity();
        boolean wholeAndGroupWasExtracted = firstGroup.getAndGroups().size() > 1 || secondGroup.getAndGroups().size() > 1;
        ArrayList<OrGroup> orGroups = new ArrayList<>();
        if (wholeAndGroupWasExtracted) {
            orGroups.add(createOrGroupForFullyExtractedGroup(firstGroup, secondGroup, similarity, firstCohort, secondCohort));
        } else {
            orGroups.addAll(createOrGroupsForPartiallyExtractedGroup(firstGroup, secondGroup, similarity, firstCohort, secondCohort));
        }
        return orGroups;
    }

    /**
     * Creates <code>OrGroup</code> if the entire <code>AndGroup</code> was extracted from the original <code>OrGroup</code>s.
     * @param firstGroup First <code>OrGroup</code>.
     * @param secondGroup Second <code>OrGroup</code>.
     * @param similarity Similar <code>AndGroup</code> between the pair.
     * @param firstCohort <code>Cohort</code> that contains the first <code>OrGroup</code>.
     * @param secondCohort <code>Cohort</code> that contains the second <code>OrGroup</code>.
     * @return New <code>OrGroup</code> after extracting similarity.
     */
    private static OrGroup createOrGroupForFullyExtractedGroup(OrGroup firstGroup, OrGroup secondGroup,
                                                               AndGroup similarity, Cohort firstCohort, Cohort secondCohort) {
        // [(a || b) && year1] || [(a || c) && year2] <=> (a) || [(b && year1) || (c && year2)] <=> a || (b && year1) || (c && year2)
        ArrayList<AndGroup> andGroupsForOrGroup = new ArrayList<>();
        andGroupsForOrGroup.add(similarity);
        andGroupsForOrGroup.add(firstGroup.getRemainingAndGroup(similarity, firstCohort));
        andGroupsForOrGroup.add(secondGroup.getRemainingAndGroup(similarity, secondCohort));
        return new OrGroup(andGroupsForOrGroup);
    }

    /**
     * Creates <code>OrGroup</code> if a partial <code>AndGroup</code> was extracted from the original
     * <code>AndGroup</code>s in <code>OrGroup</code>s. Both <code>OrGroup</code>s are assumed to only have one
     * <code>AndGroup</code>.
     * @param firstGroup First <code>OrGroup</code>.
     * @param secondGroup Second <code>OrGroup</code>.
     * @param similarity Similar <code>AndGroup</code> between the pair.
     * @param firstCohort <code>Cohort</code> that contains the first <code>OrGroup</code>.
     * @param secondCohort <code>Cohort</code> that contains the second <code>OrGroup</code>.
     * @return New <code>OrGroup</code> after extracting similarity.
     */
    private static ArrayList<OrGroup> createOrGroupsForPartiallyExtractedGroup(OrGroup firstGroup, OrGroup secondGroup,
                                                                               AndGroup similarity, Cohort firstCohort, Cohort secondCohort) {
        AndGroup remainingAndGroupInFirstGroup = firstGroup.getRemainingAndGroup(similarity, firstCohort);
        AndGroup remainingAndGroupInSecondGroup = secondGroup.getRemainingAndGroup(similarity, secondCohort);
        ArrayList<AndGroup> andGroupsForCohorts = new ArrayList<>();
        andGroupsForCohorts.add(new AndGroup(createGroups(similarity, remainingAndGroupInFirstGroup, remainingAndGroupInSecondGroup)));
        for (int i = 0; i < cohorts.size(); i++) {
            Cohort thisCohort = cohorts.get(i);
            if (!thisCohort.equals(firstCohort) && !thisCohort.equals(secondCohort)) {
                andGroupsForCohorts.add(thisCohort.getYearRequirementAsAndGroup());
            }
        }
        ArrayList<OrGroup> orGroups = new ArrayList<>();
        orGroups.add(new OrGroup(andGroupsForCohorts));
        return orGroups;
    }

    /**
     * Creates <code>OrGroup</code>s in the context of a partially extracted group.
     * @param similarity Similarity between two <code>OrGroup</code>s.
     * @param remainingAndGroupInFirstGroup The <code>AndGroup</code> of the remaining elements in the first <code>AndGroup</code>
     *                                      that similarity was extracted from.
     * @param remainingAndGroupInSecondGroup The <code>AndGroup</code> of the remaining elements in the second <code>AndGroup</code>
     *      *                                      that similarity was extracted from.
     * @return List of <code>OrGroup</code>s after extracting similarity.
     */
    private static ArrayList<OrGroup> createGroups(AndGroup similarity, AndGroup remainingAndGroupInFirstGroup,
                                                   AndGroup remainingAndGroupInSecondGroup) {
        ArrayList<OrGroup> orGroups = new ArrayList<>();

        // [(a && b) && year1] || [(a && c) && year2] <=> a && [(b && year1) || (c && year2)]
        ArrayList<AndGroup> remainingAndGroups = new ArrayList<>();
        remainingAndGroups.add(remainingAndGroupInFirstGroup);
        remainingAndGroups.add(remainingAndGroupInSecondGroup);
        OrGroup remainingAndGroupsAsOrGroup = new OrGroup(remainingAndGroups);

        ArrayList<AndGroup> similarities = new ArrayList<>();
        similarities.add(similarity);
        OrGroup similarityAsOrGroup = new OrGroup(similarities);

        orGroups.add(similarityAsOrGroup);
        orGroups.add(remainingAndGroupsAsOrGroup);
        return orGroups;
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

    /**
     * Simplifies individual cohorts by simplifying each OrGroup.
     * @param cohort Input cohort.
     * @return New cohort with simplified <code>OrGroup</code>s.
     */
    public static Cohort simplifyCohort(Cohort cohort) {
        ArrayList<OrGroup> orGroups = cohort.getOrGroups();
        Cohort newCohort = new Cohort();
        ArrayList<OrGroup> newOrGroups = new ArrayList<>();
        for (int i = 0; i < orGroups.size(); i++) {
            OrGroup orGroup = orGroups.get(i);
            if (orGroup instanceof SingleOrGroup && orGroup.isYearRequirement()) {
                YearPrerequisite yearPrerequisite = (YearPrerequisite) ((SingleOrGroup) orGroup).getRequirement();
                newCohort = new Cohort(yearPrerequisite.getStartYear(), yearPrerequisite.getEndYear());
            }
            OrGroup simplifiedOrGroup = simplifyOrGroup(orGroup);
            newOrGroups.add(simplifiedOrGroup);
        }
        newCohort.addOrGroups(newOrGroups);
        return newCohort;
    }

    /**
     * Simplifies an <code>OrGroup</code> by extracting common <code>AndGroup</code>s.
     * @param orGroup <code>OrGroup</code> to be simplified.
     * @return Simplified <code>OrGroup</code>.
     */
    public static OrGroup simplifyOrGroup(OrGroup orGroup) {
        ArrayList<AndGroup> andGroups = new ArrayList<>(orGroup.getAndGroups());
        ArrayList<AndGroupPair> distinctPairs = getDistinctAndGroupPairs(andGroups);

        // sort according to number of similar and groups
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
        while (pairWithMostSimilarities != null && pairWithMostSimilarities.getSimilarity().size() >= 1) {
            AndGroup firstAndGroup = (AndGroup) pairWithMostSimilarities.getFirstElement();
            AndGroup secondAndGroup = (AndGroup) pairWithMostSimilarities.getSecondElement();
            // if group has been processed before, skip
            if (!andGroups.contains(firstAndGroup) || !andGroups.contains(secondAndGroup)) {
                continue;
            } else {
                ArrayList<OrGroup> orGroupsForNewAndGroupToBeAdded = new ArrayList<>();
                // (a & b & c) || (a & c & d) <=> a & c & (b || d)
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

                andGroups.remove(firstAndGroup);
                andGroups.remove(secondAndGroup);

                pairWithMostSimilarities = sortedAndGroupPairs.poll();
            }
        }

        // add the remaining groups
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

    /**
     * Get the remaining groups that are left in the cohort.
     * @param cohort Cohort that contains <code>OrGroup</code>s.
     * @param orGroup <code>OrGroup</code> that was already processed.
     * @return <code>AndGroup</code> of the remaining <code>OrGroup</code>s in the form of an <code>OrGroup</code>.
     */
    public static AndGroup getRemainingOrGroups(Cohort cohort, OrGroup orGroup) {
        ArrayList<OrGroup> orGroupsFromCohort = cohort.getOrGroups();
        orGroupsFromCohort.remove(orGroup);
        return new AndGroup(orGroupsFromCohort);
    }


}
