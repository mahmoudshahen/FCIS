package com.example.mahmoudshahen.fcis;

import java.io.Serializable;

/**
 * Created by mahmoud shahen on 06/04/2017.
 */

public class Instructor implements Serializable {
    String name, email;
    public Instructor() {
    }

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
}
