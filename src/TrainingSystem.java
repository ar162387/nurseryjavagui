public enum TrainingSystem {
    Y_TRELLIS("Y-Trellis"),
    A_FRAME_TRELLIS("A-frame Trellis"),
    PERGOLA("Pergola"),
    TRELLIS("Trellis"),
    CORDON("Cordon"),
    T_BAR_TRELLIS("T-Bar Trellis"),


    GUYOT("Guyot"),

    DONT_MIND("I Don't Mind");

    private final String displayName;

    TrainingSystem(String displayName) {
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
