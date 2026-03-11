package com.apps.quantitymeasurement.core;

enum ArithmeticOperation {

    ADD {
        @Override
        double compute(double a, double b) {
            return a + b;
        }
    },

    SUBTRACT {
        @Override
        double compute(double a, double b) {
            return a - b;
        }
    },

    DIVIDE {
        @Override
        double compute(double a, double b) {

            if (b == 0)
                throw new ArithmeticException("Division by zero");

            return a / b;
        }
    };

    abstract double compute(double a, double b);
}