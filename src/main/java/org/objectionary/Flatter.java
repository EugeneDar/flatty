package org.objectionary;

import org.objectionary.entities.*;

import java.util.HashMap;
import java.util.Map;

/**
 * This class realizes the flattening of the objects.
 * @since 0.1.0
 */
public final class Flatter {

    /**
     * The counter of created objects.
     */
    private static int counter;

    /**
     * The objects box.
     */
    private final ObjectsBox box;

    /**
     * Constructor.
     * @param box The objects box.
     */
    public Flatter(final ObjectsBox box) {
        this.box = box;
        Flatter.counter = 10; // TODO: 10 is a magic number
    }

    /**
     * Flattens the objects.
     * @return The flattened objects box.
     */
    public ObjectsBox flat() {
        boolean found = true;
        while (found) {
            found = false;
            for (final Map.Entry<String, Map<String, Entity>> entry : this.box.getBox().entrySet()) {
                for (final Map.Entry<String, Entity> binding : entry.getValue().entrySet()) {
                    if (binding.getValue() instanceof ObjectWithApplication) {
                        flatOne(this.box, binding.getKey(), (ObjectWithApplication) binding.getValue(), entry.getValue());
                        found = true;
                        break;
                    }
                }
                if (found) {
                    break;
                }
            }
        }

        return this.box;
    }

    /**
     * Flattens one object.
     * @param allObjectsBox The box which contains all objects.
     * @param bindingKey The name of binding to this non-flat object.
     * @param nonFlatObject The non-flat object itself.
     * @param userObject The object which contains the non-flat object.
     */
    private static void flatOne (ObjectsBox allObjectsBox, String bindingKey, ObjectWithApplication nonFlatObject, Map<String, Entity> userObject) {
        final Map<String, Entity> bindingsCopy = deepCopy(allObjectsBox.getObject(nonFlatObject.getName()));
        final Map<String, Entity> applicationCopy = deepCopy(nonFlatObject.getApplication());

        final Map<String, Entity> reframedApplication = deepReframe(applicationCopy);

        for (final Map.Entry<String, Entity> entry : reframedApplication.entrySet()) {
            if (bindingsCopy.containsKey(entry.getKey())) {
                bindingsCopy.put(entry.getKey(), entry.getValue());
            }
        }

        final String newObjectName = String.format("ν%d", counter);
        counter += 1;

        allObjectsBox.putObject(newObjectName, bindingsCopy);

        userObject.put(bindingKey, new FlatObject(newObjectName, "ξ"));

        // TODO add graph transformation
    }

    /**
     * Returns the deep copy of the bindings.
     * @param bindings The bindings.
     * @return The deep copy of the bindings.
     */
    private static Map<String, Entity> deepCopy(final Map<String, Entity> bindings) {
        final Map<String, Entity> result = new HashMap<>(bindings.size());
        for (final Map.Entry<String, Entity> entry : bindings.entrySet()) {
            result.put(entry.getKey(), entry.getValue().copy());
        }
        return result;
    }

    /**
     * Returns the deep reframe of the bindings.
     * @param bindings The bindings.
     * @return The deep reframe of the bindings.
     */
    private static Map<String, Entity> deepReframe(Map<String, Entity> bindings) {
        Map<String, Entity> result = new HashMap<>(bindings.size());
        for (final Map.Entry<String, Entity> entry : bindings.entrySet()) {
            result.put(entry.getKey(), entry.getValue().reframe());
        }
        return result;
    }

}
