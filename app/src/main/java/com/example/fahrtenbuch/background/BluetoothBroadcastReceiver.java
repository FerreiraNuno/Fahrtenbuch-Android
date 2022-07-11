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

                Intent serviceIntent = new Intent(context, RideTracker.class);
                context.startForegroundService(serviceIntent);
            }
        } else if (action.equals(BluetoothDevice.ACTION_ACL_DISCONNECTED)) {
            Toast.makeText(context, "Die Fahrt wurde beendet", Toast.LENGTH_SHORT).show();
            Intent serviceIntent = new Intent(context, RideTracker.class);
            context.stopService(serviceIntent);
        } else if (action.equals(BluetoothDevice.ACTION_ACL_DISCONNECT_REQUESTED)) {
            Toast.makeText(context, "Die Fahrt wurde beendet", Toast.LENGTH_SHORT).show();
            Intent serviceIntent = new Intent(context, RideTracker.class);
            context.stopService(serviceIntent);
        }
    }
}
