package tacocloud.tacocloud.dto;

public enum TacoSize {
    SMALL, MEDIUM, LARGE;
    

    public static TacoSize fromString(String text){
        switch (text){
            case "SMALL":
                return SMALL;
            case "MEDIUM":
                return MEDIUM;
        }
        return LARGE;
    }
}
