package seedu.address.storage;

import static org.junit.Assert.assertEquals;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.util.XmlUtil;
import seedu.address.model.ModuleList;
import seedu.address.testutil.TypicalModules;

public class XmlSerializableModuleListTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data",
            "XmlSerializableModuleListTest");
    private static final Path TYPICAL_MODULES_FILE = TEST_DATA_FOLDER.resolve("typicalModulesListSmall.xml");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void toModelType_typicalPersonsFile_success() throws Exception {
        XmlSerializableModuleList dataFromFile = XmlUtil.getDataFromFile(TYPICAL_MODULES_FILE,
                XmlSerializableModuleList.class);
        ModuleList moduleListFromFile = dataFromFile.toModelType();
        ModuleList typicalModulesList = TypicalModules.getTypicalModuleList();
        assertEquals(moduleListFromFile, typicalModulesList);
    }

}
