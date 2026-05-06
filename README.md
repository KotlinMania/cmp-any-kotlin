# gazebo-kotlin in Kotlin

[![GitHub link](https://img.shields.io/badge/GitHub-KotlinMania%2Fgazebo--kotlin-blue.svg)](https://github.com/KotlinMania/gazebo-kotlin)
[![Maven Central](https://img.shields.io/maven-central/v/io.github.kotlinmania/gazebo-kotlin)](https://central.sonatype.com/artifact/io.github.kotlinmania/gazebo-kotlin)
[![Build status](https://img.shields.io/github/actions/workflow/status/KotlinMania/gazebo-kotlin/ci.yml?branch=main)](https://github.com/KotlinMania/gazebo-kotlin/actions)

This is a Kotlin Multiplatform line-by-line transliteration port of [`facebookincubator/gazebo`](https://github.com/facebookincubator/gazebo).

**Original Project:** This port is based on [`facebookincubator/gazebo`](https://github.com/facebookincubator/gazebo). All design credit and project intent belong to the upstream authors; this repository is a faithful port to Kotlin Multiplatform with no behavioural changes intended.

### Porting status

This is an **in-progress port**. The goal is feature parity with the upstream Rust crate while providing a native Kotlin Multiplatform API. Every Kotlin file carries a `// port-lint: source <path>` header naming its upstream Rust counterpart so the AST-distance tool can track provenance.

---

## Upstream README — `facebookincubator/gazebo`

> The text below is reproduced and lightly edited from [`https://github.com/facebookincubator/gazebo`](https://github.com/facebookincubator/gazebo). It is the upstream project's own description and remains under the upstream authors' authorship; links have been rewritten to absolute upstream URLs so they continue to resolve from this repository.

## Gazebo - a library of Rust utilities

[![GitHub link](https://img.shields.io/badge/GitHub-facebookincubator%2Fgazebo-blue.svg)](https://github.com/facebookincubator/gazebo)
[![crates.io version](https://img.shields.io/crates/v/gazebo.svg)](https://crates.io/crates/gazebo)
[![docs.rs availability](https://img.shields.io/docsrs/gazebo?label=docs.rs)](https://docs.rs/gazebo/)
[![Build status](https://img.shields.io/github/workflow/status/facebookincubator/gazebo/ci.svg)](https://github.com/facebookincubator/gazebo/actions)

This library contains a collection of well-tested utilities. Most modules stand
alone, but taking a few representative examples:

- `gazebo::prelude::*` is intended to be imported as such, and provides
  extension traits to common types. For example, it provides `Vec::map` which is
  equivalent to `iter().map(f).collect::<Vec<_>>()`, and `str::split1` like
  `split` but which only splits once. We hope some of these functions one day
  make it into the Rust standard library.
- `gazebo::dupe` provides the trait `Dupe` with the member `dupe`, all of which
  are exactly like `Clone`. The difference is that `Dupe` should not be
  implemented for types that reallocate or have expensive `clone` operations -
  e.g. there is `Dupe` for `Arc` and `usize`, but not for `String` and `Vec`. By
  using `dupe` it is easy to focus on the `clone` calls (which should be rare)
  and ignore things whose cost is minimal.
- `gazebo::cell::ARef` provides a type which is either a `Ref<T>` or a direct
  reference `&T`, with operations that make it look like `Ref` -- allowing you
  to uniformly convert a reference into something like a `Ref`.

The functionality provided by Gazebo is not stable, and continues to evolve with
both additions (as we find new useful features) and removals (as we find better
patterns or libraries encapsulating the ideas better). While the code varies in
usefulness and design quality, it is all well tested and documented.

## Using Gazebo

Gazebo can be depended upon by adding `gazebo` to your `[dependencies]`, using
the standard
[Cargo patterns](https://doc.rust-lang.org/cargo/reference/specifying-dependencies.html).

The two interesting directories in this repo are `gazebo` (which contains the
source to Gazebo itself) and `gazebo_derive` (which contains support for
`#[derive(Dupe)]` and other Gazebo traits). Usually you will directly import
`gazebo`, but `gazebo_derive` is a required transitive dependency if you are
sourcing the library from GitHub.

## Learn More

You can learn more about Gazebo in
[this introductory video](https://www.youtube.com/watch?v=pQJkx9HL_04), or from
the following blog posts:

- [Rust Nibbles - Gazebo: Prelude](https://developers.facebook.com/blog/post/2021/06/29/rust-nibbles-gazebo-prelude/)
- [Rust Nibbles - Gazebo: Dupe](https://developers.facebook.com/blog/post/2021/07/06/rust-nibbles-gazebo-dupe/)
- [Rust Nibbles - Gazebo: Variants](https://developers.facebook.com/blog/post/2021/07/13/rust-nibbles-gazebo-variants)
- [Rust Nibbles - Gazebo: AnyLifetime](https://developers.facebook.com/blog/post/2021/07/20/rust-nibbles-gazebo-any-lifetime/)
- [Rust Nibbles - Gazebo: Comparisons](https://developers.facebook.com/blog/post/2021/07/27/rust-nibbles-gazebo-comparisons/)
- [Rust Nibbles - Gazebo: Casts and Transmute](https://developers.facebook.com/blog/post/2021/08/03/rust-nibbles-gazebo-casts-transmute/)
- [Rust Nibbles - Gazebo: The rest of the tent](https://developers.facebook.com/blog/post/2021/08/10/rust-nibbles-gazebo-rest-of-tent/)

## Making a release

1. Check the
   [GitHub Actions](https://github.com/facebookincubator/gazebo/actions) are
   green.
2. Update `CHANGELOG.md` with the changes since the last release.
   [This link](https://github.com/facebookincubator/gazebo/compare/v0.1.0...main)
   can help (update to compare against the last release).
3. Update the version numbers of the two `Cargo.toml` files. Bump them by 0.0.1
   if there are no incompatible changes, or 0.1.0 if there are. Bump the
   dependency in `gazebo` to point at the latest `gazebo_derive` version.
4. Copy the files `CHANGELOG.md`, the two `LICENSE-` files and `README.md` into
   each `gazebo` and `gazebo_derive` subdirectory.
5. Run `cargo publish --allow-dirty --dry-run`, then without the `--dry-run`,
   first in `gazebo_derive` and then `gazebo` directories.
6. Create a
   [GitHub release](https://github.com/facebookincubator/gazebo/releases/new)
   with `v0.X.Y`, using the `gazebo` version as the name.

## License

Gazebo is both MIT and Apache License, Version 2.0 licensed, as found in the
[LICENSE-MIT](https://github.com/facebookincubator/gazebo/blob/HEAD/LICENSE-MIT) and [LICENSE-APACHE](https://github.com/facebookincubator/gazebo/blob/HEAD/LICENSE-APACHE) files.

---

## About this Kotlin port

### Installation

```kotlin
dependencies {
    implementation("io.github.kotlinmania:gazebo-kotlin:0.1.0")
}
```

### Building

```bash
./gradlew build
./gradlew test
```

### Targets

- macOS arm64
- Linux x64
- Windows mingw-x64
- iOS arm64 / simulator-arm64 (Swift export + XCFramework)
- JS (browser + Node.js)
- Wasm-JS (browser + Node.js)
- Android (API 24+)

### Porting guidelines

See [AGENTS.md](AGENTS.md) and [CLAUDE.md](CLAUDE.md) for translator discipline, port-lint header convention, and Rust → Kotlin idiom mapping.

### License

This Kotlin port is distributed under the same MIT license as the upstream [`facebookincubator/gazebo`](https://github.com/facebookincubator/gazebo). See [LICENSE](LICENSE) (and any sibling `LICENSE-*` / `NOTICE` files mirrored from upstream) for the full text.

Original work copyrighted by the gazebo authors.  
Kotlin port: Copyright (c) 2026 Sydney Renee and The Solace Project.

### Acknowledgments

Thanks to the [`facebookincubator/gazebo`](https://github.com/facebookincubator/gazebo) maintainers and contributors for the original Rust implementation. This port reproduces their work in Kotlin Multiplatform; bug reports about upstream design or behavior should go to the upstream repository.
