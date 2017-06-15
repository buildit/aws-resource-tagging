package digital.buildit.resourcetagging.taggers;


import digital.buildit.resourcetagging.event.Event;

import java.util.function.Function;

/**
 *
 * Created by will on 14/06/2017.
 */
abstract class AbstractTagger<T> implements Tagger {

    Function<Event, T> identifierFunction;

    AbstractTagger(Function<Event, T> identifierFunction) {
        this.identifierFunction = identifierFunction;
    }
}
