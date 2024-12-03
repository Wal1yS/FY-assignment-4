import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * This is our main class where we read animals and simulate days to
 * get the list of exceptions and sounds
 * @author Valerii Tiniakov*/
public class Main {
    /**
     * const variable for max number of days
     */
    public static final int MAX_DAYS = 30;
    /**
     * const variable for min number of days
     */
    public static final int MIN_DAYS = 1;
    /**
     * const variable for min number of animals
     */
    public static final int MIN_NUMBER_OF_ANIMALS = 1;
    /**
     * const variable for max number of animals
     */
    public static final int MAX_NUMBER_OF_ANIMALS = 20;
    /**
     * const variable for getting the type of the animal from separated string
     */
    public static final int TYPE = 0;
    /**
     * const variable for getting the weight of the animal from separated string
     */
    public static final int ARG1 = 1;
    /**
     * const variable for getting the speed of the animal from separated string
     */
    public static final int ARG2 = 2;
    /**
     * const variable for getting the energy of the animal from separated string
     */
    public static final int ARG3 = 3;
    /**
     * const variable for checking if the number of argument in separated string is correct
     */
    public static final int CTN_OF_ARG = 4;

    /**
     * main function where program is executing: read the input file, simulate days etc.
     * @param args - arguments
     * @exception IOException - for preventing problems with input/output
     * @exception InvalidInputsException - for checking the validity of inputs data
     * {@link #runSimulation(int, float, List)}
     * {@link #readAnimals()}
     */
    public static void main(String[] args) throws IOException, InvalidInputsException {
        BufferedReader reader = new BufferedReader(new FileReader("input.txt"));
        try {
            try {
                int days = Integer.parseInt(reader.readLine());
                float grassAmount = Float.parseFloat(reader.readLine());
                int numberOfAnimals = Integer.parseInt(reader.readLine());
                String line = reader.readLine();
                Field field = null;
                try {
                    if (((days > MAX_DAYS) || (days < MIN_DAYS))) {
                        throw new InvalidInputsException();
                    }
                    field = new Field(grassAmount);
                    if (((numberOfAnimals < MIN_NUMBER_OF_ANIMALS) || (numberOfAnimals > MAX_NUMBER_OF_ANIMALS))) {
                        throw new InvalidInputsException();
                    }
                    if (readAnimals() != null) {
                        List<Animal> animals = (List<Animal>) readAnimals();
                        runSimulation(days, grassAmount, animals);
                    }
                } catch (GrassOutOfBoundsException e) {
                    System.out.println(e.getMessage());
                } catch (InvalidNumberOfAnimalParametersException e) {
                    System.out.println(e.getMessage());
                } catch (InvalidInputsException e) {
                    System.out.println(e.getMessage());
                }
            } catch (NumberFormatException e) {
                throw new InvalidInputsException();
            }
        } catch (Exception e) {
            System.out.println("Invalid inputs");
        }

    }

