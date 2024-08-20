import java.util.*;

public class FilterOptions {
    private static final Map<Category, PlantType[]> categoryToTypesMap = initializeTypeMap();


    private static final String[] pollinators = {
            "Crimson Crisp", "Gala", "Granny Smith", "Pixie Crunch", "Red Delicious",
            "Red Fuji", "Honeycrisp", "Golden Delicious", "Fuji", "Bartlett", "Bosc",
            "D’Anjou", "Redhaven", "Contender", "Methley", "Satsuma", "Elberta"
    };

    public static PlantType[] getAvailableTypes(Category category) {
        return categoryToTypesMap.getOrDefault(category, new PlantType[0]);
    }

    public static Set<String> getAvailablePollinators() {
        // Convert the array to a Set to eliminate any duplicates and return a mutable copy
        return new HashSet<>(Arrays.asList(pollinators));
    }

    private static Map<Category, PlantType[]> initializeTypeMap() {
        Map<Category, PlantType[]> categoryToPlantTypeMap = new HashMap<>();
        categoryToPlantTypeMap.put(Category.POME, new PlantType[]{
                PlantType.APPLE, PlantType.PEAR, PlantType.CRABAPPLE, PlantType.QUINCE, PlantType.DONT_MIND
        });
        categoryToPlantTypeMap.put(Category.VINE, new PlantType[]{
                PlantType.GRAPE, PlantType.PASSIONFRUIT, PlantType.KIWI, PlantType.DONT_MIND
        });
        categoryToPlantTypeMap.put(Category.CITRUS, new PlantType[]{
                PlantType.LEMON, PlantType.TANGERINE, PlantType.KUMQUAT, PlantType.ORANGE, PlantType.LIME,
                PlantType.MANDARIN, PlantType.CITRON, PlantType.TANGELO, PlantType.YUZU, PlantType.GRAPEFRUIT,
                PlantType.POMELO, PlantType.DONT_MIND
        });
        categoryToPlantTypeMap.put(Category.STONE_FRUIT, new PlantType[]{
                PlantType.PEACH, PlantType.PLUM, PlantType.NECTARINE, PlantType.DONT_MIND
        });
        return categoryToPlantTypeMap;
    }



}
