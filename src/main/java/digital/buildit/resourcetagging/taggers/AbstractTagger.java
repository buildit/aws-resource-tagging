package digital.buildit.resourcetagging.taggers;


import digital.buildit.resourcetagging.event.IdentifierSupplier;

/**
 *
 * Created by will on 14/06/2017.
 */
abstract class AbstractTagger<T> implements Tagger {

    IdentifierSupplier<T> idSupplier;

    AbstractTagger(IdentifierSupplier<T> idSupplier) {
        this.idSupplier = idSupplier;
    }
}
