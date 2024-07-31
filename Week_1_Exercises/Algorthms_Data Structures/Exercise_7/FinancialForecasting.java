package financial_forecasting;

import java.util.Scanner;

public class FinancialForecasting {

    // Recursive method to calculate future value
    public static double calculateFutureValueRecursive(double initialAmount, double annualGrowthRate, int years) {
        // Base Case: If no years are left, return the initial amount
        if (years == 0) {
            return initialAmount;
        }
        // Recursive Case: Calculate future value for one less year
        return calculateFutureValueRecursive(initialAmount * (1 + annualGrowthRate), annualGrowthRate, years - 1);
    }

    // Iterative method to calculate future value
    public static double calculateFutureValueIterative(double initialAmount, double annualGrowthRate, int years) {
        double futureValue = initialAmount;
        for (int i = 0; i < years; i++) {
            futureValue *= (1 + annualGrowthRate);
        }
        return futureValue;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // User input for initial amount
        System.out.print("Enter the initial amount: ");
        double initialAmount = scanner.nextDouble();

        // User input for annual growth rate
        System.out.print("Enter the annual growth rate (as a percentage, e.g., 5 for 5%): ");
        double annualGrowthRatePercentage = scanner.nextDouble();
        double annualGrowthRate = annualGrowthRatePercentage / 100.0;

        // User input for number of years
        System.out.print("Enter the number of years: ");
        int years = scanner.nextInt();

        // Calculate future value using recursive method
        double futureValueRecursive = calculateFutureValueRecursive(initialAmount, annualGrowthRate, years);
        System.out.printf("The future value of the investment (recursive) is: %.2f%n", futureValueRecursive);

        // Calculate future value using iterative method
        double futureValueIterative = calculateFutureValueIterative(initialAmount, annualGrowthRate, years);
        System.out.printf("The future value of the investment (iterative) is: %.2f%n", futureValueIterative);

        // Close scanner
        scanner.close();
    }
}
