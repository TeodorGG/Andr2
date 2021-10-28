package com.example.lab2_;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Xml;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Toast;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import java.security.spec.ECField;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;


public class MainActivity extends AppCompatActivity {

    private AutoCompleteTextView et_search;
    private Button b_search, b_add;
    private CalendarView calendar;
    private RecyclerView recyclerview;
    ArrayList<Model> list;
    Adapter adapter;
    String d;


    public void getDatFromXml(){
        File xmlFile = new File(getFilesDir().getPath() + "/list.xml");

        if(xmlFile.exists()){
            try {
                InputStream is = new FileInputStream(xmlFile);

                DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                Document doc = dBuilder.parse(is);

                Element element=doc.getDocumentElement();
                element.normalize();

                NodeList nList = doc.getElementsByTagName("Element");

                for (int i=0; i<nList.getLength(); i++) {

                    Node node = nList.item(i);
                    if (node.getNodeType() == Node.ELEMENT_NODE) {
                        Element element2 = (Element) node;
                        list.add(new Model(
                                getValue("DateElement", element2),
                                getValue("TimeElement", element2),
                                getValue("TitleElement", element2),
                                getValue("DeskElement", element2)));
                    }
                }

            } catch (Exception e) {e.printStackTrace();}
        }
    }

    private static String getValue(String tag, Element element) {
        NodeList nodeList = element.getElementsByTagName(tag).item(0).getChildNodes();
        Node node = nodeList.item(0);
        return node.getNodeValue();
    }

    @Override
    protected void onDestroy() {
        saveInXml();
        super.onDestroy();
    }

    private void saveInXml(){
        File xmlFile = new File(getFilesDir().getPath() + "/list.xml");
        try {
            xmlFile.createNewFile();
        } catch (IOException e) {
            Log.e("IOException", "Exception in create new File(");
        }

        FileOutputStream fileos = null;
        try{
            fileos = new FileOutputStream(xmlFile);

        } catch(FileNotFoundException e) {
            Log.e("FileNotFoundException",e.toString());
        }

        XmlSerializer serializer = Xml.newSerializer();

        try {
            serializer.setOutput(fileos, "UTF-8");
            serializer.startDocument(null, Boolean.valueOf(true));
            serializer.setFeature("http://xmlpull.org/v1/doc/features.html#indent-output", true);
            serializer.startTag(null,"root");

            for(Model a : list){
                serializer.startTag(null, "Element");
                serializer.startTag(null, "TitleElement");
                serializer.text(a.getTitle_task());
                serializer.endTag(null,"TitleElement");
                serializer.startTag(null, "DeskElement");
                serializer.text(a.getDescription());
                serializer.endTag(null,"DeskElement");
                serializer.startTag(null, "DateElement");
                serializer.text(a.getDate());
                serializer.endTag(null,"DateElement");
                serializer.startTag(null, "TimeElement");
                serializer.text(a.getTime());
                serializer.endTag(null,"TimeElement");
                serializer.endTag(null,"Element");
            }

            serializer.endTag(null,"root");
            serializer.endDocument();
            serializer.flush();
            fileos.close();

            //TextView tv = (TextView)findViewById(R.);

        } catch(Exception e) {
            Log.e("Exception","Exception occured in wroting");
        }
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        list = new ArrayList<>();
        getDatFromXml();

        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
        d = String.valueOf(format.format(new Date()));
        Log.d("asdasdsad", new Date()+"");
        getWidjet();
        listenerMethod();
        setRecyclerView();
    }

    private void setRecyclerView() {
        recyclerview.setLayoutManager(new LinearLayoutManager(this));
        adapter = new Adapter(list, this, this, d);
        recyclerview.setAdapter(adapter);
    }

    private void listenerMethod() {
        b_search.setOnClickListener(v->{
            // TO DO add search
            String ssa = et_search.getText().toString().trim();
            for(Model a : list){
                if(a.getTitle_task().equals(ssa)){
                    Intent add_intent = new Intent(MainActivity.this, UpdateActivity.class);
                    add_intent.putExtra("task", a);
                    add_intent.putExtra("position", list.indexOf(a));

                    startActivityForResult(add_intent, 25);
                    return;
                }
            }
            Toast.makeText(MainActivity.this, "Task not exist", Toast.LENGTH_SHORT).show();
        });

        et_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ArrayList<String> ss = new ArrayList<>();

                for(Model a : list){
                    if(a.getTitle_task().contains(s.toString())){
                        ss.add(a.getTitle_task());
                    }
                }

                ArrayAdapter arrayAdapter=new ArrayAdapter(MainActivity.this, android.R.layout.simple_dropdown_item_1line, ss);
                et_search.setAdapter(arrayAdapter);
                et_search.setThreshold(1);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });



        b_add.setOnClickListener(v->{
            //TO DO funcion add

            Log.d("test_Data", d);
            Intent add_intent = new Intent(MainActivity.this, AddActivity.class);
            add_intent.putExtra("data_mili", d);
            startActivityForResult(add_intent, 24);
        });

        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                d = dayOfMonth+"."+month+"."+year;
                adapter.setData(d);
                adapter.notifyDataSetChanged();
                Log.d("lisad", list.toString());
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        saveInXml();

        if(requestCode == 24 && data != null){
            Log.d("data_from_ic", data.getSerializableExtra("new_task").toString());
            Model new_task = (Model) data.getSerializableExtra("new_task");
            list.add(new_task);
            adapter.notifyDataSetChanged();

            // TO DO add notification

        } else if(requestCode == 25  && data != null){
            Model updated_task = (Model) data.getSerializableExtra("updated_task");
            int pos = data.getIntExtra("position",0);
            list.set(pos, updated_task);
            adapter.notifyDataSetChanged();


            // TO DO add notification
        }
    }

    private void getWidjet() {
        et_search = findViewById(R.id.et_search);

        b_search = findViewById(R.id.b_search);
        b_add = findViewById(R.id.b_add);

        calendar = findViewById(R.id.calendar);

        recyclerview = findViewById(R.id.recyclerview);

    }

    public void update(Model s) {
        Intent add_intent = new Intent(MainActivity.this, UpdateActivity.class);
        add_intent.putExtra("task", s);
        add_intent.putExtra("position", list.indexOf(s));

        startActivityForResult(add_intent, 25);
    }
}