package digital.buildit.resourcetagging.factory.annotation;

import digital.buildit.resourcetagging.event.idextractors.ResourceIdExtractor;
import digital.buildit.resourcetagging.factory.IdExtractor;
import digital.buildit.resourcetagging.factory.ResourceIdExtractorFactory;
import org.reflections.Reflections;

import java.text.MessageFormat;
import java.util.Set;
import java.util.stream.Collectors;

public class AnnotationBasedResourceIdExtractorFactory implements ResourceIdExtractorFactory {

    @Override
    public ResourceIdExtractor createInstance(String eventName) {
        Reflections reflections = new Reflections("digital.buildit");

        Set<Class<?>> candidateClasses =
                reflections.getTypesAnnotatedWith(IdExtractor.class)
                        .stream()
                        .filter(ResourceIdExtractor.class::isAssignableFrom)
                        .filter(c -> c.getAnnotation((IdExtractor.class)).eventName().equalsIgnoreCase(eventName))
                        .collect(Collectors.toSet());

        if (candidateClasses.isEmpty()) {
            throw new IllegalArgumentException(
                    MessageFormat.format(
                            "No suitable {0} implementation was found for eventName \"{1}\".",
                            ResourceIdExtractor.class.getSimpleName(),
                            eventName));
        }
        if (candidateClasses.size() > 1) {
            throw new IllegalStateException(
                    MessageFormat.format(
                            "Ambiguity sucks:  More than one {0} implementation was found for eventName \"{1}\".",
                            ResourceIdExtractor.class.getSimpleName(),
                            eventName));
        }

        try {
            return (ResourceIdExtractor) ((Class<?>) candidateClasses.toArray()[0]).newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
