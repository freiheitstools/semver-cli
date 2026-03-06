# Artifact Signing and Verification

## Overview

All release artifacts for `mein-tool` are signed with GPG (GNU Privacy Guard).
Each [GitHub Release](https://github.com/OWNER/REPO/releases) includes the following files per artifact:

| File | Description |
|------|-------------|
| `mein-tool-<version>.jar` | Platform-independent uber JAR |
| `mein-tool-<version>-mac.aarch64.bin` | Native binary for macOS (ARM) |
| `mein-tool-<version>-linux.amd64.bin` | Native binary for Linux (x86_64) |
| `*.asc` | GPG detached signature for each artifact |
| `*.sha256` | SHA-256 checksum for each artifact |

## Verifying Downloaded Artifacts

### 1. Import the signing public key

```bash
gpg --keyserver keys.openpgp.org --recv-keys <KEY_FINGERPRINT>
```

> Replace `<KEY_FINGERPRINT>` with the actual GPG key fingerprint of the project maintainers.

### 2. Verify the GPG signature

```bash
gpg --verify mein-tool-1.2.3.jar.asc mein-tool-1.2.3.jar
```

A successful verification shows:

```
gpg: Good signature from "OWNER <email@example.com>"
```

### 3. Verify the SHA-256 checksum

On Linux:

```bash
sha256sum --check mein-tool-1.2.3.jar.sha256
```

On macOS:

```bash
shasum -a 256 --check mein-tool-1.2.3.jar.sha256
```

A successful verification shows:

```
mein-tool-1.2.3.jar: OK
```

## Setting Up GPG Signing for Releases (Maintainers)

### Generate a GPG key

```bash
gpg --full-generate-key
```

- Select **RSA and RSA**
- Key size: **4096 bits**
- Expiry: choose a reasonable timeframe (e.g. 2 years)
- Enter your name and email address

### Export the private key as base64

```bash
gpg --export-secret-keys --armor <KEY_ID> | base64 -w 0
```

On macOS, use `base64` without `-w 0` (it does not wrap by default):

```bash
gpg --export-secret-keys --armor <KEY_ID> | base64
```

### Store as GitHub Secrets

1. Navigate to the repository: **Settings** > **Secrets and variables** > **Actions**
2. Create a new secret `GPG_PRIVATE_KEY` with the base64-encoded private key
3. Create a new secret `GPG_PASSPHRASE` with the passphrase for the GPG key

| Secret | Description |
|--------|-------------|
| `GPG_PRIVATE_KEY` | Base64-encoded private GPG key (armored export) |
| `GPG_PASSPHRASE` | Passphrase for the GPG key |

### Publish the public key

Upload your public key to a keyserver so users can import it:

```bash
gpg --keyserver keys.openpgp.org --send-keys <KEY_ID>
```

## Homebrew Tap

### Setting up the Tap repository

1. Create a new GitHub repository named `OWNER/homebrew-tap`
2. Add a Formula file, e.g. `Formula/mein-tool.rb`:

```ruby
class MeinTool < Formula
  desc "DESCRIPTION"
  homepage "https://github.com/OWNER/REPO"
  license "Apache-2.0"
  version "1.2.3"

  if OS.mac?
    url "https://github.com/OWNER/REPO/releases/download/v#{version}/mein-tool-#{version}-mac.aarch64.bin"
    sha256 "SHA256_MACOS"
  elsif OS.linux?
    url "https://github.com/OWNER/REPO/releases/download/v#{version}/mein-tool-#{version}-linux.amd64.bin"
    sha256 "SHA256_LINUX"
  end

  def install
    if OS.mac?
      bin.install "mein-tool-#{version}-mac.aarch64.bin" => "mein-tool"
    elsif OS.linux?
      bin.install "mein-tool-#{version}-linux.amd64.bin" => "mein-tool"
    end
  end

  test do
    assert_match version.to_s, shell_output("#{bin}/mein-tool --version")
  end
end
```

> Replace `SHA256_MACOS` and `SHA256_LINUX` with the actual SHA-256 checksums from the release `.sha256` files.

### Installing via Homebrew

```bash
brew tap OWNER/tap
brew install mein-tool
```

### Updating

```bash
brew update
brew upgrade mein-tool
```
