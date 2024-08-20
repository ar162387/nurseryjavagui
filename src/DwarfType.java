public enum DwarfType {
    YES("Yes"),
    NO("No"),
    DONT_MIND("I Don't Mind");

    private final String displayName;

    DwarfType(String displayName) {
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
