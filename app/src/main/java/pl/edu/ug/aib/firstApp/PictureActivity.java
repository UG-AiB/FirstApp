package pl.edu.ug.aib.firstApp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.util.Base64;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.InstanceState;
import org.androidannotations.annotations.NonConfigurationInstance;
import org.androidannotations.annotations.OnActivityResult;
import org.androidannotations.annotations.ViewById;

import java.io.ByteArrayOutputStream;
import java.io.File;

import pl.edu.ug.aib.firstApp.data.User;

@EActivity(R.layout.activity_picture)
public class PictureActivity extends ActionBarActivity {

    public static final int INTENT_SHOOT_WITH_CAMERA = 1;
    public static final int INTENT_SELECT_FILE = 2;
    public static final int INTENT_LOGIN = 3;

    @Extra
    @InstanceState
    User user;

    @Bean
    @NonConfigurationInstance
    RestPictureBackgroundTask restBackgroundTask;

    @ViewById
    ImageView picture;

    @OnActivityResult(INTENT_LOGIN)
    void onLogin(int result, @OnActivityResult.Extra User user) {
        if (result == RESULT_OK) {
            this.user = user;
            Log.d(getClass().getSimpleName(), "User received, session id=" + user.sessionId);
            openPictureSourceSelectionDialog();
        }
    }

    @Click
    void addPictureClicked()    {
        if (user == null) {
            Log.d(getClass().getSimpleName(), "User = null, fetching...");
            LoginActivity_.intent(this).startForResult(INTENT_LOGIN);
        } else {
            openPictureSourceSelectionDialog();
        }
    }

    private void openPictureSourceSelectionDialog() {
        final CharSequence[] items = { "Zrób zdjęcie", "Wybierz z galerii",
                "Anuluj" };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Dodaj zdjęcie");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (item == 0) {
                    startCameraIntent();
                } else if (item == 1) {
                    startSelectFromGalleryIntent();
                } else {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    private void startSelectFromGalleryIntent() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent, "Select File"), INTENT_SELECT_FILE);
    }

    private void startCameraIntent() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File file = new File(Environment.getExternalStorageDirectory()+File.separator + "image.jpg");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
        startActivityForResult(intent, INTENT_SHOOT_WITH_CAMERA);
    }

    @OnActivityResult(INTENT_SHOOT_WITH_CAMERA)
    void onPhotoFromCameraTaken(int resultCode) {
        if (resultCode == RESULT_OK) {
            File rawCameraImageFile = new File(Environment.getExternalStorageDirectory() + File.separator + "image.jpg");
            addCompressedImageFromPath(rawCameraImageFile.getAbsolutePath());
            rawCameraImageFile.delete();
        }
    }

    @OnActivityResult(INTENT_SELECT_FILE)
    void onPhotoFromGallerySelected(int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            Uri selectedImageUri = data.getData();
            String selectedImageFilePath = getPath(selectedImageUri, PictureActivity.this);
            addCompressedImageFromPath(selectedImageFilePath);
        }
    }

    private void addCompressedImageFromPath(String selectedImageFilePath) {
        Bitmap bitmap = decodeSampledBitmapFromFile(selectedImageFilePath, 400, 400);
        picture.setImageBitmap(bitmap);
        String pictureBytes = compressAndEncodeToBase64(bitmap);
        restBackgroundTask.addPicture(pictureBytes, user);
    }

    public Bitmap decodeSampledBitmapFromFile(String path, int reqWidth, int reqHeight)
    { // BEST QUALITY MATCH

        //First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);

        // Calculate inSampleSize, Raw height and width of image
        final int sourceWidth = options.outWidth;
        final int sourceHeight = options.outHeight;
        options.inSampleSize = calculateInSampleSize(sourceWidth, sourceHeight, reqWidth, reqHeight);
        options.inPreferredConfig = Bitmap.Config.RGB_565;

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;

        Bitmap bitmap = BitmapFactory.decodeFile(path, options);
        return getAccuratelyResizedBitmap(bitmap, reqWidth, reqHeight);

    }

    public static int calculateInSampleSize(int sourceWidth, int sourceHeight, int reqWidth, int reqHeight) {
        int inSampleSize = 1;

        if (sourceHeight > reqHeight || sourceWidth > reqWidth) {

            final int halfHeight = sourceHeight / 2;
            final int halfWidth = sourceWidth / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

    public Bitmap getAccuratelyResizedBitmap(Bitmap bm, int newWidth, int newHeight) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        if (scaleWidth < scaleHeight) {
            newWidth = Math.round(width*scaleWidth);
            newHeight = Math.round(height*scaleWidth);
        } else {
            newWidth = Math.round(width*scaleHeight);
            newHeight = Math.round(height*scaleHeight);
        }
        return Bitmap.createScaledBitmap(bm, newWidth, newHeight, true);
    }

    public String getPath(Uri uri, Activity activity) {
        String[] projection = { MediaStore.MediaColumns.DATA };
        Cursor cursor = activity.managedQuery(uri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    public String compressAndEncodeToBase64(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, byteArrayOutputStream);
        byte[] b = byteArrayOutputStream.toByteArray();
        String imageEncoded = Base64.encodeToString(b, Base64.NO_WRAP | Base64.NO_PADDING);
        Log.d(getClass().getSimpleName(), imageEncoded);
        Toast.makeText(this, "" + imageEncoded.length(), Toast.LENGTH_SHORT).show();
        return imageEncoded;
    }

    public void pictureAdded(int id) {
        Toast.makeText(this, "Picture added with id=" + id + ".", Toast.LENGTH_SHORT).show();
    }

    public void addPictureFailed(Exception e) {
        Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        e.printStackTrace();
    }

}
