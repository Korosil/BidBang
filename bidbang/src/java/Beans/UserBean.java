package Beans;

public class UserBean {

    private String username;
    private String password;
    private String name;
    private String surname;
    private String email;
    private String phonenumber;
    private String address;
    private String zipcode;
    private String city;
    private String state;
    private String country;
    private String trn;
    private Boolean verified;
    private float rating;
    private int rcounter;
    public boolean valid;

    public String getUsername() {
        return username;
    }

    public void setUserName(String newUsername) {
        username = newUsername;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String newPassword) {
        password = newPassword;
    }

    public String getName() {
        return name;
    }
    
    public void setName(String newName) {
        name = newName;
    }

    public String getSurname() {
        return surname;
    }
    
    public void setSurname(String newSurname) {
        surname = newSurname;
    }

    public String getEmail() {
        return email;
    }
    
    public void setEmail(String newEmail) {
        email = newEmail;
    }

    public String getPhonenumber() {
        return phonenumber;
    }
    
    public void setPhonenumber(String newPhonenumber) {
        phonenumber = newPhonenumber;
    }

    public String getAddress() {
        return address;
    }
    
    public void setAddress(String newAddress) {
        address = newAddress;
    }

    public String getZipcode() {
        return zipcode;
    }
    
    public void setZipcode(String newZipcode) {
        zipcode = newZipcode;
    }

    public String getCity() {
        return city;
    }
    
    public void setCity(String newCity) {
        city = newCity;
    }

    public String getState() {
        return state;
    }
    
    public void setState(String newState) {
        state = newState;
    }

    public String getCountry() {
        return country;
    }
    
    public void setCountry(String newCountry) {
        country = newCountry;
    }

    public String getTrn() {
        return trn;
    }
    
    public void setTrn(String newTrn) {
        trn = newTrn;
    }

    public Boolean getVerified() {
        return verified;
    }
    
    public void setVerified(Boolean newVerified) {
        verified = newVerified;
    }

    public float getRating() {
        return this.rating;
    }
    
    public void setRating(float new_rating) {
        rating = new_rating;
    }
    
    public int getRCounter() {
        return this.rcounter;
    }
    
    public void setRCounter(int new_rcounter) {
        rcounter = new_rcounter;
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean newValid) {
        valid = newValid;
    }
}
