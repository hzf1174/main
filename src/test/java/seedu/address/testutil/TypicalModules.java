package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.Module;
import seedu.address.model.ModuleList;

/**
 * A utility class containing a list of {@code Module} objects to be used in tests.
 */
public class TypicalModules {



    private TypicalModules() {} // prevents instantiation

    /**
     * Returns an {@code ModuleList} with all the typical modules.
     */
    public static ModuleList getTypicalModuleList() {
        ModuleList moduleList = new ModuleList();
        for (Module module : getTypicalModules()) {
            moduleList.addModule(module);
        }
        return moduleList;
    }

    public static List<Module> getTypicalModules() {
        return new ArrayList<>(Arrays.asList());
    }

}
