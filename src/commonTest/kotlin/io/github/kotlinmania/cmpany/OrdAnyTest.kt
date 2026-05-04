// port-lint: source tests:src/ord.rs
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
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class OrdAnyTest {

    @Test
    fun testOrdAny() {
        assertTrue(OrdAny.new(1) < OrdAny.new(2))
        assertTrue(OrdAny.new(true) > OrdAny.new(false))
        assertEquals(0, OrdAny.new("").compareTo(OrdAny.new("")))
        // Comparison between distinct types: in Rust this falls back to TypeId
        // ordering (hash-based, implementation-defined). The Kotlin port uses
        // qualified-name ordering, which is deterministic across runs.
        val intToken = OrdAny.new(1)
        val boolToken = OrdAny.new(true)
        val expected = intToken.typeId().qualifiedName!!.compareTo(boolToken.typeId().qualifiedName!!)
        assertEquals(expected < 0, intToken < boolToken)
    }
}
