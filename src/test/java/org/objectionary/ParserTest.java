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

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.objectionary.parsing.Parser;

/**
 * Parser test.
 *
 * @since 0.1.0
 */
final class ParserTest {

    /**
     * Simple parsing test.
     */
    @Disabled
    @Test
    void simpleParsingTest() {
        final String line = "ν0(𝜋) ↦ ⟦ λ ↦ int-neg, ρ ↦ 𝜋.𝛼0 ⟧";
        final Parser parser = new Parser(line);
        MatcherAssert.assertThat(parser.parse().toString(), Matchers.equalTo(line));
    }

    /**
     * Simple nested test.
     */
    @Disabled
    @Test
    void simpleNestedTest() {
        final String line = "ν0(𝜋) ↦ ⟦ 𝜑 ↦ ν2( a ↦ ξ.x ) ⟧";
        final Parser parser = new Parser(line);
        MatcherAssert.assertThat(parser.parse().toString(), Matchers.equalTo(line));
    }

    /**
     * Large test.
     */
    @Disabled
    @Test
    void largeTest() {
        final String[] input = {
            "ν0(𝜋) ↦ ⟦ 𝜑 ↦ ν3(𝜋) ⟧",
            "ν1(𝜋) ↦ ⟦ Δ ↦ 0x002A ⟧",
            "ν2(𝜋) ↦ ⟦ λ ↦ int-add, ρ ↦ 𝜋.𝛼0, 𝛼0 ↦ 𝜋.𝛼1 ⟧",
            "ν3(𝜋) ↦ ⟦ 𝜑 ↦ ν2(ξ), 𝛼1 ↦ ν1 ⟧",
            "ν5(𝜋) ↦ ⟦ 𝜑 ↦ ν3(ξ) ⟧",
        };
        final String[] correct = {
            "ν0(𝜋) ↦ ⟦ 𝜑 ↦ ν3(𝜋) ⟧",
            "ν1(𝜋) ↦ ⟦ Δ ↦ 0x002A ⟧",
            "ν2(𝜋) ↦ ⟦ λ ↦ int-add, ρ ↦ 𝜋.𝛼0, 𝛼0 ↦ 𝜋.𝛼1 ⟧",
            "ν3(𝜋) ↦ ⟦ 𝜑 ↦ ν2(ξ), 𝛼1 ↦ ν1 ⟧",
            "ν5(𝜋) ↦ ⟦ 𝜑 ↦ ν3(ξ) ⟧",
        };
        final Parser parser = new Parser(String.join("\n", input));
        MatcherAssert.assertThat(
            parser.parse().toString(),
            Matchers.equalToCompressingWhiteSpace(String.join("\n", correct))
        );
    }

    /**
     * Large test with nested objects.
     */
    @Test
    @Disabled
    void largeWithNestingTest() {
        final String[] input = {
            "ν0(𝜋) ↦ ⟦ 𝜑 ↦ ν1( x ↦ ν2( y ↦ 0x0007 ) ) ⟧",
            "ν1(𝜋) ↦ ⟦ x ↦ ø ⟧",
            "ν2(𝜋) ↦ ⟦ y ↦ ø ⟧",
        };
        final String[] correct = {
            "ν0(𝜋) ↦ ⟦ 𝜑 ↦ ν1( x ↦ ν2( y ↦ 0x0007 ) ) ⟧",
            "ν1(𝜋) ↦ ⟦ x ↦ ø ⟧",
            "ν2(𝜋) ↦ ⟦ y ↦ ø ⟧",
        };
        final Parser parser = new Parser(String.join("\n", input));
        MatcherAssert.assertThat(
            parser.parse().toString(),
            Matchers.equalToCompressingWhiteSpace(String.join("\n", correct))
        );
    }
}
