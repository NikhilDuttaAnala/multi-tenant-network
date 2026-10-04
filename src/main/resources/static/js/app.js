const tenantList = document.getElementById("tenantList");
const deviceList = document.getElementById("deviceList");

const sourceDeviceSelect =
    document.getElementById("sourceDevice");

const destinationDeviceSelect =
    document.getElementById("destinationDevice");

const checkButton =
    document.getElementById("checkButton");

const resultBox =
    document.getElementById("resultBox");

const resultText =
    document.getElementById("resultText");

async function loadTenants() {
    try {
        const response = await fetch("/api/network/tenants");

        if (!response.ok) {
            throw new Error("Unable to load tenants");
        }

        const tenants = await response.json();

        tenantList.innerHTML = "";

        tenants.forEach(function (tenant) {
            const listItem = document.createElement("li");

            listItem.textContent =
                `${tenant.name} - VLAN ${tenant.id} - ${tenant.network}`;

            tenantList.appendChild(listItem);
        });

    } catch (error) {
        tenantList.innerHTML =
            "<li>Failed to load tenant data</li>";

        console.error(error);
    }
}

async function loadDevices() {
    try {
        const response = await fetch("/api/network/devices");

        if (!response.ok) {
            throw new Error("Unable to load devices");
        }

        const devices = await response.json();

        deviceList.innerHTML = "";

        sourceDeviceSelect.innerHTML =
            '<option value="">Select source device</option>';

        destinationDeviceSelect.innerHTML =
            '<option value="">Select destination device</option>';

        devices.forEach(function (device) {
            const listItem = document.createElement("li");

            listItem.textContent =
                `${device.name} - ${device.ipAddress} - Tenant ${device.tenantId}`;

            deviceList.appendChild(listItem);

            const sourceOption =
                document.createElement("option");

            sourceOption.value = device.ipAddress;

            sourceOption.textContent =
                `${device.name} - ${device.ipAddress}`;

            sourceDeviceSelect.appendChild(sourceOption);

            const destinationOption =
                document.createElement("option");

            destinationOption.value = device.ipAddress;

            destinationOption.textContent =
                `${device.name} - ${device.ipAddress}`;

            destinationDeviceSelect.appendChild(
                destinationOption
            );
        });

    } catch (error) {
        deviceList.innerHTML =
            "<li>Failed to load device data</li>";

        console.error(error);
    }
}

async function checkCommunication() {
    const sourceIp = sourceDeviceSelect.value;
    const destinationIp = destinationDeviceSelect.value;

    if (!sourceIp || !destinationIp) {
        resultBox.className = "result-box blocked";

        resultText.textContent =
            "Please select both source and destination devices.";

        return;
    }

    if (sourceIp === destinationIp) {
        resultBox.className = "result-box blocked";

        resultText.textContent =
            "Please select two different devices.";

        return;
    }

    try {
        const url =
            `/api/network/check?sourceIp=${encodeURIComponent(sourceIp)}` +
            `&destinationIp=${encodeURIComponent(destinationIp)}`;

        const response = await fetch(url);

        if (!response.ok) {
            throw new Error("Unable to check communication");
        }

        const result = await response.json();

        if (result.allowed) {
            resultBox.className = "result-box allowed";
        } else {
            resultBox.className = "result-box blocked";
        }

        resultText.textContent =
            `${result.message} | ` +
            `${result.sourceDevice} → ` +
            `${result.destinationDevice}`;

    } catch (error) {
        resultBox.className = "result-box blocked";

        resultText.textContent =
            "An error occurred while checking communication.";

        console.error(error);
    }
}

checkButton.addEventListener(
    "click",
    checkCommunication
);

document.addEventListener(
    "DOMContentLoaded",
    async function () {
        await loadTenants();
        await loadDevices();
    }
);