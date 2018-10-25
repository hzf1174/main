package seedu.modsuni.commons.events.ui;

import seedu.modsuni.commons.events.BaseEvent;
import seedu.modsuni.model.module.Module;

/**
 * Represents a selection change in the Module List Panel
 */
public class TakenModulePanelSelectionChangedEvent extends BaseEvent {


    private final Module newSelection;

    public TakenModulePanelSelectionChangedEvent(Module newSelection) {
        this.newSelection = newSelection;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

    public Module getNewSelection() {
        return newSelection;
    }
}
