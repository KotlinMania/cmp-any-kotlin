# Claude Code Project Instructions — cmp-any-kotlin

## Project Overview

Kotlin Multiplatform port of [facebookexperimental/gazebo](https://github.com/facebookexperimental/gazebo)'s
`cmp_any` crate. Provides `EqAny` (type-erased equality) and `OrdAny`
(type-erased ordering).

The Kotlin port lives in `src/commonMain/kotlin/io/github/kotlinmania/cmpany/`.
The upstream Rust source it was translated from is **not tracked** in this
repo — fetch it into `tmp/cmp-any/` by running
`./tools/fetch-rust-source.sh` after cloning.

## Critical Workflows

### 1. Read AGENTS.md first

Every translation rule, idiom mapping, and naming convention is in
[AGENTS.md](./AGENTS.md). Don't translate a file without reading it.

### 2. Port-Lint headers (REQUIRED)

```kotlin
// port-lint: source src/<file>.rs
package io.github.kotlinmania.cmpany
```

### 3. Quality verification

```bash
./gradlew compileKotlinMacosArm64
./gradlew macosArm64Test
```

The runtime gate is `./gradlew test`.

## Translation rules

Same as the kotlinmania global rules. The translator's-mindset list applies:
- One Rust file → one Kotlin file
- Translate top-to-bottom in upstream order
- Comments are content
- Bottom-up, no stubs
- `@Suppress` is forbidden EXCEPT in `OrdAny.new`'s captured-comparator
  cast (documented in AGENTS.md §5)

## Commit Messages

Follow Sydney's style: no AI branding, no Co-Authored-By lines, no emoji.
