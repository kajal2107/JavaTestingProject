package datafactory;

import dataobjects.Language;
import enums.project.Sensitivity;
import utilities.JavaHelpers;

public class LanguageData {

    public Language getLanguageData() throws InterruptedException {

        Language data = new Language();
        String timeStamp = new JavaHelpers().getTimeStamp();

        data.setLanguageName("lantest" + timeStamp);
        data.setLanguageEngagementName("LangName");
        data.setPubliicName("publicName" + timeStamp);

        data.setEthnologueName("ethnologueName" + timeStamp);
        data.setEthnologueCode("qwe");
        data.setPronunciationGuide("pronunciationGuide" + timeStamp);
        data.setProvisionalCode("asd"); // must be 3 characters
        data.setEthnologuePopulation(JavaHelpers.getRandomNumberWithComma(1000, 5000));
        data.setRegistryOfDialectsCode(JavaHelpers.getRandomNumber(10000, 50000));
        data.setPopulation(JavaHelpers.getRandomNumberWithComma(1000, 5000));
        data.setSignLanguage(true);
        data.setDialect(true);
        data.setSignLanguageCode(JavaHelpers.generateId(false, true, false, 2) + JavaHelpers.getRandomNumberWithComma(10, 99));

        data.setSensitivity(Sensitivity.Low);

        data.setLeastOfThese(true);
        data.setReasoning("This is test Language Least Reason by Automation Testing");
        data.setSponsorEstimatedEndFY(JavaHelpers.getRandomNumber(1900, 2099));
        return data;
    }

    public Language getEditLanguageData() throws InterruptedException {

        Language data = new Language();
        String timeStamp = new JavaHelpers().getTimeStamp();

        data.setLanguageName("EditLanguage" + timeStamp);
        data.setPubliicName("publicName" + timeStamp);
        data.setLanguageEngagementName("UpdateLangName");
        data.setEthnologueName("ethnologueName" + timeStamp);
        data.setEthnologueCode("qwe");
        data.setPronunciationGuide("pronunciationGuide" + timeStamp);
        data.setProvisionalCode("asd"); // must be 3 characters
        data.setEthnologuePopulation(JavaHelpers.getRandomNumberWithComma(1000, 5000));
        data.setRegistryOfDialectsCode(JavaHelpers.getRandomNumber(10000, 50000));
        data.setPopulation(JavaHelpers.getRandomNumberWithComma(1000, 5000));
        data.setReasoning("This is test Language Least Reason by Automation Testing");
        return data;
    }


}
