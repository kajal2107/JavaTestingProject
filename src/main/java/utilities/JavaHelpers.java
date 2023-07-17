package utilities;

import com.google.gson.Gson;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class JavaHelpers {

    //Time-stamps

    /**
     * Get current time-stamp in given format
     *
     * @param format e.g. "yyyy MMM dd", 'yyyyMMdd_HHmmss' etc.
     * @return String timestamp
     */
    public String getTimeStamp(String format) {
        /*
         * Example format are :
         *
         * "yyyy MMM dd" for "2013 Nov 28"
         *
         * "yyyyMMdd_HHmmss" for "20130131000000"
         *
         * "yyyy MMM dd HH:mm:ss" for "2013 Jan 31 00:00:00"
         *
         * "dd MMM yyyy" for "28 Nov 2017"
         */
        DateFormat dateFormat = new SimpleDateFormat(format);
        Date date = new Date();
        return dateFormat.format(date);
    }

    /**
     * Get current time-stamp in "_yyyyMMdd_HHmmss" format
     *
     * @return timestamp
     */
    public String getTimeStamp() throws InterruptedException {
        Thread.sleep(1); // to keep 1 sec duration between two successive timestamps
        return getTimeStamp("_yyyyMMdd_HHmmSSS");
    }

    /**
     * Convert date to desired format
     *
     * @param oldDateString old date string
     * @param oldFormat     old date format
     * @param newFormat     new date format
     * @return new date string with new date format
     */
    public String changeDateFormat(String oldDateString, String oldFormat, String newFormat) throws java.text.ParseException {
        // MM/dd/yyyy   :  01/02/2020
        // M/d/yyyy     :  1/2/2020
        String newDateString;
        SimpleDateFormat sdf = new SimpleDateFormat(oldFormat);
        Date d = sdf.parse(oldDateString);
        sdf.applyPattern(newFormat);
        newDateString = sdf.format(d);
        return newDateString;
    }

    /**
     * Update time string to required timezone time string
     *
     * @param actualTimeFormat   Time Format for time input
     * @param time              time string you want to update
     * @param expectedTimeFormat Time Format we want our result to be
     * @param incrementDate      number by what we need to increment date to
     * @param incrementMonth     Amount of time we need to increment hour to
     * @param incrementYear      Amount of time we need to increment minutes to
     *                           to
     * @return String converted time
     *                        <p>
     *                        "yyyy MMM dd" for "2013 Nov 28"
     *                        <p>
     *                        "yyyyMMdd_HHmmss" for "20130131000000"
     *                        <p>
     *                        "yyyy MMM dd HH:mm:ss" for "2013 Jan 31 00:00:00"
     *                        <p>
     *                        "dd MMM yyyy" for "28 Nov 2017"
     *                        <p>
     *                        <p>
     *                        <p>
     *                        Example for time format:
     *                        <p>
     *                        "HH:mm:ss" for "16:00:00"(24 hr format)
     *                        <p>
     *                        "hh:mm:ss" for "4:00:00"(12 hr format)
     */
    public String updateTime(String actualTimeFormat,
                             String time,
                             String expectedTimeFormat,
                             int incrementDate,
                             int incrementMonth,
                             int incrementYear

    ) throws java.text.ParseException {
        int year = Calendar.getInstance().get(Calendar.YEAR);
        DateFormat resultDateFormat = new SimpleDateFormat(expectedTimeFormat);
        Date date = new SimpleDateFormat(actualTimeFormat).parse(time + " " + year); // we're parsing current year
// in case year not passed
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, incrementDate);
        calendar.add(Calendar.MONTH, incrementMonth);
        calendar.add(Calendar.YEAR, incrementYear);
        return resultDateFormat.format(calendar.getTime());
    }


    //Java Methods

    /**
     * Get method name where this method is called
     *
     * @return String method name
     */
    public String getMethodName() {
        return Thread.currentThread().getStackTrace()[3].getMethodName();
    }

    /**
     * @param array1 1st array
     * @param array2 2nd array
     * @return true or false based on compare
     */
    public boolean areSameArrays(String[] array1, String[] array2) {
        Arrays.sort(array1);
        Arrays.sort(array2);
        return Arrays.equals(array1, array2);
    }

    /**
     * This method will combine two arrays
     *
     * @param array1 1st array
     * @param array2 2nd array
     * @return result array
     */
    public String[] combineArrays(String[] array1, String[] array2) {

        int aLen = array1.length;
        int bLen = array2.length;
        String[] result = new String[aLen + bLen];

        System.arraycopy(array1, 0, result, 0, aLen);
        System.arraycopy(array2, 0, result, aLen, bLen);

        return result;

    }

    /**
     * This method will generate random sub arrays from given array
     *
     * @param randomArrayLength length of random array you want to generate
     * @param possibleArray     array from which you want to generate random sub arrays
     * @return random sub array
     */
    public static ArrayList<String> generateRandomArrayFromArray(int randomArrayLength, String[] possibleArray) {
        ArrayList<String> randomArray = new ArrayList<>();
        String[] possible = possibleArray;
        int preDefinedLength = randomArrayLength;
        for (int i = 0; i < preDefinedLength; i++) {
            int choice = (int) Math.floor((Math.random() * possible.length));
            if (randomArray.contains(possible[choice]))
                continue;
            randomArray.add(possible[choice]);
        }
        return randomArray;
    }
    /**
     * Get Random String From Given Array
     *
     * @param possibleArray array from which you want to get random string
     * @return random string from your given array
     */
    public static String getRandomStringFromArray(String[] possibleArray) {
        Random random = new Random();
        int index = random.nextInt(possibleArray.length);
        return possibleArray[index];
    }

    /**
     * Get all enum constant values (bracket values) as array
     *
     * @return all enum values defined in brackets
     */
    public static String[] getAllProductEnumValues(String[] enumValues) {
        String[] names = new String[enumValues.length];

        for (int i = 0; i < enumValues.length; i++) {
            names[i] = enumValues[i];
        }
        return names;
    }

    /**
     * Get Property value
     *
     * @param propertyFile property file name
     * @param propertyName property name
     * @return property value
     */
    public static String getPropertyValue(String propertyFile, String propertyName) {
        Properties prop = accessPropertiesFile(propertyFile);
        return prop.getProperty(propertyName);
    }

    /**
     * Access property file
     *
     * @param propertyFile property file name
     * @return Properties object
     */
    public static Properties accessPropertiesFile(String propertyFile) {
        Properties prop = new Properties();
        // try retrieve data from file
        try {
            prop.load(new FileInputStream(propertyFile));
        }
        // catch exception in case properties file does not exist
        catch (IOException e) {
            e.printStackTrace();
        }
        return prop;
    }

    //JSON

    /**
     * Json To String
     *
     * @param filePath json file path
     * @return string
     * @throws IOException    exception
     * @throws ParseException exception
     */
    public static String jsonToString(String filePath) throws IOException, ParseException {

        JSONParser jsonParser = new JSONParser();
        Object obj = jsonParser.parse(new FileReader(filePath));
        return obj.toString();
    }

    /**
     * Json deserialization to Java Object
     *
     * @param json string json
     * @param dto  Class Object
     * @return Class Object equivalent to json file
     */
    public static <T> T jsonDeserialization(String json, Class<T> dto) {
        return new Gson().fromJson(json, dto);
    }


    //Folder Operations

    /**
     * Delete all files from given folder
     *
     * @param folderPath folder path
     */
    public void deleteAllFilesFromFolder(String folderPath) {
        File dir = new File(folderPath);
        if (!dir.isDirectory()) {
            return;
        }
        File[] listFiles = dir.listFiles();
        if (listFiles != null) {
            for (File file : listFiles) {
                file.delete();
            }
        }
    }

    //Reading system properties

    /**
     * Set system variable - set it from system variable first, if not found -set it from properties file
     *
     * @param name                 variable name
     * @param propertyFileLocation properties file location
     * @return variable value
     */
    public static String setSystemVariable(String propertyFileLocation, String name) {
        //Reading from system properties.
        String variable = System.getProperty(name);

        //if not specified via command line, take it from constants.properties file
        if (variable == null || variable.isEmpty()) {
            variable = JavaHelpers.getPropertyValue(propertyFileLocation, name);
        }
        return variable;
    }

    /**
     * @param min minimum number to start range
     * @param max minimum number to end range
     * @return random number formatted with comma separator
     */
    public static String getRandomNumberWithComma(int min, int max) {
        return ((NumberFormat.getNumberInstance(Locale.US).format((int) (Math.random() * (max - min) + min))));
    }

    /**
     * @param min minimum number to start range
     * @param max minimum number to end range
     * @return random number within given range
     */
    public static String getRandomNumber(int min, int max) {
        return String.valueOf((int) (Math.random() * (max - min)) + min);
    }

    /**
     * @param min minimum number to start range
     * @param max minimum number to end range
     * @return random number within given range
     */
    public static Integer getIntegerRandomNumber(int min, int max) {
        return (int) (Math.random() * (max - min)) + min;
    }

    /**
     * @param lower  pass true to generate string containing all lowercase letters
     * @param caps   pass true to generate string containing all capital letters
     * @param mix    pass true to generate string containing capital and small letters
     * @param length length of the resultant string
     */
    public static String generateId(boolean lower, boolean caps, boolean mix, int length) {

        StringBuilder result = new StringBuilder();
        String characters;
        if (lower) {
            characters = "abcdefghijklmnopqrstuvwxyz";
        } else if (caps) {
            characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        } else if (mix) {
            characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        } else {
            characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        }
        int charactersLength = characters.length();
        for (int i = 0; i < length; i++) {
            result.append(characters.charAt((int) Math.floor(Math.random() * charactersLength)));
        }
        return result.toString();
    }



}
