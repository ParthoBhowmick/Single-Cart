package com.example.singlepagerivew.Cart_CheckOut;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "address_table")
public class ShippingAddress {
    private String userName,userContact,userEmail,userDist,userPlace,userAdress,userDivi;

    @PrimaryKey(autoGenerate = true)
    private int id;

    public ShippingAddress(String userName, String userContact, String userEmail,String userDivi ,String userDist, String userPlace, String userAdress ) {
        this.userName = userName;
        this.userContact = userContact;
        this.userEmail = userEmail;
        this.userDivi = userDivi;
        this.userDist = userDist;
        this.userPlace = userPlace;
        this.userAdress = userAdress;
    }


    public String getUserDivi() {
        return userDivi;
    }

    public void setUserDivi(String userDivi) {
        this.userDivi = userDivi;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserContact() {
        return userContact;
    }

    public void setUserContact(String userContact) {
        this.userContact = userContact;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserDist() {
        return userDist;
    }

    public void setUserDist(String userDist) {
        this.userDist = userDist;
    }

    public String getUserPlace() {
        return userPlace;
    }

    public void setUserPlace(String userPlace) {
        this.userPlace = userPlace;
    }

    public String getUserAdress() {
        return userAdress;
    }

    public void setUserAdress(String userAdress) {
        this.userAdress = userAdress;
    }
}
