package com.threektechone.resorthub.enums;

public enum EditResortField {
    NAME("name"),
    CITY("city"),
    DISTRICT("district"),
    ADDRESS("address"),
    DESCRIPTION("description"),
    MAX_GUEST("maxGuest"),
    AVERAGE_RATING("averageRating"),
    AMENITIES("amenities"),
    MENU_ITEMS("menuItems"),
    IMAGES("images"),
    PRICE("price");
    private final String key;

    EditResortField(String key) {
        this.key = key;
    }

    public String key() {
        return key;
    }

    public static EditResortField fromKey(String key) {
        for (EditResortField field : values()) {
            if (field.key.equals(key)) {
                return field;
            }
        }
        throw new IllegalArgumentException("Unknown edit field: " + key);
    }
    
}
