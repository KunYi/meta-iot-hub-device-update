SUMMARY="device infomation"
DESCRIPTION="the package for install machine/device informatiion" \
            "into system for deviceupdate agent"
HOMEPAGE="none"
AUTHOR="kunyi"
LICENSE="CLOSED"
SECTION="misc"
SRC_URI=""
# Generates a text file with the deviceupdate-agent applicability info
# for manufacturer and model and copies/installs that file into the image.
#
# Environment variables that can be used to configure the behaviour of this recipe.
# MANUFACTURER          The manufacturer string that will be written to the manufacturer
#                       file and reported through the Device Information PnP Interface.
# MODEL                 The model string that wil be written to the model file and
#                       reported through the Device Information PnP Interface.
# ADU_SOFTWARE_VERSION  The software version for the image/firmware. Will be written to
#                       the version file that is read by DevuceUpdate Agent.

# to check the below article
# https://docs.microsoft.com/en-us/azure/iot-hub-device-update/device-update-configuration-file
#

# Generate the manufacturer, model, and version files
do_compile() {
    echo "connection_string=replace_to_your_conntion_string" > adu-conf.txt
    echo "manufacturer=${MANUFACTURER}" >> adu-conf.txt
    echo "model=${MODEL}" >> adu-conf.txt
    echo "aduc_manufacturer=${MANUFACTURER}" >> adu-conf.txt
    echo "aduc_model=${MODEL}" >> adu-conf.txt
    echo "${ADU_SOFTWARE_VERSION}" > adu-version
}

# Install the files on the image in /etc
do_install() {
    install -d ${D}${sysconfdir}
    install -d ${D}${sysconfdir}/adu
    install -m ugo=r adu-conf.txt ${D}${sysconfdir}/adu/adu-conf.txt
    install -m ugo=r adu-version ${D}${sysconfdir}/adu-version
}

FILES_${PN} += "${sysconfdir}/adu/adu-conf.txt"

inherit allarch