    /**
     * Function that transform strings with animal parameters into the List of objects with its class
     * @return animals - list of objects
     * @exception IOException - for preventing problems with input/output
     * @exception InvalidInputsException - for checking the validity of inputs data
     * @exception InvalidNumberOfAnimalParametersException - prevent invalid number of Animal parameters     */
    private static List<Animal> readAnimals() throws IOException, InvalidInputsException,
            InvalidNumberOfAnimalParametersException {
        BufferedReader reader = new BufferedReader(new FileReader("input.txt"));
        List<Animal> animals = new ArrayList<Animal>();
        String line = reader.readLine();
        line = reader.readLine();
        line = reader.readLine();
        int numberOfAnimals = Integer.parseInt(line);
        line = reader.readLine();
        int ctn = 0;
        while ((line != null)) {
            ctn++;
            if (ctn > numberOfAnimals) {
                throw new InvalidInputsException();
            }
            String[] partscomm = line.split(" ");
            try {
                switch (partscomm[TYPE]) {
                    case "Lion":
                        if (partscomm.length == CTN_OF_ARG) {
                            try {
                                float weight = Float.parseFloat(partscomm[ARG1]);
                                float speed = Float.parseFloat(partscomm[ARG2]);
                                float energy = Float.parseFloat(partscomm[ARG3]);
                            } catch (NumberFormatException e) {
                                throw new InvalidInputsException();
                            }
                        } else {
                            throw new InvalidNumberOfAnimalParametersException();
                        }
                        Lion lion = new Lion(Float.parseFloat(partscomm[ARG1]),
                                Float.parseFloat(partscomm[ARG2]), Float.parseFloat(partscomm[ARG3]));
                        animals.add(lion);
                        break;
                    case "Boar":
                        if (partscomm.length == CTN_OF_ARG) {
                            try {
                                float weight = Float.parseFloat(partscomm[ARG1]);
                                float speed = Float.parseFloat(partscomm[ARG2]);
                                float energy = Float.parseFloat(partscomm[ARG3]);
                            } catch (NumberFormatException e) {
                                throw new InvalidInputsException();
                            }
                        } else {
                            throw new InvalidNumberOfAnimalParametersException();
                        }
                        Boar boar = new Boar(Float.parseFloat(partscomm[ARG1]),
                                Float.parseFloat(partscomm[ARG2]), Float.parseFloat(partscomm[ARG3]));
                        animals.add(boar);
                        break;
                    case "Zebra":
                        if (partscomm.length == CTN_OF_ARG) {
                            try {
                                float weight = Float.parseFloat(partscomm[ARG1]);
                                float speed = Float.parseFloat(partscomm[ARG2]);
                                float energy = Float.parseFloat(partscomm[ARG3]);
                            } catch (NumberFormatException e) {
                                throw new InvalidInputsException();
                            }
                        } else {
                            throw new InvalidNumberOfAnimalParametersException();
                        }
                        Zebra zebra = new Zebra(Float.parseFloat(partscomm[ARG1]),
                                Float.parseFloat(partscomm[ARG2]), Float.parseFloat(partscomm[ARG3]));
                        animals.add(zebra);
                        break;
                    default:
                        throw new InvalidInputsException();
                }
            } catch (WeightOutOfBoundsException e) {
                System.out.println(e.getMessage());
                return null;
            } catch (SpeedOutOfBoundsException e) {
                System.out.println(e.getMessage());
                return null;
            } catch (EnergyOutOfBoundsException e) {
                System.out.println(e.getMessage());
                return null;
            }

            line = reader.readLine();
        }
        if (ctn < numberOfAnimals) {
            throw new InvalidInputsException();
        }
        return animals;
    }
    /**
     * Function that simulates the days: hunting, eating grass, decreasing energy at the end of the day etc.
     * @param days - number of days that function need to simulate
     * @param grassAmount - amount of grass on the field before animals started to eat it
     * @param animals - list of animals
     * {@link #removeDeadAnimals(List)}
     */
    private static void runSimulation(int days, float grassAmount, List<Animal> animals) {
        removeDeadAnimals(animals);
        Field field = null;
        try {
            field = new Field(grassAmount);
        } catch (GrassOutOfBoundsException e) {
            System.out.println(e.getMessage());
        }
        for (int i = 0; i < days; i++) {
            for (int j = 0; j < animals.size(); j++) {
                if (j < animals.size() - 1) {
                    if (animals.get(j).getClass() == Lion.class) {
                        Lion lion = (Lion) animals.get(j);
                        try {
                            lion.choosePrey(animals, lion);
                            lion.huntPrey(animals.get(j), animals.get(j + 1));
                            removeDeadAnimals(animals);
                        } catch (SelfHuntingException e) {
                            System.out.println(e.getMessage());
                        } catch (TooStrongPreyException e) {
                            System.out.println(e.getMessage());
                        } catch (CannibalismException e) {
                            System.out.println(e.getMessage());
                        }
                    } else if (animals.get(j).getClass() == Zebra.class) {
                        Zebra zebra = (Zebra) animals.get(j);
                        zebra.grazeInTheFields(zebra, field);
                    } else if (animals.get(j).getClass() == Boar.class) {
                        Boar boar = (Boar) animals.get(j);
                        boar.grazeInTheFields(boar, field);
                        try {
                            boar.choosePrey(animals, boar);
                            boar.huntPrey(animals.get(j), animals.get(j + 1));
                            removeDeadAnimals(animals);
                        } catch (SelfHuntingException e) {
                            System.out.println(e.getMessage());
                        } catch (TooStrongPreyException e) {
                            System.out.println(e.getMessage());
                        } catch (CannibalismException e) {
                            System.out.println(e.getMessage());
                        }
                    }
                    continue;
                }
                if (j == animals.size() - 1) {
                    if (animals.get(j).getClass() == Lion.class) {
                        Lion lion = (Lion) animals.get(j);
                        try {
                            lion.choosePrey(animals, lion);
                            lion.huntPrey(animals.get(j), animals.get(0));
                            removeDeadAnimals(animals);
                        } catch (SelfHuntingException e) {
                            System.out.println(e.getMessage());
                        } catch (TooStrongPreyException e) {
                            System.out.println(e.getMessage());
                        } catch (CannibalismException e) {
                            System.out.println(e.getMessage());
                        }
                    } else if (animals.get(j).getClass() == Zebra.class) {
                        Zebra zebra = (Zebra) animals.get(j);
                        zebra.grazeInTheFields(zebra, field);
                    } else if (animals.get(j).getClass() == Boar.class) {
                        Boar boar = (Boar) animals.get(j);
                        boar.grazeInTheFields(boar, field);
                        try {
                            boar.choosePrey(animals, boar);
                            boar.huntPrey(animals.get(j), animals.get(0));
                            removeDeadAnimals(animals);
                        } catch (SelfHuntingException e) {
                            System.out.println(e.getMessage());
                        } catch (TooStrongPreyException e) {
                            System.out.println(e.getMessage());
                        } catch (CannibalismException e) {
                            System.out.println(e.getMessage());
                        }
                    }
                }


            }
            removeDeadAnimals(animals);
            for (Animal animal : animals) {
                animal.decrementEnergy();
            }
            removeDeadAnimals(animals);
            field.makeGrassGrow();
        }
        for (Animal animal : animals) {
            animal.makeSound();
        }
    }
    /**
     * function for printing the list of animals
     * @param animals - list of animals
     */
    private static void printAnimals(List<Animal> animals) {
        for (Animal animal: animals) {
            System.out.println(animal);
        }
    }
    /**
     * function that is removing animal from the list if its energy became lower or equal to zero 0
     * @param animals - list of animals
     */
    private static void removeDeadAnimals(List<Animal> animals) {
        Iterator<Animal> iter = animals.iterator();
        while (iter.hasNext()) {
            Animal ex = iter.next();
            if (ex.getEnergy() <= 0) {
                iter.remove();
            }
        }
    }
}




