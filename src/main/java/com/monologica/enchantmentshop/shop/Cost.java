package com.monologica.enchantmentshop.shop;

public class Cost {
    public enum Modifier {
        LINEAR, MUTLIPLICATIVE
    }

    private Modifier modifier;
    private double modifierValue;
    private double initialCost;

    public Cost(double initialCost, double modifierValue, Modifier modifier) {
        this.initialCost = initialCost;
        this.modifierValue = modifierValue;
        this.modifier = modifier;
    }

    // Calculates the cost for the next level of enchant, given a current level
    public double calculate(int currentLevel) {
        switch(modifier) {
            case LINEAR:
                return initialCost + modifierValue * currentLevel;

            case MUTLIPLICATIVE:
                return initialCost * Math.pow(modifierValue, currentLevel);
            }

        throw new IllegalStateException();
    }

    public String toString() {
        return String.format("{Type: %s, initial: %f, modifier %f}", modifier.toString(), initialCost, modifierValue);
    }

}
