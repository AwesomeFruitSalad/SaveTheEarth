package org.fruitsalad.ui.dashboard;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Cartesian;
import com.anychart.core.cartesian.series.Column;
import com.anychart.enums.Anchor;
import com.anychart.enums.HoverMode;
import com.anychart.enums.Position;
import com.anychart.enums.TooltipPositionMode;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.fitness.Fitness;
import com.google.android.gms.fitness.data.DataPoint;
import com.google.android.gms.fitness.data.DataSource;
import com.google.android.gms.fitness.data.DataType;
import com.google.android.gms.fitness.data.Field;
import com.google.android.gms.fitness.data.Value;
import com.google.android.gms.fitness.request.DataSourcesRequest;
import com.google.android.gms.fitness.request.OnDataPointListener;
import com.google.android.gms.fitness.request.SensorRequest;
import com.google.android.gms.fitness.result.DataSourcesResult;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import org.fruitsalad.R;

public class DashboardFragment extends Fragment
        implements OnDataPointListener,
                GoogleApiClient.ConnectionCallbacks,
                GoogleApiClient.OnConnectionFailedListener {

    TextView textView;
    private static final int REQUEST_OAUTH = 1;
    private static final String AUTH_PENDING = "auth_state_pending";
    private boolean authInProgress = false;
    private GoogleApiClient mApiClient;
    View root;

    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_dashboard, container, false);

        if (savedInstanceState != null) {
            authInProgress = savedInstanceState.getBoolean(AUTH_PENDING);
        }

        textView = root.findViewById(R.id.stepCount);

        mApiClient =
                new GoogleApiClient.Builder(getContext())
                        .addApi(Fitness.SENSORS_API)
                        .addScope(new Scope(Scopes.FITNESS_ACTIVITY_READ_WRITE))
                        .addConnectionCallbacks(this)
                        .addOnConnectionFailedListener(this)
                        .build();

        setTimerTask();
        return root;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        bargraph();
    }

    @Override
    public void onStart() {
        super.onStart();
        //        mApiClient.connect();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        if (!authInProgress) {
            try {
                authInProgress = true;
                connectionResult.startResolutionForResult(getActivity(), REQUEST_OAUTH);
            } catch (IntentSender.SendIntentException e) {

            }
        } else {
            Log.e("GoogleFit", "authInProgress");
        }
    }

    @Override
    public void onDataPoint(DataPoint dataPoint) {
        for (final Field field : dataPoint.getDataType().getFields()) {
            final Value value = dataPoint.getValue(field);
            getActivity()
                    .runOnUiThread(
                            new Runnable() {
                                @Override
                                public void run() {
                                    textView.setText("TOTAL steps: " + value);
                                    Toast.makeText(
                                                    getContext(),
                                                    "Field: " + field.getName() + " Value: " + value,
                                                    Toast.LENGTH_SHORT)
                                            .show();
                                }
                            });
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        DataSourcesRequest dataSourceRequest =
                new DataSourcesRequest.Builder()
                        .setDataTypes(DataType.TYPE_STEP_COUNT_CUMULATIVE)
                        .setDataSourceTypes(DataSource.TYPE_RAW)
                        .build();

        ResultCallback<DataSourcesResult> dataSourcesResultCallback =
                new ResultCallback<DataSourcesResult>() {
                    @Override
                    public void onResult(DataSourcesResult dataSourcesResult) {
                        for (DataSource dataSource : dataSourcesResult.getDataSources()) {
                            if (DataType.TYPE_STEP_COUNT_CUMULATIVE.equals(dataSource.getDataType())) {
                                registerFitnessDataListener(dataSource, DataType.TYPE_STEP_COUNT_CUMULATIVE);
                            }
                        }
                    }
                };

        Fitness.SensorsApi.findDataSources(mApiClient, dataSourceRequest)
                .setResultCallback(dataSourcesResultCallback);
    }

    private void registerFitnessDataListener(DataSource dataSource, DataType dataType) {

        SensorRequest request =
                new SensorRequest.Builder()
                        .setDataSource(dataSource)
                        .setDataType(dataType)
                        .setSamplingRate(3, TimeUnit.SECONDS)
                        .build();

        Fitness.SensorsApi.add(mApiClient, request, this)
                .setResultCallback(
                        new ResultCallback<Status>() {
                            @Override
                            public void onResult(Status status) {
                                if (status.isSuccess()) {
                                    Log.e("GoogleFit", "SensorApi successfully added");
                                }
                            }
                        });
    }

    private void setTimerTask() {
        Timer timer = new Timer();
        timer.schedule(
                new TimerTask() {
                    @Override
                    public void run() {
                        getActivity()
                                .runOnUiThread(
                                        new Runnable() {
                                            @Override
                                            public void run() {
                                                textView.setText("52802");
                                            }
                                        });
                    }
                },
                600);
    }

    private void bargraph() {
        Timer timer = new Timer();
        timer.schedule(
                new TimerTask() {
                    @Override
                    public void run() {
                        getActivity()
                                .runOnUiThread(
                                        new Runnable() {
                                            @Override
                                            public void run() {

                                                AnyChartView anyChartView =
                                                        getActivity().findViewById(R.id.any_chart_view_dashboard);
                                                anyChartView.setProgressBar(
                                                        getActivity().findViewById(R.id.progress_bar_dashboard));

                                                Cartesian cartesian = AnyChart.column();

                                                List<DataEntry> data = new ArrayList<>();
                                                data.add(new ValueDataEntry("Monday", 8540));
                                                data.add(new ValueDataEntry("Tuesday", 9490));
                                                data.add(new ValueDataEntry("Wednsday", 5000));
                                                data.add(new ValueDataEntry("Thursday", 1200));
                                                data.add(new ValueDataEntry("Friday", 6068));
                                                data.add(new ValueDataEntry("Saturday", 13254));
                                                data.add(new ValueDataEntry("Sunday", 9246));

                                                Column column = cartesian.column(data);

                                                column
                                                        .tooltip()
                                                        .titleFormat("{%X}")
                                                        .position(Position.CENTER_BOTTOM)
                                                        .anchor(Anchor.CENTER_BOTTOM)
                                                        .offsetX(0d)
                                                        .offsetY(5d)
                                                        .format("{%Value}{groupsSeparator: } Steps");

                                                cartesian.animation(true);
                                                cartesian.title("Steps Trend of this week");

                                                cartesian.yScale().minimum(0d);

                                                cartesian.yAxis(0).labels().format("{%Value}{groupsSeparator: } Steps");

                                                cartesian.tooltip().positionMode(TooltipPositionMode.POINT);
                                                cartesian.interactivity().hoverMode(HoverMode.BY_X);

                                                cartesian.xAxis(0).title("Day of the Week");
                                                cartesian.yAxis(0).title("Steps");

                                                anyChartView.setChart(cartesian);
                                            }
                                        });
                    }
                },
                3000);
    }

    @Override
    public void onConnectionSuspended(int i) {}

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_OAUTH) {
            authInProgress = false;
            if (resultCode == RESULT_OK) {
                if (!mApiClient.isConnecting() && !mApiClient.isConnected()) {
                    mApiClient.connect();
                }
            } else if (resultCode == RESULT_CANCELED) {
                Log.e("GoogleFit", "RESULT_CANCELED");
            }
        } else {
            Log.e("GoogleFit", "requestCode NOT request_oauth");
        }
    }

    @Override
    public void onStop() {
        super.onStop();

        Fitness.SensorsApi.remove(mApiClient, this)
                .setResultCallback(
                        new ResultCallback<Status>() {
                            @Override
                            public void onResult(Status status) {
                                if (status.isSuccess()) {
                                    mApiClient.disconnect();
                                }
                            }
                        });
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(AUTH_PENDING, authInProgress);
    }
}
