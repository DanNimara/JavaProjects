package machine;
import java.util.Scanner;

enum State {
    RUNNING, CHOOSE_COFFEE, FILL_WATER, FILL_MILK, FILL_BEANS, FILL_CUPS
}

public class CoffeeMachine {

    Scanner scanner;
    State state;
    int[] arr = new int[5];

    public CoffeeMachine() {
        this.arr[0] = 400;
        this.arr[1] = 540;
        this.arr[2] = 120;
        this.arr[3] = 9;
        this.arr[4] = 550;
        scanner = new Scanner(System.in);
        state = State.RUNNING;
    }

    public void makeCoffee(String action) {
        if ("back".equals(action)) {
            state = State.RUNNING;
            return;
        }
        int coffeeType = Integer.parseInt(action);
        if (coffeeType == 1) {
            buy(250, 0, 16, 4);
        } else if (coffeeType == 2) {
            buy(350, 75, 20, 7);
        } else if (coffeeType == 3) {
            buy(200, 100, 12, 6);
        }
    }

    private void buy(int water, int milk, int coffeeBeans, int money) {
        if (this.arr[0] < water) {
            System.out.println("Sorry, not enough water!");
            state = State.RUNNING;
            return;
        }
        if (this.arr[1] < milk) {
            System.out.println("Sorry, not enough milk!");
            state = State.RUNNING;
            return;
        }
        if (this.arr[2] < coffeeBeans) {
            System.out.println("Sorry, not enough coffee beans!");
            state = State.RUNNING;
            return;
        }
        if (this.arr[3] < 1) {
            System.out.println("Sorry, not enough disposable cups!");
            state = State.RUNNING;
            return;
        }
        System.out.println("I have enough resources, making you a coffee!");
        this.arr[3] -= 1;
        this.arr[0] -= water;
        this.arr[1] -= milk;
        this.arr[2] -= coffeeBeans;
        this.arr[4] += money;
        state = State.RUNNING;
    }
    public void fillWater(String arg) {
        int input = Integer.parseInt(arg);
        this.arr[0] += input;
        state = State.FILL_MILK;
    }

    public void fillMilk(String arg) {
        int input = Integer.parseInt(arg);
        this.arr[1] += input;
        state = State.FILL_BEANS;
    }

    public void fillBeans(String arg) {
        int input = Integer.parseInt(arg);
        this.arr[2] += input;
        state = State.FILL_CUPS;
    }

    public void fillCups(String arg) {
        int input = Integer.parseInt(arg);
        this.arr[3] += input;
        System.out.println();
        state = State.RUNNING;
    }
    public void take() {
        System.out.println("I gave you $" + this.arr[4]);
        this.arr[4] = 0;
    }
    public void remaining() {
        System.out.println("The coffee machine has:");
        System.out.println(this.arr[0] + " of water");
        System.out.println(this.arr[1] + " of milk");
        System.out.println(this.arr[2] + " of coffee beans");
        System.out.println(this.arr[3] + " of disposable cups");
        if (this.arr[4] > 0) {
            System.out.println("$" + this.arr[4] + " of money");
        } else {
            System.out.println(this.arr[4] + " of money");
        }
    }

    public void chooseAction(String arg) {
        if ("buy".equals(arg)) {
            state = State.CHOOSE_COFFEE;
        } else if ("fill".equals(arg)) {
            state = State.FILL_WATER;
        }
        if ("remaining".equals(arg)) {
            remaining();
        }
        if ("take".equals(arg)) {
            take();
        }
    }

    public void prompt() {
        switch (state) {
            case RUNNING:
                System.out.println("Write action (buy, fill, remaining, take, exit):");
                break;
            case CHOOSE_COFFEE:
                System.out.println("What do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino, back - to main menu:");
                break;
            case FILL_WATER:
                System.out.println("Write how many ml of water do you want to add:");
                break;
            case FILL_MILK:
                System.out.println("Write how many ml of milk do you want to add:");
                break;
            case FILL_BEANS:
                System.out.println("Write how many grams of coffee beans do you want to add:");
                break;
            case FILL_CUPS:
                System.out.println("Write how many disposable cups of coffee do you want to add:");
                break;
        }
    }

    public void processRequest(String arg) {
        switch (state) {
            case RUNNING:
                chooseAction(arg);
                break;
            case CHOOSE_COFFEE:
                makeCoffee(arg);
                break;
            case FILL_WATER:
                fillWater(arg);
                break;
            case FILL_MILK:
                fillMilk(arg);
                break;
            case FILL_BEANS:
                fillBeans(arg);
                break;
            case FILL_CUPS:
                fillCups(arg);
                break;
        }
    }

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        CoffeeMachine myCoffeeMachine = new CoffeeMachine();

        while (true) {
            myCoffeeMachine.prompt();

            String action = scan.nextLine();
            if ("exit".equals(action)) {
                break;
            }

            myCoffeeMachine.processRequest(action);
        }
    }
}
