package com.hubrick.model;

import com.hubrick.exception.SexNotFoundException;

import java.util.Arrays;

public enum Sex {
    MALE("m"),
    FEMALE("f");

    private String shortName;

    Sex(String shortName) {
        this.shortName = shortName;
    }

    public static Sex findByShortName(String shortName) throws SexNotFoundException {
        try {
            return Arrays.stream(values())
                    .filter(x -> x.shortName.equalsIgnoreCase(shortName))
                    .findFirst()
                    .get();
        }
        catch (Exception e) {
            throw new SexNotFoundException();
        }
    }
}
