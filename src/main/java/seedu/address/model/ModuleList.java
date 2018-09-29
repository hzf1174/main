package seedu.address.model;

import java.util.ArrayList;

import javafx.collections.ObservableList;

/**
 * Wraps all data relating to modules
 */
public class ModuleList implements ReadOnlyModuleList {

    private final ArrayList<Module> modules;

    public ModuleList() {
        modules = new ArrayList<>();
    }

    /**
     * Adds a module to the module list.
     */
    public void addModule(Module module) {
        modules.add(module);
    }

    @Override
    public ObservableList<Module> getModuleList() {
        return null;
    }
}
