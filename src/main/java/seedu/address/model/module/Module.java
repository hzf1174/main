package seedu.address.model;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

public class Module {

    // Information fields
    private final String code;
    private final String department;
    private final String title;
    private final String description;
    private final int credit;
    private final boolean isAvailableInSem1;
    private final boolean isAvailableInSem2;
    private final boolean isAvailableInSpecialTerm1;
    private final boolean isAvailableInSpecialTerm2;

    /**
     * Every field must be present and not null.
     */
    public Module(String code, String department, String title, String description, int credit, boolean isAvailableInSem1, boolean isAvailableInSem2, boolean isAvailableInSpecialTerm1, boolean isAvailableInSpecialTerm2) {
        requireAllNonNull(code, department, title, description, credit, isAvailableInSem1,
                isAvailableInSem2, isAvailableInSpecialTerm1, isAvailableInSpecialTerm2);
        this.code = code;
        this.department = department;
        this.title = title;
        this.description = description;
        this.credit = credit;
        this.isAvailableInSem1 = isAvailableInSem1;
        this.isAvailableInSem2 = isAvailableInSem2;
        this.isAvailableInSpecialTerm1 = isAvailableInSpecialTerm1;
        this.isAvailableInSpecialTerm2 = isAvailableInSpecialTerm2;
    }

    public String getCode() {
        return code;
    }

    public String getDepartment() {
        return department;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getCredit() {
        return credit;
    }

    public boolean isAvailableInSem1() {
        return isAvailableInSem1;
    }

    public boolean isAvailableInSem2() {
        return isAvailableInSem2;
    }

    public boolean isAvailableInSpecialTerm1() {
        return isAvailableInSpecialTerm1;
    }

    public boolean isAvailableInSpecialTerm2() {
        return isAvailableInSpecialTerm2;
    }
}
