package digital.buildit.resourcetagging.event;

import java.util.function.Supplier;

/**
 * Supplies the identifier for the aws resource.
 * Created by will on 14/06/2017.
 */
@FunctionalInterface
public interface IdentifierSupplier<T> extends Supplier<T> {


}

