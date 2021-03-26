
require deliveryoptimization.inc
DESCRIPTION = "agent of Microsoft Delivery Optimization Client"

SRC_URI += "file://0001-dunfell-add-need-target-libraries-for-build-client-lite.patch"

# build DDO_INCLUDE_AGENT
EXTRA_OECMAKE += "-DDO_INCLUDE_AGENT=TRUE"
EXTRA_OECMAKE += " -G Ninja "
RDEPENDS_${PN} = "libssl libcurl"
DEPENDS  += "openssl curl"
