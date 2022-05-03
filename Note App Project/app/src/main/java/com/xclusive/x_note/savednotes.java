package com.xclusive.x_note;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.database.Cursor;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.xclusive.x_note.Adapter.Adapter_notes;
import com.xclusive.x_note.SQlite.DataBase;
import com.xclusive.x_note.model.Model_notes;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

import android.content.Intent;
import android.widget.SearchView;
import android.widget.Toast;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

import static com.xclusive.x_note.Adapter.Adapter_notes.model_notesList;
import static com.xclusive.x_note.MainActivity.color;
import static com.xclusive.x_note.MainActivity.db;
import static com.xclusive.x_note.MainActivity.update;

public class savednotes extends AppCompatActivity implements SearchView.OnQueryTextListener {
    public static Adapter_notes Adapter_notes;
    private ArrayList<Model_notes> model_notes = new ArrayList<>();

    private RecyclerView recyclerView1;

    public static Cursor cursor;
    public static DataBase DB;
    public Model_notes Deletedfile = null;
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_savednotes);

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        DB = new DataBase(this);
        recyclerView1 = findViewById(R.id.recyclerview1);
        searchView = findViewById(R.id.searchView);
        recyclerView1.setHasFixedSize(true);

        //todo layout for different size;
        StaggeredGridLayoutManager linearLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView1.setLayoutManager(linearLayoutManager);

        cursor = DB.getAlldata();

        if (cursor.getCount() != 0) {
            while (cursor.moveToNext()) {
                model_notes.add(new Model_notes(cursor.getString(0), cursor.getString(5), cursor.getString(6), cursor.getString(1)
                        , cursor.getString(4), cursor.getString(2)));
            }

        } else {
            Toast.makeText(this, "0", Toast.LENGTH_SHORT).show();
        }

        Collections.reverse(model_notes);
        Adapter_notes = new Adapter_notes(model_notes);
        recyclerView1.setAdapter(Adapter_notes);
        Adapter_notes.notifyDataSetChanged();

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView1);

        searchView.setOnQueryTextListener(this);


    }

    public void addnotesIntent(View view) {


        update = 0;
        color = "#FCF3F3F3";
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        finish();

    }

    public void back(View view) {
        onBackPressed();
    }

    //
    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {

            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

            int pos = viewHolder.getAdapterPosition();
            Deletedfile = null;
            switch (direction) {
                case ItemTouchHelper.LEFT:
                    Deletedfile = model_notes.get(pos);
                    model_notes.remove(pos);
                    Adapter_notes.notifyItemRemoved(pos);
                    Snackbar.make(recyclerView1, "Note: " + Deletedfile.getTitle().toString(), Snackbar.LENGTH_LONG)
                            .setAction("Undo", new View.OnClickListener() {

                                /**
                                 * Called when a view has been clicked.
                                 *
                                 * @param v The view that was clicked.
                                 */
                                @Override
                                public void onClick(View v) {
                                    Model_notes deleteedtemp = Deletedfile;

                                    model_notesList.add(pos, Deletedfile);
                                    Adapter_notes.notifyItemInserted(pos);
                                    DB.insertdata(deleteedtemp.getId(), deleteedtemp.getTitle(), deleteedtemp.getSubtitle(), "",
                                            deleteedtemp.getDates(), deleteedtemp.getNotes()
                                            , deleteedtemp.getColor(), 0);
                                }
                            }).show();
                    DB.deletedata(Deletedfile.getId());

                    break;
                case ItemTouchHelper.RIGHT:

                    Deletedfile = model_notes.get(pos);

                    StringBuilder sharedata = new StringBuilder();
                    sharedata.append("TITLE: " + Deletedfile.getTitle() + "\n" +
                            "SUBTITLE: " + Deletedfile.getSubtitle() + "\n" +
                            "DATE: " + Deletedfile.getDates() + "\n" +
                            "NOTES: " + Deletedfile.getNotes()
                    );

                    try {
                        FileOutputStream fileOutputStream = savednotes.this.openFileOutput(Deletedfile.getTitle() + ".txt", MODE_PRIVATE);
                        fileOutputStream.write(sharedata.toString().getBytes());
                        fileOutputStream.close();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                    //Toast.makeText(getApplicationContext(), sharedata,Toast.LENGTH_LONG).show();
                    File file = new File(savednotes.this.getFilesDir(), Deletedfile.getTitle() + ".txt");
                    Uri path = FileProvider.getUriForFile(savednotes.this, "com.xclusive.x_note.fileprovider", file);
                    Intent intent = new Intent(Intent.ACTION_SEND);
                    intent.setType("text/txt");
                    intent.putExtra(Intent.EXTRA_SUBJECT, file);
                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    intent.putExtra(Intent.EXTRA_STREAM, path);
                    startActivity(intent);
                    Adapter_notes.notifyDataSetChanged();

                    break;
            }
        }

        @Override
        public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {

            new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                    .addSwipeLeftBackgroundColor(ContextCompat.getColor(savednotes.this, R.color.transparent))
                    .addSwipeLeftActionIcon(R.drawable.delete)
                    .addSwipeRightBackgroundColor(ContextCompat.getColor(savednotes.this, R.color.transparent))
                    .addSwipeRightActionIcon(R.drawable.share)
                    .create()
                    .decorate();
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }
    };

    /**
     * Called when the user submits the query. This could be due to a key press on the
     * keyboard or due to pressing a submit button.
     * The listener can override the standard behavior by returning true
     * to indicate that it has handled the submit request. Otherwise return false to
     * let the SearchView handle the submission by launching any associated intent.
     *
     * @param query the query text that is to be submitted
     * @return true if the query has been handled by the listener, false to let the
     * SearchView perform the default action.
     */
    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    /**
     * Called when the query text is changed by the user.
     *
     * @param newText the new content of the query text field.
     * @return false if the SearchView should perform the default action of showing any
     * suggestions if available, true if the action was handled by the listener.
     */
    @Override
    public boolean onQueryTextChange(String newText) {
        String input = newText.toLowerCase();
        ArrayList<Model_notes> searchlist = new ArrayList<>();

        for (Model_notes notes : model_notes) {
            if (notes.getTitle().toLowerCase().contains(input)) {
                searchlist.add(notes);
                //Toast.makeText(this,notes.getTitle(),Toast.LENGTH_LONG).show();
            }
        }
        Adapter_notes.updatelist(searchlist);
        Adapter_notes.notifyDataSetChanged();
        return true;
    }

    public void aboutsection(View view) {
        startActivity(new Intent(getApplicationContext(), About_us.class));


    }
}