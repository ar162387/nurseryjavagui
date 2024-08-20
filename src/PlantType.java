public enum PlantType {
    APPLE("Apple"),
    GRAPE("Grape"),
    LEMON("Lemon"),
    PEAR("Pear"),
    ORANGE("Orange"),
    MANDARIN("Mandarin"),
    PEACH("Peach"),
    PLUM("Plum"),
    TANGERINE("Tangerine"),
    KUMQUAT("Kumquat"),
    LIME("Lime"),
    KIWI("Kiwi"),
    PASSIONFRUIT("Passionfruit"),
    QUINCE("Quince"),
    NECTARINE("Nectarine"),
    CITRON("Citron"),
    TANGELO("Tangelo"),
    CRABAPPLE("Crabapple"),
    YUZU("Yuzu"),               // Missing type added
    GRAPEFRUIT("Grapefruit"),   // Missing type added
    POMELO("Pomelo"),
    DONT_MIND("I Don't Mind");           // Missing type added

    private final String displayName;

    PlantType(String displayName) {
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
