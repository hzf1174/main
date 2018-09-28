package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.CommandHistory;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;

/**
 * Clears the address book.
 */
public class SaveCommand extends Command {

  public static final String COMMAND_WORD = "save";
  public static final String MESSAGE_SUCCESS = "Current module configuration has be saved!";


  @Override
  public CommandResult execute(Model model, CommandHistory history) {
    requireNonNull(model);
//    model.resetData(new AddressBook());
//    model.commitAddressBook();
    System.out.println("Savecommand.java executed");
    return new CommandResult(MESSAGE_SUCCESS);
  }
}
