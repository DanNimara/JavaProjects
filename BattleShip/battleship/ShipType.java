package battleship;

public enum ShipType {
    AIRCRAFTCARRIER("Aircraft Carrier",5),
    BATTLESHIP("Battleship", 4),
    SUBMARINE("Submarine", 3),
    CRUISER("Cruiser", 3),
    DESTROYER("Destroyer", 2);

    private int shipSize;
    private String shipName;

    ShipType(String name, int shipSize) {
        this.shipSize = shipSize;
        this.shipName = name;
    }

    public int getShipSize() {
        return shipSize;
    }

    public String getShipName() {
        return shipName;
    }
}
