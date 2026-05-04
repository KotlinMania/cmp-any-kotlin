// port-lint: source src/eq.rs
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

import kotlin.reflect.KClass

/**
 * A comparable "token" that can be returned to wrap a reference to an [Any]
 * for equality.
 *
 * This lets erased interface types be comparable by having all
 * implementations return some "token" that can be considered equal to
 * another token.
 */
class PartialEqAny private constructor(
    private val typeId: KClass<*>,
    private val value: Any,
    private val eq: (Any, Any) -> Boolean,
) {

    /** Get [KClass] of the referenced type. */
    fun typeId(): KClass<*> = typeId

    override fun equals(other: Any?): Boolean {
        if (other !is PartialEqAny) return false
        // The eq closure is invoked only when typeId matches on both sides;
        // the runtime check below is what guarantees the captured cast is sound.
        return typeId == other.typeId && eq(value, other.value)
    }

    override fun hashCode(): Int {
        return typeId.hashCode() * 31 + value.hashCode()
    }

    companion object {
        fun <A : Any> new(a: A): PartialEqAny {
            val capturedEq: (Any, Any) -> Boolean = { lhs, rhs ->
                @Suppress("UNCHECKED_CAST")
                (lhs as A) == (rhs as A)
            }
            return PartialEqAny(
                typeId = a::class,
                value = a,
                eq = capturedEq,
            )
        }

        /** gets an instance that always compares to false */
        fun alwaysFalse(): PartialEqAny {
            class AlwaysFalse {
                override fun equals(other: Any?): Boolean = false
                override fun hashCode(): Int = 0
            }
            return new(AlwaysFalse())
        }
    }
}
