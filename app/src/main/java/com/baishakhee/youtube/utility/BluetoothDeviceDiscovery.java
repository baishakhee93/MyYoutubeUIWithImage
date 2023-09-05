package com.baishakhee.youtube.utility;

import android.Manifest;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;

public class BluetoothDeviceDiscovery {

    private BluetoothAdapter bluetoothAdapter;
    private BroadcastReceiver receiver;

    public BluetoothDeviceDiscovery(Activity activity) {
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        // Check if Bluetooth is supported on the device
        if (bluetoothAdapter == null) {
            // Bluetooth is not available on this device
            return;
        }

        // Request Bluetooth permission if not enabled
        if (!bluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            activity.startActivityForResult(enableBtIntent, 1);
        }

        // Register a BroadcastReceiver for discovering devices
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        receiver = new BroadcastReceiver() {
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                    BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                    // Handle the discovered Bluetooth device here
                    // You might want to display the device name and address
                }
            }
        };
        activity.registerReceiver(receiver, filter);
    }

    @RequiresApi(api = Build.VERSION_CODES.S)
    public void startDiscovery(Activity activity) {
        // Start discovering nearby Bluetooth devices
        if (bluetoothAdapter != null) {
            if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.BLUETOOTH_SCAN) != PackageManager.PERMISSION_GRANTED) {
                int requestCode = 0;
                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.BLUETOOTH_SCAN}, requestCode);
                return;
            }
            bluetoothAdapter.startDiscovery();
        }
    }

    public void stopDiscovery(Activity activity) {
        // Stop the discovery process
        if (bluetoothAdapter != null) {
            if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.BLUETOOTH_SCAN) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            bluetoothAdapter.cancelDiscovery();
        }
    }

    public void unregisterReceiver(Activity activity) {
        if (receiver != null) {
            activity.unregisterReceiver(receiver);
        }
    }
}
