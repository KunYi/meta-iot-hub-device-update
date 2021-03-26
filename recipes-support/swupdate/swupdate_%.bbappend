# for override original defconfig

FILESEXTRAPATHS_append := "${THISDIR}/${PN}:"

PACKAGECONFIG_CONFARGS = ""

SRC_URI += " \
     file://swupdate.cfg \
     file://09-swupdate-args \
     "
ADU_HW_COMPAT ?= "${MODEL_NAME} ${HW_REV}"
ADUC_PUBLIC_KEY ?= "${TOPDIR}/../keys/public.pem"

do_compile_append() {
    echo "${ADU_HW_COMPAT}" > ${WORKDIR}/adu-hw-compat
}

do_install_append() {
    install -d ${D}${sysconfdir}
    install -m 644 ${WORKDIR}/swupdate.cfg ${D}${sysconfdir}/swupdate.cfg
    install -m 644 ${WORKDIR}/adu-hw-compat ${D}${sysconfdir}/adu-hw-compat
    install -d ${D}/adukey
    install -m 644 ${ADUC_PUBLIC_KEY} ${D}/adukey/public.pem

    install -d ${D}${libdir}/swupdate/conf.d
    echo "SWUPDATE_ARGS=\"\$SWUPDATE_ARGS -k /adukey/public.pem\"" > ${D}${libdir}/swupdate/conf.d/adu-swupdate.env
    echo "SWUPDATE_WEBSERVER_ARGS=\"-r /www\"" >> ${D}${libdir}/swupdate/conf.d/adu-swupdate.env
    install -m 644 ${WORKDIR}/09-swupdate-args ${D}${libdir}/swupdate/conf.d/09-swupdate-args
}

FILES_${PN} += "/adukey/public.pem"
FILES_${PN} += "${sysconfdir}/swupdate.cfg"

DEPANDS_append += "curl"
