

# Guidelines Support Library

DESCRIPTION = "Microsoft Guidelines Support Library"
AUTHOR = "Microsoft Corporation"
HOMEPAGE = "https://github.com/microsoft/GSL"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=363055e71e77071107ba2bb9a54bd9a7"

SRC_URI = "git://github.com/microsoft/GSL.git;protocol=https;branch=main"
SRCREV = "0f6dbc9e2915ef5c16830f3fa3565738de2a9230"

SRC_URI[md5sum] = "64b75d496fe02852ea3ea0f3591595d0"
SRC_URI[sha256sum] = "6668473b390c49f59ffee7601357c161fb849e0cf1ebe7aa656f4f807818ec45"

S = "${WORKDIR}/git"

# Don't build GSL tests.
EXTRA_OECMAKE = "-DGSL_TEST=OFF"

inherit cmake pkgconfig
