package seedu.modsuni.ui;

import static guitests.guihandles.WebViewUtil.waitUntilBrowserLoaded;
import static org.junit.Assert.assertEquals;
import static seedu.modsuni.testutil.EventsUtil.postNow;
import static seedu.modsuni.testutil.TypicalModules.ACC1002;
import static seedu.modsuni.ui.BrowserPanel.DEFAULT_PAGE;
import static seedu.modsuni.ui.UiPart.FXML_FILE_FOLDER;

import java.net.URL;

import org.junit.Before;
import org.junit.Test;

import guitests.guihandles.BrowserPanelHandle;
import seedu.modsuni.MainApp;
import seedu.modsuni.commons.events.ui.DatabaseModulePanelSelectionChangedEvent;
import seedu.modsuni.commons.events.ui.ModulePanelSelectionChangedEvent;
import seedu.modsuni.commons.events.ui.StagedModulePanelSelectionChangedEvent;
import seedu.modsuni.commons.events.ui.TakenModulePanelSelectionChangedEvent;

public class BrowserPanelTest extends GuiUnitTest {
    private ModulePanelSelectionChangedEvent selectionChangedEventStub;
    private StagedModulePanelSelectionChangedEvent stagedSelectionChangedEventStub;
    private TakenModulePanelSelectionChangedEvent takenSelectionChangedEventStub;
    private DatabaseModulePanelSelectionChangedEvent databaseSelectionChangedEventStub;

    private BrowserPanel browserPanel;
    private BrowserPanelHandle browserPanelHandle;

    @Before
    public void setUp() {
        selectionChangedEventStub = new ModulePanelSelectionChangedEvent(ACC1002);
        stagedSelectionChangedEventStub = new StagedModulePanelSelectionChangedEvent(ACC1002);
        takenSelectionChangedEventStub = new TakenModulePanelSelectionChangedEvent(ACC1002);
        databaseSelectionChangedEventStub = new DatabaseModulePanelSelectionChangedEvent(ACC1002);

        guiRobot.interact(() -> browserPanel = new BrowserPanel());
        uiPartRule.setUiPart(browserPanel);

        browserPanelHandle = new BrowserPanelHandle(browserPanel.getRoot());
    }

    @Test
    public void displayForModulePanel() throws Exception {
        // default web page
        URL expectedDefaultPageUrl = MainApp.class.getResource(FXML_FILE_FOLDER + DEFAULT_PAGE);
        assertEquals(expectedDefaultPageUrl, browserPanelHandle.getLoadedUrl());

        // associated web page of a module
        postNow(selectionChangedEventStub);
        URL expectedModuleUrl = new URL(BrowserPanel.SEARCH_PAGE_URL + ACC1002.getCode().code.replaceAll(" ",
                "%20"));

        waitUntilBrowserLoaded(browserPanelHandle);
        assertEquals(expectedModuleUrl, browserPanelHandle.getLoadedUrl());
    }

    @Test
    public void displayForStagedModulePanel() throws Exception {
        // default web page
        URL expectedDefaultPageUrl = MainApp.class.getResource(FXML_FILE_FOLDER + DEFAULT_PAGE);
        assertEquals(expectedDefaultPageUrl, browserPanelHandle.getLoadedUrl());

        // associated web page of a module
        postNow(stagedSelectionChangedEventStub);
        URL expectedModuleUrl = new URL(BrowserPanel.SEARCH_PAGE_URL + ACC1002.getCode().code.replaceAll(" ",
                "%20"));

        waitUntilBrowserLoaded(browserPanelHandle);
        assertEquals(expectedModuleUrl, browserPanelHandle.getLoadedUrl());
    }

    @Test
    public void displayForTakenModulePanel() throws Exception {
        // default web page
        URL expectedDefaultPageUrl = MainApp.class.getResource(FXML_FILE_FOLDER + DEFAULT_PAGE);
        assertEquals(expectedDefaultPageUrl, browserPanelHandle.getLoadedUrl());

        // associated web page of a module
        postNow(takenSelectionChangedEventStub);
        URL expectedModuleUrl = new URL(BrowserPanel.SEARCH_PAGE_URL + ACC1002.getCode().code.replaceAll(" ",
                "%20"));

        waitUntilBrowserLoaded(browserPanelHandle);
        assertEquals(expectedModuleUrl, browserPanelHandle.getLoadedUrl());
    }

    @Test
    public void displayForDatabaseModulePanel() throws Exception {
        // default web page
        URL expectedDefaultPageUrl = MainApp.class.getResource(FXML_FILE_FOLDER + DEFAULT_PAGE);
        assertEquals(expectedDefaultPageUrl, browserPanelHandle.getLoadedUrl());

        // associated web page of a module
        postNow(databaseSelectionChangedEventStub);
        URL expectedModuleUrl = new URL(BrowserPanel.SEARCH_PAGE_URL + ACC1002.getCode().code.replaceAll(" ",
                "%20"));

        waitUntilBrowserLoaded(browserPanelHandle);
        assertEquals(expectedModuleUrl, browserPanelHandle.getLoadedUrl());
    }
}
