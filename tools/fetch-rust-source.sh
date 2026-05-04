#!/usr/bin/env bash
# Fetch the upstream gazebo cmp_any crate into tmp/cmp-any/.
#
# Upstream: facebookexperimental/gazebo. We only need the
# `cmp_any/` subtree.
set -euo pipefail

UPSTREAM_REPO="${UPSTREAM_REPO:-https://github.com/facebookexperimental/gazebo.git}"
UPSTREAM_REF="${UPSTREAM_REF:-main}"
SUBPATH="cmp_any"

PROJECT_ROOT="$(cd "$(dirname "$0")/.." && pwd)"
DEST="$PROJECT_ROOT/tmp/cmp-any"
WORK_DIR="$(mktemp -d)"
trap 'rm -rf "$WORK_DIR"' EXIT

echo "Fetching $UPSTREAM_REPO ($UPSTREAM_REF) :: $SUBPATH -> $DEST"

git -C "$WORK_DIR" init -q
git -C "$WORK_DIR" remote add origin "$UPSTREAM_REPO"
git -C "$WORK_DIR" config core.sparseCheckout true
echo "$SUBPATH/" > "$WORK_DIR/.git/info/sparse-checkout"
git -C "$WORK_DIR" fetch -q --depth=1 origin "$UPSTREAM_REF"
git -C "$WORK_DIR" checkout -q FETCH_HEAD

mkdir -p "$DEST"
rm -rf "$DEST"/*
cp -R "$WORK_DIR/$SUBPATH/." "$DEST/"

echo "Done. Vendored at: $DEST"
