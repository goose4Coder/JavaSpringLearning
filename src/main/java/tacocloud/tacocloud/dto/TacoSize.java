package tacocloud.tacocloud.dto;

public enum TacoSize {
    SMALL, MEDIUM, BIG;

    @Override
    public String toString() {
        return super.toString();
    }

    public static TacoSize fromString(String text){
        switch (text){
            case "SMALL":
                return SMALL;
            case "MEDIUM":
                return MEDIUM;
        }
        return BIG;
    }
}