/**
 * Abstract class that is a parent-class to all animals, includes main methods and
 * fields that every animal should have
 */
abstract class Animal {
    /**
     * const variable for min speed of an animal
     */
    public static final float MIN_SPEED = 5f;
    /**
     * const variable for max speed of an animal
     */
    public static final float MAX_SPEED = 60f;
    /**
     * const variable for min energy of an animal
     */
    public static final float MIN_ENERGY = 0f;
    /**
     * const variable for max energy of an animal
     */
    public static final float MAX_ENERGY = 100f;
    /**
     * const variable for min weight of an animal
     */
    public static final float MIN_WEIGHT = 5f;
    /**
     * const variable for max weight of an animal
     */
    public static final float MAX_WEIGHT = 200f;
    /**
     * variable for keeping information about animal weight
     */
    private float weight;
    /**
     * variable for keeping information about animal speed
     */
    private float speed;
    /**
     * variable for keeping information about animal energy
     */
    private float energy;
    /**
     * variable for keeping information about sound that animal makes
     */
    private AnimalSound sound;
    /**
     * Constructor for setting animal parameters
     * @param weight - weight of an animal
     * @param speed - speed of an animal
     * @param energy - energy of an animal
     * @exception SpeedOutOfBoundsException - for preventing the incorrect number of speed in input
     * @exception EnergyOutOfBoundsException - for preventing the incorrect number of energy in input
     * @exception WeightOutOfBoundsException - for preventing the incorrect number of weight in input
     */
    protected Animal(float weight, float speed, float energy) throws SpeedOutOfBoundsException,
            EnergyOutOfBoundsException, WeightOutOfBoundsException {
        if (weight < MIN_WEIGHT || weight > MAX_WEIGHT) {
            throw new WeightOutOfBoundsException();
        } else if (speed < MIN_SPEED || speed > MAX_SPEED) {
            throw new SpeedOutOfBoundsException();
        } else if (energy < MIN_ENERGY || energy > MAX_ENERGY) {
            throw new EnergyOutOfBoundsException();
        } else {
            this.weight = weight;
            this.speed = speed;
            this.energy = energy;
        }
    }
    /**
     * Getter for energy of the animal
     * @return energy - energy level of the animal
     */
    public float getEnergy() {
        return energy;
    }
    /**
     * Setter for energy of the animal
     * @param energy - number of energy
     */
    public void setEnergy(float energy) {
        this.energy = energy;
    }
    /**
     * Getter for weight of the animal
     * @return weight - weight of the animal
     */
    public float getWeight() {
        return weight; }
    /**
     * Getter for speed of the animal
     * @return speed - speed of the animal
     */
    public float getSpeed() {
        return speed; }
    /**
     * Function for printing out the sound that animal makes
     */
    public void makeSound() {
        System.out.println(sound.getSound());
    }
    /**
     * Function that decrease energy level of animal by 1
     */
    public void decrementEnergy() {
        this.energy -= 1;
    }
    /**
     * Function for printing out the message
     * @param animals - list of animals
     * @param field - field
     */
    public void eat(List<Animal> animals, Field field) {
        System.out.println("Eating");
    }
}
/** enum with 3 possible sound, each for 1 animal */
enum AnimalSound {
    /**for Lion*/
    LION("Roar"),
    /**for Zebra*/
    ZEBRA("Ihoho"),
    /**for Boar*/
    BOAR("Oink");
    /**variable for keeping information about sound that animal makes*/
    private final String sound;
    /**Constructor for setting the value of the sound
     * @param sound - sound that animal makes*/
    AnimalSound(String sound) {
        this.sound = sound;
    }
    /**Getter for sound that animal makes
     * @return sound - thhe sound*/
    public String getSound() {
        return sound;
    }
}






