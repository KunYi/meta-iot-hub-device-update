

require deliveryoptimization.inc
DESCRIPTION = "apt plugin of Microsoft Delivery Optimization Client"

SRC_URI += "file://0001-fixed-build-error-when-with-yocto-dunfell.patch"
# build DDO_INCLUDE_AGENT
EXTRA_OECMAKE += "-DDO_INCLUDE_PLUGINS=TRUE"

RDEPENDS_${PN} = "libssl libcurl"
DEPENDS  += "deliveryoptimization-sdk openssl curl"
