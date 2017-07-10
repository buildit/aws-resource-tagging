package digital.buildit.resourcetagging.factory.annotation;

import digital.buildit.resourcetagging.factory.Tagger;
import digital.buildit.resourcetagging.factory.TaggerFactory;
import digital.buildit.resourcetagging.taggers.ResourceTagger;
import org.reflections.Reflections;

import java.text.MessageFormat;
import java.util.Set;
import java.util.stream.Collectors;

public class AnnotationBasedTaggerFactory implements TaggerFactory {

    @Override
    public ResourceTagger createInstance(String eventName) {
        Reflections reflections = new Reflections("digital.buildit");

        Set<Class<?>> candidateClasses =
                reflections.getTypesAnnotatedWith(Tagger.class)
                        .stream()
                        .filter(ResourceTagger.class::isAssignableFrom)
                        .filter(c -> c.getAnnotation((Tagger.class)).eventName().equalsIgnoreCase(eventName))
                        .collect(Collectors.toSet());

        if (candidateClasses.isEmpty()) {
            throw new IllegalArgumentException(
                    MessageFormat.format(
                            "No suitable {0} implementation was found for eventName \"{1}\".",
                            ResourceTagger.class.getSimpleName(),
                            eventName));
        }
        if (candidateClasses.size() > 1) {
            throw new IllegalStateException(
                    MessageFormat.format(
                            "Ambiguity sucks:  More than one {0} implementation was found for eventName \"{1}\".",
                            ResourceTagger.class.getSimpleName(),
                            eventName));
        }

        try {
            return (ResourceTagger) ((Class<?>) candidateClasses.toArray()[0]).newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
