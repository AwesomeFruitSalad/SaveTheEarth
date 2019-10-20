package org.fruitsalad.utility;

import com.anychart.chart.common.dataentry.DataEntry;

import java.util.ArrayList;
import java.util.List;

public class MockData {

    public static List<DataEntry> getMockHeatMapData() {
        List<DataEntry> data = new ArrayList<>();
        data.add(new CustomHeatDataEntry("Jan", "Monday", 0, "#43a047"));
        data.add(new CustomHeatDataEntry("Jan", "Tuesday", 0, "#43a047"));
        data.add(new CustomHeatDataEntry("Jan", "Wednsday", 0, "#43a047"));
        data.add(new CustomHeatDataEntry("Jan", "Monday", 0, "#43a047"));
        data.add(new CustomHeatDataEntry("Jan", "Thursday", 0, "#43a047"));
        data.add(new CustomHeatDataEntry("Feb", "Tuesday", 0, "#43a047"));
        data.add(new CustomHeatDataEntry("Feb", "Wednsday", 0, "#90caf9"));
        data.add(new CustomHeatDataEntry("Feb", "Thursday", 0, "#43a047"));
        data.add(new CustomHeatDataEntry("Feb", "Wednsday", 1, "#43a047"));
        data.add(new CustomHeatDataEntry("Feb", "Wednsday", 1, "#43a047"));
        data.add(new CustomHeatDataEntry("Feb", "Tuesday", 0, "#43a047"));
        data.add(new CustomHeatDataEntry("March", "Tuesday", 0, "#43a047"));
        data.add(new CustomHeatDataEntry("March", "Tuesday", 1, "#43a047"));
        data.add(new CustomHeatDataEntry("March", "Monday", 1, "#43a047"));
        data.add(new CustomHeatDataEntry("March", "Friday", 1, "#43a047"));
        data.add(new CustomHeatDataEntry("April", "Thursday", 0, "#90caf9"));
        data.add(new CustomHeatDataEntry("April", "Thursday", 1, "#43a047"));
        data.add(new CustomHeatDataEntry("April", "Monday", 1, "#43a047"));
        data.add(new CustomHeatDataEntry("May", "Tuesday", 2, "#43a047"));
        data.add(new CustomHeatDataEntry("May", "Tuesday", 2, "#43a047"));
        data.add(new CustomHeatDataEntry("May", "Friday", 0, "#90caf9"));
        data.add(new CustomHeatDataEntry("May", "Friday", 1, "#43a047"));
        data.add(new CustomHeatDataEntry("June", "Friday", 1, "#43a047"));
        data.add(new CustomHeatDataEntry("June", "Major", 2, "#43a047"));
        data.add(new CustomHeatDataEntry("June", "Tuesday", 3, "#43a047"));
        data.add(new CustomHeatDataEntry("June", "Extreme", 2, "#43a047"));
        data.add(new CustomHeatDataEntry("July", "Wednsday", 3, "#43a047"));
        data.add(new CustomHeatDataEntry("July", "Wednsday", 3, "#43a047"));
        data.add(new CustomHeatDataEntry("July", "Saturday", 3, "#43a047"));
        data.add(new CustomHeatDataEntry("August", "Thursday", 3, "#43a047"));
        data.add(new CustomHeatDataEntry("August", "Wednsday", 3, "#43a047"));
        data.add(new CustomHeatDataEntry("August", "Saturday", 3, "#43a047"));
        data.add(new CustomHeatDataEntry("August", "Friday", 3, "#43a047"));
        data.add(new CustomHeatDataEntry("Sep", "Thursday", 3, "#43a047"));
        data.add(new CustomHeatDataEntry("Sep", "Wednsday", 3, "#43a047"));
        data.add(new CustomHeatDataEntry("Sep", "Friday", 3, "#43a047"));
        data.add(new CustomHeatDataEntry("Sep", "Thursday", 3, "#43a047"));
        data.add(new CustomHeatDataEntry("Sep", "Wednsday", 3, "#43a047"));
        data.add(new CustomHeatDataEntry("Oct", "Wednsday", 3, "#43a047"));
        data.add(new CustomHeatDataEntry("Oct", "Friday", 1, "#43a047"));
        data.add(new CustomHeatDataEntry("Oct", "Friday", 0, "#43a047"));
        data.add(new CustomHeatDataEntry("Oct", "Sunday", 2, "#43a047"));
        data.add(new CustomHeatDataEntry("Oct", "Saturday", 3, "#43a047"));

        return data;
    }

}

