package com.example.myclub.frangment.origin;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
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
import androidx.lifecycle.Observer;

import com.example.myclub.Interface.OnFirebaseDelete;
import com.example.myclub.Interface.OnFirebaseUpdate;
import com.example.myclub.R;
import com.example.myclub.Interface.OnFirebaseInsert;
import com.example.myclub.model.Player;
import com.example.myclub.repository.SQLiteHelper;
import com.example.myclub.main.ObserverManager;
import com.example.myclub.repository.RepoFireStorePlayer;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

import static com.example.myclub.R.*;

public class FragmentManagePlayer extends Fragment implements View.OnClickListener {
    private EditText txtAge, txtName, txtHeight, txtWeight, txtPhone, txtEmail,txtPassword;
    private TextView id;
    private String urlAvatar, urlFirebase = "";
    private ImageView avatar;
    private Spinner listPosition, listPlayerExisted;
    private Button add, update, remove, btnGetByID,reset;
    private SQLiteHelper sqLiteHelper;
    public static int RESULT_LOAD_IMG = 1012;
    private Dialog uploadingDialog;
    private FirebaseAuth mAuth;
    Uri imageUri;
    private FirebaseStorage storage = FirebaseStorage.getInstance();
    private RepoFireStorePlayer repoFireStorePlayer = RepoFireStorePlayer.getInstance();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(layout.profile_manage_player_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        sqLiteHelper = new SQLiteHelper(getContext());
        id = view.findViewById(R.id.txtIDPlayer);
        txtName = view.findViewById(R.id.txtName);
        txtAge = view.findViewById(R.id.txtAge);
        txtHeight = view.findViewById(R.id.txtHeight);
        txtWeight = view.findViewById(R.id.txtWeight);
        txtEmail = view.findViewById(R.id.txtEmail);
        txtPhone = view.findViewById(R.id.txtPhone);
        txtPassword = view.findViewById(R.id.txtPassword);
        listPosition = view.findViewById(R.id.listPosition);
        listPlayerExisted = view.findViewById(R.id.listPlayerExisted);
        avatar = view.findViewById(R.id.imgAvatar);

        uploadingDialog = new Dialog(getContext());
        uploadingDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        uploadingDialog.setContentView(layout.custom_loading_layout);
        uploadingDialog.setCancelable(false);

        avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, RESULT_LOAD_IMG);

            }
        });

        add = view.findViewById(R.id.btnAdd);
        update = view.findViewById(R.id.btnUpdate);
        remove = view.findViewById(R.id.btnRemove);
        reset = view.findViewById(R.id.btnReset);
        btnGetByID = view.findViewById(R.id.btnGetByID);
        addItemSpinner();

        add.setOnClickListener(this);
        reset.setOnClickListener(this);
        update.setOnClickListener(this);
        remove.setOnClickListener(this);
        btnGetByID.setOnClickListener(this);
        ObserverManager.getInstance().data.observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                addItemSpinner();
            }
        });
    }

    @Override
    public void onClick(View v) {

        if (v == add) {
            Player p = setPlayer();
            repoFireStorePlayer.insertPlayer(p, new OnFirebaseInsert() {
                @Override
                public void insertSqlite(Player player) {
                    uploadImageToFireBase();
                    FirebaseAuth.getInstance().createUserWithEmailAndPassword(txtEmail.getText().toString(),txtPassword.getText().toString());
                    long addedId = sqLiteHelper.insertPlayer(player);
                    if (addedId >= 0) {
                        Toast.makeText(getContext(), "Inserted " + addedId, Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(getContext(), "Inserted failed", Toast.LENGTH_SHORT).show();
                    }
                    ObserverManager.getInstance().data.setValue((int) 1);
                }
            });


    }

        if (v == update) {
            final Player playerUpdate = setPlayer();
            repoFireStorePlayer.updatePlayer(playerUpdate, new OnFirebaseUpdate() {
                @Override
                public void updateSqlite(Player player) {
                    long addedId  = sqLiteHelper.updatePlayer(playerUpdate);
                    if (addedId >= 0) {
                        Toast.makeText(getContext(), "Updated " + addedId, Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(getContext(), "Updated failed", Toast.LENGTH_SHORT).show();
                    }
                    ObserverManager.getInstance().data.setValue((int) 1);
                }
            });
            uploadImageToFireBase();
            updateAccountAuth();
        }

        if (v == remove) {
            uploadingDialog.show();
            repoFireStorePlayer.deletePlayer(id.getText().toString(), new OnFirebaseDelete() {
                @Override
                public void deleteSqlite(String id) {
                    long count  = sqLiteHelper.deletePlayer(id);
                    if (count >= 0) {
                        Toast.makeText(getContext(), "Deleted " + count, Toast.LENGTH_SHORT).show();
                        uploadingDialog.cancel();
                    } else {
                        Toast.makeText(getContext(), "Deleted failed", Toast.LENGTH_SHORT).show();
                        uploadingDialog.cancel();
                    }
                    ObserverManager.getInstance().data.setValue((int) 1);
                }
            });
        }

        if (v == btnGetByID) {
            Player playerExisted = sqLiteHelper.getPlayerbyName(listPlayerExisted.getSelectedItem().toString());
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
                // load vao trong imageview
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

        if (v == reset) {
            id.setText("");
            txtName.setText("");
            txtAge.setText("");
            txtHeight.setText("");
            txtWeight.setText("");
            txtEmail.setText("");
            txtPhone.setText("");
            txtPassword.setText("");
            avatar.refreshDrawableState();
        }

    }

    public void addItemSpinner() {
        String[] list = new String[]{"Thủ môn", "Hậu vệ", "Tiền vệ", "Tiền đạo"};
        ArrayAdapter<String> data = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, list);
        data.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        listPosition.setAdapter(data);

        //Player existed
        List<Player> listPlayer = sqLiteHelper.getAllPlayer();
        String[] listname = new String[listPlayer.size()];
        for (int i = 0; i < listname.length; i++) {
            listname[i] = listPlayer.get(i).getName();
        }
        ArrayAdapter<String> data2 = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, listname);
        data2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        listPlayerExisted.setAdapter(data2);
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

    public void updateAccountAuth(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        user.updateEmail(txtEmail.getText().toString())
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getContext(),"update tai khoan thanh cong",Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        user.updateEmail(txtEmail.getText().toString())
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getContext(),"update tai khoan thanh cong",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

}
