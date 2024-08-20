import java.text.DecimalFormat;
import java.util.Map;
import java.util.Set;

public class FruitingPlant implements FruitingPlantProperties {

    private final String productCode;
    private final String productName;
    private final float minPrice;
    private final float maxPrice;
    private final DreamFruitingPlant dreamFruitingPlant;  // Composition: generic features encapsulated here

    public FruitingPlant(String productCode, String productName, DreamFruitingPlant dreamFruitingPlant, float minPrice, float maxPrice) {
        this.productCode = productCode;
        this.productName = productName;
        this.dreamFruitingPlant = dreamFruitingPlant;
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
    }

    public String getProductCode() {
        return productCode;
    }

    public String getProductName() {
        return productName;
    }

    public float getMinPrice() {
        return minPrice;
    }

    public float getMaxPrice() {
        return maxPrice;
    }

    @Override
    public Category getCategory() {
        return dreamFruitingPlant.getCategory();
    }

    @Override
    public PlantType getType() {
        return dreamFruitingPlant.getType();
    }

    @Override
    public DwarfType getDwarfType() {
        return dreamFruitingPlant.getDwarfType();
    }

    @Override
    public TrainingSystem getTrainingSystem() {
        return dreamFruitingPlant.getTrainingSystem();
    }

    @Override
    public Set<String> getPollinators() {
        return dreamFruitingPlant.getPollinators();
    }

    @Override
    public Map<Integer, Float> getPotSizeToPriceOptions() {
        return dreamFruitingPlant.getPotSizeToPriceOptions();
    }

    @Override
    public String getDescription() {
        return dreamFruitingPlant.getDescription();
    }

    public StringBuilder getItemInformation() {
        DecimalFormat df = new DecimalFormat("0.00");
        StringBuilder output = new StringBuilder("\n*******************************************");

        output.append("\n").append(getProductName()).append(" (").append(getProductCode()).append(")\n")
                .append(getDescription()).append("\nCategory: ").append(getCategory().getDisplayName())
                .append("\nType: ").append(getType().getDisplayName())
                .append("\nDwarf: ").append(getDwarfType().getDisplayName());

        if (getCategory() == Category.VINE) {
            output.append("\nTraining System: ").append(getTrainingSystem() != null ? getTrainingSystem().getDisplayName() : "N/A");
        }

        if (getCategory() == Category.POME || getCategory() == Category.STONE_FRUIT) {
            output.append("\nPollinators: ");
            if (!getPollinators().isEmpty()) {
                for (String pollinator : getPollinators()) {
                    output.append(pollinator).append(", ");
                }
                output.setLength(output.length() - 2); // Remove trailing comma
            } else {
                output.append("N/A");
            }
        }

        output.append("\nAvailable pot sizes:\n| ");
        for (Map.Entry<Integer, Float> entry : getPotSizeToPriceOptions().entrySet()) {
            output.append(entry.getKey()).append(" inch: $").append(df.format(entry.getValue())).append(" | ");
        }

        return output;
    }
}
