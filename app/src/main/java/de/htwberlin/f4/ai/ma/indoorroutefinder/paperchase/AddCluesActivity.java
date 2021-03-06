package de.htwberlin.f4.ai.ma.indoorroutefinder.paperchase;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import de.htwberlin.f4.ai.ma.indoorroutefinder.R;
import de.htwberlin.f4.ai.ma.indoorroutefinder.location.locator.LocationSource;
import de.htwberlin.f4.ai.ma.indoorroutefinder.location.locator.Locator;
import de.htwberlin.f4.ai.ma.indoorroutefinder.location.locator.LocatorFactory;
import de.htwberlin.f4.ai.ma.indoorroutefinder.location.locator.listeners.LocationChangeListener;
import de.htwberlin.f4.ai.ma.indoorroutefinder.node.Node;
import de.htwberlin.f4.ai.ma.indoorroutefinder.persistence.DatabaseHandler;
import de.htwberlin.f4.ai.ma.indoorroutefinder.persistence.DatabaseHandlerFactory;

public class AddCluesActivity extends AppCompatActivity implements LocationChangeListener{

    private ListView listView;
    ImageView checkImage;
    TextInputEditText search;
    ArrayList<Node> nodeList;
    ArrayList<Node> allNodesList;
    ArrayList<Node> checkedNodesList;
    DatabaseHandler databaseHandler;
    ArrayAdapter<Node> arrayAdapter;
    Locator locator;
    Node lastLocation;
    boolean sorted = false;
    private MenuItem menuSort;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_clues);
        setTitle("Orte hinzufügen");
        listView = (ListView) findViewById(R.id.clue_list_add);
        checkImage = (ImageView) findViewById(R.id.add_clues_item_checkimage);
        search = (TextInputEditText) findViewById(R.id.add_clues_search);
        databaseHandler = DatabaseHandlerFactory.getInstance(this);
        locator = LocatorFactory.getInstance(getApplicationContext());


        nodeList = new ArrayList<>();
        allNodesList = new ArrayList<>();
        checkedNodesList = new ArrayList<>();
        nodeList.addAll(databaseHandler.getAllNodes());
        allNodesList.addAll(databaseHandler.getAllNodes());

        arrayAdapter = new ClueAdapter(this, R.layout.add_from_all_clues_list_item, nodeList);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if(!checkedNodesList.contains(nodeList.get(position))) {

                    checkedNodesList.add(nodeList.get(position));

                }
                else{
                    checkedNodesList.remove(nodeList.get(position));
                }
                arrayAdapter.notifyDataSetChanged();
            }
        });
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                nodeList.clear();
                nodeList.addAll(allNodesList);
                ArrayList<Node> nodesToRemove = new ArrayList<>();
                for(Node n :nodeList){
                    if(!n.getId().toLowerCase().startsWith(charSequence.toString().toLowerCase())){
                        nodesToRemove.add(n);
                    }
                }
                nodeList.removeAll(nodesToRemove);
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void afterTextChanged(Editable editable) {            }
        });

        //menuSort = (MenuItem) findViewById(R.id.action_sort);
    }

    @Override
    public void onLocationChanged(Node newLocation, LocationSource source) {
        if(newLocation!=null) {
            lastLocation = newLocation;
            locator.stopLocationUpdates();
            locator.unregisterLocationListener(this);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if(!menuSort.isVisible()) {
                        menuSort.setVisible(true);
                    }
                }
            });

        }
    }

    private class ClueAdapter extends ArrayAdapter<Node>{
        private int layoutResource;
        ArrayList<Node> list;

        public ClueAdapter(@NonNull Context context, int resource, @NonNull List<Node> objects) {
            super(context, resource, objects);
            layoutResource = resource;
            list = (ArrayList<Node>) objects;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            View view = convertView;

            if(view == null){
                LayoutInflater layoutInflater = LayoutInflater.from(getContext());
                view = layoutInflater.inflate(layoutResource, null);
            }
            Node clue = getItem(position);

            if(clue != null){
                TextView clueName = (TextView) view.findViewById(R.id.add_clues_item_name);
                TextView clueDescr = (TextView) view.findViewById(R.id.add_clues_item_description);
                ImageView clueCheckImage = (ImageView) view.findViewById(R.id.add_clues_item_checkimage);
                if(clueName != null){
                    clueName.setText(clue.getId());
                }
                if(clueDescr != null){
                    clueDescr.setText(clue.getDescription());
                }
                if(checkedNodesList.contains(list.get(position))){
                    clueCheckImage.setImageResource(R.drawable.ic_check_box_black_24dp);
                }
                else{
                    clueCheckImage.setImageResource(R.drawable.ic_check_box_outline_blank_black_24dp);
                }
            }
            return view;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_add_clues, menu);
        menuSort = menu.findItem(R.id.action_sort);
        locator.registerLocationListener(this);
        locator.startLocationUpdates();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = new Intent();
        switch (item.getItemId()) {
            case R.id.action_cart:
                intent = new Intent();
                if(checkedNodesList.isEmpty()){
                    setResult(RESULT_CANCELED, intent);
                    Toast.makeText(getApplicationContext(),"Keine Clues hinzugefügt", Toast.LENGTH_SHORT).show();
                }
                else {
                    Bundle clueBundle = new Bundle();
                    clueBundle.putSerializable("clues", checkedNodesList);
                    intent.putExtra("clues", clueBundle);
                    setResult(RESULT_OK, intent);
                }
                finish();
                return true;
            case R.id.action_sort:
                if(lastLocation != null) {
                    if (sorted) {
                        nodeList.clear();
                        nodeList.addAll(databaseHandler.getAllNodes());
                        arrayAdapter.notifyDataSetChanged();
                        sorted = false;
                    } else {
                        List<Node> tempList = locator.sortByDistanceNearestFirst(lastLocation, nodeList);
                        nodeList.clear();
                        nodeList.addAll(tempList);
                        arrayAdapter.notifyDataSetChanged();
                        sorted = true;
                    }
                }
                else{
                    Toast.makeText(this, "no location yet", Toast.LENGTH_SHORT).show();
                }

                return true;
            case android.R.id.home:
                setResult(RESULT_CANCELED, intent);
                finish();
                return true;
            default:
                return true;
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        setResult(RESULT_CANCELED, intent);
        finish();
    }

    @Override
    protected void onPause() {
        super.onPause();
        locator.stopLocationUpdates();
    }

    @Override
    protected void onResume() {
        super.onResume();
        locator.startLocationUpdates();
    }

    @Override
    protected void onStop() {
        super.onStop();
        locator.unregisterLocationListener(this);
        locator.stopLocationUpdates();
    }
}
