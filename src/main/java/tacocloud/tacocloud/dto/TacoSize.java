package tacocloud.tacocloud.dto;

public enum TacoSize {
    SMALL, MEDIUM, BIG;
    

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
