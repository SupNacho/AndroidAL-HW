package aal.supernacho.ru.andal_hw1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.PopupMenu;

import java.util.ArrayList;
import java.util.List;

public class MultyTouchList extends AppCompatActivity {
    List<String> list2;
    ArrayAdapter<String> adapter2;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multy_touch_list);
        list2 = new ArrayList<>();
        adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list2);
        for (int i = 0; i < 5; i++) {
            list2.add("Element " + i);
        }
        listView = findViewById(R.id.list_view_multy_touch);
        listView.setAdapter(adapter2);

//        registerForContextMenu(listView);
        listView.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {
            @Override
            public void onItemCheckedStateChanged(ActionMode actionMode, int i, long l, boolean b) {

            }

            @Override
            public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
                MenuInflater inflater = actionMode.getMenuInflater();
                inflater.inflate(R.menu.context_menu, menu);
                return true;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
                return false;
            }

            @Override
            public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
                actionMode.finish();
                return false;
            }

            @Override
            public void onDestroyActionMode(ActionMode actionMode) {

            }
        });

        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);

    }

    public void showPopUp(View v){
        PopupMenu popupMenu = new PopupMenu(this, v);
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.menu_add:
                        addElement();
                        MainActivity.mathOps(MultyTouchList.this);
                        return true;
                    case R.id.menu_clear:
                        clearList();
                        MainActivity.mathOps(MultyTouchList.this);
                        return true;
                    default:
                        return false;
                }
            }
        });
        popupMenu.inflate(R.menu.menu_1);
        popupMenu.show();
    }


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
                MainActivity.mathOps(MultyTouchList.this);
                return true;
            case R.id.menu_delete:
                itemDelete(info.position);
                MainActivity.mathOps(MultyTouchList.this);
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    private void itemEdit(int position){
        list2.set(position, "Edited");
        adapter2.notifyDataSetChanged();
    }

    private void itemDelete(int position){
        list2.remove(position);
        adapter2.notifyDataSetChanged();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_add:
                addElement();
                MainActivity.mathOps(MultyTouchList.this);
                return true;
            case R.id.menu_clear:
                clearList();
                MainActivity.mathOps(MultyTouchList.this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void addElement() {
        list2.add("Element " + list2.size());
        adapter2.notifyDataSetChanged();
    }

    private void clearList() {
        list2.clear();
        adapter2.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_1, menu);
        return true;
    }
}
