package umc.spring.apiPayLoad.code.exception.handler;

public class FoodCategoryHandler extends RuntimeException {
    public FoodCategoryHandler(ErrorStatus message) {
        super(String.valueOf(message));
    }
}