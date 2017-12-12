package br.pe.recife.tafeito.gui;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import br.pe.recife.tafeito.R;

public class ScheduleClientActivity extends AppCompatActivity {

    ListView listViewSchedule;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_client);
        listViewSchedule = (ListView) findViewById(R.id.lvSchedule);

        String[] values = new String[]{"Item 1", "Item 2", "Item 3", "Item 4", "Item 5"};

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, values);

        listViewSchedule.setAdapter(adapter);

    }
}