#!/usr/bin/env sh

if ! command -v jekyll ;
then
  echo "jeykyll not found";
  exit 1;
fi

set -x

jekyll serve
