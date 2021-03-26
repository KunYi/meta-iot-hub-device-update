

require deliveryoptimization.inc

DESCRIPTION = "SDK of Microsoft Delivery Optimization Client"

# build for sdk package
EXTRA_OECMAKE += "-DDO_INCLUDE_SDK=ON"

do_install_append() {
    install -d ${D}${includedir}
    install -m 0755 ${S}/sdk-cpp/include/do_config.h ${D}${includedir}/
    install -m 0755 ${S}/sdk-cpp//include/do_download.h ${D}${includedir}/
    install -m 0755 ${S}/sdk-cpp//include/do_download_status.h ${D}${includedir}/
    install -m 0755 ${S}/sdk-cpp//include/do_exceptions.h ${D}${includedir}/
}

BBCLASSEXTEND = "native nativesdk"
