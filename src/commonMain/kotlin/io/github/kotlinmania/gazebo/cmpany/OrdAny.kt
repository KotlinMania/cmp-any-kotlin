// port-lint: source cmp_any/src/ord.rs
package io.github.kotlinmania.gazebo.cmpany

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

/** Ordering between arbitrary types. */
class OrdAny private constructor(
    private val typeId: KClass<*>,
    private val typeName: String,
    private val value: Any,
    private val cmp: (Any, Any) -> Int,
) : Comparable<OrdAny> {

    /** Get [KClass] of the referenced type. */
    fun typeId(): KClass<*> = typeId

    /** Compare by type id first, then by value. */
    override fun compareTo(other: OrdAny): Int {
        val typeCmp = typeName.compareTo(other.typeName)
        if (typeCmp != 0) {
            return typeCmp
        }
        return cmp(value, other.value)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is OrdAny) return false
        return compareTo(other) == 0
    }

    override fun hashCode(): Int {
        return typeName.hashCode() * 31 + value.hashCode()
    }

    companion object {
        fun <A : Comparable<A>> new(a: A): OrdAny {
            val typeId = a::class
            // Kotlin/JS does not support `KClass.qualifiedName`, so use `toString()`
            // as the portable, stable-ish discriminator for type ordering.
            val typeName = typeId.toString()
            val capturedCmp: (Any, Any) -> Int = { lhs, rhs ->
                // The cmp closure is invoked only after the typeId-equal branch
                // in [compareTo] above; that runtime check is what guarantees the
                // captured cast below is sound.
                @Suppress("UNCHECKED_CAST")
                (lhs as A).compareTo(rhs as A)
            }
            return OrdAny(
                typeId = typeId,
                typeName = typeName,
                value = a,
                cmp = capturedCmp,
            )
        }
    }
}
