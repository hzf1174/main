package seedu.modsuni.ui;

import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.modsuni.commons.core.LogsCenter;
import seedu.modsuni.commons.events.ui.DatabaseModulePanelSelectionChangedEvent;
import seedu.modsuni.commons.events.ui.JumpToListRequestEvent;
import seedu.modsuni.model.module.Module;

/**
 * Panel containing the database list of modules.
 */
public class DatabaseModuleListPanel extends UiPart<Region> {
    private static final String FXML = "DatabaseModuleListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(DatabaseModuleListPanel.class);

    @FXML
    private ListView<Module> databaseModuleListView;

    public DatabaseModuleListPanel(ObservableList<Module> databaseModuleList) {
        super(FXML);
        setConnections(databaseModuleList);
        registerAsAnEventHandler(this);
    }

    private void setConnections(ObservableList<Module> databaseModuleList) {
        databaseModuleListView.setItems(databaseModuleList);
        databaseModuleListView.setCellFactory(listView -> new DatabaseModuleListViewCell());
        setEventHandlerForSelectionChangeEvent();
    }

    private void setEventHandlerForSelectionChangeEvent() {
        databaseModuleListView.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        logger.fine("Selection in database module list panel changed to : '" + newValue + "'");
                        raise(new DatabaseModulePanelSelectionChangedEvent(newValue));
                    }
                });
    }

    /**
     * Scrolls to the {@code ModuleCard} at the {@code index} and selects it.
     */
    private void scrollTo(int index) {
        Platform.runLater(() -> {
            databaseModuleListView.scrollTo(index);
            databaseModuleListView.getSelectionModel().clearAndSelect(index);
        });
    }

    @Subscribe
    private void handleJumpToListRequestEvent(JumpToListRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        scrollTo(event.targetIndex);
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Module} using a {@code ModuleCard}.
     */
    class DatabaseModuleListViewCell extends ListCell<Module> {
        @Override
        protected void updateItem(Module module, boolean empty) {
            super.updateItem(module, empty);

            if (empty || module == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new ModuleCard(module, getIndex() + 1).getRoot());
            }
        }
    }

}
