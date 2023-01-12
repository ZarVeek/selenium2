package org.example.tests;

import lombok.Data;

import java.util.Arrays;
import java.util.List;

@Data
public class FormRegistration {
    private String name;
    private String phone;
    private String mail;
    private String address;

    public void generateData1(){
        this.name = "Артем1 Киреев";
        this.phone = "9999999999";
        this.mail = "qwertyqwertyyyyyyyyy";
        this.address = "AinBbbbbb";
    }
    public void generateData2(){
        this.name = "Артем2 Киреев";
        this.phone = "9999999999";
        this.mail = "qwertyqwerty";
        this.address = "AinBuuuu";
    }
    public void generateData3(){
        this.name = "Артем3 Киреев";
        this.phone = "9999999999";
        this.mail = "qwertyqwerty";
        this.address = "AinBiiiiii";
    }

    public List<String> getListValue(){
        return Arrays.asList(name, phone, mail, address);
    }
}

