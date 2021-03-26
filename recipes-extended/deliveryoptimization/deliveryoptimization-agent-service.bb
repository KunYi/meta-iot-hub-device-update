# Installs and configures the DeliveryOptimization Agent Service
SUMMARY="DeliveryOptimization agent service for systemd"
DESCRIPTION="DevliveryOptimization Agent for IoT-Hub DeviceUpdate-agent"
HOMEPAGE="none"
AUTHOR="kunyi"
LICENSE="CLOSED"
SECTION="misc"
SRC_URI = "\
        file://admin-config.json \
        file://sdk-config.json \
        file://deliveryoptimization-agent.service \
        "

SYSTEMD_SERVICE_${PN} = "deliveryoptimization-agent.service"

do_install_append() {
    #install -d ${D}${sysconfdir}/deliveryoptimization-agent
    #install -m ugo=r ${WORKDIR}/admin-config.json ${D}${sysconfdir}/deliveryoptimization-agent/admin-config.json
    #install -m ugo=r ${WORKDIR}/sdk-config.json ${D}${sysconfdir}/deliveryoptimization-agent/sdk-config.json

    install -d ${D}${systemd_system_unitdir}
    install -m 0644 ${WORKDIR}/deliveryoptimization-agent.service ${D}${systemd_system_unitdir}
}

#FILES_${PN} += "${sysconfdir}/deliveryoptimization-agent/admin-config.json"
#FILES_${PN} += "${sysconfdir}/deliveryoptimization-agent/sdk-config.json"
FILES_${PN} += "${systemd_system_unitdir}/deliveryoptimization-agent.service"

REQUIRED_DISTRO_FEATURES = "systemd"
RDEPENDS_${PN} += "deliveryoptimization-agent"
SYSTEMD_SERVICE_${PN} = "deliveryoptimization-agent.service"

inherit allarch systemd features_check
