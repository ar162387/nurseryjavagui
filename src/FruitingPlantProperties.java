import java.util.Map;
import java.util.Set;

public interface FruitingPlantProperties {

    Category getCategory();

    PlantType getType();

    DwarfType getDwarfType();

    TrainingSystem getTrainingSystem();

    Set<String> getPollinators();

    Map<Integer, Float> getPotSizeToPriceOptions();

    String getDescription();
}
