package com.example.rucafe.models;

/**
 * Stores information for a coffee order.
 * @author Taze Balbosa, Yulie Ying
 */
public class Coffee extends MenuItem{

    private static final int ZERO_AMOUNT = 0;
    private static final double INITIAL_PRICE = CoffeeSize.SHORT.getPrice();
    private static final double ADDIN_PRICE = 0.30;

    private String coffeeSize;
    private int cream;
    private int milk;
    private int whippedCream;
    private int syrup;
    private int caramel;
    private int totalAddIns;

    /**
     * Creates a Coffee object that stores coffee order information.
     * @param quantity Initial quantity of coffee order.
     * @param size Initial size of coffee.
     * @param cream Initial number of cream add-ins.
     * @param milk Initial number of milk add-ins.
     * @param whippedCream Initial number of whipped cream add-ins.
     * @param syrup Initial number of syrup add-ins.
     * @param caramel Initial number of caramel add-ins.
     */
    public Coffee(int quantity, String size, int cream, int milk, int whippedCream,
                  int syrup, int caramel) {
        super(quantity);
        this.coffeeSize = size;
        this.cream = cream;
        this.milk = milk;
        this.whippedCream = whippedCream;
        this.syrup = syrup;
        this.caramel = caramel;
    }

    /**
     * Sets a quantity for the current coffee order.
     * @param amount Quantity of coffee order.
     */
    public void setQuantity(int amount) {
        quantity = amount;
    }

    /**
     * Sets the coffee size for the current coffee order.
     * @param coffeeSize Size of coffee order.
     */
    public void setCoffeeSize(String coffeeSize) {
        this.coffeeSize = coffeeSize;
    }

    /**
     * Adds an instance of the chosen coffee add-in.
     * @param obj Coffee add-in to be added.
     * @return true if add-in is allowed, false otherwise.
     */
    public boolean add(Object obj) {
        String selected = (String) obj;
        switch (selected) {
            case "Cream":
                cream++;
                break;
            case "Milk":
                milk++;
                break;
            case "Whipped Cream":
                whippedCream++;
                break;
            case "Syrup":
                syrup++;
                break;
            case "Caramel":
                caramel++;
                break;
            default:
                return false;
        }
        totalAddIns++;
        return true;
    }

    /**
     * Removes an instance of the chosen coffee add-in.
     * @param obj Coffee add-in to be removed.
     * @return true if add-in is removed successfully, false otherwise.
     */
    public boolean remove(Object obj) {
        String selected = (String) obj;
        switch (selected) {
            case "Cream":
                if (cream > ZERO_AMOUNT)
                    cream--;
                else return false;
                break;
            case "Milk":
                if (milk > ZERO_AMOUNT)
                    milk--;
                else return false;
                break;
            case "Whipped Cream":
                if (whippedCream > ZERO_AMOUNT)
                    whippedCream--;
                else return false;
                break;
            case "Syrup":
                if (syrup > ZERO_AMOUNT)
                    syrup--;
                else return false;
                break;
            case "Caramel":
                if (caramel > ZERO_AMOUNT)
                    caramel--;
                else return false;
                break;
            default:
                return false;
        }
        totalAddIns--;
        return true;
    }

    /**
     * Gets the amount of cream in the current coffee order.
     * @return Number of cream.
     */
    public int getCream() {
        return cream;
    }

    /**
     * Gets the amount of milk in the current coffee order.
     * @return Number of milk.
     */
    public int getMilk() {
        return milk;
    }

    /**
     * Gets the amount of whipped cream in the current coffee order.
     * @return Number of whipped cream.
     */
    public int getWhippedCream() {
        return whippedCream;
    }

    /**
     * Gets the amount of caramel in the current coffee order.
     * @return Number of caramel.
     */
    public int getCaramel() {
        return caramel;
    }

    /**
     * Gets the amount of syrup in the current coffee order.
     * @return Number of syrup.
     */
    public int getSyrup() {
        return syrup;
    }

    /**
     * Calculates the price of the current coffee order by coffee size and number of add-ins.
     */
    @Override
    public void itemPrice() {
        double coffeePrice = INITIAL_PRICE;
        switch (coffeeSize) {
            case "Short":
                coffeePrice = CoffeeSize.SHORT.getPrice() + (ADDIN_PRICE * totalAddIns);
                break;
            case "Tall":
                coffeePrice = CoffeeSize.TALL.getPrice() + (ADDIN_PRICE * totalAddIns);
                break;
            case "Grande":
                coffeePrice = CoffeeSize.GRANDE.getPrice() + (ADDIN_PRICE * totalAddIns);
                break;
            case "Venti":
                coffeePrice = CoffeeSize.VENTI.getPrice() + (ADDIN_PRICE * totalAddIns);
                break;
        }
        price = coffeePrice * quantity;
    }

    /**
     * Returns a string format of the current coffee order by size and add-in type and quantity.
     * @return String object of current coffee order.
     */
    @Override
    public String toString() {
        String orderedItem = "x" + quantity + " Coffee Size: ";
        int firstAddIn = ZERO_AMOUNT;
        orderedItem += coffeeSize;

        if (cream > ZERO_AMOUNT || milk > ZERO_AMOUNT || whippedCream > ZERO_AMOUNT ||
                caramel > ZERO_AMOUNT || syrup > ZERO_AMOUNT) {
            orderedItem += " (";

            if (cream > ZERO_AMOUNT) {
                orderedItem += "x" + cream + " Cream";
                firstAddIn++;
            }
            if (milk > ZERO_AMOUNT) {
                if (firstAddIn == 0)
                    firstAddIn = 1;
                else orderedItem += ", ";

                orderedItem += "x" + milk + " Milk";
            }
            if (whippedCream > ZERO_AMOUNT) {
                if (firstAddIn == 0)
                    firstAddIn = 1;
                else orderedItem += ", ";

                orderedItem += "x" + whippedCream + " Whipped Cream";
            }
            if (caramel > ZERO_AMOUNT) {
                if (firstAddIn == 0)
                    firstAddIn = 1;
                else orderedItem += ", ";

                orderedItem += "x" + caramel + " Caramel";
            }
            if (syrup > ZERO_AMOUNT) {
                if (firstAddIn == 1)
                    orderedItem += ", ";

                orderedItem += "x" + syrup + " Syrup";
            }

            orderedItem += ")";
        }

        return orderedItem;
    }
}
