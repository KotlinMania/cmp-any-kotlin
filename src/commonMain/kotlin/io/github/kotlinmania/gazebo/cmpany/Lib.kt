// port-lint: source cmp_any/src/lib.rs
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

// Tracking file for the upstream crate root. The upstream root is composed entirely of
// submodule declarations and re-exports; per the workspace rule on root re-exports
// (CLAUDE.md), no Kotlin typealias is introduced. Callers reach the canonical symbol directly
// via [io.github.kotlinmania.gazebo.cmpany.OrdAny] and
// [io.github.kotlinmania.gazebo.cmpany.PartialEqAny], which is the same path that would
// otherwise be re-exported through this file.

// Upstream submodules:
//   - The eq module is translated to PartialEqAny.kt in this same package.
//   - The ord module is translated to OrdAny.kt in this same package.

// Upstream root re-exports:
//   - PartialEqAny is re-exported at the crate root from the eq module. The Kotlin
//     translation lives in PartialEqAny.kt in this same package and is already accessible
//     by name from any caller in [io.github.kotlinmania.gazebo.cmpany] and by qualified
//     import elsewhere; no synthetic alias is introduced.
//   - OrdAny is re-exported at the crate root from the ord module. The Kotlin translation
//     lives in OrdAny.kt in this same package. Same reasoning as PartialEqAny.

// Callers migrated:
//   (none — workspace audit confirmed every Kotlin caller already imports
//   [io.github.kotlinmania.gazebo.cmpany.OrdAny] and
//   [io.github.kotlinmania.gazebo.cmpany.PartialEqAny] directly at the time this tracking
//   file landed. The single external Kotlin caller, starlark-kotlin's typing/Custom.kt,
//   already reaches OrdAny through the canonical package path.)
