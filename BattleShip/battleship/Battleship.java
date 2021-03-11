package battleship;

import java.util.Scanner;

class Battleship {

    final private char fogOfWar = '~';
    final private char shipCell = 'O';
    private int AirshipCarrierHits;
    private int BattleshipHits;
    private int SubmarineHits;
    private int CruiserHits;
    private int DestroyerHits;
    private final char[][] field;
    private final char[][] enemyField;
    private final char[][] positionedShips;

    public Battleship() {
        field = new char[10][10];
        enemyField = new char[10][10];
        positionedShips = new char[10][10];
        AirshipCarrierHits = 5;
        BattleshipHits = 4;
        SubmarineHits = 3;
        CruiserHits = 3;
        DestroyerHits = 2;
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                field[i][j] = positionedShips[i][j] = enemyField[i][j] = fogOfWar;
            }
        }
    }

    private char getField(int i, int j) {
        return field[i][j];
    }

    private char getShipField(int i, int j) {
        return positionedShips[i][j];
    }

    private void setField(int i, int j, char value) {
        field[i][j] = value;
    }

    public void inputPrompt() {
        printField(field);
        ShipType[] ships = ShipType.values();
        for (ShipType sh: ships) {
            System.out.printf("Enter the coordinates of the %s (%d cells):%n", sh.getShipName(), sh.getShipSize());
            readShip(sh);
        }
    }

    private void readShip(ShipType type) {
        Scanner scanner = new Scanner(System.in);
        String in = scanner.nextLine();
        StringBuilder input = new StringBuilder();
        input.append(in);
        try {
            int l = input.length();
            boolean sndHalf = false;
            int line1 = 0, col1 = 0, line2 = 0, col2 = 0;
            for (int i = 0; i < l; i++) {
                char curr = input.charAt(i);
                if (!sndHalf) {
                    if (curr >= 'A' && curr <= 'J') {
                        line1 = curr - 'A';
                    } else if (curr >= '2' && curr <= '9') {
                        col1 = curr - '1';
                    } else if (curr == '1') {
                        if (input.charAt(i+1) == '0') {
                            col1 = 9;
                        } else {
                            col1 = curr - '1';
                        }
                    } else if (curr == ' ') {
                        sndHalf = true;
                    } else if (curr != '0') {
                        throw new IllegalArgumentException("Error! Invalid input!");
                    }
                } else {
                    if (curr >= 'A' && curr <= 'J') {
                        line2 = curr - 'A';
                    } else if (curr >= '2' && curr <= '9') {
                        col2 = curr - '1';
                    } else if (curr == '1') {
                        if (i + 1 < l) {
                            if (input.charAt(i + 1) == '0') {
                                col2 = 9;
                            } else {
                                col2 = curr - '1';
                            }
                        } else {
                            col2 = curr - '1';
                        }
                    } else if (curr != '0') {
                        throw new IllegalArgumentException("Error! Invalid input!");
                    }
                }
            }
            if (line1 != line2 && col1 != col2) {
                throw new ArithmeticException("Error! Wrong ship location! Try again:");
            }
            if (line1 > line2) {
                int aux = line1;
                line1 = line2;
                line2 = aux;
            }
            if (col1 > col2) {
                int aux = col1;
                col1 = col2;
                col2 = aux;
            }
            boolean update = checkInput(line1, col1, line2, col2, type);

            if (update) {
                updateField(line1, col1, line2, col2, field, type);
                printField(field);
            }
        } catch (IllegalArgumentException | ArithmeticException e) {
            System.out.println(e.getMessage());
            readShip(type);
        }
    }

    private boolean checkInput(int l1, int c1, int l2, int c2, ShipType type) {
        int length;
        if (l1 == l2) {
            length = c2 - c1 + 1;
        } else {
            length = l2 - l1 + 1;
        }
        if (length != type.getShipSize()) {
            throw new ArithmeticException(String.format("Error! Wrong length of the %s! Try again:", type.getShipName()));
        }
        int[] di = new int[] {0, 0, 1, -1};
        int[] dj = new int[] {-1, 1, 0, 0};
        int leftIndex = l1 == l2 ? c1 : l1;
        int rightIndex = l1 == l2 ? c2 : l2;
        for (int dir = 0; dir < 4; dir ++) {
            int dx;
            int dy;
            for (int i = leftIndex; i <= rightIndex; i++) {
                if (l1 == l2) {
                    dx = l1 + di[dir];
                    dy = i + dj[dir];
                } else {
                    dx = i + di[dir];
                    dy = c1 + dj[dir];
                }
                if (dx >= 0 && dx <= 9 && dy >= 0 && dy <= 9) {
                    if (field[dx][dy] == shipCell) {
                        throw new ArithmeticException("Error! You placed it too close to another one. Try again:");
                    }
                }
            }
        }
        return true;
    }

    private void printField(char[][] field) {
        System.out.print(" ");
        for (int i = 1; i <= 10; i++) {
            System.out.print(" " + i);
        }
        System.out.println();
        for (int i = 0; i < 10; i++) {
            int x = 'A' + i;
            System.out.print((char) x);
            for (int j = 0; j < 10; j++) {
                System.out.print(" " + field[i][j]);
            }
            System.out.println();
        }
    }

    private void updateField (int l1, int c1, int l2, int c2, char[][] field, ShipType type) {
        if (l1 == l2) {
            for (int j = c1; j <= c2; j++) {
                switch(type) {
                    case AIRCRAFTCARRIER:
                        positionedShips[l1][j] = 'A';
                        break;
                    case BATTLESHIP:
                        positionedShips[l1][j] = 'B';
                        break;
                    case SUBMARINE:
                        positionedShips[l1][j] = 'S';
                        break;
                    case CRUISER:
                        positionedShips[l1][j] = 'C';
                        break;
                    case DESTROYER:
                        positionedShips[l1][j] = 'D';
                }
                field[l1][j] = shipCell;
            }
        } else {
            for (int i = l1; i <= l2; i++) {
                switch(type) {
                    case AIRCRAFTCARRIER:
                        positionedShips[i][c1] = 'A';
                        break;
                    case BATTLESHIP:
                        positionedShips[i][c1] = 'B';
                        break;
                    case SUBMARINE:
                        positionedShips[i][c1] = 'S';
                        break;
                    case CRUISER:
                        positionedShips[i][c1] = 'C';
                        break;
                    case DESTROYER:
                        positionedShips[i][c1] = 'D';
                }
                field[i][c1] = shipCell;
            }
        }
    }

    public void Hit (Battleship enemyShip) {
        boolean alreadyHit = false;
        Scanner scanner = new Scanner(System.in);
        String in = scanner.nextLine();
        StringBuilder input = new StringBuilder();
        input.append(in);
        try {
            char line;
            int col;
            if (input.length() == 2) {
                line = input.charAt(0);
                col = (int) input.charAt(1) - '0';
            } else if (input.length() == 3) {
                line = input.charAt(0);
                col = (int) (input.charAt(1) - '0') * 10 + (int) input.charAt(2) - '0';
            } else {
                throw new Exception("Error! You entered the wrong coordinates! Try again:");
            }
            if (line >= 'A' && line <= 'J' && col >= 1 && col <= 10) {
                int i = line - 'A';
                int j = col - 1;
                if (enemyShip.getField(i, j) == shipCell){
                    char shipHit = 'X';
                    if (enemyField[i][j] != shipHit) {
                        enemyField[i][j] = shipHit;
                        enemyShip.setField(i, j, shipHit);
                    } else {
                        alreadyHit = true;
                    }
                    char ship = enemyShip.getShipField(i, j);
                    ShipType hitShip = ship == 'A' ? ShipType.AIRCRAFTCARRIER : ship == 'B' ? ShipType.BATTLESHIP
                            : ship == 'S' ? ShipType.SUBMARINE : ship == 'C' ? ShipType.CRUISER : ShipType.DESTROYER;
                    if (!alreadyHit) {
                        switch (hitShip) {
                            case AIRCRAFTCARRIER:
                                --AirshipCarrierHits;
                                if (AirshipCarrierHits == 0) {
                                    System.out.println("You sank a ship!");
                                } else if (remainingHits() > 0) {
                                    System.out.println("You hit a ship!");
                                }
                                break;
                            case BATTLESHIP:
                                --BattleshipHits;
                                if (BattleshipHits == 0) {
                                    System.out.println("You sank a ship!");
                                } else if (remainingHits() > 0) {
                                    System.out.println("You hit a ship!");
                                }
                                break;
                            case SUBMARINE:
                                --SubmarineHits;
                                if (SubmarineHits == 0) {
                                    System.out.println("You sank a ship!");
                                } else if (remainingHits() > 0) {
                                    System.out.println("You hit a ship!");
                                }
                                break;
                            case CRUISER:
                                --CruiserHits;
                                if (CruiserHits == 0) {
                                    System.out.println("You sank a ship!");
                                } else if (remainingHits() > 0) {
                                    System.out.println("You hit a ship!");
                                }
                                break;
                            case DESTROYER:
                                --DestroyerHits;
                                if (DestroyerHits == 0) {
                                    System.out.println("You sank a ship!");
                                } else if (remainingHits() > 0) {
                                    System.out.println("You hit a ship!");
                                }
                        }
                    } else {
                        System.out.println("You hit a ship!");
                    }

                } else {
                    char shipMiss = 'M';
                    enemyField[i][j] = shipMiss;
                    enemyShip.setField(i, j, shipMiss);
                    System.out.println("You missed!");
                }
            } else {
                throw new Exception("Error! You entered the wrong coordinates! Try again:");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            Hit(enemyShip);
        }
    }

    public int remainingHits() {
        return AirshipCarrierHits + BattleshipHits + SubmarineHits + CruiserHits + DestroyerHits;
    }

    public void printFields() {
        printField(enemyField);
        System.out.println("---------------------");
        printField(field);
    }


}