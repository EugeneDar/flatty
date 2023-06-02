/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2023 Objectionary.com
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included
 * in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NON-INFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package org.objectionary;

import java.util.HashMap;
import java.util.Map;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.objectionary.entities.Data;
import org.objectionary.entities.Empty;
import org.objectionary.entities.Entity;
import org.objectionary.entities.FlatObject;
import org.objectionary.entities.Lambda;
import org.objectionary.entities.Locator;
import org.objectionary.entities.NestedObject;

/**
 * ObjectsBox test.
 *
 * @since 0.1.0
 */
final class ObjectsBoxTest {

    @Disabled
    @Test
    void boxWithEmptyToStringTest() {
        final ObjectsBox box = new ObjectsBox();
        final Map<String, Entity> bindings = new HashMap<>();
        bindings.put("x", new Empty());
        box.put("foo", bindings);
        MatcherAssert.assertThat(
            box.toString(),
            Matchers.equalTo("foo(𝜋) ↦ ⟦ x ↦ ø ⟧")
        );
    }

    @Disabled
    @Test
    void boxWithDataToStringTest() {
        final ObjectsBox box = new ObjectsBox();
        final Map<String, Entity> bindings = new HashMap<>();
        bindings.put("Δ", new Data(Integer.parseInt("000A", 16)));
        box.put("foo", bindings);
        MatcherAssert.assertThat(
            box.toString(),
            Matchers.equalTo("foo(𝜋) ↦ ⟦ Δ ↦ 0x000A ⟧")
        );
    }

    @Disabled
    @Test
    void boxWithLocatorToStringTest() {
        final ObjectsBox box = new ObjectsBox();
        final Map<String, Entity> bindings = new HashMap<>();
        bindings.put("x", new Locator("𝜋.𝜋.y"));
        box.put("bar", bindings);
        MatcherAssert.assertThat(
            box.toString(),
            Matchers.equalTo("bar(𝜋) ↦ ⟦ x ↦ 𝜋.𝜋.y ⟧")
        );
    }

    @Disabled
    @Test
    void boxWithFlatObjectToStringTest() {
        final ObjectsBox box = new ObjectsBox();
        final Map<String, Entity> bindings = new HashMap<>();
        bindings.put("y", new FlatObject("bar", "ξ"));
        box.put("foo", bindings);
        MatcherAssert.assertThat(
            box.toString(),
            Matchers.equalTo("foo(𝜋) ↦ ⟦ y ↦ bar(ξ) ⟧")
        );
    }

    @Disabled
    @Test
    void boxWithLambdaToStringTest() {
        final ObjectsBox box = new ObjectsBox();
        final Map<String, Entity> bindings = new HashMap<>();
        bindings.put("λ", new Lambda("Plus"));
        box.put("v", bindings);
        MatcherAssert.assertThat(
            box.toString(),
            Matchers.equalTo("v(𝜋) ↦ ⟦ λ ↦ Plus ⟧")
        );
    }

    @Disabled
    @Test
    void boxWithNestedObjectToStringTest() {
        final ObjectsBox box = new ObjectsBox();
        final Map<String, Entity> application = new HashMap<>();
        application.put("x", new Locator("𝜋.𝜋.z"));
        final Map<String, Entity> bindings = new HashMap<>();
        bindings.put("y", new NestedObject("v", application));
        box.put("foo", bindings);
        MatcherAssert.assertThat(
            box.toString(),
            Matchers.equalTo("foo(𝜋) ↦ ⟦ y ↦ v( x ↦ 𝜋.𝜋.z ) ⟧")
        );
    }

    @Disabled
    @Test
    void zeroObjectOrderTest() {
        final ObjectsBox box = new ObjectsBox();
        {
            final Map<String, Entity> bindings = new HashMap<>();
            bindings.put("x", new Empty());
            box.put("a", bindings);
        }
        {
            final Map<String, Entity> bindings = new HashMap<>();
            bindings.put("y", new Empty());
            box.put("b", bindings);
        }
        {
            final Map<String, Entity> bindings = new HashMap<>();
            bindings.put("z", new Empty());
            box.put("ν0", bindings);
        }
        final String result = box.toString();
        assert result != null;
        MatcherAssert.assertThat(
            result.split("\n")[0],
            Matchers.equalTo("ν0(𝜋) ↦ ⟦ z ↦ ø ⟧")
        );
    }

    @Disabled
    @Test
    void deltaOrderTest() {
        final ObjectsBox box = new ObjectsBox();
        final Map<String, Entity> bindings = new HashMap<>();
        bindings.put("Δ", new Data(Integer.parseInt("000A", 16)));
        bindings.put("x", new Empty());
        bindings.put("y", new FlatObject("bar", "𝜋"));
        bindings.put("a", new Lambda("Atom"));
        box.put("foo", bindings);
        final String result = box.toString();
        assert result != null;
        MatcherAssert.assertThat(
            result.split(" ")[3],
            Matchers.equalTo("Δ")
        );
    }

    @Disabled
    @Test
    void lambdaOrderTest() {
        final ObjectsBox box = new ObjectsBox();
        final Map<String, Entity> bindings = new HashMap<>();
        bindings.put("λ", new Data(Integer.parseInt("000A", 16)));
        bindings.put("a1", new Empty());
        bindings.put("a2", new FlatObject("bar", "𝜋"));
        bindings.put("a3", new Lambda("Atom"));
        box.put("foo", bindings);
        final String result = box.toString();
        assert result != null;
        MatcherAssert.assertThat(
                result.split(" ")[3],
                Matchers.equalTo("λ")
        );
    }

    @Disabled
    @Test
    void phiOrderTest() {
        final ObjectsBox box = new ObjectsBox();
        final Map<String, Entity> bindings = new HashMap<>();
        bindings.put("𝜑", new Data(Integer.parseInt("000A", 16)));
        bindings.put("a", new Empty());
        bindings.put("b", new FlatObject("bar", "𝜋"));
        bindings.put("c", new Lambda("Atom"));
        box.put("foo", bindings);
        final String result = box.toString();
        assert result != null;
        MatcherAssert.assertThat(
                result.split(" ")[3],
                Matchers.equalTo("𝜑")
        );
    }
}
