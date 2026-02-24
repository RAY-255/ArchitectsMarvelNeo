#!/usr/bin/env bash
set -euo pipefail

ROOT_DIR="$(cd "$(dirname "$0")/.." && pwd)"
UPSTREAM_DIR="${ROOT_DIR}/.tmp/ArchitectsMarvel"

mkdir -p "${ROOT_DIR}/.tmp"

if [ ! -d "${UPSTREAM_DIR}" ]; then
  git clone https://github.com/RAY-255/ArchitectsMarvel "${UPSTREAM_DIR}"
fi

# Best-effort copy points. Adjust paths after inspecting upstream layout.
if [ -d "${UPSTREAM_DIR}/src/main/resources/assets" ]; then
  rsync -av --delete "${UPSTREAM_DIR}/src/main/resources/assets/" "${ROOT_DIR}/src/main/resources/assets/"
fi

if [ -d "${UPSTREAM_DIR}/src/main/resources/data" ]; then
  rsync -av --delete "${UPSTREAM_DIR}/src/main/resources/data/" "${ROOT_DIR}/src/main/resources/data/"
fi

echo "Upstream import attempted. Review and adapt Java registration/data classes as needed for NeoForge 1.21.1."
