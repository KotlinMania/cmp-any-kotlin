// port-lint: source tests:src/eq.rs
package io.github.kotlinmania.cmpany

/*
 * Copyright (c) Meta Platforms, Inc. and affiliates.
 * Copyright (c) 2025 Sydney Renee, The Solace Project
 *
 * This source code is dual-licensed under either the MIT license found in the
 * LICENSE-MIT file in the root directory of this source tree or the Apache
 * License, Version 2.0 found in the LICENSE-APACHE file in the root directory
 * of this source tree. You may select, at your option, one of the
 * above-listed licenses.
 */

import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class PartialEqAnyTest {

    private class Wrap<T : Any>(val inner: T) {
        fun token(): PartialEqAny = PartialEqAny.new(inner)
    }

    @Test
    fun testCmpAny() {
        val w1 = Wrap(1)
        val w2 = Wrap(1)
        val w3 = Wrap(2)

        assertTrue(w1.token() == w2.token())
        assertFalse(w1.token() == w3.token())

        val w4 = Wrap("foo")
        val w5 = Wrap("foo")
        val w6 = Wrap("bar")

        assertTrue(w4.token() == w5.token())
        assertFalse(w4.token() == w6.token())

        assertFalse(w1.token() == w6.token())
    }

    @Test
    fun alwaysFalseCmp() {
        val w = Wrap(1)
        val f = PartialEqAny.alwaysFalse()

        assertFalse(f == f)
        assertFalse(f == w.token())
    }
}
