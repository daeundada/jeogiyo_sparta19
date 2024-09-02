package sparta.jeogiyo.domain.store.domain;

public enum StoreCategory {

    KOREAN_FOOD("KOREAN_FOOD"),
    CHINESE_FOOD("CHINESE_FOOD"),
    SNACK_FOOD("SNACK_FOOD"),
    CHICKEN("CHICKEN"),
    PIZZA("PIZZA");

    private final String category;

    StoreCategory(String category) {
        this.category = category;
    }
}
