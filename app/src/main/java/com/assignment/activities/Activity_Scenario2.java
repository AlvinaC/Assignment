package com.assignment.activities;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.assignment.BaseActivity;
import com.assignment.Network.VolleyErrorHelper;
import com.assignment.Network.VolleyHelper;
import com.assignment.R;
import com.assignment.models.Location;
import com.assignment.util.Alerts;
import com.assignment.util.CommonMethods;
import com.assignment.util.Constants;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Summary: Scenario 2
 */
public class Activity_Scenario2 extends BaseActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener {
    private static final String TAG = "Activity_Scenario2";

    private Spinner spinner;
    private ArrayList<Location> loc_list;
    private TextView txt_by_car_val, txt_by_train_val;
    private int position;
    private Button btn_navigate;
    private ProgressDialog pDialog;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scenario_2);
        init();
        setToolbarWithBackNavigation(Constants.SCENARIO_2);
        setListeners();
        if (savedInstanceState == null) {
            fetchdata();
        }
    }

    private void fetchdata() {
        if (CommonMethods.isNetworkConnected(this))
            getLocations();
        else {
            showInternetAlertDialog();
        }
    }


    private void init() {
        spinner = (Spinner) findViewById(R.id.spinner);
        txt_by_car_val = (TextView) findViewById(R.id.txt_by_car_val);
        txt_by_train_val = (TextView) findViewById(R.id.txt_by_train_val);
        btn_navigate = (Button) findViewById(R.id.btn_navigate);
        loc_list = new ArrayList<Location>();
    }


    private void setListeners() {
        spinner.setOnItemSelectedListener(this);
        btn_navigate.setOnClickListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
        //saved to get the long, lat
        this.position = position;
        String car_val = loc_list.get(position).getBycar();
        String train_val = loc_list.get(position).getByTrain();
        if (car_val != null)
            txt_by_car_val.setText(loc_list.get(position).getBycar());
        else
            txt_by_car_val.setText("");
        if (train_val != null)
            txt_by_train_val.setText(loc_list.get(position).getByTrain());
        else
            txt_by_train_val.setText("");

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    private void getLocations() {
        String tag_json_obj = "json_obj_req";
        String url = Constants.GENERIC_URL;
        pDialog = new ProgressDialog(this);
        String message = "Loading....";
        Alerts.getInstance().showDialog(this, pDialog, message);

        JsonArrayRequest jsonObjReq = new JsonArrayRequest(Request.Method.GET,
                url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d("LocationResponse", response.toString());
                        try {
                            if (response != null) {
                                for (int i = 0; i < response.length(); i++) {
                                    JSONObject obj = response.getJSONObject(i);

                                    String id = obj.getString("id");
                                    String name = obj.getString("name");
                                    JSONObject f_obj = obj.getJSONObject("fromcentral");
                                    String byCar = null;
                                    if (f_obj.has("car"))
                                        byCar = f_obj.getString("car");
                                    String byTrain = null;
                                    if (f_obj.has("train"))
                                        byTrain = f_obj.getString("train");
                                    JSONObject l_obj = obj.getJSONObject("location");
                                    String lattitude = l_obj.getString("latitude");
                                    String longitude = l_obj.getString("longitude");
                                    Location loc = new Location(id, name, byCar, byTrain, longitude, lattitude);
                                    loc.setId(id);
                                    loc.setName(name);
                                    loc.setBycar(byCar);
                                    loc.setByTrain(byTrain);
                                    loc.setLongitude(longitude);
                                    loc.setLattitude(lattitude);
                                    loc_list.add(loc);
                                }
                                setSpinnerAdapter();
                            } else {
                                Toast.makeText(getApplicationContext(), Constants.NO_LOCATIONS, Toast.LENGTH_SHORT).show();
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        Alerts.getInstance().dismiss(Activity_Scenario2.this, pDialog);


                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Alerts.getInstance().dismiss(Activity_Scenario2.this, pDialog);
                Alerts.getInstance().showDialogOnResponseError(Activity_Scenario2.this);
                String errorString = VolleyErrorHelper.getMessage(error, Activity_Scenario2.this);
                if (errorString != null) {
                    Toast.makeText(getApplicationContext(), errorString, Toast.LENGTH_SHORT).show();
                }
            }
        }) {
        };

        // Adding request to request queue
        VolleyHelper.getInstance(getApplicationContext()).addToRequestQueue(jsonObjReq, tag_json_obj);
    }

    private void setSpinnerAdapter() {
        ArrayAdapter<Location> dataAdapter = new ArrayAdapter<Location>(this, android.R.layout.simple_spinner_item, loc_list) {

            public View getView(int position, View convertView, ViewGroup parent) {
                View v = super.getView(position, convertView, parent);
                Typeface externalFont = Typeface.createFromAsset(getAssets(), "arial.ttf");
                ((TextView) v).setTypeface(externalFont);
                ((TextView) v).setTextColor(getResources().getColor(R.color.intro_page_indicator_dark));
                return v;
            }


            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                View v = super.getDropDownView(position, convertView, parent);
                Typeface externalFont = Typeface.createFromAsset(getAssets(), "arial.ttf");
                ((TextView) v).setTypeface(externalFont);
                ((TextView) v).setTextColor(getResources().getColor(R.color.intro_page_indicator_dark));
                return v;
            }
        };
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);
    }

    public void showInternetAlertDialog() {

        Alerts.showDialogOK(this, getResources().getString(R.string.internet),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case DialogInterface.BUTTON_POSITIVE:
                                if (dialog != null)
                                    dialog.dismiss();
                                break;
                        }
                    }
                });
    }


    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.btn_navigate:
                if (!loc_list.isEmpty()) {
                    Intent intent = new Intent(this, MapActivity.class);
                    intent.putExtra(Constants.LOCATION_NAME, spinner.getSelectedItem().toString());
                    intent.putExtra(Constants.lATITUDE, loc_list.get(position).getLattitude());
                    intent.putExtra(Constants.LONGITUDE, loc_list.get(position).getLongitude());
                    startActivity(intent);
                }
                break;
        }

    }

    //region config change handle
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt("Spinner_item_position", position);
        outState.putSerializable("location_list", loc_list);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        int spinner_position = savedInstanceState.getInt("Spinner_item_position");
        loc_list = (ArrayList<Location>) savedInstanceState.getSerializable("location_list");
        restoreData(spinner_position);
        super.onRestoreInstanceState(savedInstanceState);
    }

    private void restoreData(int spinner_position) {
        if (loc_list.size() > 0) {
            setSpinnerAdapter();
            spinner.setSelection(spinner_position);
            txt_by_car_val.setText(loc_list.get(spinner_position).getBycar());
            txt_by_train_val.setText(loc_list.get(spinner_position).getByTrain());
        } else {
            fetchdata();
        }
    }
    //endregion

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Alerts.getInstance().dismiss(Activity_Scenario2.this, pDialog);
    }
}