/** Class that extends all Animal variables and method, but specify them
 * and add new one for Zebra especially*/
class Zebra extends Animal implements Herbivore {
    /** const variable for keeping information about how many grass zebra eats*/
    public static final double EATING_GRASS = 0.1;
    /** const variable for keeping zebra sound */
    private final AnimalSound sound = AnimalSound.ZEBRA;
    /**
     * Constructor for setting zebra parameters
     * @param weight - weight of a zebra
     * @param speed - speed of a zebra
     * @param energy - energy of a zebra
     * @exception SpeedOutOfBoundsException - for preventing the incorrect number of speed in input
     * @exception EnergyOutOfBoundsException - for preventing the incorrect number of energy in input
     * @exception WeightOutOfBoundsException - for preventing the incorrect number of weight in input
     */
    public Zebra(float weight, float speed, float energy) throws SpeedOutOfBoundsException,
            EnergyOutOfBoundsException, WeightOutOfBoundsException {
        super(weight, speed, energy);
    }
    /**
     * Function that overrides grazeInTheFields method in Herbivore interface, it defines possibility of eating grass
     * and calculates the energy level after the eating
     * @param graze - animal that is going to eat
     * @param field - field where it is going to happen
     */
    @Override
    public void grazeInTheFields(Animal graze, Field field) {
        if (field.getGrassAmount() >= EATING_GRASS * graze.getWeight()) {
            if ((graze.getEnergy() + EATING_GRASS * graze.getWeight()) <= MAX_ENERGY) {
                graze.setEnergy((float) (graze.getEnergy() + EATING_GRASS * graze.getWeight()));
            } else {
                graze.setEnergy(MAX_ENERGY);
            }
            field.setGrassAmount((float) (field.getGrassAmount() - EATING_GRASS * graze.getWeight()));
        }
    }
    /**
     * Function for printing out the zebra sound
     */
    @Override
    public void makeSound() {
        System.out.println(sound.getSound());
    }
}
/** Class that extends all Animal variables and method, but specify them
 * and add new one for Lion especially*/
