package ua.com.meom.helpers;

import ua.com.meom.constants.Constants;
import ua.com.meom.entities.RankingRecord;
import ua.com.meom.entities.Settings;
import ua.com.meom.enums.KeyCommand;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * This class contains methods that work with dynamic context
 */
public class GameContext {

    private static final String FILE_PATH = System.getProperty("user.home") + System.getProperty("file.separator") + Constants.Common.APP_NAME + System.getProperty("file.separator") + Constants.Common.RANKING_FILE_NAME;

    private static Settings settings;
    private static RankingRecord record;
    private static List<KeyCommand> keyCommandList;

    /**
     * Sets the defined settings to context
     *
     * @param settings settings instance to add
     */
    public static void setSettings(Settings settings) {
        GameContext.settings = settings;
    }

    /**
     * Returns the settings instance with user preferences
     */
    public static Settings getSettings() {
        return settings;
    }

    /**
     * Sets the defined record to context
     *
     * @param record user record to add
     */
    public static void setRecord(RankingRecord record) {
        GameContext.record = record;
    }

    /**
     * Returns the ranking record instance with user results
     */
    public static RankingRecord getRecord() {
        return record;
    }

    /**
     * Saves the current record to the existing records list
     */
    public static void saveRecordToDisk() {
        File file = new File(FILE_PATH);
        try {
            file.getParentFile().mkdirs();
            file.createNewFile();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        List<RankingRecord> recordList = getRecordList();
        recordList.add(getRecord());
        try (
                FileOutputStream fout = new FileOutputStream(file, false);
                ObjectOutputStream oos = new ObjectOutputStream(fout)
        ) {
            oos.writeObject(recordList);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Returns all supported in the application levels
     */
    public static String[] getAvailableLevels() {
        return "1,2,3,4,5,6,7,8,9,10".split(",");
    }

    /**
     * Returns the last available level for the current keyboard language
     */
    public static int getMaxLevel() {
        String[] levelsArray = getAvailableLevels();
        return Integer.parseInt(levelsArray[levelsArray.length - 1]);
    }

    /**
     * Returns a list with localized sound options
     */
    public static String[] getSoundOptions() {
        Locale locale = Locale.getDefault();
        ResourceBundle rb = ResourceBundle.getBundle(Constants.Common.LOCALE_PREFIX, locale);
        return new String[]{rb.getString("sound_on_label"), rb.getString("sound_off_label")};
    }

    /**
     * Returns a list with localized ranking table column names
     */
    public static String[] getRankingTableColumnNames() {
        Locale locale = Locale.getDefault();
        ResourceBundle rb = ResourceBundle.getBundle(Constants.Common.LOCALE_PREFIX, locale);
        return new String[]{rb.getString("ranking_table_place"), rb.getString("ranking_table_name"), rb.getString("ranking_table_scores"), rb.getString("ranking_table_level"), rb.getString("ranking_table_mistakes"), rb.getString("ranking_table_date")};
    }

    /**
     * Returns the saved previously ranking record list
     */
    public static List<RankingRecord> getRecordList() {
        List<RankingRecord> resultList = new ArrayList<>();
        try (
                FileInputStream streamIn = new FileInputStream(FILE_PATH);
                ObjectInputStream objectinputstream = new ObjectInputStream(streamIn)
        ) {
            resultList = (List<RankingRecord>) objectinputstream.readObject();
        } catch (EOFException e) {
            //Do nothing - the missed file will be created automatically after the first result is saved
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultList;
    }

    /**
     * Saves a new key command to the context
     *
     * @param command key command to add to list
     */
    public static void addKey(KeyCommand command) {
        if (keyCommandList == null) {
            keyCommandList = new ArrayList<>();
        }
        keyCommandList.add(command);
    }

    /**
     * Removes the last key command from the context
     */
    public static void removeLastKey() {
        if (keyCommandList != null && keyCommandList.size() > 0) {
            keyCommandList.remove(keyCommandList.size() - 1);
        }
    }

    /**
     * Returns the key command list
     */
    public static List<KeyCommand> getKeyCommandList() {
        if (keyCommandList == null) {
            keyCommandList = new ArrayList<>();
        }
        return keyCommandList;
    }
}
