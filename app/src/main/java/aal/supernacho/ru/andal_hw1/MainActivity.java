package aal.supernacho.ru.andal_hw1;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    List<String> list;
    ArrayAdapter<String> adapter;
    ListView listView;
    Button button;

    public static void mathOps (Context c){
        double a = Math.random() * 100;
        double b = Math.random() * 100;
        double selector = Math.random() * 2;
        if (selector > 1.5){
            toast(c, a + b);
        } else if (selector > 1 && selector < 1.5) {
            toast(c,a * b);
        } else if (selector > 0.5 && selector < 1) {
            toast(c,a - b);
        } else {
            toast(c,a / b);
        }
    }

    private static void toast(Context c, double d){
        Toast.makeText(c, "Random math Result: " + d, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        list = new ArrayList<>();
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list);
        for (int i = 0; i < 5; i++) {
            list.add("Element " + i);
        }
        listView = findViewById(R.id.list_view_main);
        listView.setAdapter(adapter);

        registerForContextMenu(listView);
        button = findViewById(R.id.button_nex_activity);
        button.setOnClickListener(onClickListener);

    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.button_nex_activity:
                    Intent intent = new Intent(MainActivity.this, MultyTouchList.class);
                    startActivity(intent);
                    break;
                default:
                    Toast.makeText(MainActivity.this, "Unknown view: " + view, Toast.LENGTH_SHORT).show();
            }
        }
    };

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.context_menu, menu);
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case R.id.menu_edit:
                itemEdit(info.position);
                mathOps(MainActivity.this);
                return true;
            case R.id.menu_delete:
                itemDelete(info.position);
                mathOps(MainActivity.this);
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    private void itemEdit(int position){
        list.set(position, "Edited");
        adapter.notifyDataSetChanged();
    }

    private void itemDelete(int position){
        list.remove(position);
        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_add:
                addElement();
                mathOps(MainActivity.this);
                return true;
            case R.id.menu_clear:
                clearList();
                mathOps(MainActivity.this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void addElement() {
        list.add("Element " + list.size());
        adapter.notifyDataSetChanged();
    }

    private void clearList() {
        list.clear();
        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_1, menu);
        return true;
    }
}
