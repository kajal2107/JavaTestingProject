package dataobjects;

import enums.project.Sensitivity;

public class Language {

    private String languageName;
    private String languageEngagementName;
    private String publicName;
    private String ethnologueName;
    private String ethnologueCode;
    private String pronunciationGuide;
    private String provisionalCode;
    private String ethnologuePopulation;
    private String registryOfDialectsCode;
    private String population;
    private Sensitivity sensitivity;


    private boolean isSignLanguage;
    private String signLanguageCode;

    private boolean isLeastOfThese;
    private boolean isDialect;

    private boolean isHasExternalFirstScripture;

    private String reasoning;
    private String sponsorEstimatedEndFY;

    public Sensitivity getSensitivity() {
        return sensitivity;
    }

    public void setSensitivity(Sensitivity sensitivity) {
        this.sensitivity = sensitivity;
    }

    public boolean isDialect() {
        return isDialect;
    }

    public void setDialect(boolean dialect) {
        isDialect = dialect;
    }

    public boolean isHasExternalFirstScripture() {
        return isHasExternalFirstScripture;
    }

    public void setHasExternalFirstScripture(boolean hasExternalFirstScripture) {
        isHasExternalFirstScripture = hasExternalFirstScripture;
    }

    public boolean isLeastOfThese() {
        return isLeastOfThese;
    }

    public void setLeastOfThese(boolean leastOfThese) {
        this.isLeastOfThese = leastOfThese;
    }

    public boolean isSignLanguage() {
        return isSignLanguage;
    }

    public void setSignLanguage(boolean signLanguage) {
        isSignLanguage = signLanguage;
    }

    public String getSignLanguageCode() {
        return signLanguageCode;
    }

    public void setSignLanguageCode(String signLanguageCode) {
        this.signLanguageCode = signLanguageCode;
    }

    public String getLanguageName() {
        return languageName;
    }

    public void setLanguageName(String languageName) {
        this.languageName = languageName;
    }

    public String getLanguageEngagementName() {
        return languageEngagementName;
    }

    public void setLanguageEngagementName(String languageEngagementName) {
        this.languageEngagementName = languageEngagementName;
    }

    public String getSponsorEstimatedEndFY() {
        return sponsorEstimatedEndFY;
    }

    public void setSponsorEstimatedEndFY(String sponsorEstimatedEndFY) {
        this.sponsorEstimatedEndFY = sponsorEstimatedEndFY;
    }

    public String getPubliicName() {
        return publicName;
    }

    public void setPubliicName(String publicName) {
        this.publicName = publicName;
    }

    public String getEthnologueName() {
        return ethnologueName;
    }

    public void setEthnologueName(String ethnologueName) {
        this.ethnologueName = ethnologueName;
    }

    public String getEthnologuePopulation() {
        return ethnologuePopulation;
    }

    public void setEthnologuePopulation(String ethnologuePopulation) {
        this.ethnologuePopulation = ethnologuePopulation;
    }

    public String getPublicName() {
        return publicName;
    }

    public void setPublicName(String publicName) {
        this.publicName = publicName;
    }

    public String getEthnologueCode() {
        return ethnologueCode;
    }

    public void setEthnologueCode(String ethnologueCode) {
        this.ethnologueCode = ethnologueCode;
    }

    public String getPronunciationGuide() {
        return pronunciationGuide;
    }

    public void setPronunciationGuide(String pronunciationGuide) {
        this.pronunciationGuide = pronunciationGuide;
    }

    public String getProvisionalCode() {
        return provisionalCode;
    }

    public void setProvisionalCode(String provisionalCode) {
        this.provisionalCode = provisionalCode;
    }

    public String getRegistryOfDialectsCode() {
        return registryOfDialectsCode;
    }

    public void setRegistryOfDialectsCode(String registryOfDialectsCode) {
        this.registryOfDialectsCode = registryOfDialectsCode;
    }

    public String getPopulation() {
        return population;
    }

    public void setPopulation(String population) {
        this.population = population;
    }

    public String getReasoning() {
        return reasoning;
    }

    public void setReasoning(String reasoning) {
        this.reasoning = reasoning;
    }
}