class Lion extends Animal implements Carnivore {
    /** const variable for keeping lion sound */
    private final AnimalSound sound = AnimalSound.LION;
    /**
     * Constructor for setting lion parameters
     * @param weight - weight of a zebra
     * @param speed - speed of a zebra
     * @param energy - energy of a zebra
     * @exception SpeedOutOfBoundsException - for preventing the incorrect number of speed in input
     * @exception EnergyOutOfBoundsException - for preventing the incorrect number of energy in input
     * @exception WeightOutOfBoundsException - for preventing the incorrect number of weight in input
     */
    public Lion(float weight, float speed, float energy) throws SpeedOutOfBoundsException,
            EnergyOutOfBoundsException, WeightOutOfBoundsException {
        super(weight, speed, energy);
    }
    /**
     * Function that overrides choosePrey method in Carnivore interface, it prevents animal from hunting itself
     * @param animals - list of animals
     * @param hunter - hunter
     * @exception SelfHuntingException - for preventing animal from hunting itself
     * @return animal if all is ok
     */
    @Override
    public <T> Animal choosePrey(List<Animal> animals, T hunter) throws SelfHuntingException {
        if (animals.size() == 1) {
            throw new SelfHuntingException();
        } else {
            return animals.get(0);
        }
    }
    /**
     * Function that overrides huntPrey method in Carnivore interface, it defines possibility of eating the prey
     * and calculates the energy level after the eating
     * @param hunter - animal that is going to hunt
     * @param prey - animal that is going to be eaten
     * @exception TooStrongPreyException - to indicate that prey is too strong to be hunted
     * @exception CannibalismException - to prevent animal from hunting another animal with the same type
     */
    @Override
    public void huntPrey(Animal hunter, Animal prey) throws TooStrongPreyException, CannibalismException {
        if (hunter.getClass() == prey.getClass()) {
            throw new CannibalismException();
        }
        if ((hunter.getEnergy() <= prey.getEnergy()) && (hunter.getSpeed() <= prey.getSpeed())) {
            throw new TooStrongPreyException();
        }
        if ((hunter.getEnergy() + prey.getWeight()) <= MAX_ENERGY) {
            hunter.setEnergy(hunter.getEnergy() + prey.getWeight());
        } else {
            hunter.setEnergy(MAX_ENERGY);
        }
        prey.setEnergy(MIN_ENERGY);
    }
    /**
     * Function for printing out the lion sound
     */
    @Override
    public void makeSound() {
        System.out.println(sound.getSound());
    }
}
/** Class that extends all Animal variables and method, but specify them
 * and add new one for Boar especially*/
