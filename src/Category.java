public enum
Category {
    POME("Pome"),
    VINE("Vine"),
    CITRUS("Citrus"),
    STONE_FRUIT("Stone Fruit"),
    DONT_MIND("I Don't Mind");

    private final String displayName;

    Category(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
}
