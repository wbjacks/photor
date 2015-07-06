import jodd.petite.PetiteContainer;

public class PetiteManager {
    // TODO: (wjackson) this might be useful for reference, but might not
    private static final List<Class> CORE_SERVICES = new ArrayList<>();
    private static final List<Class> WEB_SERVICES = new ArrayList<>();
    // etc. break down by module
    
    private static final PETITE_CONTAINER = new PetiteContainer();

    static {
        CORE_SERVICES.add(FooService.class);
        CORE_SERVICES.add(BarService.class);

        // TODO: (wbjacks) call registerServices here?
        // registerServices();
    }

    public static registerServices() {
        for (Class coreService : CORE_SERVICES) {
            PETITE_CONTAINER.registerPetiteBean(coreService, null, null, null, false);
        }
    }

    public static <T> T getBean(Class<T> cla$$) {
        // TODO: (wbjacks) no idea if this will actually work...
        return (T)_petiteContainer.getBean(cla$$.getName());
    }
}
