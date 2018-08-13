package com.example.johnn.petstoredatabase;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;

import com.example.johnn.petstoredatabase.data.PetContract.PetEntry;
import com.example.johnn.petstoredatabase.data.PetDbHelper;

public class CatalogActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    public static final String LOG_TAG = CatalogActivity.class.getName();

    PetCursorAdapter mCursorAdapter;

    private PetDbHelper mDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalog);

        // Setup FAB to open EditorActivity
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CatalogActivity.this, EditorActivity.class);
                startActivity(intent);
            }
        });

        // Find and set empty view on the ListView, so that it only shows when the list has 0 items
        ListView petListView = findViewById(R.id.list);
        View emptyView = findViewById(R.id.empty_view);
        petListView.setEmptyView(emptyView);

        mCursorAdapter = new PetCursorAdapter(this, null);
        petListView.setAdapter(mCursorAdapter);

        getLoaderManager().initLoader(0, null, this);
    }

    // Insert hardcoded pet data into the database for debugging purposes only
    private void insertPet() {
        // Put database in write mode *database should not be accessed directly*
        //SQLiteDatabase db = mDbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(PetEntry.COLUMN_PET_NAME, "Toto");
        values.put(PetEntry.COLUMN_PET_BREED, "Terrier");
        values.put(PetEntry.COLUMN_PET_GENDER, PetEntry.GENDER_MALE);
        values.put(PetEntry.COLUMN_PET_WEIGHT, 7);

        // Returns -1 if error *access database directly*
        //long newRowId = db.insert(PetEntry.TABLE_NAME, null, values);

        Uri newUri = getContentResolver().insert(PetEntry.CONTENT_URI, values);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_catalog.xml file and adds menu items to the app bar
        getMenuInflater().inflate(R.menu.menu_catalog, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "Insert dummy data" menu option
            case R.id.action_insert_dummy_data:
                insertPet();
                return true;
            // Respond to a click on the "Delete all entries" menu option
            case R.id.action_delete_all_entries:
                // Do nothing for now
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, @Nullable Bundle bundle) {
        String[] projection = {PetEntry._ID, PetEntry.COLUMN_PET_NAME, PetEntry.COLUMN_PET_BREED};
        return new CursorLoader(this, PetEntry.CONTENT_URI, projection, null, null, null);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor cursor) {
        // Update adapter with a new cursor
        mCursorAdapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        // Callback used to delete cursor
        mCursorAdapter.swapCursor(null);
    }
}
