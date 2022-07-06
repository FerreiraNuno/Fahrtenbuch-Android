package com.example.fahrtenbuch.background;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.example.fahrtenbuch.db.Database;

public class BluetoothBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        final String action = intent.getAction();
        if (action.equals(BluetoothDevice.ACTION_ACL_CONNECTED)) {
            // Get the BluetoothDevice object from the Intent
            BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
            String connectedDeviceMac = device.getAddress();

            Database db = new Database(context);
            String bluetoothBeacon = db.getBluetoothDevice();
            if (connectedDeviceMac.equals(bluetoothBeacon)) {
                Toast.makeText(context, "Verbunden mit Bluetooth Beacon!" , Toast.LENGTH_SHORT).show();

                Intent service = new Intent(context, RideTracker.class);
                context.startService(service);
            }
        } else if (BluetoothDevice.ACTION_ACL_DISCONNECTED.equals(action)) {
            Toast.makeText(context, "Device Disconnected", Toast.LENGTH_SHORT).show();
        }
    }
}
