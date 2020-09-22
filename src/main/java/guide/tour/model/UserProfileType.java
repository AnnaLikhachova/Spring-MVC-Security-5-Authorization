package guide.tour.model;

public enum UserProfileType {
    SOFTWARE_DEVELOPER("SOFTWARE_DEVELOPER"),
    UI_DESIGNER("UI_DESIGNER"),
    DATA_ARCHITECT("DATA_ARCHITECT"),
    CLOUD_ARCHITECT("CLOUD_ARCHITECT"),
    TECHNICAL_LEAD("TECHNICAL_LEAD");
     
    private String userProfileType;
     
    private UserProfileType(String userProfileType){
        this.userProfileType = userProfileType;
    }
     
    public String getUserProfileType(){
        return userProfileType;
    }
     
}
