import java.util.Arrays;
import java.util.Random;

public class MaxcutGeneticAlgorithm {
    private static final int[] TARGET_ARRAY = {1, 2, 3, 4, 5};
    private static final int POPULATION_SIZE = 100;
    private static final double MUTATION_RATE = 0.01;
    private static final int MAX_GENERATIONS = 1000;
    private static final int MAX_TIME_MILLISECONDS = 180000; // Maximum time in milliseconds

    private static Random random = new Random();

    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();

        int[][] population = generateRandomPopulation(TARGET_ARRAY.length);

        int generation = 0;
        while (generation < MAX_GENERATIONS && !containsArray(population, TARGET_ARRAY)) {
            long currentTime = System.currentTimeMillis();
            long elapsedTime = currentTime - startTime;
            if (elapsedTime > MAX_TIME_MILLISECONDS) {
                System.out.println("Maximum time exceeded. Exiting...");
                break;
            }

            population = evolvePopulation(population);
            generation++;

            System.out.println("Generation " + generation + ": " + Arrays.deepToString(population));
        }

        if (containsArray(population, TARGET_ARRAY)) {
            System.out.println("Solution found in generation " + generation);
        } else {
            System.out.println("No solution found within " + MAX_GENERATIONS + " generations.");
        }
    }

    private static int[][] generateRandomPopulation(int length) {
        int[][] population = new int[POPULATION_SIZE][length];
        for (int i = 0; i < POPULATION_SIZE; i++) {
            for (int j = 0; j < length; j++) {
                population[i][j] = random.nextInt(10); // Random numbers from 0 to 9
            }
        }
        return population;
    }

    private static int[][] evolvePopulation(int[][] population) {
        int[][] newPopulation = new int[POPULATION_SIZE][population[0].length];
        for (int i = 0; i < POPULATION_SIZE; i++) {
            int[] parent1 = selectParent(population);
            int[] parent2 = selectParent(population);
            int[] child = crossover(parent1, parent2);
            newPopulation[i] = mutate(child);
        }
        return newPopulation;
    }

    private static int[] selectParent(int[][] population) {
        return population[random.nextInt(population.length)];
    }

    private static int[] crossover(int[] parent1, int[] parent2) {
        // Simple one-point crossover
        int pivot = random.nextInt(TARGET_ARRAY.length);
        int[] child = new int[TARGET_ARRAY.length];
        for (int i = 0; i < TARGET_ARRAY.length; i++) {
            child[i] = (i < pivot) ? parent1[i] : parent2[i];
        }
        return child;
    }

    private static int[] mutate(int[] array) {
        int[] mutatedArray = Arrays.copyOf(array, array.length);
        for (int i = 0; i < mutatedArray.length; i++) {
            if (random.nextDouble() < MUTATION_RATE) {
                mutatedArray[i] = random.nextInt(10); // Random numbers from 0 to 9
            }
        }
        return mutatedArray;
    }

    private static boolean containsArray(int[][] population, int[] array) {
        for (int[] individual : population) {
            if (Arrays.equals(individual, array)) {
                return true;
            }
        }
        return false;
    }
}
