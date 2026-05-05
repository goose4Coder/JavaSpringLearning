package tacocloud.tacocloud.dto;

public enum IngredientCategory {
    MEAT,SAUCE,BREAD,VEGETABLE, EXTRA;


    public static IngredientCategory fromString(String text){
        switch (text){
            case "MEAT":
                return MEAT;
            case "SAUCE":
                return SAUCE;
            case "BREAD":
                return BREAD;
            case "VEGETABLE":
                return VEGETABLE;
        }
        return EXTRA;
    }
}
