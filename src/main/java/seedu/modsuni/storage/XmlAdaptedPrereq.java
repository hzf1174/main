package seedu.modsuni.storage;

import java.util.ArrayList;
import java.util.List;

import seedu.modsuni.commons.exceptions.IllegalValueException;
import seedu.modsuni.model.module.Code;
import seedu.modsuni.model.module.Prereq;

/**
 * JAXB-friendly version of the Prereq.
 */
public class XmlAdaptedPrereq {
    private List<String> and;
    private List<String> or;

    public XmlAdaptedPrereq() { }

    public List<String> getAnd() {
        return and;
    }

    public void setAnd(List<String> and) {
        this.and = and;
    }

    public List<String> getOr() {
        return or;
    }

    public void setOr(List<String> or) {
        this.or = or;
    }

    /**
     * Converts this jaxb-friendly adapted prereq object into the model's Module object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted prereq code
     */
    public Prereq toModelType() throws IllegalValueException {
        List<Code> andCodes = new ArrayList<>();
        List<Code> orCodes = new ArrayList<>();
        if (and != null) {
            for (String element : and) {
                if (!Code.isValidCode(element)) {
                    throw new IllegalValueException(Code.MESSAGE_CODE_CONSTRAINTS);
                }
                andCodes.add(new Code(element));
            }
        }
        if (or != null) {
            for (String element : or) {
                if (!Code.isValidCode(element)) {
                    throw new IllegalValueException(Code.MESSAGE_CODE_CONSTRAINTS);
                }
                orCodes.add(new Code(element));
            }
        }
        return new Prereq(andCodes, orCodes);
    }

    @Override
    public String toString() {
        String returnVal = "";
        if (and != null) {
            returnVal = "And: " + and.toString();
        }
        if (or != null) {
            returnVal += ", Or: " + or.toString();
        }
        return returnVal;
    }
}
