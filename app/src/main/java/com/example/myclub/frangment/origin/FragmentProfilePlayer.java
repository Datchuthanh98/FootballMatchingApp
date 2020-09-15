package com.example.myclub.frangment.origin;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myclub.Interface.OnFirebaseUpdate;
import com.example.myclub.R;
import com.example.myclub.model.Player;
import com.example.myclub.repository.SQLiteHelper;
import com.example.myclub.main.ObserverManager;
import com.example.myclub.repository.RepoFireStorePlayer;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class FragmentProfilePlayer extends Fragment implements View.OnClickListener {
    private EditText txtAge, txtName, txtHeight, txtWeight, txtPhone, txtEmail;
    private TextView id;
    private String urlAvatar, urlFirebase = "";
    private ImageView avatar;
    private Spinner listPosition;
    private Button  update;
    private SQLiteHelper sqLiteHelper;
    public static int RESULT_LOAD_IMG = 10112;
    private Dialog uploadingDialog;
    Uri imageUri;
    private FirebaseStorage storage = FirebaseStorage.getInstance();
    private RepoFireStorePlayer repoFireStorePlayer = RepoFireStorePlayer.getInstance();
    private SharedPreferences sharedPref;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.profile_profile_player_fragment, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        uploadingDialog = new Dialog(getContext());
        uploadingDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        uploadingDialog.setContentView(R.layout.custom_loading_layout);
        uploadingDialog.setCancelable(false);

        sqLiteHelper = new SQLiteHelper(getContext());
        id = view.findViewById(R.id.txtIDPlayer);
        txtName = view.findViewById(R.id.txtName);
        txtAge = view.findViewById(R.id.txtAge);
        txtHeight = view.findViewById(R.id.txtHeight);
        txtWeight = view.findViewById(R.id.txtWeight);
        txtEmail = view.findViewById(R.id.txtEmail);
        txtPhone = view.findViewById(R.id.txtPhone);
        listPosition = view.findViewById(R.id.listPosition);

        avatar = view.findViewById(R.id.imgAvatar);

        avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, RESULT_LOAD_IMG);

            }
        });

        update = view.findViewById(R.id.btnUpdate);
        addItemSpinner();
        update.setOnClickListener(this);

        sharedPref =getContext().getSharedPreferences("sessionUser", Context.MODE_PRIVATE);
        getPlayer(sharedPref.getString("idPlayer",""));

    }

    @Override
    public void onClick(View v) {
        if (v == update) {
            final Player playerUpdate = setPlayer();

            repoFireStorePlayer.updatePlayer(playerUpdate, new OnFirebaseUpdate() {
                @Override
                public void updateSqlite(Player player) {
                    long addedId  = sqLiteHelper.updatePlayer(playerUpdate);
                    ObserverManager.getInstance().data.setValue((int) 1);
                    if (addedId >= 0) {
                        Toast.makeText(getContext(), "Updated " + addedId, Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(getContext(), "Updated failed", Toast.LENGTH_SHORT).show();
                    }
                    uploadImageToFireBase();
                }
            });
        }

    }

    public void addItemSpinner() {
        String[] list = new String[]{"Thủ môn", "Hậu vệ", "Tiền vệ", "Tiền đạo"};
        ArrayAdapter<String> data = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, list);
        data.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        listPosition.setAdapter(data);
    }

    @Override
    public void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            try {
                imageUri = data.getData();
                final InputStream imageStream = getContext().getContentResolver().openInputStream(imageUri);
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                avatar.setImageBitmap(selectedImage);

                Cursor returnCursor =
                        getContext().getContentResolver().query(imageUri, null, null, null, null);
                int nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
                int sizeIndex = returnCursor.getColumnIndex(OpenableColumns.SIZE);
                returnCursor.moveToFirst();
                urlFirebase ="/player/" + returnCursor.getString(nameIndex);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_LONG).show();
            }

        } else {
            Toast.makeText(getContext(), "You haven't picked Image", Toast.LENGTH_LONG).show();
        }
    }

    public void uploadImageToFireBase() {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        final Player player = setPlayer();
        UploadTask task = storage.getReference().child(urlFirebase).putFile(imageUri);
        // hien ra dialog
        uploadingDialog.show();

        task.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                uploadingDialog.cancel();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // huy dialog hien loi
                uploadingDialog.cancel();
                Toast.makeText(getContext(), "Failed", Toast.LENGTH_SHORT).show();
                Log.e("upload failed", "onFailure: ", e);
            }
        });
    }

    public Player setPlayer() {
        Player player = new Player();
        player.setId(id.getText().toString());
        player.setAge(Integer.parseInt(txtAge.getText().toString()));
        player.setName(txtName.getText().toString());
        player.setHeight(Double.parseDouble(txtHeight.getText().toString()));
        player.setWeight(Double.parseDouble(txtWeight.getText().toString()));
        player.setPhone(txtPhone.getText().toString());
        player.setEmail(txtEmail.getText().toString());
        player.setPosition(listPosition.getSelectedItem().toString());
        player.setUrlAvatar(urlFirebase);
        return player;
    }


    public void getPlayer(String idPlayer){
        Player playerExisted = sqLiteHelper.getPlayerbyID(idPlayer);
        id.setText(playerExisted.getId());
        txtName.setText(playerExisted.getName());
        txtAge.setText("" + playerExisted.getAge());
        txtHeight.setText("" + playerExisted.getHeight());
        txtWeight.setText("" + playerExisted.getWeight());
        txtEmail.setText(playerExisted.getEmail());
        txtPhone.setText(playerExisted.getPhone());
        urlFirebase = playerExisted.getUrlAvatar();
        listPosition.setSelection(3);
        if (playerExisted.getPosition().equals("Thủ môn")) {
            listPosition.setSelection(0);
        } else if (playerExisted.getPosition().equals("Hậu vệ")) {
            listPosition.setSelection(1);
        } else if (playerExisted.getPosition().equals("Tiền vệ")) {
            listPosition.setSelection(2);
        } else {
            listPosition.setSelection(3);
        }
        String url = playerExisted.getUrlAvatar(); // /Quanao/1234.jpg
        String[] files = url.split("/");
        String fileName = files[2];
        File folder = new File(getContext().getCacheDir(), files[1]);
        if (!folder.exists()) folder.mkdir();
        final File file = new File(folder, fileName);
        if (file.exists()) {
            avatar.setImageURI(Uri.fromFile(file));
            imageUri=(Uri.fromFile(file));
        } else {
            storage.getReference().child(url).getFile(file)
                    .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                            avatar.setImageURI(Uri.fromFile(file));
                            imageUri=(Uri.fromFile(file));
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });
        }
    }

}
