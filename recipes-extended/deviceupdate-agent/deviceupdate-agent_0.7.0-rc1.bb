DESCRIPTION = "DeviceUpdate agent for Azure IoT Hub"
AUTHOR = "Microsoft Corporation"
HOMEPAGE = "https://github.com/Azure/iot-hub-device-update"
LICENSE = "MIT"

SRC_URI = "gitsm://github.com/Azure/iot-hub-device-update.git;branch=release/2021-q1 \
	   file://0001-fix-build-error-of-agent-for-dunfell.patch \
	   "

SRCREV = "49ab1b72d9bc6ec63df69448305bc2537cd0010e"
LIC_FILES_CHKSUM = "file://LICENSE.md;md5=95a70c9e1af3b97d8bde6f7435d535a8"

PV = "0.7.0-rc1+git${SRCPV}"
S = "${WORKDIR}/git"

USERADD_PACKAGES = "${PN}"

# create group 'adu' and user 'adu'
# create user 'do' and group 'do'
GROUPADD_PARAM_${PN} = "--gid 800 --system --force adu; \
			--gid 801 --system --force do \
			"

USERADD_PARAM_${PN} = "--system --no-create-home \
                        --gid 800 --uid 800 \
			--shell /bin/false \
			adu; \
			--system --no-create-home \
                        --gid 801 --uid 801 \
			--shell /bin/false \
			--groups adu \
                        do \
                      "

do_install_append() {
    install -d ${D}${systemd_system_unitdir}
    install -m 0644 ${S}/daemon/adu-agent.service ${D}${systemd_system_unitdir}
    install -d -m 0666 ${D}/var/lib/adu
    install -d -m a+rwx,g-x,o-wx,ug+s ${D}/adu
}

pkg_postinst_${PN}_append() {
#!/bin/sh -e
mkdir -p  "$D/var/lib/adu"
chmod 0766 "$D/var/lib/adu"
chown -R adu:adu "$D/var/lib/adu"
chgrp -c adu "$D/usr/lib/adu/adu-shell"
chmod u+s "$D/usr/lib/adu/adu-shell"
chmod 0750 "$D/usr/lib/adu/adu-swupdate.sh"
chown -R adu:adu $D/adu
}

# add update script and service unit file into package
FILES_${PN} =+ "${libdir}/adu/adu-swupdate.sh \
		${libdir}/adu/adu-shell \
		${systemd_system_unitdir}/adu-agent.service \
		/adu \
		"

SYSTEMD_SERVICE_${PN} = "adu-agent.service"

inherit cmake pkgconfig useradd systemd features_check

ADU_BUILD_TYPE ?= "Release"
# setting platform linux and build type
EXTRA_OECMAKE += "-DCMAKE_BUILD_TYPE:STRING=${ADU_BUILD_TYPE}"
EXTRA_OECMAKE += "-DADUC_PLATFORM_LAYER:STRING=linux"
# config file path and disable install deaemon
EXTRA_OECMAKE += "-DADUC_INSTALL_DAEMON:BOOL=false"
EXTRA_OECMAKE += "-DADUC_LOG_FOLDER:STRING=/adu/logs"
EXTRA_OECMAKE += "-DADUC_CONF_FOLDER:STRING=/adu"
# for installedCriteria file
EXTRA_OECMAKE += "-DADUC_DATA_FOLDER:STRING=/etc"
EXTRA_OECMAKE += "-DADUC_INSTALLEDCRITERIA_FILE:STRING=adu-version"
EXTRA_OECMAKE += "-DADUC_REGISTER_DAEMON:BOOL=false"
# for Provision with IoTEdge
EXTRA_OECMAKE += "-DADUC_PROVISION_WITH_EIS:BOOL=false"

RDEPENDS_${PN} += "bash libcurl deviceupdate-agent device-info-files"
# for install deliveryoptimization
RDEPENDS_${PN} += "deliveryoptimization-agent-service"
RDEPENDS_${PN} += "deliveryoptimization-agent"
RDEPENDS_${PN} += "deliveryoptimization-apt"

# DeviceUpdate Agent depends on azure-iot-sdk-c and DeliveryOptimization Agent & SDK
DEPENDS += "azure-iot-sdk-c deliveryoptimization-sdk"

REQUIRED_DISTRO_FEATURES = "systemd"

