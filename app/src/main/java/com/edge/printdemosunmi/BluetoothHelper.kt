package com.edge.printdemosunmi

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import android.content.Context
import android.widget.Toast
import androidx.annotation.RequiresPermission
import java.io.IOException
import java.util.UUID

class BluetoothHelper {
    private val PRINTER_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB")

    var Innerprinter_Address:String = "00:11:22:33:44:55"
    var bluetoothSocket:BluetoothSocket? = null

    fun getBTAdapter():BluetoothAdapter {
        return BluetoothAdapter.getDefaultAdapter()
    }

    @RequiresPermission(value = "android.permission.BLUETOOTH_CONNECT")
    private fun getDevice(bluetoothAdapter: BluetoothAdapter): BluetoothDevice? {
        var innerprinter_device: BluetoothDevice? = null
        val devices: Set<BluetoothDevice> = bluetoothAdapter.bondedDevices
        for (device in devices) {
            if (device.address == Innerprinter_Address) {
                innerprinter_device = device
                break
            }
        }
        return innerprinter_device
    }
    @RequiresPermission(value = "android.permission.BLUETOOTH_CONNECT")
    fun getSocket(device:BluetoothDevice):BluetoothSocket {
        val socket: BluetoothSocket
        socket = device.createRfcommSocketToServiceRecord(PRINTER_UUID)
        socket.connect()
        return socket
    }

    @RequiresPermission(value = "android.permission.BLUETOOTH_CONNECT")
    fun connectBlueTooth(context: Context): Boolean {
        if (bluetoothSocket == null) {
            if (getBTAdapter() == null) {
                Toast.makeText(context, "Bluetooth Not found", Toast.LENGTH_SHORT).show()
                return false
            }
            if (!getBTAdapter().isEnabled) {
                Toast.makeText(context, "Bluetooth disabled", Toast.LENGTH_SHORT).show()
                return false
            }
            val device = getDevice(getBTAdapter())
            if (device == null) {
                Toast.makeText(context, "Device not found" ,Toast.LENGTH_SHORT).show()
                return false
            } else
            {
                Toast.makeText(context, "Device found" ,Toast.LENGTH_SHORT).show()
            }
            try {
                bluetoothSocket = getSocket(device)
            } catch (e: IOException) {
                Toast.makeText(context, "Error connection input", Toast.LENGTH_SHORT).show()
                return false
            }
        }
        return true
    }

}