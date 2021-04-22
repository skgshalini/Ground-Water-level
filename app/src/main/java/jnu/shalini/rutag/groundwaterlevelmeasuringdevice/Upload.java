package jnu.shalini.rutag.groundwaterlevelmeasuringdevice;

public class Upload {
    private String depth;
    private String date;
    private String time;
    private String latitude;
    private String longitude;
    private String country;
    private String state;
    private String city;
    private String username;
    private String useremail;
    private String userphoneno;




    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getLatitude() {
        return latitude;
    }


    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUseremail() {
        return useremail;
    }

    public void setUseremail(String useremail) {
        this.useremail = useremail;
    }

    public String getUserphoneno() {
        return userphoneno;
    }

    public void setUserphoneno(String userphoneno) {
        this.userphoneno = userphoneno;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Upload(String depth, String date, String time , String tvLatitude, String tvLongitude, String tvCountry, String tvState, String tvCity, String useremail
            , String username,String userphoneno){
        this.depth=depth;
        this.date=date;
        this.time=time;
        this.latitude =tvLatitude;
        this.longitude =tvLongitude;
        this.country =tvCountry;
        this.city =tvCity;
        this.state=tvState;
        this.useremail=useremail;
        this.username=username;
        this.userphoneno=userphoneno;
    }

    public String getDepth() {
        return depth;
    }

    public void setDepth(String depth) {
        this.depth = depth;
    }
}
