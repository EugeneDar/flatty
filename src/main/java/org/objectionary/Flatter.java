package org.objectionary;

import org.objectionary.entities.ObjectWithApplication;
import org.objectionary.entities.Entity;

import java.util.HashMap;
import java.util.Map;

/**
 * This class realizes the flattening of the objects.
 * @since 0.1.0
 */
public final class Flatter {

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
                        flatOne(this.box, binding.getKey(), (ObjectWithApplication) binding.getValue());
                        found = true;
                    }
                }
            }
        }

        return this.box;
    }

    /**
     * Flattens one object.
     * @param box The objects box.
     * @param name The name of the object.
     * @param object The object.
     */
    private static void flatOne (ObjectsBox box, String name, ObjectWithApplication object) {
        final Map<String, Entity> newBindings = deepCopy(box.getObject(object.getName()));

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

    private static Map<String, Entity> deepReframe(final Map<String, Entity> bindings) {
        final Map<String, Entity> result = new HashMap<>(bindings.size());
        for (final Map.Entry<String, Entity> entry : bindings.entrySet()) {
            result.put(entry.getKey(), entry.getValue().reframe());
        }
        return result;
    }


}
