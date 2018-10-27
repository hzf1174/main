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
import seedu.modsuni.commons.events.ui.JumpToListRequestEvent;
import seedu.modsuni.commons.events.ui.TakenModulePanelSelectionChangedEvent;
import seedu.modsuni.model.module.Module;

/**
 * Panel containing the taken list of modules.
 */
public class TakenModuleListPanel extends UiPart<Region> {
    private static final String FXML = "TakenModuleListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(TakenModuleListPanel.class);

    @FXML
    private ListView<Module> takenModuleListView;

    public TakenModuleListPanel(ObservableList<Module> takenModuleList) {
        super(FXML);
        setConnections(takenModuleList);
        registerAsAnEventHandler(this);
    }

    private void setConnections(ObservableList<Module> takenModuleList) {
        takenModuleListView.setItems(takenModuleList);
        takenModuleListView.setCellFactory(listView -> new TakenModuleListViewCell());
        setEventHandlerForSelectionChangeEvent();
    }

    private void setEventHandlerForSelectionChangeEvent() {
        takenModuleListView.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        logger.fine("Selection in taken module list panel changed to : '" + newValue + "'");
                        raise(new TakenModulePanelSelectionChangedEvent(newValue));
                    }
                });
    }

    /**
     * Scrolls to the {@code ModuleCard} at the {@code index} and selects it.
     */
    private void scrollTo(int index) {
        Platform.runLater(() -> {
            takenModuleListView.scrollTo(index);
            takenModuleListView.getSelectionModel().clearAndSelect(index);
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
    class TakenModuleListViewCell extends ListCell<Module> {
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
