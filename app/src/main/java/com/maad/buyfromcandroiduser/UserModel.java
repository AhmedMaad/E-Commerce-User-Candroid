package com.maad.buyfromcandroiduser;

class UserModel {

    private String name;
    private String email;
    private String address;
    private String phoneNumber;
    private String profilePicture;
    public static String id;

    public UserModel() {
    }

    public UserModel(String name, String email, String address, String phoneNumber
            , String profilePicture, String id) {
        this.name = name;
        this.email = email;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.profilePicture = profilePicture;
        UserModel.id = id;
    }

    //Used when we first create a user
    public UserModel(String email, String id) {
        this.email = email;
        UserModel.id = id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getAddress() {
        return address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getProfilePicture() {
        return profilePicture;
    }
}
