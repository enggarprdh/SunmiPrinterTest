package com.edge.printdemosunmi

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresPermission
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.edge.printdemosunmi.databinding.ActivityMainBinding



class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    val sunmiHelper = SunmiPrinterHelper()
    val btHelper = BluetoothHelper()
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val res = sunmiHelper.initSunmiPrinterService(this)
        Toast.makeText(this, res, Toast.LENGTH_LONG).show()

        binding.btnPrint.setOnClickListener{
//            sunmiHelper.printHeader()
            sunmiHelper.printBody()
//            sunmiHelper.feedPaper()
        }

        binding.btnShowSerialNo.setOnClickListener{
            sunmiHelper.initPrinter()
            val serialNo = sunmiHelper.getPrinterSerialNo()
            if (serialNo == "")
                Toast.makeText(this, "Printer not connected", Toast.LENGTH_SHORT).show()
            else
                Toast.makeText(this, serialNo, Toast.LENGTH_SHORT).show()

        }

        binding.btnPrintTable.setOnClickListener {
            sunmiHelper.printTest()
        }

        binding.btnBluetooth.setOnClickListener {
           if (ActivityCompat.checkSelfPermission(
                   this,
                   Manifest.permission.BLUETOOTH_CONNECT
               ) != PackageManager.PERMISSION_GRANTED
           ) {
               // TODO: Consider calling
               //    ActivityCompat#requestPermissions
               // here to request the missing permissions, and then overriding
               //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
               //                                          int[] grantResults)
               // to handle the case where the user grants the permission. See the documentation
               // for ActivityCompat#requestPermissions for more details.

           }
            connectBluetooth()
        }
        binding.btnFeed.setOnClickListener{
            sunmiHelper.feedPaper()
        }
    }

    @RequiresPermission(value = "android.permission.BLUETOOTH_CONNECT")
    fun connectBluetooth()
    {
        if (ContextCompat.checkSelfPermission(this@MainActivity, Manifest.permission.BLUETOOTH_CONNECT) == PackageManager.PERMISSION_DENIED)
        {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S)
            {
                ActivityCompat.requestPermissions(this@MainActivity, arrayOf(Manifest.permission.BLUETOOTH_CONNECT), 2)
                return
            }
        }
        btHelper.connectBlueTooth(this@MainActivity)
    }

    fun printReceipt()
    {

    }
}