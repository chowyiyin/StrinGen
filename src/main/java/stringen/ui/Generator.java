package stringen.ui;

import java.util.ArrayList;
import java.util.stream.Collectors;

import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import stringen.logic.AndGroup;
import stringen.logic.Cohort;
import stringen.logic.LogicManager;
import stringen.logic.OrGroup;
import stringen.logic.SingleAndGroup;
import stringen.logic.SingleOrGroup;
import stringen.logic.prerequisites.MajorPreclusion;
import stringen.logic.prerequisites.MajorPrerequisite;
import stringen.logic.prerequisites.MajorRequirement;
import stringen.logic.prerequisites.MajorRequirementList;
import stringen.logic.prerequisites.ModulePreclusion;
import stringen.logic.prerequisites.ModulePrerequisite;
import stringen.logic.prerequisites.ModuleRequirement;
import stringen.logic.prerequisites.ModuleRequirementList;
import stringen.logic.prerequisites.Prerequisite;

public class Generator {

    public String generateString(ArrayList<EntryWindow> entryWindows) {
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
        return new Cohort(cohortCard.getStartYear(), cohortCard.getEndYear());
    }

    public String generateDetails(Module module) {
        //TODO IMPLEMENT
        return "This will be the generated details";
    }

    private ArrayList<OrGroup> createGroups(EntryWindow entryWindow) {
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

            orGroups.add(createRequirementGroup(i, endingIndexForRequirement, items));
            i = endingIndexForRequirement;
        }
        return orGroups;
    }

    private OrGroup createRequirementGroup(int requirementHeaderIndex, int endingIndexForRequirement,
                                           ObservableList<HBox> items) {
        ArrayList<ArrayList<OrGroup>> listOfOrGroups = new ArrayList<>();
        // create list of or groups to be grouped as and groups
        int i = requirementHeaderIndex;
        boolean isNewConjunction = true;

        while (i < endingIndexForRequirement) {
            EntryFieldCard entryFieldCard = (EntryFieldCard) items.get(i);
            if (isNewConjunction) {
                ArrayList<OrGroup> newOrGroupList = new ArrayList<>();
                newOrGroupList.add(createOrGroup(entryFieldCard));
                listOfOrGroups.add(newOrGroupList);
                isNewConjunction = false;
            } else {
                ArrayList<OrGroup> currentOrGroup = listOfOrGroups.get(listOfOrGroups.size() - 1);
                currentOrGroup.add(createOrGroup(entryFieldCard));
            }

            if (entryFieldCard.containsOrLabel()) {
                isNewConjunction = true;
            }

            i++;
        }

        ArrayList<ArrayList<OrGroup>> listOfOrGroupsAfterCombining = new ArrayList<>();
        for (int j = 0; j < listOfOrGroups.size(); j++) {
            ArrayList<OrGroup> group = listOfOrGroups.get(j);
            listOfOrGroupsAfterCombining.add(combineMajorOrModuleRequirements(group));
        }

        ArrayList<AndGroup> andGroups = new ArrayList<>();
        for (int k = 0; k < listOfOrGroupsAfterCombining.size(); k++) {
            andGroups.add(new AndGroup(listOfOrGroups.get(k)));
        }
        return new OrGroup(andGroups);
    }

    private OrGroup createOrGroup(EntryFieldCard entry) {
        ArrayList<? extends Prerequisite> requirements = entry.getResponses();
        if (requirements.size() == 1) {
            return new SingleOrGroup(requirements.get(0));
        } else {
            ArrayList<AndGroup> andGroups = new ArrayList<>();
            for (int i = 0; i < requirements.size(); i++) {
                andGroups.add(new SingleAndGroup(requirements.get(i)));
            }
            return new OrGroup(andGroups);
        }
    }

    private ArrayList<OrGroup> combineMajorOrModuleRequirements(
           ArrayList<OrGroup> orGroups) {
        if (hasMoreThanOneSingleMajorPrerequisite(orGroups)) {
            orGroups = combineMajorPrerequisites(orGroups);
        }
        if (hasMoreThanOneSingleMajorPreclusion(orGroups)) {
            orGroups = combineMajorPreclusions(orGroups);
        }
        if (hasMoreThanOneSingleModulePrerequisite(orGroups)) {
            orGroups = combineModulePrerequisites(orGroups);
        }
        if (hasMoreThanOneSingleModulePreclusion(orGroups)) {
            orGroups = combineModulePreclusions(orGroups);
        }
        return orGroups;
    }

    private boolean isSingleMajorRequirement(OrGroup orGroup) {
        return orGroup instanceof SingleOrGroup &&
                ((SingleOrGroup) orGroup).getPrerequisite() instanceof MajorRequirementList &&
                ((MajorRequirementList)((SingleOrGroup) orGroup).getPrerequisite())
                        .getMajorRequirements().size() == 1;
    }

    private boolean isSingleModuleRequirement(OrGroup orGroup) {
        return orGroup instanceof SingleOrGroup &&
                ((SingleOrGroup) orGroup).getPrerequisite() instanceof ModuleRequirementList &&
                ((ModuleRequirementList)((SingleOrGroup) orGroup).getPrerequisite())
                        .getModuleRequirements().size() == 1;
    }

    private boolean hasMoreThanOneSingleMajorPrerequisite(ArrayList<OrGroup> orGroups) {
        return orGroups.stream().filter(orGroup -> isSingleMajorRequirement(orGroup) &&
                ((MajorRequirementList)((SingleOrGroup) orGroup).getPrerequisite()).getPrefix()
                .equals(MajorPrerequisite.PREFIX))
                .collect(Collectors.toList()).size() > 1;
    }

    private boolean hasMoreThanOneSingleMajorPreclusion(ArrayList<OrGroup> orGroups) {
        return orGroups.stream().filter(orGroup -> isSingleMajorRequirement(orGroup) &&
                ((MajorRequirementList)((SingleOrGroup) orGroup).getPrerequisite()).getPrefix()
                        .equals(MajorPreclusion.PREFIX))
                .collect(Collectors.toList()).size() > 1;
    }

    private boolean hasMoreThanOneSingleModulePrerequisite(ArrayList<OrGroup> orGroups) {
        return orGroups.stream().filter(orGroup -> isSingleModuleRequirement(orGroup) &&
                ((ModuleRequirementList)((SingleOrGroup) orGroup).getPrerequisite()).getPrefix()
                        .equals(ModulePrerequisite.PREFIX))
                .collect(Collectors.toList()).size() > 1;
    }

    private boolean hasMoreThanOneSingleModulePreclusion(ArrayList<OrGroup> orGroups) {
        return orGroups.stream().filter(orGroup -> isSingleModuleRequirement(orGroup) &&
                ((ModuleRequirementList)((SingleOrGroup) orGroup).getPrerequisite()).getPrefix()
                        .equals(ModulePreclusion.PREFIX))
                .collect(Collectors.toList()).size() > 1;
    }

    private ArrayList<OrGroup> combineMajorPrerequisites(ArrayList<OrGroup> orGroups) {
        ArrayList<MajorRequirement> majorPrerequisites = new ArrayList<>();
        ArrayList<OrGroup> orGroupsToBeRemoved = new ArrayList<>();
        for (int i = 0; i < orGroups.size(); i++) {
            OrGroup orGroup = orGroups.get(i);
            if (isSingleMajorRequirement(orGroup)) {
                MajorRequirementList majorPrerequisiteList = (MajorRequirementList) ((SingleOrGroup) orGroup).getPrerequisite();
                majorPrerequisites.addAll(majorPrerequisiteList.getMajorRequirements());
                orGroupsToBeRemoved.add(orGroup);
            }
        }
        orGroups.removeAll(orGroupsToBeRemoved);
        MajorRequirementList newMajorPrerequisiteList = new MajorRequirementList(majorPrerequisites,
                majorPrerequisites.size(), MajorPrerequisite.PREFIX);
        orGroups.add(new SingleOrGroup(newMajorPrerequisiteList));
        return orGroups;
    }

    private ArrayList<OrGroup> combineMajorPreclusions(ArrayList<OrGroup> orGroups) {
        ArrayList<MajorRequirement> majorPreclusions = new ArrayList<>();
        ArrayList<OrGroup> orGroupsToBeRemoved = new ArrayList<>();
        for (int i = 0; i < orGroups.size(); i++) {
            OrGroup orGroup = orGroups.get(i);
            if (isSingleMajorRequirement(orGroup)) {
                MajorRequirementList majorPreclusionList = (MajorRequirementList) ((SingleOrGroup) orGroup).getPrerequisite();
                majorPreclusions.addAll(majorPreclusionList.getMajorRequirements());
                orGroupsToBeRemoved.add(orGroup);
            }
        }
        orGroups.removeAll(orGroupsToBeRemoved);
        MajorRequirementList newMajorPreclusionList = new MajorRequirementList(majorPreclusions,
                majorPreclusions.size(), MajorPreclusion.PREFIX);
        orGroups.add(new SingleOrGroup(newMajorPreclusionList));
        return orGroups;
    }

    private ArrayList<OrGroup> combineModulePrerequisites(ArrayList<OrGroup> orGroups) {
        ArrayList<ModuleRequirement> modulePrerequisites = new ArrayList<>();
        ArrayList<OrGroup> orGroupsToBeRemoved = new ArrayList<>();
        for (int i = 0; i < orGroups.size(); i++) {
            OrGroup orGroup = orGroups.get(i);
            if (isSingleModuleRequirement(orGroup)) {
                ModuleRequirementList modulePrerequisiteList = (ModuleRequirementList) ((SingleOrGroup) orGroup).getPrerequisite();
                modulePrerequisites.addAll(modulePrerequisiteList.getModuleRequirements());
                orGroupsToBeRemoved.add(orGroup);
            }
        }
        orGroups.removeAll(orGroupsToBeRemoved);
        ModuleRequirementList newModulePrerequisiteList = new ModuleRequirementList(modulePrerequisites,
                modulePrerequisites.size(), ModulePrerequisite.PREFIX);
        orGroups.add(new SingleOrGroup(newModulePrerequisiteList));
        return orGroups;
    }

    private ArrayList<OrGroup> combineModulePreclusions(ArrayList<OrGroup> orGroups) {
        ArrayList<ModuleRequirement> modulePreclusions = new ArrayList<>();
        ArrayList<OrGroup> orGroupsToBeRemoved = new ArrayList<>();
        for (int i = 0; i < orGroups.size(); i++) {
            OrGroup orGroup = orGroups.get(i);
            if (isSingleModuleRequirement(orGroup)) {
                ModuleRequirementList modulePreclusionList = (ModuleRequirementList) ((SingleOrGroup) orGroup).getPrerequisite();
                modulePreclusions.addAll(modulePreclusionList.getModuleRequirements());
                orGroupsToBeRemoved.add(orGroup);
            }
        }
        orGroups.removeAll(orGroupsToBeRemoved);
        ModuleRequirementList newModulePreclusionList = new ModuleRequirementList(modulePreclusions,
                modulePreclusions.size(), ModulePreclusion.PREFIX);
        orGroups.add(new SingleOrGroup(newModulePreclusionList));
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
