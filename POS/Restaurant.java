package POS;

public class Restaurant {
    private String name;
    private int id;
    private Address address;
    private String description;

    public Restaurant (String name, int id, Address address, String description) {
        this.name = name;
        this.id = id;
        this.address = address;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public Address getAddress() {
        return address;
    }

    public String getDescription() {
        return description;
    }
}
