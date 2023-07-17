package utilities;

public class Constants {
    public static final String PROPERTY_FILE = "src/main/resources/constants.properties";
    public static final String OS = System.getProperty("os.name");

    //Environment i.e. Dev,QA,Prod etc
    public static final String ENV = JavaHelpers.setSystemVariable(PROPERTY_FILE, "Env");

    //Setting up the URLs
    public static final String URL = JavaHelpers.getPropertyValue(PROPERTY_FILE, "url_" + ENV);

    //Selenium constants
    public static final int WEBDRIVER_WAIT_DURATION = Integer.parseInt(JavaHelpers.getPropertyValue(PROPERTY_FILE, "WebDriverWaitDuration"));
    public static final int PAGEFACTORY_WAIT_DURATION = Integer.parseInt(JavaHelpers.getPropertyValue(PROPERTY_FILE, "PageFactoryWaitDuration"));
    public static final String SCREENSHOT_LOCATION = JavaHelpers.getPropertyValue(PROPERTY_FILE, "ScreenshotLocation");

    private Constants() {
        throw new IllegalStateException("Constants class");
    }

    //Error Messages
    public static final String teamMemberErrorMessage = "You do not have permission to update ProjectMember.roles";
    public static final String changePasswordErrorMessage = "Something wasn't right. Try again, or reset password.";
    public static final String engagementErrorMessage = "user anon does not have the requested power: CreateFile";
    public static final String projectStatusErrorMessage = "The status of this project is unable to be changed.";
    public static final String engagementStatusErrorMessage = "The status of this engagement is unable to be changed.";
    public static final String partnershipErrorMessage = "Failed to create partnership";
    public static final String budgetPermissionErrorMessage = "You do not have permission to view this project's field budget";

    //Validation Messages
    public static final String passwordCharacterLimitValidationMessage = "password must be longer than or equal to 6 characters";
    public static final String comparePasswordMessage = "Passwords must match";
    public static final String passwordChangeRequestMessage = "You have submitted a password change request!";
    public static final String hasExternalFirstScriptureCheckedMessage = "First Scripture was completed outside of Seed Company";
    public static final String invalidEmailErrorMessage = "Invalid email";
    public static final String hasExternalFirstScriptureUnCheckedMessage = "No Scripture yet";
    public static final String postsShareabilityText = "PUBLICExternal";
    public static final String postsUpdatedShareabilityText = "PRIVATEInternal";
    public static final String requiredMessage = "Required";

    //Suggestion Text
    public static final String goalProgressTargetSuggestionText = "Target is ";
    public static final String searchPartnerSuggestionText = "Organization";

    //Invalid Data
    public static final String invalidLoginEmail = "piyush@temp.com";
    public static final String invalidLoginPassword = "1111";

    //End Points
    public static final String logInEndPoint = "/login";
    public static final String registerEndPoint = "/register";
    public static final String forgotPasswordEndPoint = "/forgot-password";

    //Welcome Labels
    public static final String welcomeLabel = "Hi, ";

    //Color code
    public static final String disableGrayColorCode = "rgba(60, 68, 78, 1)";
    public static final String disableEditButtonColorCode = "rgba(0, 0, 0, 0.26)";

    //Goal Chapters
    public static final String[] scripturePartialKnownChapter = new String[] {"1"};
    public static final String scriptureReferenceBook = "Genesis";
    public static final String[] scripturePartialKnownGenesisChapter = new String[] {"1","2","3"};

}


