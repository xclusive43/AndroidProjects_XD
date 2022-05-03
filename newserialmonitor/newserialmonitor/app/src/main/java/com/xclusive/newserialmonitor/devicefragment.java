package com.xclusive.newserialmonitor;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.ListFragment;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.hoho.android.usbserial.driver.UsbSerialDriver;
import com.hoho.android.usbserial.driver.UsbSerialProber;

import java.util.ArrayList;
import java.util.Locale;
public class devicefragment extends ListFragment {
    class ListItem{
        UsbDevice device;
        int port;
        UsbSerialDriver driver;

        public ListItem(UsbDevice device, int port, UsbSerialDriver driver) {
            this.device = device;
            this.port = port;
            this.driver = driver;
        }
    }

    private ArrayList<ListItem> listItems = new ArrayList<>();
    private ArrayAdapter<ListItem> listAdapter;
    private int BaudRate =9600;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        listAdapter = new ArrayAdapter<ListItem>(getActivity(),0,listItems){

            @Override
            public View getView(int position,  View view,  ViewGroup parent) {
                ListItem item = listItems.get(position);
                if(view ==null)

                    //todo: id the view layout is empty
                      view = getActivity().getLayoutInflater().inflate(R.layout.devicelist_layout, parent, false);
                    TextView   text11 = view.findViewById(R.id.text11);
                    TextView   text2 = view.findViewById(R.id.text2);

                if (item.driver ==null)
                    //todo: no driver found
                    text11.setText("Driver not found...!");


                else if (item.driver.getPorts().size()==1)
                    //todo: get the port number having size of 1 if true then replace the data text from serialDriver
                    //text11.setText(item.driver.getClass().getSimpleName().replace("SerialDriver", ""));
                    text11.setText("Ardunio uno");

                    else
                    //text1.setText("item.driver.getClass().getSimpleName()");
                    text11.setText("Ardunio uno");
                    text2.setText("Baud Rate : 9600\t");
                    text2.setText(String.format(Locale.US, "Vendor %04X, Product %04X", item.device.getVendorId(), item.device.getProductId()));


                    return view;
                }

        };

    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setListAdapter(null);//Todo: to set the listAdapter as Null

          View header = getActivity().getLayoutInflater().inflate(R.layout.headertext, null, false);
          getListView().addHeaderView(header, null, false);
          setEmptyText("Device not Connected....\nplease check your connection properly");
          //Toast.makeText(getContext(),"Device not connected!!",Toast.LENGTH_SHORT).show();
     ((TextView) getListView().getEmptyView()).setTextSize(18);
     ((TextView) getListView().getEmptyView()).setTextColor(getResources().getColor(R.color.black));
      setListAdapter(listAdapter);

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_devices, menu); //for show in right side menu list
    }

    @Override
    public void onResume() {
        super.onResume();
        refresh();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.refresh) {
            refresh();
            return true;
        }
//
//        else if (id ==R.id.baud_rate) {
//            final String[] baudRates = getResources().getStringArray(R.array.baud_rates);
//            int pos = java.util.Arrays.asList(baudRates).indexOf(String.valueOf(baudRate));
//            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//            builder.setTitle("Baud rate");
//            builder.setSingleChoiceItems(baudRates, pos, new DialogInterface.OnClickListener() {
//                public void onClick(DialogInterface dialog, int item) {
//                    baudRate = Integer.valueOf(baudRates[item]);
//                    dialog.dismiss();
//                }
//            });
//            builder.create().show();
//            return true;
         else {
            return super.onOptionsItemSelected(item);
        }

    }

    private void refresh() {
        //Toast.makeText(getContext(),"refreshing..", Toast.LENGTH_SHORT).show();

//        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//        builder.setTitle("Refreshing..please wait");
//        builder.create().show();

        UsbManager usbManager = (UsbManager) getActivity().getSystemService(Context.USB_SERVICE);
        UsbSerialProber usbDefaultProber = UsbSerialProber.getDefaultProber();
        UsbSerialProber usbCustomProber = CustomProber.getCustomProber();
        listItems.clear();
        for(UsbDevice device : usbManager.getDeviceList().values()) {
            UsbSerialDriver driver = usbDefaultProber.probeDevice(device);
            if(driver == null) {
                driver = usbCustomProber.probeDevice(device);
            }
            if(driver != null) {
                for(int port = 0; port < driver.getPorts().size(); port++)
                    listItems.add(new ListItem(device, port, driver));
            } else {
                listItems.add(new ListItem(device, 0, null));
            }
        }
        listAdapter.notifyDataSetChanged();
    }


    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        ListItem item = listItems.get(position-1);
        if(item.driver == null) {
            Toast.makeText(getActivity(), "no driver", Toast.LENGTH_SHORT).show();
        } else {

            //todo: Starting new Fragment and pass the values of deviceid, port,baud rate to next Activity;
            Bundle args = new Bundle();
            args.putInt("device", item.device.getDeviceId());
            args.putInt("port", item.port);
            args.putInt("baud", BaudRate);
            Fragment fragment = new Dashboard1_fragment();
            fragment.setArguments(args);
            getFragmentManager().beginTransaction().replace(R.id.fragment, fragment, "terminal").addToBackStack(null).commit();
        }
    }

}