class Boar extends Animal implements Omnivore {
    /** const variable for keeping information about how many grass boar eats*/
    public static final double EATING_GRASS = 0.1;
    /** const variable for keeping boar sound */
    private final AnimalSound sound = AnimalSound.BOAR;
    /**
     * Constructor for setting boar parameters
     * @param weight - weight of a zebra
     * @param speed - speed of a zebra
     * @param energy - energy of a zebra
     * @exception SpeedOutOfBoundsException - for preventing the incorrect number of speed in input
     * @exception EnergyOutOfBoundsException - for preventing the incorrect number of energy in input
     * @exception WeightOutOfBoundsException - for preventing the incorrect number of weight in input
     */
    public Boar(float weight, float speed, float energy) throws SpeedOutOfBoundsException, EnergyOutOfBoundsException,
            WeightOutOfBoundsException {
        super(weight, speed, energy);
    }
    /**
     * Function that overrides choosePrey method in Carnivore interface, it prevents animal from hunting itself
     * @param animals - list of animals
     * @param hunter - hunter
     * @exception SelfHuntingException - for preventing animal from hunting itself
     * @return animal if all is ok
     */
    @Override
    public <T> Animal choosePrey(List<Animal> animals, T hunter) throws SelfHuntingException {
        if (animals.size() == 1) {
            throw new SelfHuntingException();
        } else {
            return animals.get(0);
        }
    }
    /**
     * Function that overrides huntPrey method in Carnivore interface, it defines possibility of eating the prey
     * and calculates the energy level after the eating
     * @param hunter - animal that is going to hunt
     * @param prey - animal that is going to be eaten
     * @exception TooStrongPreyException - to indicate that prey is too strong to be hunted
     * @exception CannibalismException - to prevent animal from hunting another animal with the same type
     */
    @Override
    public void huntPrey(Animal hunter, Animal prey) throws TooStrongPreyException, CannibalismException {
        if (hunter.getClass() == prey.getClass()) {
            throw new CannibalismException();
        }
        if ((hunter.getEnergy() <= prey.getEnergy()) && (hunter.getSpeed() <= prey.getSpeed())) {
            throw new TooStrongPreyException();
        }

        if ((hunter.getEnergy() + prey.getWeight()) <= MAX_ENERGY) {
            hunter.setEnergy(hunter.getEnergy() + prey.getWeight());
        } else {
            hunter.setEnergy(MAX_ENERGY);
        }
        prey.setEnergy(MIN_ENERGY);
    }
    /**
     * Function that overrides grazeInTheFields method in Herbivore interface, it defines possibility of eating grass
     * and calculates the energy level after the eating
     * @param graze - animal that is going to eat
     * @param field - field where it is going to happen
     */
    @Override
    public void grazeInTheFields(Animal graze, Field field) {
        if (field.getGrassAmount() >= EATING_GRASS * graze.getWeight()) {
            if ((graze.getEnergy() + EATING_GRASS * graze.getWeight()) <= MAX_ENERGY) {
                graze.setEnergy((float) (graze.getEnergy() + EATING_GRASS * graze.getWeight()));
            } else {
                graze.setEnergy(MAX_ENERGY);
            }
            field.setGrassAmount((float) (field.getGrassAmount() - EATING_GRASS * graze.getWeight()));
        }
    }
    /**
     * Function for printing out the boar sound
     */
    @Override
    public void makeSound() {
        System.out.println(sound.getSound());
    }
}


/** Class for filed, containing the variables and methods for working with filed */
class Field {
    /**
     * const variable for max grass amount
     */
    public static final int MAX_GRASS = 100;
    /**
     * const variable for min grass amount
     */
    public static final int MIN_GRASS = 0;
    /**
     * const variable for keeping information about grass growing coefficient
     */
    public static final int GROWING = 2;
    /**
     * variable for keeping information about current amount of grass
     */
    private float grassAmount;
    /**
     * Constructor for setting filed parameters
     * @param grassAmount - amount of grass on the field
     * @exception GrassOutOfBoundsException - for preventing the incorrect amount of grass in input
     */
    public Field(float grassAmount) throws GrassOutOfBoundsException {
        if ((grassAmount < MIN_GRASS) || (grassAmount > MAX_GRASS)) {
            throw new GrassOutOfBoundsException();
        } else {
            this.grassAmount = grassAmount;
        }
    }
    /**
     * Getter for current amount of grass
     * @return amount of grass
     */
    public float getGrassAmount() {
        return grassAmount;
    }
    /**
     * Setter for current amount of grass
     * @param grassAmount - amount of grass
     */
    public void setGrassAmount(float grassAmount) {
        this.grassAmount = grassAmount;
    }
    /**
     * Function that making grass grow by special const coefficient and checks if it reached the max possible value
     */
    public void makeGrassGrow() {
        if ((this.getGrassAmount() * GROWING) > MAX_GRASS) {
            this.setGrassAmount(MAX_GRASS);
        } else {
            this.setGrassAmount(this.getGrassAmount() * GROWING);
        }
    }
}






