import javax.swing.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ItemSearcher {

    private static final String filePath = "./inventory_v2.txt";
    private static final Icon icon = new ImageIcon("./the_greenie_geek.png");
    private static Inventory inventory;
    private static final String appName = "Greenie Geek";
    private static final String[] pollinators = {
            "Crimson Crisp", "Gala", "Granny Smith", "Pixie Crunch", "Red Delicious",
            "Red Fuji", "Honeycrisp", "Golden Delicious", "Fuji", "Bartlett", "Bosc",
            "D’Anjou", "Redhaven", "Contender", "Methley", "Satsuma", "Elberta" , "I Don't Mind"
    };

    public static void main(String[] args) {
        inventory = loadInventory(filePath);

        FruitingPlantProperties dreamPlant = getFilters();
        processSearchResults(dreamPlant);
        System.exit(0);
    }

    public static FruitingPlantProperties getFilters() {
        // Category (mandatory)
        Category category = (Category) JOptionPane.showInputDialog(
                null, "Please select your preferred category", appName,
                JOptionPane.QUESTION_MESSAGE, icon, Category.values(), null);
        if (category == null) System.exit(0); // Exit if no category is selected, as this is mandatory

        // Type (optional)
        PlantType type = getPlantType(category);

        // Dwarf (mandatory for Pome, Stone Fruit, Citrus)
        DwarfType dwarf = getDwarfType(category);

        // Training System (optional for Vine)
        TrainingSystem trainingSystem = getTrainingSystem(category);

        // Pollinators (optional for Pome, Stone Fruit)
        Set<String> selectedPollinators = getPollinators(category);

        // Pot size (mandatory)
        int potSize = getPotSize();

        // Price range (mandatory)
        float minPrice = getPriceFromUser("min");
        float maxPrice = getPriceFromUser("max", minPrice);

        // Instantiate the FruitingPlantProperties using the gathered input
        Map<Integer, Float> potSizeToPrice = new HashMap<>();
        potSizeToPrice.put(potSize, minPrice); // Assume user is filtering by this single pot size and price range

        return new DreamFruitingPlant(category, type, dwarf, trainingSystem, selectedPollinators, potSizeToPrice, "");
    }

    private static PlantType getPlantType(Category category) {
        PlantType[] availableTypes = FilterOptions.getAvailableTypes(category);
        PlantType type = (PlantType) JOptionPane.showInputDialog(
                null, "Please select your preferred type (or Skip)", appName,
                JOptionPane.QUESTION_MESSAGE, icon, availableTypes, null);
        return type != null ? type : null;  // Return null if user skips selection
    }

    private static DwarfType getDwarfType(Category category) {
        if (category == Category.CITRUS || category == Category.POME || category == Category.STONE_FRUIT) {
            DwarfType dwarf = (DwarfType) JOptionPane.showInputDialog(
                    null, "Would you like a dwarf tree?", appName,
                    JOptionPane.QUESTION_MESSAGE, icon, DwarfType.values(),null);
            return dwarf != null ? dwarf : DwarfType.DONT_MIND;  // Handle null as 'DONT_MIND'
        }
        return DwarfType.DONT_MIND;  // Default to 'DONT_MIND' for other categories
    }

    private static TrainingSystem getTrainingSystem(Category category) {
        if (category == Category.VINE) {
            TrainingSystem trainingSystem = (TrainingSystem) JOptionPane.showInputDialog(
                    null, "Please select a training system (or Skip)", appName,
                    JOptionPane.QUESTION_MESSAGE, icon, TrainingSystem.values(), null);
            return trainingSystem != null ? trainingSystem : null;  // Return null if user skips selection
        }
        return null;  // Return null if not a VINE category
    }

    private static Set<String> getPollinators(Category category) {
        Set<String> selectedPollinators = new HashSet<>();
        if (category == Category.POME || category == Category.STONE_FRUIT) {
            // Get available pollinators and convert to a list for easier manipulation
            List<String> availablePollinatorsList = new ArrayList<>(Arrays.asList(FilterOptions.getAvailablePollinators().toArray(new String[0])));

            // Add "I Don't Mind" as the last option
            availablePollinatorsList.add("I Don't Mind");

            // Convert back to an array for use with JOptionPane (if needed)
            String[] availablePollinators = availablePollinatorsList.toArray(new String[0]);

            boolean addMore = true;
            while (addMore) {
                String selectedPollinator = (String) JOptionPane.showInputDialog(
                        null, "Select Pollinator", appName,
                        JOptionPane.QUESTION_MESSAGE, icon, availablePollinators, availablePollinators[0]);
                if (selectedPollinator != null && !selectedPollinator.equals("I Don't Mind")) {
                    selectedPollinators.add(selectedPollinator);
                    int response = JOptionPane.showConfirmDialog(null, "Would you like to add another pollinator?", appName, JOptionPane.YES_NO_OPTION);
                    addMore = (response == JOptionPane.YES_OPTION);
                } else {
                    addMore = false;  // Stop if "I Don't Mind" is selected or dialog is canceled
                }
            }
        }
        return selectedPollinators;  // Return selected pollinators or empty set if "I Don't Mind" or not applicable
    }


    private static int getPotSize() {
        int potSize = Integer.parseInt((String) JOptionPane.showInputDialog(
                null, "Pot size (inch)? **min 8 inch", appName,
                JOptionPane.QUESTION_MESSAGE, icon, null, null));
        if (potSize < 8) {
            JOptionPane.showMessageDialog(null, "Invalid input. Please enter a positive number greater than 8.",
                    appName, JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }
        return potSize;
    }

    private static float getPriceFromUser(String type) {
        float price = -1;
        while (price < 0) {
            String userInput = (String) JOptionPane.showInputDialog(null,
                    "Enter " + type + " price range value:", appName,
                    JOptionPane.QUESTION_MESSAGE, icon, null, null);
            if (userInput == null) System.exit(0);
            try {
                price = Float.parseFloat(userInput);
                if (price < 0) JOptionPane.showMessageDialog(null, "Price must be >= 0.", appName, JOptionPane.ERROR_MESSAGE);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Invalid input. Please try again.", appName, JOptionPane.ERROR_MESSAGE);
            }
        }
        return price;
    }

    private static float getPriceFromUser(String type, float minPrice) {
        float price = -1;
        while (price < minPrice) {
            String userInput = (String) JOptionPane.showInputDialog(null,
                    "Enter " + type + " price range value:", appName,
                    JOptionPane.QUESTION_MESSAGE, icon, null, null);
            if (userInput == null) System.exit(0);
            try {
                price = Float.parseFloat(userInput);
                if (price < minPrice) JOptionPane.showMessageDialog(null, "Price must be >= " + minPrice, appName, JOptionPane.ERROR_MESSAGE);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Invalid input. Please try again.", appName, JOptionPane.ERROR_MESSAGE);
            }
        }
        return price;
    }


    public static void processSearchResults(FruitingPlantProperties dreamPlant) {
        List<FruitingPlant> matching = inventory.findMatch(dreamPlant);
        if (!matching.isEmpty()) {
            Map<String, FruitingPlant> options = new HashMap<>();
            StringBuilder infoToShow = new StringBuilder("Matches found!! The following plants meet your criteria: \n");
            for (FruitingPlant match : matching) {
                infoToShow.append(match.getItemInformation());
                options.put(match.getProductName(), match);
            }
            String choice = (String) JOptionPane.showInputDialog(null,
                    infoToShow + "\n\nPlease select which item you'd like to reserve:", appName,
                    JOptionPane.INFORMATION_MESSAGE, icon, options.keySet().toArray(), "");
            if (choice == null) System.exit(0);
            FruitingPlant chosenPlant = options.get(choice);
            submitOrder(getContactInfo(), chosenPlant, dreamPlant.getPotSizeToPriceOptions().keySet().iterator().next());
            JOptionPane.showMessageDialog(null, "Thank you! Your reservation has been submitted. Please head to your local Greenie Geek to pay and pick up!",
                    appName, JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "Unfortunately, none of our plants meet your criteria :(\nPlease consider expanding your search criteria.",
                    appName, JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public static Customer getContactInfo() {
        String name;
        do {
            name = (String) JOptionPane.showInputDialog(null,
                    "Please enter your full name (in format firstname surname): ",
                    appName, JOptionPane.QUESTION_MESSAGE, icon, null, null);
            if (name == null) System.exit(0);
        } while (!isValidFullName(name));
        String phoneNumber;
        do {
            phoneNumber = (String) JOptionPane.showInputDialog(null,
                    "Please enter your phone number (10-digit number in the format 0412345678): ",
                    appName, JOptionPane.QUESTION_MESSAGE, icon, null, null);
            if (phoneNumber == null) System.exit(0);
        } while (!isValidPhoneNumber(phoneNumber));
        return new Customer(name, phoneNumber);
    }

    public static boolean isValidFullName(String fullName) {
        // Regex to allow uppercase or lowercase letters, spaces, hyphens, and apostrophes in names
        String regex = "^[A-Z][a-zA-Z'-]+\\s[A-Z][a-zA-Z'-]+$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(fullName);
        return matcher.matches();
    }


    public static boolean isValidPhoneNumber(String phoneNumber) {
        Pattern pattern = Pattern.compile("^0\\d{9}$");
        Matcher matcher = pattern.matcher(phoneNumber);
        return matcher.matches();
    }

    public static void submitOrder(Customer customer, FruitingPlant plant, int potSize) {
        String filePath = customer.name().replace(" ", "_") + "_" + plant.getProductCode() + ".txt";
        Path path = Path.of(filePath);
        String lineToWrite = "Order details:" +
                "\n\tName: " + customer.name() + " (" + customer.phoneNumber() + ")" +
                "\n\tItem: " + plant.getProductName() + " (" + plant.getProductCode() + ")" +
                "\n\tPot size (inch): " + potSize;

        try {
            Files.writeString(path, lineToWrite);
        } catch (IOException io) {
            System.out.println("Order could not be placed. \nError message: " + io.getMessage());
            System.exit(0);
        }
    }

    public static Inventory loadInventory(String filePath) {
        Inventory inventory = new Inventory();
        Path path = Path.of(filePath);
        List<String> fileContents;
        try {
            fileContents = Files.readAllLines(path);
        } catch (IOException io) {
            System.out.println("File could not be found");
            System.exit(0);
            return null; // Just to satisfy the compiler; we actually exit the program above
        }

        for (int i = 1; i < fileContents.size(); i++) {
            String line = fileContents.get(i);
            String[] info = line.split(",(?=(?:[^\\[\\]]*\\[[^\\[\\]]*\\])*[^\\[\\]]*$)");

            Category category = Category.valueOf(info[0].trim().toUpperCase().replace(" ", "_"));
            String productName = info[1].trim();
            String productCode = info[2].trim();
            PlantType type = PlantType.valueOf(info[3].trim().toUpperCase().replace(" ", "_"));
            DwarfType dwarf = info[4].trim().equalsIgnoreCase("yes") ? DwarfType.YES : DwarfType.NO;
            TrainingSystem trainingSystem = info[5].trim().equals("NA") ? null : TrainingSystem.valueOf(info[5].trim().toUpperCase().replace("-", "_").replace(" ", "_"));

            // Parse pollinators
            Set<String> pollinators = new HashSet<>();
            if (!info[6].trim().equals("[]")) {
                String[] pollinatorsArray = info[6].trim().replace("[", "").replace("]", "").split(",");
                for (String pollinator : pollinatorsArray) {
                    pollinators.add(pollinator.trim());
                }
            }

            // Parse prices
            String[] pricesRaw = info[7].replace("[", "").replace("]", "").split(",");
            List<Float> prices = new ArrayList<>();
            for (String price : pricesRaw) {
                prices.add(Float.parseFloat(price.trim()));
            }

            // Parse pot sizes
            String[] potSizesRaw = info[8].replace("[", "").replace("]", "").split(",");
            List<Integer> potSizes = new ArrayList<>();
            for (String potSize : potSizesRaw) {
                potSizes.add(Integer.parseInt(potSize.trim()));
            }

            // Create a map of pot sizes to prices
            Map<Integer, Float> potSizeToPrice = new LinkedHashMap<>();
            for (int j = 0; j < potSizes.size(); j++) {
                potSizeToPrice.put(potSizes.get(j), prices.get(j));
            }

            // Parse description
            String description = info[9].trim().replace("['", "").replace("']", "");

            // Instantiate FruitingPlant
            DreamFruitingPlant dreamFruitingPlant = new DreamFruitingPlant(category, type, dwarf, trainingSystem, pollinators, potSizeToPrice, description);
            FruitingPlant plant = new FruitingPlant(productCode, productName, dreamFruitingPlant, Collections.min(prices), Collections.max(prices));

            inventory.addItem(plant);
        }
        return inventory;
    }
}
