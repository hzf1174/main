package seedu.address.storage;

import java.util.Objects;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.model.module.Module;

/**
 * JAXB-friendly version of Module.
 */
public class XmlAdaptedModule {

    @XmlElement(required = true)
    private String code;
    @XmlElement(required = true)
    private String department;
    @XmlElement(required = true)
    private String title;
    @XmlElement(required = true)
    private String description;
    @XmlElement(required = true)
    private int credit;
    @XmlElement(required = true)
    private boolean isAvailableInSem1;
    @XmlElement(required = true)
    private boolean isAvailableInSem2;
    @XmlElement(required = true)
    private boolean isAvailableInSpecialTerm1;
    @XmlElement(required = true)
    private boolean isAvailableInSpecialTerm2;

    /**
     * Constructs an XmlAdaptedModule.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedModule() {}

    /**
     * Constructs an {@code XmlAdaptedModule} with the given module details.
     */
    public XmlAdaptedModule(String code, String department, String title, String description, int credit,
            boolean isAvailableInSem1, boolean isAvailableInSem2, boolean isAvailableInSpecialTerm1,
            boolean isAvailableInSpecialTerm2) {
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

    /**
     * Converts a given Module into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedModule
     */
    public XmlAdaptedModule(Module source) {
        code = source.getCode();
        department = source.getDepartment();
        title = source.getTitle();
        description = source.getDescription();
        credit = source.getCredit();
        isAvailableInSem1 = source.isAvailableInSem1();
        isAvailableInSem2 = source.isAvailableInSem2();
        isAvailableInSpecialTerm1 = source.isAvailableInSpecialTerm1();
        isAvailableInSpecialTerm2 = source.isAvailableInSpecialTerm2();
    }

    /**
     * Converts this jaxb-friendly adapted module object into the model's Module object.
     */
    public Module toModelType() {
        return new Module(code, department, title, description, credit, isAvailableInSem1, isAvailableInSem2,
            isAvailableInSpecialTerm1, isAvailableInSpecialTerm2);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof XmlAdaptedPerson)) {
            return false;
        }
        XmlAdaptedModule otherModule = (XmlAdaptedModule) other;
        return Objects.equals(code, otherModule.code)
                && Objects.equals(department, otherModule.department)
                && Objects.equals(title, otherModule.title)
                && Objects.equals(department, otherModule.department)
                && Objects.equals(credit, otherModule.credit)
                && Objects.equals(isAvailableInSem1, otherModule.isAvailableInSem1)
                && Objects.equals(isAvailableInSem2, otherModule.isAvailableInSem2)
                && Objects.equals(isAvailableInSpecialTerm1, otherModule.isAvailableInSpecialTerm1)
                && Objects.equals(isAvailableInSpecialTerm2, otherModule.isAvailableInSpecialTerm2);
    }
}
