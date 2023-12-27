package org.techtown.transmanageradmin;

public class MapData {
    private String property, name;

    public MapData(String property, String name) {
        this.property = property;
        this.name = name;
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
