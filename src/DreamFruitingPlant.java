import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class DreamFruitingPlant implements FruitingPlantProperties {

    private final Category category;
    private final PlantType type;
    private final DwarfType dwarf;
    private final TrainingSystem trainingSystem;
    private final Set<String> pollinators;
    private final Map<Integer, Float> potSizeToPrice;
    private final String description;

    public DreamFruitingPlant(Category category, PlantType type, DwarfType dwarf, TrainingSystem trainingSystem,
                              Set<String> pollinators, Map<Integer, Float> potSizeToPrice, String description) {
        this.category = category;
        this.type = type;
        this.dwarf = dwarf;
        this.trainingSystem = trainingSystem;
        this.pollinators = pollinators;
        this.potSizeToPrice = potSizeToPrice;
        this.description = description;
    }

    @Override
    public Category getCategory() {
        return category;
    }

    @Override
    public PlantType getType() {
        return type;
    }

    @Override
    public DwarfType getDwarfType() {
        return dwarf;
    }

    @Override
    public TrainingSystem getTrainingSystem() {
        return trainingSystem;
    }

    @Override
    public Set<String> getPollinators() {
        return pollinators;
    }

    @Override
    public Map<Integer, Float> getPotSizeToPriceOptions() {

        return potSizeToPrice;
    }

    @Override
    public String getDescription() {
        return description;
    }

    public Float getMinPrice() {
        if (potSizeToPrice.isEmpty()) return null;
        return Collections.min(potSizeToPrice.values());
    }

    public Float getMaxPrice() {
        if (potSizeToPrice.isEmpty()) return null;
        return Collections.max(potSizeToPrice.values());
    }
}
