package stringen.ui;

import java.util.ArrayList;
import java.util.HashMap;

import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import stringen.logic.AndGroup;
import stringen.logic.Cohort;
import stringen.logic.LogicManager;
import stringen.logic.OrGroup;
import stringen.logic.SingleOrGroup;
import stringen.logic.requirements.Requirement;
import stringen.logic.requirements.RequirementList;
import stringen.ui.exceptions.InvalidInputException;

public class Generator {

    public String generateString(ArrayList<EntryWindow> entryWindows) throws InvalidInputException {
        ArrayList<Cohort> cohorts = new ArrayList<>();
        for (EntryWindow entryWindow: entryWindows) {
            Cohort cohort = getCohort(entryWindow);
            ArrayList<OrGroup> orGroups = createGroups(entryWindow);
            cohort.addOrGroups(orGroups);
            cohorts.add(cohort);
        }
        return LogicManager.generateString(cohorts);
    }

    public Cohort getCohort(EntryWindow entryWindow) {
        ListView<HBox> listContent = entryWindow.getListContent();
        CohortListCard cohortCard = (CohortListCard) listContent.getItems().get(1);
        // TODO: IMPLEMENT ISPREREQUIISTE!!!!
        return new Cohort(cohortCard.getStartYear(), cohortCard.getEndYear(), true);
    }

    private ArrayList<OrGroup> createGroups(EntryWindow entryWindow) throws InvalidInputException {
        ListView<HBox> listContent = entryWindow.getListContent();
        ObservableList<HBox> items = listContent.getItems();
        int startingIndex = 0;
        while (startingIndex < items.size() && !isRequirementLine(items.get(startingIndex))) {
            startingIndex++;
        }
        int i = startingIndex + 1;
        ArrayList<OrGroup> orGroups = new ArrayList<>();

        boolean hasMoreRequirements = true;
        while (i < items.size() && hasMoreRequirements) {
            int endingIndexForRequirement = i + 1;
            while (endingIndexForRequirement < items.size()
                    && !((EntryFieldCard) items.get(endingIndexForRequirement)).isNewRequirement()) {
                endingIndexForRequirement++;
            }

            boolean isLastRequirement = endingIndexForRequirement == i + 1;
            if (isLastRequirement) {
                hasMoreRequirements = false;
            }
            OrGroup orGroup = createRequirementGroup(i, endingIndexForRequirement, items);
            if (orGroup != null) {
                orGroups.add(orGroup);
            }
            i = endingIndexForRequirement;
        }
        return orGroups;
    }

    private OrGroup createRequirementGroup(int requirementHeaderIndex, int endingIndexForRequirement,
                                           ObservableList<HBox> items) throws InvalidInputException {
        ArrayList<ArrayList<OrGroup>> listOfOrGroups = new ArrayList<>();
        // create list of or groups to be grouped as and groups
        int i = requirementHeaderIndex;
        boolean isNewConjunction = true;

        while (i < endingIndexForRequirement) {
            EntryFieldCard entryFieldCard = (EntryFieldCard) items.get(i);
            OrGroup newOrGroup = createOrGroup(entryFieldCard);
            if (newOrGroup == null) {
                i++;
                continue;
            }
            if (isNewConjunction) {
                ArrayList<OrGroup> newOrGroupList = new ArrayList<>();
                newOrGroupList.add(newOrGroup);
                listOfOrGroups.add(newOrGroupList);
                isNewConjunction = false;
            } else {
                ArrayList<OrGroup> currentOrGroup = listOfOrGroups.get(listOfOrGroups.size() - 1);
                currentOrGroup.add(newOrGroup);
            }

            if (entryFieldCard.containsOrLabel()) {
                isNewConjunction = true;
            }

            i++;
        }

        ArrayList<ArrayList<OrGroup>> listOfOrGroupsAfterCombining = new ArrayList<>();
        for (int j = 0; j < listOfOrGroups.size(); j++) {
            ArrayList<OrGroup> group = listOfOrGroups.get(j);
            listOfOrGroupsAfterCombining.add(combineSinglesIntoLists(group));
        }

        ArrayList<AndGroup> andGroups = new ArrayList<>();
        for (int k = 0; k < listOfOrGroupsAfterCombining.size(); k++) {
            andGroups.add(new AndGroup(listOfOrGroups.get(k)));
        }
        if (andGroups.isEmpty()) {
            return null;
        }

        return new OrGroup(andGroups);
    }

    private OrGroup createOrGroup(EntryFieldCard entry) throws InvalidInputException {
        Requirement requirement = entry.getResponses();
        if (requirement == null) {
            return null;
        }
        return new SingleOrGroup(requirement);
    }

    private boolean isSingleRequirementList(OrGroup orGroup) {
        return orGroup instanceof SingleOrGroup &&
                ((SingleOrGroup) orGroup).getRequirement() instanceof RequirementList &&
                ((RequirementList)((SingleOrGroup) orGroup).getRequirement()).getRequirements().size() == 1;
    }

    public ArrayList<OrGroup> combineSinglesIntoLists(ArrayList<OrGroup> orGroups) {
        HashMap<String, ArrayList<RequirementList>> requirementListHashMap = new HashMap<>();
        ArrayList<OrGroup> singles = new ArrayList<>();
        for (int i = 0; i < orGroups.size(); i++) {
            OrGroup orGroup = orGroups.get(i);
            if (isSingleRequirementList(orGroup)) {
                RequirementList requirementList = ((RequirementList)((SingleOrGroup) orGroup).getRequirement());
                String prefix = requirementList.getPrefix();
                if (requirementListHashMap.containsKey(prefix)) {
                    requirementListHashMap.get(prefix).add(requirementList);
                } else {
                    ArrayList<RequirementList> requirementLists = new ArrayList<>();
                    requirementLists.add(requirementList);
                    requirementListHashMap.put(prefix, requirementLists);
                }
                singles.add(orGroup);
            }
        }
        orGroups.removeAll(singles);

        for (String prefix : requirementListHashMap.keySet()) {
            ArrayList<RequirementList> requirementLists = requirementListHashMap.get(prefix);
            ArrayList<Requirement> requirements = new ArrayList<>();
            for (int i = 0; i < requirementLists.size(); i++) {
                requirements.addAll(requirementLists.get(i).getRequirements());
            }
            orGroups.add(new SingleOrGroup(new RequirementList(requirements, requirements.size(), prefix)));
        }
        return orGroups;
    }

    private boolean isRequirementLine(HBox hBox) {
        if (hBox.getChildren().size() == 1) {
            if (hBox instanceof TitleListCard) {
                TitleListCard title = (TitleListCard) hBox;
                return title.isRequirementLine();
            }
        }
        return false;
    }

    private boolean isNewRequirement(HBox hBox) {
        if (hBox.getChildren().size() == 1) {
            return hBox.getChildren().get(0) instanceof Label;
        } else {
            return false;
        }
    }

}