/** Interface with choosePrey and huntPrey methods that will be used in:
 * @see Lion
 * @see Boar
 */
interface Carnivore {
    /**
     * Function that prevents animal from hunting itself
     * @param animals - list of animals
     * @param hunter - hunter
     * @exception SelfHuntingException - for preventing animal from hunting itself
     * @param <T> - Type
     * @return animal - if all is ok*/
    public <T> Animal choosePrey(List<Animal> animals, T hunter) throws SelfHuntingException;
    /**
     * Function defines possibility of eating the prey
     * and calculates the energy level after the eating
     * @param hunter - animal that is going to hunt
     * @param prey - animal that is going to be eaten
     * @exception TooStrongPreyException - to indicate that prey is too strong to be hunted
     * @exception CannibalismException - to prevent animal from hunting another animal with the same type*/
    public void huntPrey(Animal hunter, Animal prey) throws TooStrongPreyException,
         CannibalismException;
}
/** Interface with grazeInTheFields methods that will be used in:
 * @see Zebra
 * @see Boar
 * */
interface Herbivore {
    /**
     * Function that defines possibility of eating grass
     * and calculates the energy level after the eating
     * @param graze - animal that is going to eat
     * @param field - field where it is going to happen
     */
    public void grazeInTheFields(Animal graze, Field field);
}
/** Interface that exteends method from both Carnivore and Herbivore, will be used in:
 * @see Boar
 * */
interface Omnivore extends Carnivore, Herbivore { }






/** Exception class that is created to prevent invalid input and print out appropriate message*/
class InvalidInputsException extends Exception {
    @Override
    public String getMessage() {
        return "Invalid inputs";
    }
}
/** Exception class that is created to prevent invalid number of Animal parameters and print out appropriate message*/
class InvalidNumberOfAnimalParametersException extends Exception {
    @Override
    public String getMessage() {
        return "Invalid number of animal parameters";
    }
}
/** Exception class that is created to prevent invalid amount of grass and print out appropriate message*/
class GrassOutOfBoundsException extends Exception {
    @Override
    public String getMessage() {
        return "The grass is out of bounds";
    }
}
/** Exception class that is created to prevent hunter from hunting itself and print out appropriate message*/
class SelfHuntingException extends Exception {
    @Override
    public String getMessage() {
        return "Self-hunting is not allowed";
    }
}
/** Exception class that is created to prevent hunter from hunting the prey
 * that is stronger than him and print out appropriate message*/
class TooStrongPreyException extends Exception {
    @Override
    public String getMessage() {
        return "The prey is too strong or too fast to attack";
    }
}
/** Exception class that is created to prevent invalid input and print out appropriate message*/
class CannibalismException extends Exception {
    @Override
    public String getMessage() {
        return "Cannibalism is not allowed";
    }
}
/** Exception class that is created to prevent invalid number for animal weight and print out appropriate message*/
class WeightOutOfBoundsException extends Exception {
    @Override
    public String getMessage() {
        return "The weight is out of bounds";
    }
}
/** Exception class that is created to prevent invalid number for animal speed and print out appropriate message*/
class SpeedOutOfBoundsException extends Exception {
    @Override
    public String getMessage() {
        return "The speed is out of bounds";
    }
}
/** Exception class that is created to prevent invalid number for animal energy and print out appropriate message*/
class EnergyOutOfBoundsException extends Exception {
    @Override
    public String getMessage() {
        return "The energy is out of bounds";
    }
}
