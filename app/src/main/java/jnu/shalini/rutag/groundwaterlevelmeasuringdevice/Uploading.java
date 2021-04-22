package jnu.shalini.rutag.groundwaterlevelmeasuringdevice;

public class Uploading {
    public Uploading(){}
    private String name;
    private String email;
    private String phoneNo;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }
    public Uploading(String name, String email, String phoneNo){
        this.name=name;
        this.email=email;
        this.phoneNo=phoneNo;

    }
}
