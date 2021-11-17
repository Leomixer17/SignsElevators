package net.leonardo_dgs.signselevators;

public final class SignsElevatorsProvider {
    private static SignsElevators instance;

    public static SignsElevators get() {
        return instance;
    }

    public static void register(SignsElevators instance) {
        SignsElevatorsProvider.instance = instance;
    }

    public static void unregister() {
        instance = null;
    }
}
