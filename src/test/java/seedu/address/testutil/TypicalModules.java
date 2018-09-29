package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.module.Module;
import seedu.address.model.ModuleList;

/**
 * A utility class containing a list of {@code Module} objects to be used in tests.
 */
public class TypicalModules {

    public static final Module CS1010 = new ModuleBuilder().withCode("CS1010")
            .withDepartment("Computer Science").withTitle("Programming Methodology")
            .withDescription("This module introduces the fundamental "
                    + "concepts of problem solving by computing and programming using an imperative "
                    + "programming language. It is the first and foremost introductory course to computing. "
                    + "It is also the first part of a three-part series on introductory programming and "
                    + "problem solving by computing, which also includes CS1020 and CS2010. Topics covered "
                    + "include problem solving by computing, writing pseudo-codes, basic problem formulation "
                    + "and problem solving, program development, coding, testing and debugging, fundamental "
                    + "programming constructs (variables, types, expressions, assignments, functions, control"
                    + " structures, etc.), fundamental data structures: arrays, strings and structures, "
                    + "simple file processing, and basic recursion. This module is appropriate for SoC "
                    + "students.").withCredit(4).withIsAvailableInSem1(true).withIsAvailableInSem2(true)
            .withisAvailableInSpecialTerm1(false).withisAvailableInSpecialTerm2(false).build();

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
        return new ArrayList<>(Arrays.asList(CS1010));
    }

}
