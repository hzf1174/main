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
import seedu.modsuni.commons.events.ui.StagedModulePanelSelectionChangedEvent;
import seedu.modsuni.model.module.Module;

/**
 * Panel containing the staged list of modules.
 */
public class StagedModuleListPanel extends UiPart<Region> {
    private static final String FXML = "StagedModuleListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(StagedModuleListPanel.class);

    @FXML
    private ListView<Module> stagedModuleListView;

    public StagedModuleListPanel(ObservableList<Module> stagedModuleList) {
        super(FXML);
        setConnections(stagedModuleList);
        registerAsAnEventHandler(this);
    }

    private void setConnections(ObservableList<Module> stagedModuleList) {
        stagedModuleListView.setItems(stagedModuleList);
        stagedModuleListView.setCellFactory(listView -> new StagedModuleListViewCell());
        setEventHandlerForSelectionChangeEvent();
    }

    private void setEventHandlerForSelectionChangeEvent() {
        stagedModuleListView.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        logger.fine("Selection in staged module list panel changed to : '" + newValue + "'");
                        raise(new StagedModulePanelSelectionChangedEvent(newValue));
                    }
                });
    }

    /**
     * Scrolls to the {@code ModuleCard} at the {@code index} and selects it.
     */
    private void scrollTo(int index) {
        Platform.runLater(() -> {
            stagedModuleListView.scrollTo(index);
            stagedModuleListView.getSelectionModel().clearAndSelect(index);
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
    class StagedModuleListViewCell extends ListCell<Module> {
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
