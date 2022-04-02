// Peter Arias

package com.example.android.firstsensorcomputingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.res.Resources;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager sensorManager;
    Sensor accelerometer;
    TextView accelDataText; // used to display data in the center of the screen after button press
    int buttonPressed; // used to indicate which button was pressed
    float x, y, z, magnitude;  // used to obtain the corresponding axis data from accelerometer
    String sensorData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        accelDataText = findViewById(R.id.accel_data);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        // check if accelerometer is available on current device
        if (accelerometer != null) {
            registerSensor(accelerometer);
        } else {
            accelDataText.setText("Accelerometer not available.");
        }
    }

    // register sensors to be used
    private void registerSensor(Sensor sensor) {
        sensorManager.registerListener(MainActivity.this, sensor, sensorManager.SENSOR_DELAY_NORMAL);
    }

    public void onXBtnPress(View button) {
        buttonPressed = 1;
    }

    public void onYBtnPress(View button) {
        buttonPressed = 2;
    }

    public void onZBtnPress(View button) {
        buttonPressed = 3;
    }

    public void onMagnitudeBtnPress(View button) {
        buttonPressed = 4;
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        x = sensorEvent.values[0];
        y = sensorEvent.values[1];
        z = sensorEvent.values[2];
        magnitude = (float) Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2) + Math.pow(z, 2));
        // allows access to data in res
        Resources res = getResources();

        switch (buttonPressed) {
            case 1: { // display x-axis using sensor data
                sensorData = String.format(res.getString(R.string.x_axis_data), x);
                break;
            }
            case 2: { // display y-axis using sensor data
                sensorData = String.format(res.getString(R.string.y_axis_data), y);
                break;
            }
            case 3: { // display z-axis using sensor data
                sensorData = String.format(res.getString(R.string.z_axis_data), z);
                break;
            }
            case 4: { // display the magnitude using sensor data
                sensorData = String.format(res.getString(R.string.magnitude_data), magnitude);
                break;
            }
            default: {
                break;
            }
        }
        accelDataText.setText(sensorData);
    }

    // unregister listeners for sensors when not in use - saves battery
    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
