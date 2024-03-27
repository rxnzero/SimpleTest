import java.util.Random;

public class GeneticAlgorithm {
    private static final String TARGET_STRING = "Hello, Genetic Algorithm!";
    private static final int POPULATION_SIZE = 100;
    private static final double MUTATION_RATE = 0.01;

    private static Random random = new Random();

    public static void main(String[] args) {
        char[] population = generateRandomPopulation(TARGET_STRING.length());

        int generation = 0;
        while (!String.valueOf(population).equals(TARGET_STRING)) {
            population = evolvePopulation(population);
            generation++;

//            System.out.println("Generation " + generation + ": " + String.valueOf(population));
        }

        System.out.println("Solution found in generation " + generation + ": " + String.valueOf(population));
    }

    private static char[] generateRandomPopulation(int length) {
        char[] population = new char[length];
        for (int i = 0; i < length; i++) {
            population[i] = generateRandomChar();
        }
        return population;
    }

    private static char generateRandomChar() {
        return (char) (random.nextInt(94) + 32); // ASCII printable characters
    }

    private static char[] evolvePopulation(char[] population) {
        char[] newPopulation = new char[population.length];
        for (int i = 0; i < population.length; i++) {
            char parent1 = selectParent(population);
            char parent2 = selectParent(population);
            char child = crossover(parent1, parent2);
            newPopulation[i] = mutate(child);
        }
        return newPopulation;
    }

    private static char selectParent(char[] population) {
        return population[random.nextInt(population.length)];
    }

    private static char crossover(char parent1, char parent2) {
        // Simple one-point crossover
        int pivot = random.nextInt(TARGET_STRING.length());
        StringBuilder child = new StringBuilder();
        for (int i = 0; i < TARGET_STRING.length(); i++) {
            if (i < pivot) {
                child.append(parent1);
            } else {
                child.append(parent2);
            }
        }
        return child.toString().toCharArray()[pivot];
    }

    private static char mutate(char gene) {
        if (random.nextDouble() < MUTATION_RATE) {
            return generateRandomChar();
        }
        return gene;
    }
}