# Build and install Delivery Optimization Agent and SDK.

# Environment variables that can be used to configure the behavior of this recipe.
# BUILD_TYPE            Changes the type of build produced by this recipe.
#                       Valid values are Debug, Release, RelWithDebInfo, and MinRelSize.
#                       These values are the same as the CMAKE_BUILD_TYPE variable.

DESCRIPTION = "Microsoft Delivery Optimization Client"
AUTHOR = "Microsoft Corporation"
HOMEPAGE = "https://github.com/microsoft/do-client"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=ee51f94efd0db5b258b5b1b8107fea02"

SRC_URI = "gitsm://github.com/microsoft/do-client.git;branch=main"

SRCREV = "713fe558a8a7fa8213ebe261aacbfd16f531b9b0"
PV =  "0.6.0+git${SRCPV}"
S = "${WORKDIR}/git"

DEPENDS = "boost cpprest libproxy msft-gsl"

inherit cmake pkgconfig

BUILD_TYPE ?= "Debug"
EXTRA_OECMAKE += "-DCMAKE_BUILD_TYPE=${BUILD_TYPE}"
# Don't build DO tests.
EXTRA_OECMAKE += "-DDO_BUILD_TESTS=OFF"

# cpprest installs its config.cmake file in a non-standard location.
# Tell cmake where to find it.
EXTRA_OECMAKE += "-Dcpprestsdk_DIR=${WORKDIR}/recipe-sysroot/usr/lib/cmake"

