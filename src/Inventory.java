import java.util.*;
import java.util.stream.Collectors;

public class Inventory {

    private final Set<FruitingPlant> inventory = new HashSet<>();

    public void addItem(FruitingPlant fruitingPlant) {
        this.inventory.add(fruitingPlant);
    }

    public Set<PlantType> getAllTypes() {
        return inventory.stream()
                .map(FruitingPlant::getType)
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    public List<FruitingPlant> findMatch(FruitingPlantProperties dreamProperties) {
        return inventory.stream()
                .filter(plant -> matchesCategory(plant, dreamProperties.getCategory()))
                .filter(plant -> matchesType(plant, dreamProperties.getType()))
                .filter(plant -> matchesDwarfType(plant, dreamProperties.getDwarfType()))
                .filter(plant -> matchesTrainingSystem(plant, dreamProperties.getTrainingSystem()))
                .filter(plant -> matchesPollinators(plant, dreamProperties.getPollinators()))
                .filter(plant -> matchesPotSizeAndPrice(plant, dreamProperties.getPotSizeToPriceOptions()))
                .collect(Collectors.toList());
    }

    private boolean matchesCategory(FruitingPlant plant, Category category) {
        return category == null || category == Category.DONT_MIND || plant.getCategory() == category;
    }

    private boolean matchesType(FruitingPlant plant, PlantType type) {
        return type == null || type == PlantType.DONT_MIND || plant.getType() == type;
    }

    private boolean matchesDwarfType(FruitingPlant plant, DwarfType dwarfType) {
        return dwarfType == null || dwarfType == DwarfType.DONT_MIND || plant.getDwarfType() == dwarfType;
    }

    private boolean matchesTrainingSystem(FruitingPlant plant, TrainingSystem trainingSystem) {
        return trainingSystem == null || trainingSystem == TrainingSystem.DONT_MIND || plant.getTrainingSystem() == trainingSystem;
    }

    private boolean matchesPollinators(FruitingPlant plant, Set<String> pollinators) {
        return pollinators == null || pollinators.isEmpty() || pollinators.contains("I Don't Mind") ||
                pollinators.stream().anyMatch(plant.getPollinators()::contains);
    }

    private boolean matchesPotSizeAndPrice(FruitingPlant plant, Map<Integer, Float> potSizeToPriceOptions) {
        if (potSizeToPriceOptions.isEmpty() || potSizeToPriceOptions.values().contains(Float.MIN_VALUE)) {
            return true; // Skip check if not specified or 'I Don't Mind' for price
        }
        return potSizeToPriceOptions.entrySet().stream()
                .anyMatch(e -> matchesPotSizeAndPrice(plant, e.getKey(), e.getValue()));
    }

    private boolean matchesPotSizeAndPrice(FruitingPlant plant, Integer potSize, Float price) {
        return (potSize == null || potSize == Integer.MIN_VALUE || plant.getPotSizeToPriceOptions().containsKey(potSize)) &&
                (price == null || price == Float.MIN_VALUE || plant.getPotSizeToPriceOptions().get(potSize) >= price);
    }


}
