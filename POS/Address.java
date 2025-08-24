package POS;

public class Address {
    private String street;
    private String city;
    private String province;
    private String postalCode;
    private String country;

    public Address(String street, String city, String province, String postalCode, String country) {
        this.street = street;
        this.city = city;
        this.province = province;
        this.postalCode = postalCode;
        this.country = country;
    }

    // Getters
    public String getStreet() {
        return street;
    }

    public String getCity() {
        return city;
    }

    public String getProvince() {
        return province;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public String getCountry() {
        return country;
    }

    public String getDetails() {
        return "Street : " + street + ", City : " + city + ", Province : " + province + ",Country :"  + country +  ", Postal Code : " + postalCode;
    }


    @Override
    public String toString() {
        return street + ", " + city + ", " + province + " " + postalCode + ", " + country;
    }
}
